package com.sappyoak.dsanalyzer.formats.emevd

import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.at

/**
 * The block of strings and event script refers to by offset, held as bytes
 */
public class StringTable(private val bytes: ByteArray) {
    public val size: Int get() = bytes.size

    public fun shiftJisAt(offset: Int): String = BinaryReader.of(bytes).at(offset) { readStringTerminated(Charsets.UTF_8) }
    public fun toByteArray(): ByteArray = bytes.copyOf()

    public companion object {
        public val Empty: StringTable = StringTable(ByteArray(0))
    }

}