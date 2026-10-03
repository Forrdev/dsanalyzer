package com.sappyoak.dsanalyzer.app.scripts

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
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

/**
 * Firelink only checks the flag; Izalith registers it; and a span somewhere else covers a flag
 * nothing names directly. Between them, the three cases the old one-script search could not answer
 */
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
    selected = FIRELINK,
    flagIndex = INDEX
)

private fun follow(
    state: ScriptsState,
    flagId: Int,
    seenIn: MapId? = null
): Transition<ScriptsState, ScriptsEffect> =
    reduceScripts(state, ScriptsMessage.FlagRequested(flagId, seenIn))


class FlagSearchTest : FunSpec({
    test("a flag is followed to what writes it, not to where it was seen") {
        val transition = follow(READY, FLAG, seenIn = FIRELINK_MAP)

        assertSoftly {
            transition.state.selected shouldBe IZALITH
            transition.state.pending shouldBe 900L
            transition.state.findings?.chosen?.label shouldBe "RegisterBonfire"
            transition.effects shouldBe listOf(ScriptsEffect.LoadScript(INSTALLATION, IZALITH))
        }
    }

    test("the places it did not go are kept, because they are usually the interesting part") {
        val findings = checkNotNull(follow(READY, FLAG, seenIn = FIRELINK_MAP).state.findings)

        assertSoftly {
            findings.references.map { it.script } shouldBe listOf(IZALITH, FIRELINK, ScriptId.Common)
            findings.others.map { it.script } shouldBe listOf(FIRELINK, ScriptId.Common)
        }
    }

    /** Where nothing writes it, the map the flag was seen in comes before the rest */
    test("among readers, the map it was seen in wins") {
        val readersOnly = FlagIndex.of(
            found = listOf(
                found(ScriptId.Common, 800L, FlagTarget.One(FLAG), FlagAccess.Reads, "AwaitFlagState"),
                found(FIRELINK, 700L, FlagTarget.One(FLAG), FlagAccess.Reads, "IfFlagState")
            ),
            scripts = 2
        )

        val transition = follow(READY.copy(flagIndex = readersOnly), FLAG, seenIn = FIRELINK_MAP)

        transition.state.findings?.chosen?.script shouldBe FIRELINK
    }

    /** A flag set as part of a range was invisible before, however many scripts were searched */
    test("a flag inside a span is found through it") {
        val transition = follow(READY, SPANNED_FLAG, seenIn = FIRELINK_MAP)

        assertSoftly {
            transition.state.findings?.chosen?.label shouldBe "SetFlagRangeState"
            transition.state.findings?.chosen?.viaSpan shouldBe true
            transition.state.selected shouldBe IZALITH
        }
    }

    /**
     * Finding nothing is an answer: the game sets plenty of flags itself. It is only an honest
     * answer if it says how much it read, and what it could not
     */
    test("a flag no script names says so, and admits what it could not read") {
        val transition = follow(READY, UNKNOWN_FLAG, seenIn = FIRELINK_MAP)

        assertSoftly {
            transition.state.findings?.chosen shouldBe null
            transition.state.problem shouldContain "No script names flag $UNKNOWN_FLAG"
            transition.state.problem shouldContain "searched 3"
            transition.state.problem shouldContain "1 flag references are passed in by their callers"
            transition.effects shouldBe emptyList()
        }
    }

    test("following a flag before the index is ready waits for it rather than guessing") {
        val building = READY.copy(flagIndex = FlagIndex.Empty, indexing = true)

        val transition = follow(building, FLAG, seenIn = FIRELINK_MAP)

        assertSoftly {
            transition.state.pendingFlag shouldBe FlagRequest(FLAG, FIRELINK_MAP)
            transition.state.problem shouldContain "Reading every script"
            transition.effects shouldBe emptyList()
        }
    }

    test("the index arriving answers the follow that was waiting on it") {
        val waiting = follow(READY.copy(flagIndex = FlagIndex.Empty, indexing = true), FLAG, FIRELINK_MAP).state

        val transition = reduceScripts(waiting, ScriptsMessage.FlagsIndexed(INDEX, unreadable = emptyList()))

        assertSoftly {
            transition.state.indexing shouldBe false
            transition.state.pendingFlag shouldBe null
            transition.state.selected shouldBe IZALITH
            transition.state.findings?.chosen?.label shouldBe "RegisterBonfire"
        }
    }

    test("the index arriving with nothing waiting changes nothing else") {
        val transition = reduceScripts(
            READY.copy(flagIndex = FlagIndex.Empty, indexing = true),
            ScriptsMessage.FlagsIndexed(INDEX, unreadable = listOf(IZALITH))
        )

        assertSoftly {
            transition.state.indexing shouldBe false
            transition.state.unreadable shouldBe listOf(IZALITH)
            transition.state.findings shouldBe null
            transition.effects shouldBe emptyList()
        }
    }

    test("opening a workspace reads every script once rather than on demand") {
        val catalog = listOf(ScriptId.Common, FIRELINK, IZALITH)

        val transition = reduceScripts(
            ScriptsState(installation = INSTALLATION),
            ScriptsMessage.CatalogLoaded(catalog)
        )

        assertSoftly {
            transition.state.indexing shouldBe true
            transition.effects shouldBe listOf(
                ScriptsEffect.LoadScript(INSTALLATION, ScriptId.Common),
                ScriptsEffect.IndexFlags(INSTALLATION, catalog)
            )
        }
    }
})