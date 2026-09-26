package com.sappyoak.dsanalyzer.formats.emevd

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

private val FLAG_ARGS = byteArrayOf(0, 0, 0, 0, 0x10, 0x27, 0, 0)

private val SAMPLE = emevdFile(
    events = listOf(
        EventSpec(
            id = 0,
            instructions = listOf(
                InstructionSpec(bank = 2000, id = 0, args = byteArrayOf(1, 0, 0, 0)),
                InstructionSpec(bank = 1003, id = 2, args = FLAG_ARGS, layerMask = 0b11)
            )
        ),
        EventSpec(
            id = 11810000,
            restBehavior = 1,
            instructions = listOf(InstructionSpec(bank = 3, id = 0)),
            parameters = listOf(Parameter(instructionIndex = 0, targetStartByte = 4, sourceStartByte = 0, byteCount = 4, unkId = 0))
        )
    ),
    linkedFileOffsets = listOf(0),
    strings = "N:\\FRPG\\data\\Event\\common.emevd\u0000"
)

private fun read(bytes: ByteArray = SAMPLE): Emevd = readEmevd(BinaryReader.of(bytes))

class EmevdReaderTest : FunSpec({
    test("events read with their ids, rest behavior and instruction counts") {
        val emevd = read()
        assertSoftly {
            emevd.events.map { it.id } shouldBe listOf(0L, 11810000L)
            emevd.events[0].restBehavior shouldBe RestBehavior.Default
            emevd.events[1].restBehavior shouldBe RestBehavior.Restart
            emevd.events.map { it.instructions.size } shouldBe listOf(2, 1)
        }
    }

    test("instructions keep their bank, id and argument bytes") {
        val instructions = read().events[0].instructions
        assertSoftly {
            instructions.map { it.toString() } shouldBe listOf("2000[0]", "1003[2]")
            instructions[0].args.toByteArray() shouldBe byteArrayOf(1, 0, 0, 0)
            instructions[1].args.toByteArray() shouldBe FLAG_ARGS
            instructions[1].args.size shouldBe 8
        }
    }

    test("an instruction with no arguments gets an empty block") {
        read().events[1].instructions.single().args shouldBe ArgData.Empty
    }

    test("a layer mask is read when one is set and left null when not") {
        val instructions = read().events[0].instructions
        assertSoftly {
            instructions[0].layerMask shouldBe null
            instructions[1].layerMask shouldBe 0b11u
        }
    }

    test("parameters read against the event's own instructions") {
        read().events[1].parameters.single() shouldBe
                Parameter(instructionIndex = 0, targetStartByte = 4, sourceStartByte = 0, byteCount = 4, unkId = 0)
    }

    test("linked files resolve through the string table") {
        read().linkedFiles shouldBe listOf("N:\\FRPG\\data\\Event\\common.emevd")
    }

    test("a console or later-game layout is rejected rather than misread") {
        assertSoftly {
            shouldThrow<BinaryFormatException> { read(emevdFile(events = emptyList(), bigEndian = true)) }
            shouldThrow<BinaryFormatException> { read(emevdFile(events = emptyList(), version = 0xCD)) }
        }
    }
})