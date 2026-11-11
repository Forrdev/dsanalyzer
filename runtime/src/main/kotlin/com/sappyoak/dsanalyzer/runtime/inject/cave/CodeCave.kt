package com.sappyoak.dsanalyzer.runtime.inject.cave

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.inject.HookSite
import com.sappyoak.dsanalyzer.runtime.inject.X86
import com.sappyoak.dsanalyzer.runtime.write.WriteKey

/**
 * A single detour. A jump written over [site] into code the cave assembles itself, in a block
 * it shares with the other caves installed alongside it. A cave owns where it leaves, so it re-runs
 * the displaced instructions only if it returns to the site
 */
public interface CodeCave {
    public val key: WriteKey
    public val site: HookSite

    public fun assemble(origin: Address, at: Address): ByteArray
}

public abstract class X86Cave : CodeCave {
    override fun assemble(origin: Address, at: Address): ByteArray =
        X86(origin).apply { emit(at) }.assemble()

    protected abstract fun X86.emit(at: Address)
}

/** Where a `CALL rel32` at [at], whose bytes are [call], lands */
public fun callTarget(at: Address, call: ByteArray): Address {
    require(call.size == CALL_SIZE && call[0] == CALL_OPCODE) { "Not a CALL rel32" }
    val rel = (1 until CALL_SIZE).fold(0) { acc, i -> acc or ((call[i].toInt() and 0xFF) shl (8 * (i - 1))) }
    return at + CALL_SIZE.toLong() + rel.toLong()
}

private const val CALL_SIZE = 5
private const val CALL_OPCODE = 0xE8.toByte()
