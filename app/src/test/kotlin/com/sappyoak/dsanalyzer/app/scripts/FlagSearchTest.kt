package com.sappyoak.dsanalyzer.app.scripts

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.formats.emevd.ArgData
import com.sappyoak.dsanalyzer.formats.emevd.Instruction
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgDefinition
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgType
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgValue
import com.sappyoak.dsanalyzer.formats.emevd.emedf.DecodedArg
import com.sappyoak.dsanalyzer.formats.emevd.emedf.DecodedInstruction
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Emedf
import com.sappyoak.dsanalyzer.game.world.WorldRef
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.title

private const val FLAG = 11_010_902
private const val OTHER_FLAG = 11_020_000

private val PARISH = ScriptId.Of(checkNotNull(MapId.parse("m10_02_00_00")))

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

private fun contents(decoded: Map<Long, List<DecodedInstruction>>) = ScriptContents(
    script = PARISH,
    events = decoded.keys.map { EventSummary(WorldRef.ScriptEvent(PARISH, it), it, null, instructionCount = 1) },
    linkedFiles = emptyList(),
    definitions = Emedf.Empty,
    decoded = decoded
)

private val SETS_FLAG = contents(
    mapOf(
        700L to listOf(instruction(PLAIN_ARG to 1L)),
        800L to listOf(instruction(FLAG_ARG to FLAG.toLong(), PLAIN_ARG to 1L))
    )
)

private fun follow(state: ScriptsState, flagId: Int): Transition<ScriptsState, ScriptsEffect> =
    reduceScripts(state, ScriptsMessage.FlagRequested(flagId))

class FlagSearchTest : FunSpec({
    test("a flag is found in the event whose instruction names it") {
        assertSoftly {
            SETS_FLAG.eventUsing(FLAG) shouldBe 800L
            SETS_FLAG.eventUsing(OTHER_FLAG) shouldBe null
        }
    }

    test("an argument that is not a flag reference is not matched on its value") {
        contents(mapOf(700L to listOf(instruction(PLAIN_ARG to FLAG.toLong())))).eventUsing(FLAG) shouldBe null
    }

    test("following a flag focuses the event that names it") {
        val state = ScriptsState(selected = PARISH, contents = SETS_FLAG)

        val transition = follow(state, FLAG)

        assertSoftly {
            transition.state.focused shouldBe 800L
            transition.state.problem shouldBe null
            transition.effects shouldBe emptyList()
        }
    }

    test("a flag this script does not name says which script was searched") {
        val state = ScriptsState(selected = PARISH, contents = SETS_FLAG)

        val transition = follow(state, OTHER_FLAG)

        assertSoftly {
            transition.state.focused shouldBe null
            transition.state.problem shouldBe "Flag $OTHER_FLAG is not named by ${PARISH.title}"
        }
    }

    test("following a flag with no script open says so rather than loading one") {
        val transition = follow(ScriptsState(), FLAG)

        assertSoftly {
            transition.state.problem shouldBe "No script is open"
            transition.effects shouldBe emptyList()
        }
    }
})

