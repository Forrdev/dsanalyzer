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

    /** '[base + disp] */
    public data class Based(public val base: Reg, public val disp: Int) : Mem {
        init {
            require(base != Reg.ESP) { "ESP-based operands are not encoded" }
        }
    }
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
    public fun push(reg: Reg): Unit = bytes(0x50 + reg.code)
    public fun inc(reg: Reg): Unit = bytes(0x40 + reg.code)

    public fun inc(mem: Mem): Unit = modRm(0xFF, 0, mem)
    public fun cmp(reg: Reg, mem: Mem): Unit = modRm(0x3B, reg.code, mem)
    public fun and(reg: Reg, mem: Mem): Unit = modRm(0x23, reg.code, mem)
    public fun add(reg: Reg, mem: Mem): Unit = modRm(0x03, reg.code, mem)

    /** `CMP dword [mem], imm8`, sign-extended */
    public fun cmp(mem: Mem, imm: Byte) {
        modRm(0x83, 7, mem)
        bytes(imm.toInt())
    }

    public fun mov(reg: Reg, mem: Mem): Unit = modRm(0x8B, reg.code, mem)
    public fun mov(mem: Mem, reg: Reg): Unit = modRm(0x89, reg.code, mem)
    public fun mov(to: Reg, from: Reg): Unit = bytes(0x8B, 0b11_000_000 or (to.code shl 3) or from.code)

    public fun mov(reg: Reg, imm: Int) {
        bytes(0xB8 + reg.code)
        int(imm)
    }

    /** `CMP byte [mem], imm8` */
    public fun cmpByte(mem: Mem, imm: Byte) {
        modRm(0x80, 7, mem)
        bytes(imm.toInt())
    }

    public fun mov(mem: Mem, imm: Int) {
        modRm(0xC7, 0, mem)
        int(imm)
    }

    /** `MOVZX reg, word [mem]` */
    public fun movzxWord(reg: Reg, mem: Mem) {
        bytes(0x0F)
        modRm(0xB7, reg.code, mem)
    }

    /** `MOV word [mem], reg16`: the low half of [reg] */
    public fun movWord(mem: Mem, reg: Reg) {
        bytes(0x66)
        modRm(0x89, reg.code, mem)
    }

    /** `CMP reg, imm8`, sign-extended */
    public fun cmp(reg: Reg, imm: Byte): Unit = immediate8(0x83, 7, reg, imm)
    public fun shl(reg: Reg, imm: Byte): Unit = immediate8(0xC1, 4, reg, imm)
    public fun and(reg: Reg, imm: Int): Unit = immediate32(4, reg, imm)
    public fun add(reg: Reg, imm: Int): Unit = immediate32(0, reg, imm)
    public fun call(mem: Mem): Unit = modRm(0xFF, 2, mem)

    /** `CALL rel32` to a function at [target] */
    public fun call(target: Address) {
        bytes(0xE8)
        relative(target)
    }

    public fun jmp(label: Label) {
        bytes(0xE9)
        relative(label)
    }

    public fun jmp(target: Address) {
        bytes(0xE9)
        relative(target)
    }

    public fun jz(label: Label): Unit = jcc(0x84, label)
    public fun jnz(label: Label): Unit = jcc(0x85, label)

    /** Unsigned `<` */
    public fun jb(label: Label): Unit = jcc(0x82, label)

    /** Unsigned `>=` */
    public fun jae(label: Label): Unit = jcc(0x83, label)

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
            is Mem.Based -> {
                bytes(0b10_000_000 or (reg shl 3) or mem.base.code)
                int(mem.disp)
            }
        }
    }

    private fun immediate8(opcode: Int, extension: Int, reg: Reg, imm: Byte) {
        bytes(opcode, 0b11_000_000 or (extension shl 3) or reg.code, imm.toInt())
    }

    private fun immediate32(extension: Int, reg: Reg, imm: Int) {
        bytes(0x81, 0b11_000_000 or (extension shl 3) or reg.code)
        int(imm)
    }

    private fun jcc(condition: Int, label: Label) {
        bytes(0x0F, condition)
        relative(label)
    }

    /** A rel32 to [target], counted from the end of the displacement */
    private fun relative(target: Address) {
        int((target.value - (origin.value + out.size() + Int.SIZE_BYTES)).toInt())
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
