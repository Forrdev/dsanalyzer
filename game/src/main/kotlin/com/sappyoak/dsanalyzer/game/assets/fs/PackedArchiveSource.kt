package com.sappyoak.dsanalyzer.game.assets.fs

import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.file.Path
import java.nio.channels.FileChannel

/**
 * Memory mapped access to a '.bdt' archive
 *
 * A single MappedByteBuffer addresses at most 'Int.MAX_VALUE' bytes, and DS1's archives
 * sit close enough to that limit to cross it. So the file is  mapped in overlapping windows
 * rather than whole, and reads that straddle a boundary fall back to a channel read.
 */
class PackedArchive(private val path: Path) : AutoCloseable {
    private val file = RandomAccessFile(path.toFile(), "r")
    private val channel: FileChannel = file.channel

    /**
     * Windows, each up to [WINDOW_SIZE], overlapping by [OVERLAP]
     *
     * The overlap exists so a read shorter than it never straddles two windows,
     * which covers essentially every entry header and most small files, leaving
     * the slow path for genuinely large reads only
     */
    private val windows: List<Window> = buildWindows()

    val size: Long = channel.size()

    fun read(offset: Long, length: Int): ByteArray? {
        if (offset < 0 || length <= 0 || offset + length > size) return null

        // Fast path: a window that fully contains the read
        val window = windows.firstOrNull { offset >= it.start && offset + length <= it.end }
        if (window != null) {
            val result = ByteArray(length)
            val local = (offset - window.start).toInt()

            // Duplicate rather than mutating the shared buffer's position. A mapped buffer
            // is shared across every read, and a stateful position would make concurrent access
            // silently wrong rather than merely contended
            window.buffer.duplicate().apply { position(local) }.get(result)
            return result
        }

        // Straddles a boundary, or is larger than a window
        return readViaChannel(offset, length)
    }

    override fun close() {
        channel.close()
        file.close()
    }

    private fun readViaChannel(offset: Long, length: Int): ByteArray? {
        val buffer = ByteBuffer.allocate(length)
        var read = 0
        while (read < length) {
            val count = channel.read(buffer, offset + read)
            if (count <= 0) break
            read += count
        }

        return if (read == length) buffer.array() else null
    }

    private fun buildWindows(): List<Window> {
        val result = arrayListOf<Window>()
        var start = 0L
        while (start < size) {
            val length = minOf(WINDOW_SIZE.toLong(), size - start)
            result.add(Window(
                start = start,
                length = length,
                buffer = channel.map(FileChannel.MapMode.READ_ONLY, start, length)
            ))
            if (start + length >= size) break
            start += length - OVERLAP
        }

        return result
    }
    private class Window(val start: Long, val length: Long, val buffer: ByteBuffer) {
        val end: Long get() = start + length
    }

    companion object {
        /**
         * Comfortably inside the Int.MAX_VALUE mapping limit.
         *
         * We cant use Int.MAX_VALUE because the overlap has to fit as well, and leaving
         * room avoids an off-by-one at exactly the boundary, where a failure would appear
         * only on archives large enough to have one
         */
        private const val WINDOW_SIZE = 1 shl 30

        /**
         * Overlap between windows.
         *
         * Any read shorter than this is guaranteed to sit inside a single window, so
         * the slow path is reserved for reads genuinely larger than the overlap rather
         * than ones that merely happen to land near a boundary
         */
        private const val OVERLAP = 1 shl 20
    }
}