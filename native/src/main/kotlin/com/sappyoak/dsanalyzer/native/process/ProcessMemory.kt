package com.sappyoak.dsanalyzer.native.process

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.MemoryRegion
import com.sappyoak.dsanalyzer.shared.binary.PointerSize

/**
 * Raw access to another process's memory
 */
public interface ProcessMemory {
    /** Width of a pointer in the target, which is not necessarily ours */
    public val pointerSize: PointerSize

    public fun read(address: Address, length: Int): ByteArray?
    public fun write(address: Address, bytes: ByteArray): Boolean

    /** The mapped regions overlapping [range], clipped to it in address order */
    public fun regions(range: AddressRange): List<MemoryRegion>
}