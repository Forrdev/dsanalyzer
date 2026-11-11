package com.sappyoak.dsanalyzer.runtime.write.debugdraw

import com.sappyoak.dsanalyzer.formats.msb.part.CollisionHitFilter
import com.sappyoak.dsanalyzer.runtime.session.CollisionDrawSettings
import com.sappyoak.dsanalyzer.runtime.session.DrawMode
import com.sappyoak.dsanalyzer.runtime.session.FilterColors
import com.sappyoak.dsanalyzer.shared.math.Vec4
import com.sappyoak.dsanalyzer.shared.binary.littleEndian
import com.sappyoak.dsanalyzer.shared.binary.putVec4

/**
 * The block of the tool's own memory every draw cave reads. Turning a filter on is one dword, and
 * nothing here is game memory, so none of it is journaled
 */
internal object DrawControl {
    /** Bit n set draws hit filter n */
    const val Mask: Int = 0x00

    /** [DrawMode.bits] */
    const val Mode: Int = 0x04
    const val RadiusSquared: Int = 0x08

    /** The player's position, cached by the gate for the distance cull */
    const val Position: Int = 0x10

    /** Edition caves' own state up to [Colors], such as render states saved around a flush */
    const val Scratch: Int = 0x20

    /** Wire then fill, four floats each, per hit filter */
    const val Colors: Int = 0x80
    const val COLOR_STRIDE: Int = 0x20
    const val FILTERS: Int = 32

    /** State of an edition's caves that does not fit [Scratch] */
    const val Edition: Int = Colors + FILTERS * COLOR_STRIDE
    const val EDITION_SIZE: Int = 0x100

    const val SIZE: Int = Edition + EDITION_SIZE

    /** Mask, mode and radius² */
    const val HEADER_SIZE: Int = 0x0C
}

/** The mode as the gates test it: bit 0 lo, bit 1 hi */
internal val DrawMode.bits: Int
    get() = when (this) {
        DrawMode.Lo -> 1
        DrawMode.Hi -> 2
        DrawMode.Both -> 3
    }

internal fun CollisionDrawSettings.header(): ByteArray = littleEndian(DrawControl.HEADER_SIZE)
    .putInt(filters.fold(0) { mask, filter -> mask or (1 shl filter.id) })
    .putInt(mode.bits)
    .putFloat(radius?.let { it * it } ?: Float.POSITIVE_INFINITY)
    .array()

internal fun CollisionDrawSettings.colorTable(): ByteArray {
    val table = littleEndian(DrawControl.FILTERS * DrawControl.COLOR_STRIDE)
    for (id in 0 until DrawControl.FILTERS) {
        val filter = CollisionHitFilter.entries.firstOrNull { it.id == id }
        val colors = filter?.let(colors::get) ?: FilterColors.Game
        table.putVec4(colors.wire).putVec4(if (fill) colors.fill else colors.fill.copy(w = 0f))
    }
    return table.array()
}
