package com.sappyoak.dsanalyzer.runtime.write

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

internal class FieldWrite(private val pointer: GamePointer, private val offset: Int) {
    val lifetime: Lifetime get() = pointer.lifetime

    fun addressIn(memory: GameMemory): Address? =
        memory.addressOf(pointer).takeUnless { it.isNull }?.let { it + offset.toLong() }
}
