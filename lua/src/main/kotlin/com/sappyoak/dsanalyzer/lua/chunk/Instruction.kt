package com.sappyoak.dsanalyzer.lua.chunk

/** Field widths of a 5.0 instruction, from the low bits up: opcode, C, B, A; Bx spans C and B */
internal const val SIZE_OP = 6
internal const val SIZE_A = 8
internal const val SIZE_B = 9
internal const val SIZE_C = 9

private const val POS_C = SIZE_OP
private const val POS_B = POS_C + SIZE_C
private const val POS_A = POS_B + SIZE_B
private const val SIZE_BX = SIZE_C + SIZE_B

private const val MASK_OP = (1 shl SIZE_OP) - 1
private const val MASK_A = (1 shl SIZE_A) - 1
private const val MASK_B = (1 shl SIZE_B) - 1
private const val MASK_C = (1 shl SIZE_C) - 1
private const val MASK_BX = (1 shl SIZE_BX) - 1

/** The bias that lets Bx carry a signed jump */
private const val MAX_SBX = MASK_BX shr 1

/** An RK operand at or above this names constant `value - RK_CONSTANT_BASE` instead of a register */
public const val RK_CONSTANT_BASE: Int = 250

@JvmInline
public value class Instruction(public val raw: Int) {
    public val opcode: Opcode get() = Opcode.entries[raw and MASK_OP]
    public val a: Int get() = (raw ushr POS_A) and MASK_A
    public val b: Int get() = (raw ushr POS_B) and MASK_B
    public val c: Int get() = (raw ushr POS_C) and MASK_C
    public val bx: Int get() = (raw ushr POS_C) and MASK_BX
    public val sbx: Int get() = bx - MAX_SBX

    override fun toString(): String = "$opcode(0x%08X)".format(raw)

    public companion object {
        internal fun isKnown(raw: Int): Boolean = Opcode.of(raw and MASK_OP) != null
    }
}

/** Whether an RK operand names a constant */
public fun isConstant(rk: Int): Boolean = rk >= RK_CONSTANT_BASE
