package com.sappyoak.dsanalyzer.game.world.scripts

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgReference
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgType
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Opcode
import com.sappyoak.dsanalyzer.game.bundledText

private val DEFINITIONS = loadInstructionDefinitions()

/** The kind strings the generated aliases carry, which is what joins them to [ArgReference] */
private val DECLARED_KIND = Regex(""""reference"\s*:\s*"([^"]+)"""")


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

    test("every argument kind the resource declares lands on an ArgReference") {
        val declared = DECLARED_KIND
            .findAll(bundledText("/definitions/ds1-event-aliases.json"))
            .map { it.groupValues[1] }
            .toSortedSet()

        assertSoftly {
            declared.shouldNotBeEmpty()
            declared.filter { ArgReference.named(it) == null } shouldBe emptyList()
        }
    }

    test("The flag arguments declared are recognized as flags") {
        val set = checkNotNull(DEFINITIONS[Opcode(2003, 2)])
        val range = checkNotNull(DEFINITIONS[Opcode(2003, 22)])
        val tested = checkNotNull(DEFINITIONS[Opcode(3, 0)])

        assertSoftly {
            set.args.first().reference shouldBe ArgReference.EventFlag
            range.args.mapNotNull { it.reference }.take(2) shouldBe listOf(
                ArgReference.EventFlagRangeStart, ArgReference.EventFlagRangeEnd
            )
            tested.args.any { it.reference == ArgReference.EventFlagType } shouldBe true
        }
    }

    test("exactly the range instructions carry both ends of a span") {
        val spans = DEFINITIONS.all.filter { definition ->
            definition.args.any { it.reference == ArgReference.EventFlagRangeStart }
        }

        assertSoftly {
            spans.size shouldBe 7
            spans.all { definition ->
                definition.args.any { it.reference == ArgReference.EventFlagRangeEnd }
            } shouldBe true
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
