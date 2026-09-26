package com.sappyoak.dsanalyzer.formats.emevd.emedf

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.emevd.ArgData
import com.sappyoak.dsanalyzer.formats.emevd.Instruction
import com.sappyoak.dsanalyzer.formats.emevd.Parameter
import com.sappyoak.dsanalyzer.formats.emevd.RestBehavior
import com.sappyoak.dsanalyzer.formats.emevd.ScriptEvent

// A definition shaped like the real "END IF Event Flag": three bytes then an int, so the int
// only starts once the reader has skipped a byte of padding.
private val END_IF_FLAG = InstructionDefinition(
    opcode = Opcode(1003, 2),
    name = "END IF Event Flag",
    args = listOf(
        ArgDefinition("End Type", ArgType.UByte, "Event End Type", 0.0),
        ArgDefinition("Flag State", ArgType.UByte, "ON/OFF", 1.0),
        ArgDefinition("Flag Type", ArgType.UByte, null, 0.0),
        ArgDefinition("Flag ID", ArgType.Int, null, 0.0)
    )
)

private val SET_FLAG = InstructionDefinition(
    opcode = Opcode(2003, 2),
    name = "Set Event Flag",
    args = listOf(
        ArgDefinition("Flag ID", ArgType.Int, null, 0.0),
        ArgDefinition("State", ArgType.UByte, "ON/OFF", 1.0)
    )
)

private fun instruction(definition: InstructionDefinition, vararg bytes: Int) =
    Instruction(definition.opcode.bank, definition.opcode.id, ArgData(bytes.map { it.toByte() }.toByteArray()), null)

private fun literals(decoded: DecodedInstruction) = decoded.args.map { it.value }

class DecodeTest : FunSpec({
    test("arguments start at a multiple of their own width") {
        val decoded = instruction(END_IF_FLAG, 0, 1, 0, 0, 0x10, 0x27, 0, 0).decode(END_IF_FLAG)
        assertSoftly {
            literals(decoded) shouldBe listOf(
                ArgValue.Literal(0), ArgValue.Literal(1), ArgValue.Literal(0), ArgValue.Literal(10000)
            )
            decoded.sizeMismatch shouldBe false
            END_IF_FLAG.packedSize shouldBe 8
        }
    }

    test("an argument left off the end falls back to the definition's default") {
        val decoded = instruction(SET_FLAG, 0x20, 0x4e, 0, 0).decode(SET_FLAG)
        literals(decoded) shouldBe listOf(ArgValue.Literal(20000), ArgValue.Omitted)
    }

    test("an argument a caller writes over reads as coming from the caller") {
        val event = ScriptEvent(
            id = 90005,
            restBehavior = RestBehavior.Default,
            instructions = listOf(instruction(SET_FLAG, 0, 0, 0, 0, 1)),
            parameters = listOf(Parameter(instructionIndex = 0, targetStartByte = 0, sourceStartByte = 4, byteCount = 4, unkId = 0))
        )
        val emedf = Emedf(mapOf(SET_FLAG.opcode to SET_FLAG), emptyMap())

        literals(event.decode(emedf).single()) shouldBe
                listOf(ArgValue.FromCaller(sourceStartByte = 4, byteCount = 4), ArgValue.Literal(1))
    }

    test("an instruction with no definition keeps its bytes and decodes to nothing") {
        val decoded = instruction(END_IF_FLAG, 1, 2, 3, 4).decode(null)
        assertSoftly {
            decoded.definition shouldBe null
            decoded.args shouldBe emptyList()
            decoded.instruction.args.size shouldBe 4
        }
    }

    test("more bytes than the definition accounts for is reported") {
        instruction(SET_FLAG, 0, 0, 0, 0, 1, 0, 0, 0, 9, 9).decode(SET_FLAG).sizeMismatch shouldBe true
    }

    test("floats decode as floats") {
        val definition = InstructionDefinition(
            Opcode(2004, 6),
            "Set Speed",
            listOf(ArgDefinition("Speed", ArgType.Float, null, 0.0))
        )
        val decoded = instruction(definition, 0, 0, 0x80, 0x3f).decode(definition)
        (literals(decoded).single() as ArgValue.Literal).asFloat shouldBe 1.0f
    }
})