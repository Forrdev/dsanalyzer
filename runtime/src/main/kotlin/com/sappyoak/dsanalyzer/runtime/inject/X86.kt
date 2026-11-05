package com.sappyoak.dsanalyzer.runtime.inject

import java.io.ByteArrayOutputStream

import com.sappyoak.dsanalyzer.native.memory.Address

/** Bytes of `JMP rel32` */
internal const val JMP_SIZE = 5

/** A 32 bit general register, numbered as ModRM encodes it */
public enum class Reg(internal val code: Int) {
    EAX(0),
    ECX(1),
    EDX(2),
    EBX(3),
    ESP(4),
    EBP(5),
    ESI(6),
    EDI(7);
}

public sealed interface Mem {
    public data class Absolute(public val address: Address) : Mem
}

public class Label internal constructor()

public class X86(private val origin: Address) {
    private val out = ByteArrayOutputStream()
    private val bound = mutableMapOf<Label, Int>()
    private val pending = mutableListOf<Pair<Int, Label>>()

    public fun label(): Label = Label()

    public fun bind(label: Label) {
        check(label !in bound) { "Label bound twice" }
        bound[label] = out.size()
    }

    public fun pushfd(): Unit = bytes(0x9C)
    public fun popfd(): Unit = bytes(0x9D)
    public fun pushad(): Unit = bytes(0x60)
    public fun popad(): Unit = bytes(0x61)
    public fun push(imm: Byte): Unit = bytes(0x6A, imm.toInt())

    public fun inc(mem: Mem): Unit = modRm(0xFF, 0, mem)
    public fun cmp(reg: Reg, mem: Mem): Unit = modRm(0x3B, reg.code, mem)

    /** `CMP dword [mem], imm8`, sign-extended */
    public fun cmp(mem: Mem, imm: Byte) {
        modRm(0x83, 7, mem)
        bytes(imm.toInt())
    }

    public fun mov(reg: Reg, mem: Mem): Unit = modRm(0x8B, reg.code, mem)
    public fun call(mem: Mem): Unit = modRm(0xFF, 2, mem)

    public fun jmp(label: Label) {
        bytes(0xE9)
        relative(label)
    }

    public fun jmp(target: Address) {
        bytes(0xE9)
        int((target.value - (origin.value + out.size() + Int.SIZE_BYTES)).toInt())
    }

    public fun jz(label: Label) {
        bytes(0x0F, 0x84)
        relative(label)
    }

    public fun raw(code: ByteArray): Unit = out.writeBytes(code)

    public fun assemble(): ByteArray {
        val code = out.toByteArray()
        for ((at, label) in pending) {
            val target = bound[label] ?: error("Label used but never bound")
            val rel = target - (at + Int.SIZE_BYTES)
            for (i in 0 until Int.SIZE_BYTES) code[at + i] = (rel ushr (8 * i)).toByte()
        }
        return code
    }

    private fun modRm(opcode: Int, reg: Int, mem: Mem) {
        bytes(opcode)
        when (mem) {
            is Mem.Absolute -> {
                bytes((reg shl 3) or 0b101)
                int(mem.address.value.toInt())
            }
        }
    }

    private fun relative(label: Label) {
        pending += out.size() to label
        int(0)
    }

    private fun int(value: Int) {
        for (i in 0 until Int.SIZE_BYTES) out.write(value ushr (8 * i))
    }

    private fun bytes(vararg values: Int) = values.forEach(out::write)

    public companion object {
        /** `JMP rel32` from [from] to [to], padded with NOPs to [length] bytes */
        public fun jumpPatch(from: Address, to: Address, length: Int): ByteArray {
            require(length >= JMP_SIZE) { "A jump needs $JMP_SIZE bytes" }
            val code = X86(from).apply { jmp(to) }.assemble()
            return code + ByteArray(length - JMP_SIZE) { 0x90.toByte() }
        }
    }
}
