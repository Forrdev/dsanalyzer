package com.sappyoak.dsanalyzer.formats.emevd

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue
import com.sappyoak.dsanalyzer.shared.binary.assertZeros
import com.sappyoak.dsanalyzer.shared.binary.at

private const val MAGIC = "ELD\u0000"
private const val FORMAT_MARKER = 0x65
private const val DS1_VERSION = 0xCC

public class EventNames(private val byId: Map<Long, String>) {
    public val size: Int get() = byId.size

    /** Every name the file holds, for a caller indexing or translating them */
    public val all: Map<Long, String> get() = byId

    public operator fun get(eventId: Long): String? = byId[eventId]

    public companion object {
        public val Empty: EventNames = EventNames(emptyMap())
    }
}

public fun readEmeld(reader: BinaryReader): EventNames {
    reader.order = ByteOrder.LITTLE_ENDIAN
    reader.position = 0
    reader.assertValue(MAGIC) { readAscii(MAGIC.length) }

    val formatAt = reader.position
    val bigEndian = reader.readBoolean()
    val is64Bit = reader.readByte().toInt() == -1

    if (bigEndian || is64Bit) {
        throw BinaryFormatException("Not a DS1 PC event name file (bigEndian=$bigEndian, 64bit=$is64Bit)", formatAt)
    }

    repeat(2) { reader.assertValue(0) { readByte().toInt() } }
    reader.assertValue(FORMAT_MARKER) { readShort().toInt() }
    reader.assertValue(DS1_VERSION) { readShort().toInt() }
    reader.skip(4) // file size

    val eventCount = reader.readInt()
    val eventsOffset = reader.readInt()
    reader.assertZeros(1) // a table no DS1 file uses
    reader.skip(4)
    reader.assertZeros(1) // and another
    reader.skip(4)
    reader.skip(4) // strings length
    val stringsOffset = reader.readInt()
    reader.assertZeros(2)

    reader.position = eventsOffset.toLong()
    val names = List(eventCount) { reader.readEventName(stringsOffset) }
    return EventNames(names.toMap())
}

internal fun BinaryReader.readEventName(stringsOffset: Int): Pair<Long, String> {
    val id = readInt().toLong()
    val nameOffset = readInt()
    assertZeros(1)
    return id to at(stringsOffset + nameOffset) { readUTF16() }
}