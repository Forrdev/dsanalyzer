package com.sappyoak.dsanalyzer.formats.binder

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.hasFlag
import com.sappyoak.dsanalyzer.shared.binary.readUByte
import com.sappyoak.dsanalyzer.shared.binary.reverseBits

/** Flags indicating the features supported by a binder */
@JvmInline
@Serializable
public value class BinderFormatFlags(public val bits: Int) {
    public val isBigEndian: Boolean get() = bits.hasFlag(BIG_ENDIAN)
    public val hasIds: Boolean get() = bits.hasFlag(HAS_IDS)
    public val hasNames: Boolean get() = bits.hasFlag(HAS_NAMES1 or HAS_NAMES2)
    public val hasLongOffsets: Boolean get() = bits.hasFlag(LONG_OFFSETS)
    public val hasCompression: Boolean get() = bits.hasFlag(COMPRESSION)

    public companion object {
        private const val BIG_ENDIAN = 0x01
        private const val HAS_IDS = 0x02
        private const val HAS_NAMES1 = 0x04
        private const val HAS_NAMES2 = 0x08
        private const val LONG_OFFSETS = 0x10
        private const val COMPRESSION = 0x20
        private const val FLAG_7 = 0x80

        public fun read(reader: BinaryReader, bitBigEndian: Boolean): BinderFormatFlags {
            val raw = reader.readUByte().toInt()
            val alreadyOrdered = bitBigEndian || (raw.hasFlag(FLAG_7) && !raw.hasFlag(BIG_ENDIAN))
            return BinderFormatFlags(if (alreadyOrdered) raw else raw.reverseBits())
        }
    }
}

/** Flags indicating features for specific files within the binder */
@JvmInline
@Serializable
public value class BinderEntryFlags(public val bits: Int) {
    public val hasCompression: Boolean get() = bits.hasFlag(COMPRESSION)

    public companion object {
        private const val COMPRESSION = 0x01

        public fun read(reader: BinaryReader, bitBigEndian: Boolean): BinderEntryFlags {
            val raw = reader.readUByte().toInt()
            return BinderEntryFlags(if (bitBigEndian) raw else raw.reverseBits())
        }
    }
}