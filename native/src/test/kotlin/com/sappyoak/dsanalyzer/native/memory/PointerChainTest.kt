package com.sappyoak.dsanalyzer.native.memory

import io.kotest.core.spec.style.FunSpec

import com.sappyoak.dsanalyzer.native.process.FakeProcessMemory
import com.sappyoak.dsanalyzer.shared.platform.PointerSize
import io.kotest.matchers.shouldBe

private val BASE = Address(0x1000)

class PointerChainTest : FunSpec({
    test("follows a chain of 32 bit pointers") {
        val memory = FakeProcessMemory(BASE, 0x100, pointerSize = PointerSize.IntPointer)
        memory[BASE] = BASE + 0x40
        memory[BASE + 0x48] = BASE + 0x80

        PointerChain.of(BASE, 0x08, 0x0C).resolve(memory).shouldBe(BASE + 0x8C)
    }

    test("follows a chain of 64 bit pointers") {
        val memory = FakeProcessMemory(BASE, 0x100)
        memory[BASE] = BASE + 0x40

        PointerChain.of(BASE, 0x10).resolve(memory).shouldBe(BASE + 0x50)
    }

    test("A null link resolves to the null address rather than failing") {
        val memory = FakeProcessMemory(BASE, 0x100)
        memory[BASE] = BASE + 0x40
        PointerChain.of(BASE, 0x08, 0x0C).resolve(memory).shouldBe(Address.Null)
    }

    test("An unreadable link resolves to the null address") {
        val memory = FakeProcessMemory(BASE, 0x100)
        memory[BASE] = Address(0x9999_0000)
        PointerChain.of(BASE, 0x08, 0x0C).resolve(memory).shouldBe(Address.Null)
    }

    test("No offsets means the base itself") {
        PointerChain.of(BASE).resolve(FakeProcessMemory(BASE, 8)).shouldBe(BASE)
    }

    test("Each link costs exactly one read") {
        val memory = FakeProcessMemory(BASE, 0x100)
        memory[BASE] = BASE + 0x40
        memory[BASE + 0x48] = BASE + 0x80

        PointerChain.of(BASE, 0x08, 0x0C).resolve(memory)

        memory.reads.shouldBe(2)
    }
})