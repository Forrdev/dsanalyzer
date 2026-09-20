package com.sappyoak.dsanalyzer.formats.binder.v3

import com.sappyoak.dsanalyzer.formats.binder.Binder
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

internal const val BND3_MAGIC = "BND3"

fun readBND3(reader: BinaryReader): Binder {
    val shared = reader.readVersion3SharedFields(BND3_MAGIC)
    val entries = List(shared.entryCount) {
        reader.readVersion3Entry(shared.flags, shared.bigEndian)
    }

    return Binder(
        version = shared.version,
        flags = shared.flags,
        entries = entries
    )
}