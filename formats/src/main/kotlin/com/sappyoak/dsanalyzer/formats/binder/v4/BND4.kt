package com.sappyoak.dsanalyzer.formats.binder.v4

import com.sappyoak.dsanalyzer.formats.binder.Binder
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

internal const val BND4_MAGIC = "BND4"

/** Reads a self-contained fourth generation binder */
fun readBND4(reader: BinaryReader): Binder {
    val shared = reader.readVersion4SharedFields(BND4_MAGIC)
    val entries = reader.at(shared.entrySize) {
        List(shared.entryCount) { readVersion4Entry(shared) }
    }

    return Binder(
        version = shared.version,
        flags = shared.flags,
        entries = entries
    )
}