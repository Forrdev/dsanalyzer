package com.sappyoak.dsanalyzer.runtime.inject

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.assertFailsWith

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.inject.CaveTemplate.Hole

private val ORIGIN = Address(0x140000000)

/** `JMP rel32`, `MOV RAX, imm64`, `PUSH imm32` */
private fun template(): CaveTemplate = CaveTemplate(
    CaveTemplate.hex("E9 00 00 00 00  48 B8 00 00 00 00 00 00 00 00  68 00 00 00 00"),
    mapOf("back" to Hole.Rel32(1), "table" to Hole.Abs64(7), "radius" to Hole.Imm32(16))
)

private fun ByteArray.unsigned(): List<Int> = map { it.toInt() and 0xFF }

class CaveTemplateTest : FunSpec({
    test("every hole is filled where it was declared") {
        val built = template().build(
            ORIGIN,
            mapOf("back" to 0x140000100, "table" to 0x1122334455667788, "radius" to 0x7F)
        )
        built.unsigned() shouldBe listOf(
            0xE9, 0xFB, 0x00, 0x00, 0x00,
            0x48, 0xB8, 0x88, 0x77, 0x66, 0x55, 0x44, 0x33, 0x22, 0x11,
            0x68, 0x7F, 0x00, 0x00, 0x00
        )
    }

    test("the template itself is never changed by a build") {
        val cave = template()
        cave.build(ORIGIN, mapOf("back" to 0x140000100, "table" to 1, "radius" to 2))
        cave.build(ORIGIN, mapOf("back" to 0x140000005, "table" to 0, "radius" to 0)).unsigned().take(5) shouldBe
                listOf(0xE9, 0x00, 0x00, 0x00, 0x00)
    }

    test("a missing or extra value is refused") {
        assertFailsWith<IllegalArgumentException> { template().build(ORIGIN, mapOf("back" to 0L)) }
    }

    test("a relative target out of 32-bit reach is refused") {
        assertFailsWith<IllegalArgumentException> {
            template().build(ORIGIN, mapOf("back" to 0x7FFF00000000, "table" to 0, "radius" to 0))
        }
    }

    test("a hole past the end of the code is refused up front") {
        assertFailsWith<IllegalArgumentException> { CaveTemplate(CaveTemplate.hex("90 90"), mapOf("x" to Hole.Imm32(0))) }
    }
})
