package com.sappyoak.dsanalyzer.native.memory

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.assertFailsWith

private fun bytes(vararg values: Int) = ByteArray(values.size) { values[it].toByte() }

class AOBPatternTest : FunSpec({
    test("Find every match with wildcards matching anything") {
        val pattern = AOBPattern.parse("48 8B ?? 05")
        val buffer = bytes(0x00, 0x48, 0x8B, 0x11, 0x05, 0x48, 0x8B, 0xFF, 0x05, 0x48)

        pattern.findIn(buffer).shouldBe(listOf(1, 5))
    }

    test("A leading wildcard does not become the anchor") {
        val pattern = AOBPattern.parse("? 8B")
        pattern.findIn(bytes(0x00, 0x8B, 0x01, 0x8B)).shouldBe(listOf(0, 2))
    }

    test("Formatting as string should return the original pattern") {
        val str = "48 8B ?? ?? 05"
        AOBPattern.parse(str).toString().shouldBe(str)
    }

    test("Rejects invalid patterns") {
        assertSoftly {
            assertFailsWith<IllegalArgumentException> { AOBPattern.parse("") }
            assertFailsWith<IllegalArgumentException> { AOBPattern.parse("?? ??") }
            assertFailsWith<IllegalArgumentException> { AOBPattern.parse("48 8") }
            assertFailsWith<IllegalArgumentException> { AOBPattern.parse("48 ZZ") }
        }
    }
})