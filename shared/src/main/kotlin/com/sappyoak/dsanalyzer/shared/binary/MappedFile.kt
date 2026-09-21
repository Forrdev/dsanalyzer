package com.sappyoak.dsanalyzer.shared.binary

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.nio.file.Path
import java.nio.file.StandardOpenOption

import com.sappyoak.dsanalyzer.shared.platform.PointerSize

/**
 * A file mapped read only into memory.
 *
 * The mapping's lifetime is the [Arena]'s so [close] unmaps immediately rather
 * than whenever a collector gets around to it. This matters a lot on Windows, where a
 * lingering mapping keeps the files locked.
 *
 * The arena is shared, so a reader may be used from whichever thread a coroutine lands on
 */
public class MappedFile private constructor(
    private val arena: Arena,
    private val segment: MemorySegment
) : AutoCloseable {
    public val size: Long = segment.byteSize()

    public fun reader(
        order: ByteOrder = ByteOrder.LITTLE_ENDIAN,
        pointerSize: PointerSize = PointerSize.IntPointer
    ): BinaryReader = MemorySegmentBinaryReader(segment, 0, order, pointerSize)

    override fun close() {
        arena.close()
    }

    public companion object {
        public fun open(path: Path): MappedFile {
            val arena = Arena.ofShared()
            try {
                val segment = FileChannel.open(path, StandardOpenOption.READ).use { channel ->
                    channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size(), arena)
                }
                return MappedFile(arena, segment)
            } catch (err: Throwable) {
                arena.close()
                throw err
            }
        }
    }
}