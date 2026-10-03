package com.sappyoak.dsanalyzer.runtime.pointers

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.PointerChain
import com.sappyoak.dsanalyzer.native.process.ProcessMemory

/**
 * Reads structures out of a running game and remembers where it found them
 */
public class GameMemory(
    public val memory: ProcessMemory,
    private val pointers: ResolvedPointers
) {
    private val resolved = HashMap<GamePointer, Address>()

    public val cached: Int get() = resolved.size

    /** Where [pointer] is now, or [Address.Null] when its walk cannot be completed */
    public fun addressOf(pointer: GamePointer): Address {
        resolved[pointer]?.let { return it }

        val base = pointers[pointer] ?: return Address.Null
        val address = PointerChain(base, pointer.offsets).resolve(memory)
        if (!address.isNull && pointer.lifetime != Lifetime.Volatile) {
            resolved[pointer] = address
        }

        return address
    }

    /**
     * Forgets every walk that does not outlive [event]
     */
    public fun invalidate(event: Lifetime) {
        resolved.keys.removeAll { it.lifetime >= event }
    }
}