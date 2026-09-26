package com.sappyoak.dsanalyzer.native.memory

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.native.process.FakeProcessMemory

private val BASE = Address(0x400000)
private val SIGNATURE = byteArrayOf(0x11, 0x22, 0x33, 0x44)
private val PATTERN = AOBPattern.parse("11 22 ?? 44")

class ScannerTest : FunSpec({
    test("finds matches anywhere in the range") {
        val memory = FakeProcessMemory(BASE, 256)
        memory[BASE + 10] = SIGNATURE
        memory[BASE + 200] = SIGNATURE

        memory.scan(AddressRange(BASE, 256), PATTERN).shouldBe(
            listOf(BASE + 10, BASE + 200)
        )
    }

    test("Finds a match straddling a chunk boundary exactly once") {
        val memory = FakeProcessMemory(BASE, 64)
        memory[BASE + 14] = SIGNATURE

        memory.scan(AddressRange(BASE, 64), PATTERN, chunkSize = 16).shouldBe(
            listOf(BASE + 14)
        )
    }

    test("Skips unreadable regions") {
        val guard = AddressRange(BASE + 64, 64)
        val memory = FakeProcessMemory(BASE, 256, unreadable = listOf(guard))
        memory[BASE + 8] = SIGNATURE
        memory[BASE + 180] = SIGNATURE

        memory.scan(AddressRange(BASE, 256), PATTERN, chunkSize = 32).shouldBe(
            listOf(BASE + 8, BASE + 180)
        )
    }

    test("only looks in the regions it is asked for") {
        val memory = FakeProcessMemory(BASE, 256, code = listOf(AddressRange(BASE, 100)))
        memory[BASE + 10] = SIGNATURE
        memory[BASE + 200] = SIGNATURE

        memory.scan(AddressRange(BASE, 256), PATTERN) { it.readable && it.executable }
            .shouldBe(listOf(BASE + 10))
    }

    test("respects the requested range") {
        val memory = FakeProcessMemory(BASE, 256)
        memory[BASE + 10] = SIGNATURE
        memory[BASE + 200] = SIGNATURE

        memory.scan(AddressRange(BASE + 100, 156), PATTERN).shouldBe(listOf(BASE + 200))
    }
})