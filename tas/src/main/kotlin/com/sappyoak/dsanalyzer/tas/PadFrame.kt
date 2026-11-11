package com.sappyoak.dsanalyzer.tas

import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.abs

/**
 * One frame of pad input, as `PadDevice_Poll` leaves it for an XInput pad
 *
 * [buttons] is the XInput button word. Sticks run -1..1, triggers 0..1
 */
public data class PadFrame(
    public val buttons: Int = 0,
    public val leftX: Float = 0f,
    public val leftY: Float = 0f,
    public val rightX: Float = 0f,
    public val rightY: Float = 0f,
    public val leftTrigger: Float = 0f,
    public val rightTrigger: Float = 0f
) {
    init {
        require(buttons in 0..0xFFFF) { "The button word is 16 bits" }
    }

    internal val axes: List<Float> get() = listOf(leftX, leftY, rightX, rightY, leftTrigger, rightTrigger)

    /** No button held and every axis inside [deadzone] */
    public fun isIdle(deadzone: Float = DEFAULT_DEADZONE): Boolean = buttons == 0 && axes.all { abs(it) < deadzone }

    /** The ring's record: the button word zero-extended, then the axes in [axes] order, then padding */
    internal fun encode(): ByteArray {
        val buffer = ByteBuffer.allocate(Mailbox.RECORD_SIZE).order(ByteOrder.LITTLE_ENDIAN)
        buffer.putInt(buttons)
        axes.forEach(buffer::putFloat)
        return buffer.array()
    }

    internal companion object {
        /** Wide enough for a resting stick's drift */
        const val DEFAULT_DEADZONE = 0.2f

        /** Floats in a record after its button word */
        const val AXIS_COUNT = 6

        fun decode(record: ByteArray): PadFrame {
            val buffer = ByteBuffer.wrap(record).order(ByteOrder.LITTLE_ENDIAN)
            val buttons = buffer.int and 0xFFFF
            return of(buttons, FloatArray(AXIS_COUNT) { buffer.float })
        }

        /** From a button word and the axes in [axes] order */
        fun of(buttons: Int, axes: FloatArray): PadFrame =
            PadFrame(buttons, axes[0], axes[1], axes[2], axes[3], axes[4], axes[5])
    }
}
