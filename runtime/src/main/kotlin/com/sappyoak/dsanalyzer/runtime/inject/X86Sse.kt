package com.sappyoak.dsanalyzer.runtime.inject

/** An SSE register, numbered as ModRM encodes it */
public enum class Xmm(internal val code: Int) {
    XMM0(0),
    XMM1(1),
    XMM2(2),
    XMM3(3),
    XMM4(4),
    XMM5(5),
    XMM6(6),
    XMM7(7);
}

private const val SCALAR_SINGLE = 0xF3
private const val MOVE_LOAD = 0x10
private const val MOVE_STORE = 0x11
private const val ADD = 0x58
private const val MUL = 0x59
private const val SUB = 0x5C
private const val COMPARE_ORDERED = 0x2F

/** `MOVSS xmm, dword [mem]` */
public fun X86.movss(to: Xmm, from: Mem): Unit = scalar(MOVE_LOAD, to, from)

/** `MOVSS dword [mem], xmm` */
public fun X86.movss(to: Mem, from: Xmm): Unit = scalar(MOVE_STORE, from, to)

/** `MOVUPS xmm, [mem]`: 16 unaligned bytes */
public fun X86.movups(to: Xmm, from: Mem): Unit = packed(MOVE_LOAD, to, from)

/** `MOVUPS [mem], xmm` */
public fun X86.movups(to: Mem, from: Xmm): Unit = packed(MOVE_STORE, from, to)

public fun X86.addss(to: Xmm, from: Xmm): Unit = scalar(ADD, to, from)
public fun X86.subss(to: Xmm, from: Mem): Unit = scalar(SUB, to, from)
public fun X86.mulss(to: Xmm, from: Xmm): Unit = scalar(MUL, to, from)

/** `COMISS xmm, dword [mem]`: sets ZF/CF like an unsigned compare, so read it with [ja]/[jbe] */
public fun X86.comiss(left: Xmm, right: Mem) {
    bytes(0x0F)
    modRm(COMPARE_ORDERED, left.code, right)
}

/** Unsigned `>`, or `>` after [comiss] */
public fun X86.ja(label: Label): Unit = jcc(0x87, label)

/** Unsigned `<=`, or `<=` after [comiss] */
public fun X86.jbe(label: Label): Unit = jcc(0x86, label)

private fun X86.scalar(opcode: Int, reg: Xmm, mem: Mem) {
    bytes(SCALAR_SINGLE, 0x0F)
    modRm(opcode, reg.code, mem)
}

private fun X86.scalar(opcode: Int, to: Xmm, from: Xmm) {
    bytes(SCALAR_SINGLE, 0x0F, opcode, 0b11_000_000 or (to.code shl 3) or from.code)
}

private fun X86.packed(opcode: Int, reg: Xmm, mem: Mem) {
    bytes(0x0F)
    modRm(opcode, reg.code, mem)
}
