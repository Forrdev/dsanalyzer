package com.sappyoak.dsanalyzer.game.world.maps

import kotlinx.serialization.Serializable

private val PATTERN = Regex("""m(\d{2})_(\d{2})_(\d{2})_(\d{2})""")
private const val NAME_LENGTH = 12

@JvmInline
@Serializable
public value class MapId(public val packed: Int) {
    public val area: Int get() = (packed ushr 24) and 0xFF
    public val block: Int get() = (packed ushr 16) and 0xFF
    public val region: Int get() = (packed ushr 8) and 0xFF
    public val index: Int get() = packed and 0xFF

    public val withoutVariant: MapId get() = MapId(packed and 0xFFFFFF00.toInt())
    public val name: String get() = toString()

    override fun toString(): String = buildString(NAME_LENGTH) {
        append('m')
        appendComponent(area)
        append('_')
        appendComponent(block)
        append('_')
        appendComponent(region)
        append('_')
        appendComponent(index)
    }

    public companion object {
        public fun of(area: Int, block: Int, region: Int = 0, index: Int = 0): MapId =
            MapId((area shl 24) or (block shl 16) or (region shl 8) or index)

        public fun parse(text: String): MapId? {
            val match = PATTERN.matchEntire(text.trim()) ?: return null
            val (area, block, region, index) = match.destructured
            return of (area.toInt(), block.toInt(), region.toInt(), index.toInt())
        }
    }
}

private fun StringBuilder.appendComponent(value: Int) {
    if (value < 10) append('0')
    append(value)
}