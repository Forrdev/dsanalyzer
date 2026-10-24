package com.sappyoak.dsanalyzer.formats.havok

import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.math.Mat4
import com.sappyoak.dsanalyzer.shared.math.Vec3
import com.sappyoak.dsanalyzer.shared.math.Vec4
import com.sappyoak.dsanalyzer.shared.math.asDirection
import com.sappyoak.dsanalyzer.shared.math.asPoint

/** Longer than any name a Dark Souls Havok file stores */
private const val STRING_LIMIT = 256

/**
 * A Havok file's data block, read by offset. A pointer is never the stored word: each container
 * says through its own tables which words are pointers and where they go, and a word no table
 * names reads as null
 */
public abstract class HavokData(private val bytes: ByteArray, public val pointerSize: Int) {
    private val buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)

    public val size: Int get() = bytes.size

    public fun byte(at: Int): Int = bytes[at].toInt() and 0xFF
    public fun unsignedShort(at: Int): Int = buffer.getShort(at).toInt() and 0xFFFF
    public fun int(at: Int): Int = buffer.getInt(at)
    public fun float(at: Int): Float = buffer.getFloat(at)

    public fun vec3(at: Int): Vec3 = Vec3(float(at), float(at + 4), float(at + 8))
    public fun vec4(at: Int): Vec4 = Vec4(float(at), float(at + 4), float(at + 8), float(at + 12))

    /**
     * An 'hkTransform': three rotation columns, then the translation, each an 'hkVector4'. A column
     * is where an axis goes, so it is a row here. Havok leaves the w lanes unset; they are not kept
     */
    public fun transform(at: Int): Mat4 = Mat4(
        vec3(at).asDirection(),
        vec3(at + 16).asDirection(),
        vec3(at + 32).asDirection(),
        vec3(at + 48).asPoint()
    )

    /** Where the pointer stored at [at] points, or null for a null pointer */
    public abstract fun pointer(at: Int): Int?

    /** The 'hkArray' stored at [at] */
    public abstract fun array(at: Int): HkArray

    /** The null-terminated ASCII string the pointer at [at] points to */
    public fun string(at: Int): String? {
        val start = pointer(at) ?: return null
        var end = start
        while (end < bytes.size && end - start < STRING_LIMIT && bytes[end] != 0.toByte()) end++
        return String(bytes, start, end - start, Charsets.US_ASCII)
    }
}
