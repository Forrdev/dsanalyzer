package com.sappyoak.dsanalyzer.formats.emevd.emedf

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.emevd.ArgData
import com.sappyoak.dsanalyzer.formats.emevd.Instruction

private const val EVENT = 11_015_300L

private fun arg(name: String, reference: ArgReference?) =
    ArgDefinition(name = name, type = ArgType.Int, enumName = null, default = 0.0, reference = reference)

private fun instruction(
    bank: Int,
    id: Int,
    name: String,
    vararg args: Pair<ArgDefinition, ArgValue>
) = DecodedInstruction(
    instruction = Instruction(bank = bank, id = id, args = ArgData.Empty, layerMask = null),
    definition = InstructionDefinition(opcode = Opcode(bank, id), name = name, args = args.map { it.first }),
    args = args.map { (definition, value) -> DecodedArg(definition, value) }
)

private fun literal(value: Long) = ArgValue.Literal(value)

private val FLAG = arg("flag", ArgReference.EventFlag)
private val FIRST = arg("first_flag", ArgReference.EventFlagRangeStart)
private val LAST = arg("last_flag", ArgReference.EventFlagRangeEnd)
private val ADDRESSING = arg("flag_type", ArgReference.EventFlagType)
private val STATE = arg("state", null)

class FlagUseTest : FunSpec({
    test("the bank says whether a script reads a flag or acts on one") {
        val set = instruction(2003, 2, "SetFlagState", FLAG to literal(100), STATE to literal(1))
        val tested = instruction(3, 0, "IfFlagState", ADDRESSING to literal(0), FLAG to literal(100))
        val registered = instruction(2009, 3, "RegisterBonfire", FLAG to literal(100))

        assertSoftly {
            set.flagUses(EVENT).single().access shouldBe FlagAccess.Writes
            registered.flagUses(EVENT).single().access shouldBe FlagAccess.Writes
            tested.flagUses(EVENT).single().access shouldBe FlagAccess.Reads
        }
    }

    test("a span of flags is one reference that knows its bounds") {
        val use = instruction(
            2003,
            22,
            "SetFlagRangeState",
            FIRST to literal(11_010_900),
            LAST to literal(11_010_999),
            STATE to literal(1)
        ).flagUses(EVENT).single()

        val span = use.target as FlagTarget.Span

        assertSoftly {
            span shouldBe FlagTarget.Span(11_010_900, 11_010_999)
            (11_010_950 in span) shouldBe true
            (11_010_899 in span) shouldBe false
            use.label shouldBe "SetFlagRangeState"
        }
    }

    /**
     * `IfThisEventFlagEnabled` is this instruction with a flag of 0, so a literal zero means the
     * event's own id. Comparing the literal against the flag being looked for would miss every one
     * of these and could match the wrong flag outright
     */
    test("a flag addressed from its own event resolves against the event id") {
        val relative = instruction(3, 0, "IfFlagState", ADDRESSING to literal(1), FLAG to literal(0))
        val absolute = instruction(3, 0, "IfFlagState", ADDRESSING to literal(0), FLAG to literal(0))

        assertSoftly {
            relative.flagUses(EVENT).single().target shouldBe FlagTarget.One(EVENT.toInt())
            absolute.flagUses(EVENT).single().target shouldBe FlagTarget.One(0)
        }
    }

    test("a flag addressed from the event's slot is recorded rather than guessed at") {
        val use = instruction(3, 0, "IfFlagState", ADDRESSING to literal(2), FLAG to literal(3))

        use.flagUses(EVENT).single().target shouldBe FlagTarget.SlotRelative(3)
    }

    test("a flag the caller passes in is recorded as unresolved") {
        val use = instruction(
            2003,
            2,
            "SetFlagState",
            FLAG to ArgValue.FromCaller(sourceStartByte = 0, byteCount = 4),
            STATE to literal(1)
        )

        use.flagUses(EVENT).single().target shouldBe FlagTarget.FromCaller
    }

    test("an instruction with no flag argument names no flags") {
        instruction(2003, 2, "SetFlagState", STATE to literal(1)).flagUses(EVENT).shouldBeEmpty()
    }

    test("an instruction no definition covers names no flags") {
        DecodedInstruction(
            instruction = Instruction(bank = 2003, id = 2, args = ArgData.Empty, layerMask = null),
            definition = null,
            args = listOf(DecodedArg(FLAG, literal(100)))
        ).flagUses(EVENT).shouldBeEmpty()
    }
})