package com.sappyoak.dsanalyzer.formats.binder

import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.formats.binder.v3.*
import com.sappyoak.dsanalyzer.formats.binder.v4.*
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException

private const val MAGIC_LENGTH = 4

public fun readBinder(reader: BinaryReader, data: BinaryReader = reader): Binder =
    when (val magic = reader.at(0) { readAscii(MAGIC_LENGTH) }) {
        BND3_MAGIC -> readBND3(reader)
        BXF3_HEADER_MAGIC -> {
            requireDataFile(reader, data)
            checkBXF3Data(data)
            readBXF3Header(reader)
        }

        else -> throw BinaryFormatException("Not a supported binder: $magic", 0)
    }

private fun requireDataFile(reader: BinaryReader, data: BinaryReader) {
    if (data === reader) {
        throw BinaryFormatException("A split binder needs its data file passed too", 0)
    }
}