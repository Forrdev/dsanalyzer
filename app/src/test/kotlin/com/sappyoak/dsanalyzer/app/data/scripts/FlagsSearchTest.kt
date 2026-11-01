package com.sappyoak.dsanalyzer.app.data.scripts

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.nio.file.Path

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagTarget
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagUse
import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

private const val FLAG = 11_010_902
private const val SPANNED_FLAG = 11_020_050
private const val UNKNOWN_FLAG = 99_999

private val FIRELINK_MAP = checkNotNull(MapId.parse("m10_02_00_00"))
private val FIRELINK = ScriptId.Of(FIRELINK_MAP)

private val IZALITH = ScriptId.Of(MapId.of(14, 1))

private val INSTALLATION = Installation(
    id = InstallationId("ptde"),
    root = Path.of("game"),
    executable = Path.of("game", "DARKSOULS.exe"),
    build = GameBuild(GameEdition.PrepareToDie)
)

private fun found(
    script: ScriptId,
    eventId: Long,
    target: FlagTarget,
    access: FlagAccess,
    label: String
) = FoundFlagUse(script, eventId, FlagUse(target, access, label))

private val INDEX = FlagIndex.of(
    found = listOf(
        found(FIRELINK, 700L, FlagTarget.One(FLAG), FlagAccess.Reads, "IfFlagState"),
        found(ScriptId.Common, 800L, FlagTarget.One(FLAG), FlagAccess.Reads, "AwaitFlagState"),
        found(IZALITH, 900L, FlagTarget.One(FLAG), FlagAccess.Writes, "RegisterBonfire"),
        found(IZALITH, 950L, FlagTarget.Span(11_020_000, 11_020_099), FlagAccess.Writes, "SetFlagRangeState"),
        found(FIRELINK, 960L, FlagTarget.FromCaller, FlagAccess.Writes, "SetFlagState")
    ),
    scripts = 3
)

private val READY = ScriptsState(
    installation = INSTALLATION,
    scripts = listOf(ScriptId.Common, FIRELINK, IZALITH),
    flagIndex = INDEX
)

private fun follow(
    state: ScriptsState,
    flagId: Int,
    seenIn: MapId? = null
): Transition<ScriptsState, ScriptsEffect> =
    reduceScripts(state, ScriptsMessage.FlagRequested(flagId, seenIn))


class FlagSearchTest : FunSpec({
    test("what writes a flag ranks above where it was seen, and nothing is navigated to") {
        val (state, effects) = follow(READY, FLAG, seenIn = FIRELINK_MAP)
        val first = checkNotNull(state.findings?.references?.firstOrNull())

        assertSoftly {
            first.script shouldBe IZALITH
            first.eventId shouldBe 900L
            first.label shouldBe "RegisterBonfire"
            withClue("searching is all this does now; the pane shows the result") {
                effects.shouldBeEmpty()
            }
        }
    }

    test("every reference is kept, because the others are usually the interesting part") {
        val findings = checkNotNull(follow(READY, FLAG, seenIn = FIRELINK_MAP).state.findings)

        assertSoftly {
            findings.flagId shouldBe FLAG
            findings.references.map { it.script } shouldBe listOf(IZALITH, FIRELINK, ScriptId.Common)
        }
    }

    /** Where nothing writes it, the map the flag was seen in comes before the rest */
    test("among readers, the map it was seen in ranks first") {
        val readersOnly = FlagIndex.of(
            found = listOf(
                found(ScriptId.Common, 800L, FlagTarget.One(FLAG), FlagAccess.Reads, "AwaitFlagState"),
                found(FIRELINK, 700L, FlagTarget.One(FLAG), FlagAccess.Reads, "IfFlagState")
            ),
            scripts = 2
        )

        val state = follow(READY.copy(flagIndex = readersOnly), FLAG, seenIn = FIRELINK_MAP).state

        state.findings?.references?.firstOrNull()?.script shouldBe FIRELINK
    }

    /** A flag set as part of a range was invisible before, however many scripts were searched */
    test("a flag inside a span is found through it") {
        val state = follow(READY, SPANNED_FLAG, seenIn = FIRELINK_MAP).state
        val first = checkNotNull(state.findings?.references?.firstOrNull())

        assertSoftly {
            first.label shouldBe "SetFlagRangeState"
            first.viaSpan shouldBe true
            first.script shouldBe IZALITH
        }
    }

    test("a flag no script names says so, and admits what it could not read") {
        val (state, effects) = follow(READY, UNKNOWN_FLAG, seenIn = FIRELINK_MAP)

        assertSoftly {
            withClue("findings exist and are empty, which is not the same as not having looked") {
                state.findings?.flagId shouldBe UNKNOWN_FLAG
                state.findings?.references?.shouldBeEmpty()
            }
            state.problem shouldContain "No script names flag $UNKNOWN_FLAG"
            state.problem shouldContain "searched 3"
            state.problem shouldContain "1 flag references are passed in by their callers"
            effects.shouldBeEmpty()
        }
    }

    test("asking before the index is ready says what is happening rather than guessing") {
        val building = READY.copy(flagIndex = FlagIndex.Empty, indexing = true)

        val (state, effects) = follow(building, FLAG, seenIn = FIRELINK_MAP)

        assertSoftly {
            withClue("no findings rather than empty findings: nothing has been looked at yet") {
                state.findings shouldBe null
            }
            state.problem shouldContain "Reading every script"
            effects.shouldBeEmpty()
        }
    }

    test("the index arriving does not answer anything by itself") {
        val waiting = follow(READY.copy(flagIndex = FlagIndex.Empty, indexing = true), FLAG, FIRELINK_MAP).state

        val (state, effects) = reduceScripts(
            waiting,
            ScriptsMessage.FlagsIndexed(INDEX, unreadable = emptyList())
        )

        assertSoftly {
            state.indexing shouldBe false
            state.flagIndex.built shouldBe true
            withClue("the view re-asks once built flips, which is what replaced replaying it here") {
                state.findings shouldBe null
            }
            effects.shouldBeEmpty()
        }
    }

    test("and re-asking once it is built gives the answer") {
        val built = reduceScripts(
            READY.copy(flagIndex = FlagIndex.Empty, indexing = true),
            ScriptsMessage.FlagsIndexed(INDEX, unreadable = listOf(IZALITH))
        ).state

        val state = follow(built, FLAG, seenIn = FIRELINK_MAP).state

        assertSoftly {
            state.unreadable shouldBe listOf(IZALITH)
            state.findings?.references?.firstOrNull()?.label shouldBe "RegisterBonfire"
        }
    }
})
