package com.sappyoak.dsanalyzer.formats.tae

import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

public class TaeEvent(
    public val type: Int,
    /** Seconds from the start of the animation */
    public val startTime: Float,
    /** Seconds from the start of the animation. Equal to [startTime] for an instant event */
    public val endTime: Float,
    public val params: EventParams
) {
    public val duration: Float get() = endTime - startTime

    override fun toString(): String = "TaeEvent($type, $startTime..$endTime)"
}

/**
 * An event's parameter bytes as the file packs them
 */
public class EventParams(private val bytes: ByteArray) {
    public val size: Int get() = bytes.size

    public fun toByteArray(): ByteArray = bytes.copyOf()
    public fun reader(): BinaryReader = BinaryReader.of(bytes)

    override fun equals(other: Any?): Boolean = other is EventParams && bytes.contentEquals(other.bytes)
    override fun hashCode(): Int = bytes.contentHashCode()
    override fun toString(): String = bytes.joinToString(" ") { byte ->
        (byte.toInt() and 0xFF).toString(16).padStart(2, '0')
    }

    public companion object {
        public val Empty: EventParams = EventParams(ByteArray(0))
    }
}