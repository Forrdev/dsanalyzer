package com.sappyoak.dsanalyzer.game.world.flags

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.formats.emevd.ArgData
import com.sappyoak.dsanalyzer.formats.emevd.Emevd
import com.sappyoak.dsanalyzer.formats.emevd.EventCall
import com.sappyoak.dsanalyzer.formats.emevd.Instruction
import com.sappyoak.dsanalyzer.formats.emevd.StringTable
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgDefinition
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgType
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgValue
import com.sappyoak.dsanalyzer.formats.emevd.emedf.DecodedArg
import com.sappyoak.dsanalyzer.formats.emevd.emedf.DecodedInstruction
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Emedf
import com.sappyoak.dsanalyzer.formats.emevd.emedf.InstructionDefinition
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Opcode
import com.sappyoak.dsanalyzer.game.data.DecodedScript
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.index.EventCallIndex
import com.sappyoak.dsanalyzer.game.world.scripts.index.EventKey
import com.sappyoak.dsanalyzer.game.world.scripts.index.FoundCall

private const val TEMPLATE = 900L
private const val CALLER = 0L
private const val HEADER = 8

private val SET_FLAG = InstructionDefinition(
    Opcode(2003, 2),
    "Set Event Flag",
    listOf(ArgDefinition("Event Flag ID", ArgType.UInt, null, 0.0), ArgDefinition("Flag State", ArgType.UByte, "ON/OFF", 0.0))
)

/** 'Set Event Flag', its flag passed in by the caller and its state written in */
private val SET_PASSED_FLAG = DecodedInstruction(
    Instruction(2003, 2, ArgData(ByteArray(8)), null),
    SET_FLAG,
    listOf(DecodedArg(SET_FLAG.args[0], ArgValue.FromCaller(0, 4)), DecodedArg(SET_FLAG.args[1], ArgValue.Literal(1)))
)

private fun call(slot: Int, flag: Int): FoundCall {
    val args = ByteBuffer.allocate(HEADER + 4).order(ByteOrder.LITTLE_ENDIAN).putInt(slot).putInt(TEMPLATE.toInt()).putInt(flag)
    return FoundCall(ScriptId.Common, CALLER, EventCall(slot, TEMPLATE, ArgData(args.array())))
}

private val SCRIPT = DecodedScript(
    ScriptId.Common,
    Emevd(emptyList(), emptyList(), StringTable(ByteArray(0))),
    Emedf(emptyMap(), emptyMap()),
    mapOf(TEMPLATE to listOf(SET_PASSED_FLAG))
)

class BoundEventsTest : FunSpec({
    test("an event its callers hand a flag runs once per call, each with that call's flag") {
        val calls = EventCallIndex.of(
            found = listOf(call(0, 11010100), call(1, 11010200), call(2, 11010200)),
            needs = mapOf(EventKey(ScriptId.Common, TEMPLATE) to 4),
            scripts = 1
        )

        SCRIPT.bound(calls).map { it.instructions.single().int(0) } shouldContainExactlyInAnyOrder
                listOf(11010100, 11010200, 11010200)
    }

    test("with no index to read callers from, the event runs once with the flag unknown") {
        SCRIPT.bound(EventCallIndex.Empty).map { it.instructions.single().int(0) } shouldContainExactlyInAnyOrder listOf(null)
    }
})
