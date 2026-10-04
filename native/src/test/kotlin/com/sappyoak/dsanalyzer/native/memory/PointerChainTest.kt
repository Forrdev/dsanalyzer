package com.sappyoak.dsanalyzer.native.memory

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.native.process.FakeProcessMemory
import com.sappyoak.dsanalyzer.shared.platform.PointerSize

private val BASE = Address(0x1000)

class PointerChainTest : FunSpec({
    test("follows a chain of 32 bit pointers") {
        val memory = FakeProcessMemory(BASE, 0x100, pointerSize = PointerSize.IntPointer)
        memory[BASE + 0x08] = BASE + 0x40
        memory[BASE + 0x4C] = BASE + 0x80

        PointerChain.of(BASE, 0x08, 0x0C).resolve(memory).shouldBe(BASE + 0x80)
    }

    test("follows a chain of 64 bit pointers") {
        val memory = FakeProcessMemory(BASE, 0x100)
        memory[BASE + 0x10] = BASE + 0x40

        PointerChain.of(BASE, 0x10).resolve(memory).shouldBe(BASE + 0x40)
    }

    test("Every offset is followed, including the last") {
        val memory = FakeProcessMemory(BASE, 0x100)
        memory[BASE] = BASE + 0x20
        memory[BASE + 0x20] = BASE + 0x60

        PointerChain.of(BASE, 0x00, 0x00).resolve(memory).shouldBe(BASE + 0x60)
    }

    test("A null link resolves to the null address rather than failing") {
        val memory = FakeProcessMemory(BASE, 0x100)
        PointerChain.of(BASE, 0x08, 0x0C).resolve(memory).shouldBe(Address.Null)
    }

    test("An unreadable link resolves to the null address") {
        val memory = FakeProcessMemory(BASE, 0x100)
        memory[BASE + 0x08] = Address(0x9999_0000)
        PointerChain.of(BASE, 0x08, 0x0C).resolve(memory).shouldBe(Address.Null)
    }

    test("No offsets means the base itself") {
        val memory = FakeProcessMemory(BASE, 8)

        assertSoftly {
            PointerChain.of(BASE).resolve(memory).shouldBe(BASE)
            memory.reads.shouldBe(0)
        }
    }

    test("Each link costs exactly one read") {
        val memory = FakeProcessMemory(BASE, 0x100)
        memory[BASE + 0x08] = BASE + 0x40
        memory[BASE + 0x4C] = BASE + 0x80

        PointerChain.of(BASE, 0x08, 0x0C).resolve(memory)

        memory.reads.shouldBe(2)
    }
})