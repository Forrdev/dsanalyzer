package com.sappyoak.dsanalyzer.runtime.inject

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.native.memory.Address

private fun ByteArray.ints(): List<Int> = map { it.toInt() and 0xFF }

class X86ReachTest : FunSpec({
    test("a jump within 2 GB of a 64-bit site is encoded") {
        X86.jumpPatch(Address(0x1_402C_EE84), Address(0x1_3FFF_0000), 8).ints() shouldBe
                listOf(0xE9, 0x77, 0x11, 0xD2, 0xFF, 0x90, 0x90, 0x90)
    }

    test("a jump a rel32 cannot span is refused rather than truncated") {
        shouldThrow<IllegalArgumentException> { X86.jumpPatch(Address(0x1_402C_EE84), Address(0x2_5000_0000), 8) }
        shouldThrow<IllegalArgumentException> { X86.jumpPatch(Address(0x1_402C_EE84), Address(0x0200_0000), 8) }
    }

    test("a 32-bit jump wraps, so both halves of the address space reach each other") {
        X86.jumpPatch(Address(0x0040_1000), Address(0xF000_0000), 5).ints() shouldBe listOf(0xE9, 0xFB, 0xEF, 0xBF, 0xEF)
    }
})
