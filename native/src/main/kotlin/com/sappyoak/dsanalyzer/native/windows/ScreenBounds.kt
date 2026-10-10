package com.sappyoak.dsanalyzer.native.windows

import java.lang.foreign.MemorySegment
import java.lang.foreign.StructLayout

/**
 * A rectangle in virtual-screen coordinates, as width and height rather than as edges.
 *
 * Win32 hands out `RECT`s, which are edges and exclusive on the right and bottom. Converting once
 * here means the off-by-one only has one place to live, and every caller downstream gets the size
 * it was going to compute anyway.
 *
 * The origin can be negative: a monitor to the left of the primary one starts at a negative x.
 */
public data class ScreenBounds(
    public val x: Int,
    public val y: Int,
    public val width: Int,
    public val height: Int
) {
    public val isEmpty: Boolean get() = width <= 0 || height <= 0

    /** The aspect the game is actually rendering at, which the frustum should agree with */
    public val aspect: Float get() = if (height <= 0) 0f else width.toFloat() / height.toFloat()

    public fun sameSizeAs(other: ScreenBounds): Boolean =
        width == other.width && height == other.height

    internal companion object {
        /** Reads a `RECT`-shaped [layout] out of [segment], turning its edges into a size */
        fun between(segment: MemorySegment, layout: StructLayout): ScreenBounds {
            val left = segment.intField(layout, "left")
            val top = segment.intField(layout, "top")
            return ScreenBounds(
                x = left,
                y = top,
                width = segment.intField(layout, "right") - left,
                height = segment.intField(layout, "bottom") - top
            )
        }
    }
}