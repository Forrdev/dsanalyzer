package com.sappyoak.dsanalyzer.formats.esd

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

private const val DATA = 0x6C

/**
 * The smallest interesting Prepare to Die ESD, laid out by hand. Offsets in the body count from
 * [DATA]: group table at 0x2C, states at 0x3C (two, then the copy of state 0 the format repeats),
 * the condition at 0xA8, its offset list at 0xC4, a command at 0xC8, its argument list at 0xD8,
 * the argument's bytes at 0xE0 and the evaluator's at 0xE2
 */
private fun fixture(): ByteArray {
    val buffer = ByteBuffer.allocate(DATA + 0xE8).order(ByteOrder.LITTLE_ENDIAN)
    buffer.put("fSSL".toByteArray())
    intArrayOf(1, 1, 1, 0x54, 0, 6, 0x2C, 1, 0x10, 1, 0x24, 3, 0x1C, 1, 0x10, 1, 8, 1, 0, 0, 0, 0, 0, 0, 0, 0)
        .forEach { buffer.putInt(it) }

    fun at(offset: Int, vararg values: Int) = values.forEachIndexed { i, v -> buffer.putInt(DATA + offset + i * 4, v) }
    at(0x00, 1, 0, 0, 0, 0)
    at(0x14, 0x2C, 1, 0, 0, 0, 0)                       // groups at 0x2C, one; no name
    at(0x2C, 7, 0x3C, 2, 0x3C)                         // group 7: two states at 0x3C
    at(0x3C, 10, 0xC4, 1, 0xC8, 1, 0, 0, 0, 0)         // state 10: one condition, one entry command
    at(0x60, 11, 0, 0, 0, 0, 0, 0, 0, 0)               // state 11: nothing
    at(0x84, 10, 0xC4, 1, 0xC8, 1, 0, 0, 0, 0)         // the repeated copy of state 10
    at(0xA8, 0x60, 0, 0, 0, 0, 0xE2, 3)                // condition -> state 11, evaluator 3 bytes
    at(0xC4, 0xA8)
    at(0xC8, 1, 100, 0xD8, 1)                          // command 1[100], one argument
    at(0xD8, 0xE0, 2)
    byteArrayOf(0x41, 0xA1.toByte()).forEachIndexed { i, b -> buffer.put(DATA + 0xE0 + i, b) }
    byteArrayOf(0x40, 0x41, 0xA1.toByte()).forEachIndexed { i, b -> buffer.put(DATA + 0xE2 + i, b) }
    return buffer.array()
}

class EsdReaderTest : FunSpec({
    val esd = readEsd(BinaryReader.of(fixture()))
    val group = esd.groups.getValue(7)

    test("a group's states are read by id, and the repeated first state is not a state of its own") {
        assertSoftly {
            esd.groups.keys shouldContainExactly listOf(7L)
            group.states.keys shouldContainExactly listOf(10L, 11L)
            esd.name shouldBe null
        }
    }

    test("a condition leads to its state by id, and keeps its evaluator as bytes") {
        val condition = group.states.getValue(10).conditions.single()

        assertSoftly {
            condition.target shouldBe 11L
            condition.evaluator.hex() shouldBe "40 41 A1"
            condition.subconditions shouldBe emptyList()
        }
    }

    test("commands are bank and id with each argument its own bytecode") {
        val command = group.states.getValue(10).entryCommands.single()

        assertSoftly {
            command.toString() shouldBe "1[100]"
            command.args.map { it.hex() } shouldContainExactly listOf("41 A1")
            group.states.getValue(11).entryCommands shouldBe emptyList()
        }
    }
})
