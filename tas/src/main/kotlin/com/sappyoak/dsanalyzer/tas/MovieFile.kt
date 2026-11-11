package com.sappyoak.dsanalyzer.tas

import java.nio.BufferUnderflowException
import java.nio.ByteBuffer

import com.sappyoak.dsanalyzer.tas.rng.RngCodec
import com.sappyoak.dsanalyzer.shared.binary.littleEndian

public object MovieFile {
    private val MAGIC = "DSTM".toByteArray(Charsets.US_ASCII)
    private const val VERSION: Short = 3
    private const val SEGMENTS: Short = 2
    private const val SINGLE_MOVIE: Short = 1
    private const val NONE: Byte = 0
    private const val PRESENT: Byte = 1
    private const val FRAME = Short.SIZE_BYTES + PadFrame.AXIS_COUNT * Float.SIZE_BYTES

    public fun encode(run: Run): ByteArray {
        val size = MAGIC.size + Short.SIZE_BYTES + Int.SIZE_BYTES + run.segments.sumOf {
            2 + (if (it.rng != null) RngCodec.SIZE else 0) + Int.SIZE_BYTES + it.movie.size * FRAME
        }
        val buffer = littleEndian(size)
        buffer.put(MAGIC).putShort(VERSION).putInt(run.segments.size)
        for (segment in run.segments) {
            buffer.put(segment.waitFor?.code?.toByte() ?: NONE)
            val rng = segment.rng
            buffer.put(if (rng != null) PRESENT else NONE)
            if (rng != null) RngCodec.write(buffer, rng)
            buffer.putInt(segment.movie.size)
            segment.movie.frames.forEach { buffer.frame(it) }
        }
        return buffer.array()
    }

    /** Null when [bytes] is not a run any known version reads */
    public fun decode(bytes: ByteArray): Run? {
        val buffer = bytes.littleEndian()
        return try {
            val magic = ByteArray(MAGIC.size).also(buffer::get)
            if (!magic.contentEquals(MAGIC)) return null
            val run = when (buffer.short) {
                SINGLE_MOVIE -> buffer.movie()?.let(Run::of)
                SEGMENTS -> buffer.segments(withRng = false)
                VERSION -> buffer.segments(withRng = true)
                else -> null
            }
            run?.takeIf { !buffer.hasRemaining() }
        } catch (_: BufferUnderflowException) {
            null
        }
    }

    private fun ByteBuffer.segments(withRng: Boolean): Run? {
        val count = int
        if (count < 0) return null
        val segments = List(count) {
            val code = get().toInt()
            val sync = if (code == NONE.toInt()) null else SyncPoint.of(code) ?: return null
            val rng = if (withRng) {
                when (get()) {
                    NONE -> null
                    PRESENT -> RngCodec.read(this) ?: return null
                    else -> return null
                }
            } else {
                null
            }
            Segment(movie() ?: return null, sync, rng)
        }
        return Run(segments)
    }

    private fun ByteBuffer.movie(): Movie? {
        val count = int
        if (count < 0 || remaining() < count.toLong() * FRAME) return null
        return Movie(List(count) {
            val buttons = short.toInt() and 0xFFFF
            PadFrame.of(buttons, FloatArray(PadFrame.AXIS_COUNT) { float })
        })
    }

    private fun ByteBuffer.frame(frame: PadFrame) {
        putShort(frame.buttons.toShort())
        frame.axes.forEach(::putFloat)
    }
}
