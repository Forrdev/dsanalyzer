package com.sappyoak.dsanalyzer.native.memory

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.native.process.FakeProcessMemory
import com.sappyoak.dsanalyzer.shared.platform.PointerSize

private val BASE = Address(0x400000)
private val MODULE = AddressRange(BASE, 0x1000)

private fun int32(value: Int): ByteArray =
    ByteBuffer.allocate(Int.SIZE_BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array()

private fun bytes(vararg values: Int) = ByteArray(values.size) { values[it].toByte() }

private fun memory(
    unreadable: List<AddressRange> = emptyList(),
    code: List<AddressRange>? = null
) = FakeProcessMemory(BASE, 0x1000, PointerSize.IntPointer, unreadable, code)

/** mov edx, [imm32], which is how the 32 bit game reaches a global */
private val ABSOLUTE = Signature.parse("CharData", "8B 15 ?? ?? ?? ?? F3 0F", SignatureTarget.Embedded(2))

/** mov rax, [rip + disp32], where the displacement sits past the end of the pattern */
private val RIP = Signature.parse("WorldChr", "48 8B 05", SignatureTarget.Relative(3, 7))

private fun FakeProcessMemory.writeAbsolute(at: Long, operand: Int) {
    this[BASE + at] = bytes(0x8B, 0x15)
    this[BASE + at + 2] = int32(operand)
    this[BASE + at + 6] = bytes(0xF3, 0x0F)
}

class SignatureTest : FunSpec({
    test("an operand holding an address absolutely resolves to that address") {
        val memory = memory().apply { writeAbsolute(0x100, 0x400ABC) }

        memory.resolve(ABSOLUTE, MODULE).shouldBe(SignatureScan.Resolved(Address(0x400ABC)))
    }

    test("a displacement resolves against the end of its instruction") {
        val memory = memory()
        memory[BASE + 0x100] = bytes(0x48, 0x8B, 0x05)
        memory[BASE + 0x103] = int32(0x40)

        memory.resolve(RIP, MODULE).shouldBe(SignatureScan.Resolved(BASE + 0x147))
    }

    test("a displacement pointing backwards is signed") {
        val memory = memory()
        memory[BASE + 0x100] = bytes(0x48, 0x8B, 0x05)
        memory[BASE + 0x103] = int32(-0x80)

        memory.resolve(RIP, MODULE).shouldBe(SignatureScan.Resolved(BASE + 0x87))
    }

    test("a signature naming code resolves to the match itself") {
        val memory = memory()
        memory[BASE + 0x200] = bytes(0x55, 0x8B, 0xEC)

        memory.resolve(Signature.parse("FuncItemGet", "55 8B EC"), MODULE)
            .shouldBe(SignatureScan.Resolved(BASE + 0x200))
    }

    test("a pattern that is no longer unique is ambiguous rather than the first match") {
        val memory = memory().apply {
            writeAbsolute(0x100, 0x400ABC)
            writeAbsolute(0x300, 0x400ABC)
        }

        memory.resolve(ABSOLUTE, MODULE)
            .shouldBe(SignatureScan.Ambiguous(listOf(BASE + 0x100, BASE + 0x300)))
    }

    test("a pattern that matches nothing is not found") {
        memory().resolve(ABSOLUTE, MODULE).shouldBe(SignatureScan.NotFound)
    }

    test("only code is searched, so the same bytes in data do not match") {
        val memory = memory(code = listOf(AddressRange(BASE, 0x200))).apply { writeAbsolute(0x400, 0x400ABC) }

        memory.resolve(ABSOLUTE, MODULE).shouldBe(SignatureScan.NotFound)
    }

    test("an address named past the end of the readable bytes is unreadable") {
        val memory = memory(unreadable = listOf(AddressRange(BASE + 0x300, 0x100)))
        memory[BASE + 0x2FE] = bytes(0x8B, 0x15)

        memory.resolve(Signature.parse("Trailing", "8B 15", SignatureTarget.Embedded(2)), MODULE)
            .shouldBe(SignatureScan.Unreadable)
    }
})