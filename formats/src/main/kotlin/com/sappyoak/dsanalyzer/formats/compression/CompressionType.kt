package com.sappyoak.dsanalyzer.formats.compression

import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.readUByte

enum class CompressionType {
    None,
    DCX,
    DCP,
    ZLIB;
}

public fun BinaryReader.getCompressionType(): CompressionType {
    if (size < 4) return CompressionType.None

    return when (at(0) { readAscii(4) }) {
        "DCX\u0000" -> CompressionType.DCX
        "DCP\u0000" -> CompressionType.DCP
        else -> if (isBareZlib()) CompressionType.ZLIB else CompressionType.None
    }
}

private fun BinaryReader.isBareZlib(): Boolean = size >= 2 && at(0) {
    if (readUByte().toInt() != 0x78) {
        return@at false
    }

    when (readUByte().toInt()) {
        0x01, 0x5E, 0x9C, 0xDA -> true
        else -> false
    }
}