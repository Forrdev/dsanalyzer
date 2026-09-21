package com.sappyoak.dsanalyzer.native.memory

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_BYTE

import com.sappyoak.dsanalyzer.native.process.ProcessMemory

/** Large enough to make the per-read cost negligible, small enough to not matter */
private const val DEFAULT_CHUNK_SIZE = 1 shl 20

/**
 * Every address in [range] where [pattern] matches
 *
 * Walks the mapped regions and reads each readable one int chunks, so guard pages and
 * unmapped gaps are skipped without knowing about the module's layout. Consecutive chunks
 * overlap by one byte less than the pattern, so a match straddling a chunk boundary is still
 * found exactly once.
 *
 * This returns Addresses only. Turning a match into the address it refers to depends on the target's
 * instruction set, and belongs with the signature that is being searched for
 */
public fun ProcessMemory.scan(
    range: AddressRange,
    pattern: AOBPattern,
    chunkSize: Int = DEFAULT_CHUNK_SIZE
): List<Address> {
    require(chunkSize >= pattern.length) { "Chunks must fit the pattern" }

    val overlap = pattern.length - 1
    val matches = mutableListOf<Address>()

    Arena.ofConfined().use { arena ->
        val native = arena.allocate(chunkSize.toLong())
        val local = ByteArray(chunkSize)

        for (region in regions(range).filter { it.readable }) {
            var offset = 0L
            while (offset + pattern.length <= region.range.size) {
                val length = minOf(chunkSize.toLong(), region.range.size - offset).toInt()
                val start = region.range.start + offset
                if (read(start, native.asSlice(0, length.toLong()))) {
                    MemorySegment.copy(native, JAVA_BYTE, 0, local, 0, length)
                    pattern.findIn(local, length).mapTo(matches) { start + it.toLong() }
                }
                if (offset + length >= region.range.size) break
                offset += length - overlap
            }
        }
    }

    return matches
}