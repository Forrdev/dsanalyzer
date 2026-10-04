package com.sappyoak.dsanalyzer.formats.binder.v3


import com.sappyoak.dsanalyzer.formats.binder.Binder
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue

internal const val BXF3_HEADER_MAGIC = "BHF3"
internal const val BXF3_DATA_MAGIC = "BDF3"

/**
 * Reads the header half of a version 3 split binder
 */
fun readBXF3Header(reader: BinaryReader): Binder {
    val shared = reader.readVersion3SharedFields(BXF3_HEADER_MAGIC)
    val entries = List(shared.entryCount) {
        reader.readVersion3Entry(shared.flags, shared.bitBigEndian)
    }

    return Binder(
        version = shared.version,
        flags = shared.flags,
        entries = entries
    )
}

/**
 * Confirms the data half of the binder is what it claims to be
 */
fun checkBXF3Data(reader: BinaryReader) {
    reader.at(0) {
        assertValue(BXF3_DATA_MAGIC) { readFixedString(4) }
        skip(8)
        assertValue(0) { readInt() }
    }
}