package com.sappyoak.dsanalyzer.app.scripts

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.nio.file.Path

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.formats.emevd.ArgData
import com.sappyoak.dsanalyzer.formats.emevd.Instruction
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgDefinition
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgType
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgValue
import com.sappyoak.dsanalyzer.formats.emevd.emedf.DecodedArg
import com.sappyoak.dsanalyzer.formats.emevd.emedf.DecodedInstruction
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Emedf
import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.world.WorldRef
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.title

private const val FLAG = 11_010_902
private const val OTHER_FLAG = 11_020_000

private val FIRELINK_MAP = checkNotNull(MapId.parse("m10_02_00_00"))
private val FIRELINK = ScriptId.Of(FIRELINK_MAP)

/** Somewhere else entirely, standing in for whatever the Scripts tab was last left on */
private val DEPTHS = ScriptId.Of(MapId.of(10, 0))

/** An argument the definitions name as a flag, which is what makes it followable */
private val FLAG_ARG = ArgDefinition(
    name = "Target Event Flag ID",
    type = ArgType.Int,
    enumName = null,
    default = 0.0
)

private val PLAIN_ARG = ArgDefinition(name = "State", type = ArgType.UByte, enumName = null, default = 0.0)

private fun instruction(vararg args: Pair<ArgDefinition, Long>) = DecodedInstruction(
    instruction = Instruction(bank = 2003, id = 2, args = ArgData.Empty, layerMask = null),
    definition = null,
    args = args.map { (definition, value) -> DecodedArg(definition, ArgValue.Literal(value)) }
)

private fun contents(
    script: ScriptId = FIRELINK,
    decoded: Map<Long, List<DecodedInstruction>>
) = ScriptContents(
    script = script,
    events = decoded.keys.map { EventSummary(WorldRef.ScriptEvent(script, it), it, null, instructionCount = 1) },
    linkedFiles = emptyList(),
    definitions = Emedf.Empty,
    decoded = decoded
)

private fun setsFlag(script: ScriptId, flagId: Int, inEvent: Long) = contents(
    script,
    mapOf(
        700L to listOf(instruction(PLAIN_ARG to 1L)),
        inEvent to listOf(instruction(FLAG_ARG to flagId.toLong(), PLAIN_ARG to 1L))
    )
)

private val SETS_FLAG = setsFlag(FIRELINK, FLAG, inEvent = 800L)

/** The same flag, but set by common instead, which is where plenty of them really are set */
private val COMMON_SETS_FLAG = setsFlag(ScriptId.Common, OTHER_FLAG, inEvent = 900L)

private val NAMES_NOTHING = contents(FIRELINK, mapOf(700L to listOf(instruction(PLAIN_ARG to 1L))))

private val INSTALLATION = Installation(
    id = InstallationId("ptde"),
    root = Path.of("game"),
    executable = Path.of("game", "DARKSOULS.exe"),
    build = GameBuild(GameEdition.PrepareToDie)
)

/** A workspace with both scripts available and some other map open, as the Scripts tab leaves it */
private val OPEN_ELSEWHERE = ScriptsState(
    installation = INSTALLATION,
    scripts = listOf(ScriptId.Common, DEPTHS, FIRELINK),
    selected = DEPTHS,
    contents = contents(DEPTHS, mapOf(100L to listOf(instruction(PLAIN_ARG to 1L))))
)

private fun follow(
    state: ScriptsState,
    flagId: Int,
    seenIn: MapId? = null
): Transition<ScriptsState, ScriptsEffect> =
    reduceScripts(state, ScriptsMessage.FlagRequested(flagId, seenIn))

private fun load(
    state: ScriptsState,
    loaded: ScriptContents
): Transition<ScriptsState, ScriptsEffect> =
    reduceScripts(state, ScriptsMessage.ScriptLoaded(loaded))

class FlagSearchTest : FunSpec({
    test("a flag is found in the event whose instruction names it") {
        assertSoftly {
            SETS_FLAG.eventUsing(FLAG) shouldBe 800L
            SETS_FLAG.eventUsing(OTHER_FLAG) shouldBe null
        }
    }

    test("an argument that is not a flag reference is not matched on its value") {
        contents(decoded = mapOf(700L to listOf(instruction(PLAIN_ARG to FLAG.toLong()))))
            .eventUsing(FLAG) shouldBe null
    }

    test("following a flag focuses the event that names it") {
        val state = ScriptsState(selected = FIRELINK, contents = SETS_FLAG)

        val transition = follow(state, FLAG)

        assertSoftly {
            transition.state.focused shouldBe 800L
            transition.state.problem shouldBe null
            transition.effects shouldBe emptyList()
        }
    }

    test("a flag this script does not name says which script was searched") {
        val state = ScriptsState(selected = FIRELINK, contents = SETS_FLAG)

        val transition = follow(state, OTHER_FLAG)

        assertSoftly {
            transition.state.focused shouldBe null
            transition.state.problem shouldBe "Flag $OTHER_FLAG is not named by ${FIRELINK.title}"
        }
    }

    test("following a flag with no script open says so rather than loading one") {
        val transition = follow(ScriptsState(), FLAG)

        assertSoftly {
            transition.state.problem shouldBe "No script is open"
            transition.effects shouldBe emptyList()
        }
    }

    test("a flag seen in a map opens that map's script rather than the one already open") {
        val transition = follow(OPEN_ELSEWHERE, FLAG, seenIn = FIRELINK_MAP)

        assertSoftly {
            transition.state.selected shouldBe FIRELINK
            transition.state.loading shouldBe true
            transition.state.pendingFlag?.flagId shouldBe FLAG
            transition.state.problem shouldBe null
            transition.effects shouldBe listOf(ScriptsEffect.LoadScript(INSTALLATION, FIRELINK))
        }
    }

    test("the search resumes against the script it was waiting for") {
        val requested = follow(OPEN_ELSEWHERE, FLAG, seenIn = FIRELINK_MAP).state

        val transition = load(requested, SETS_FLAG)

        assertSoftly {
            transition.state.focused shouldBe 800L
            transition.state.pendingFlag shouldBe null
            transition.state.problem shouldBe null
            transition.effects shouldBe emptyList()
        }
    }

    /** Plenty of flags are set by common rather than by the map the character is standing in */
    test("a flag the map does not name is looked for in common next") {
        val requested = follow(OPEN_ELSEWHERE, OTHER_FLAG, seenIn = FIRELINK_MAP).state

        val transition = load(requested, NAMES_NOTHING)

        assertSoftly {
            transition.state.selected shouldBe ScriptId.Common
            transition.state.pendingFlag?.flagId shouldBe OTHER_FLAG
            transition.effects shouldBe listOf(ScriptsEffect.LoadScript(INSTALLATION, ScriptId.Common))
        }
    }

    test("a flag common does name is focused there") {
        val inMap = follow(OPEN_ELSEWHERE, OTHER_FLAG, seenIn = FIRELINK_MAP).state
        val inCommon = load(inMap, NAMES_NOTHING).state

        val transition = load(inCommon, COMMON_SETS_FLAG)

        assertSoftly {
            transition.state.selected shouldBe ScriptId.Common
            transition.state.focused shouldBe 900L
            transition.state.pendingFlag shouldBe null
            transition.state.problem shouldBe null
        }
    }

    test("a flag neither names is reported against both, not against whatever was open") {
        val inMap = follow(OPEN_ELSEWHERE, OTHER_FLAG, seenIn = FIRELINK_MAP).state
        val inCommon = load(inMap, NAMES_NOTHING).state

        val transition = load(inCommon, contents(ScriptId.Common, mapOf(1L to emptyList())))

        assertSoftly {
            transition.state.pendingFlag shouldBe null
            transition.state.problem shouldBe
                    "Flag $OTHER_FLAG is not named by Firelink Shrine or Common"
        }
    }

    test("choosing a script by hand abandons a search that was waiting") {
        val requested = follow(OPEN_ELSEWHERE, FLAG, seenIn = FIRELINK_MAP).state

        val transition = reduceScripts(requested, ScriptsMessage.ScriptSelected(DEPTHS))

        transition.state.pendingFlag shouldBe null
    }
})