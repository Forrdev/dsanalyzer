package com.sappyoak.dsanalyzer.formats.binder.v4

import com.sappyoak.dsanalyzer.formats.binder.Binder
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue

internal const val BXF4_HEADER_MAGIC = "BHF4"
internal const val BXF4_DATA_MAGIC = "BDF4"

public fun readBXF4Header(reader: BinaryReader): Binder {
    val shared = reader.readVersion4SharedFields(BXF4_HEADER_MAGIC)
    val entries = reader.at(shared.entrySize) {
        List(shared.entryCount) { readVersion4Entry(shared) }
    }

    return Binder(
        version = shared.version,
        flags = shared.flags,
        entries = entries
    )
}

public fun checkBXF4Data(reader: BinaryReader): Binder {
    reader.at(0) {
        assertValue(BXF4_DATA_MAGIC) { readAscii(BXF4_DATA_MAGIC.length) }
    }
}