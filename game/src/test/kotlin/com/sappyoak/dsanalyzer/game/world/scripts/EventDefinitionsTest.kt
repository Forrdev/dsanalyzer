package com.sappyoak.dsanalyzer.game.world.scripts

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgReference
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgType
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Opcode

private val DEFINITIONS = loadInstructionDefinitions()

class EventDefinitionsTest : FunSpec({
    test("the bundled definitions load") {
        DEFINITIONS.size shouldBeGreaterThan 200
    }

    test("a known instruction reads with its name and argument widths") {
        val definition = checkNotNull(DEFINITIONS[Opcode(1003, 2)])
        assertSoftly {
            definition.name shouldBe "END IF Event Flag"
            definition.args.map { it.type } shouldBe listOf(ArgType.UByte, ArgType.UByte, ArgType.UByte, ArgType.Int)
            definition.packedSize shouldBe 8
        }
    }

    test("the real bank 1 survives, rather than the colliding extra bank") {
        checkNotNull(DEFINITIONS[Opcode(1, 1)]).name shouldBe "IF Elapsed Frames"
    }

    test("enum values resolve for arguments that name one") {
        val state = checkNotNull(DEFINITIONS[Opcode(2003, 2)]).args.last()
        assertSoftly {
            DEFINITIONS.enumValue(state.enumName, 1) shouldBe "ON"
            DEFINITIONS.enumValue(state.enumName, 0) shouldBe "OFF"
        }
    }

    test("instructions read as names") {
        val setFlag = checkNotNull(DEFINITIONS[Opcode(2003, 2)])
        assertSoftly {
            setFlag.name shouldBe "Set Event Flag"
            setFlag.alias shouldBe "SetFlagState"
            setFlag.label shouldBe "SetFlagState"
            checkNotNull(setFlag.summary) shouldContain "toggle"
        }
    }

    test("an argument points at the kind of thing it was declared to take") {
        assertSoftly {
            checkNotNull(DEFINITIONS[Opcode(2003, 2)]).args.first().reference shouldBe ArgReference.EventFlag
            checkNotNull(DEFINITIONS[Opcode(3, 7)]).args.last().reference shouldBe ArgReference.Region
            checkNotNull(DEFINITIONS[Opcode(2004, 8)]).args.first().reference shouldBe ArgReference.Character
        }
    }

    test("laying names over the definitions leaves the argument widths alone") {
        val definition = checkNotNull(DEFINITIONS[Opcode(1003, 2)])
        assertSoftly {
            definition.args.map { it.type } shouldBe listOf(ArgType.UByte, ArgType.UByte, ArgType.UByte, ArgType.Int)
            definition.packedSize shouldBe 8
        }
    }
})
