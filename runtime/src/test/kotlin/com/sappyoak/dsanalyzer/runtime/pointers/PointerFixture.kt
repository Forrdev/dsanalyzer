package com.sappyoak.dsanalyzer.runtime.pointers

import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget

internal const val MOV_EDX = 0x8B
internal const val MOV_EDX_MODRM = 0x15

internal val PRESENT: Signature = Signature.parse(
    name = "Present",
    pattern = "8B 15 ?? ?? ?? ?? F3 0F",
    target = SignatureTarget.Embedded(2)
)

internal val ABSENT: Signature = Signature.parse(
    name = "Absent",
    pattern = "DE AD BE EF",
    target = SignatureTarget.Embedded(0)
)

/** Two structures behind one signature, which is how a nested structure is declared */
internal fun pointer(
    name: String,
    lifetime: Lifetime,
    offsets: List<Long>,
    base: Signature = PRESENT,
    size: Int = 0x40,
    displacement: Long = 0
): GamePointer = GamePointer(name, base, offsets, lifetime, size, displacement)

/**
 * Plants the signature at [at], with its operand naming [global].
 *
 * The walk from there is the caller's to lay out: the operand is read to reach [global], and
 * every offset in a pointer is followed from whatever is stored there
 */
internal fun FakeGame.plantSignature(at: Int, global: Int) {
    write(at, MOV_EDX, MOV_EDX_MODRM)
    pointer(at + 2, global)
    write(at + 6, 0xF3, 0x0F)
}