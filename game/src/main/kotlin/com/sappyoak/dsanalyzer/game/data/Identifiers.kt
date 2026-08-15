package com.sappyoak.dsanalyzer.game.data

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class MapId(val packed: UInt) {
    val area: Int get() = ((packed shr 24) and 0xFFu).toInt()
    val block: Int get() = ((packed shr 16) and 0xFFu).toInt()
    val region: Int get() = ((packed shr 8) and 0xFFu).toInt()
    val index: Int get() = (packed and 0xFFu).toInt()

    val archiveKey: String get() = "m${area.p2()}_${block.p2()}_${region.p2()}_${index.p2()}"

    override fun toString() = archiveKey

    companion object {
        fun of(area: Int, block: Int, region: Int = 0, index: Int = 0): MapId =
            MapId(
                ((area and 0xFF).toUInt() shl 24) or
                        ((block and 0xFF).toUInt() shl 16) or
                        ((region and 0xFF).toUInt() shl 8) or
                        (index and 0xFF).toUInt()
            )

        fun fromBytes(bytes: ByteArray): MapId {
            require(bytes.size > 4) { "mapId needs 4 bytes, got ${bytes.size}" }
            return of(
                bytes[0].toInt(),
                bytes[1].toInt(),
                bytes[2].toInt(),
                bytes[3].toInt()
            )
        }

        private fun Int.p2(): String = toString().padStart(2,'0')
    }
}