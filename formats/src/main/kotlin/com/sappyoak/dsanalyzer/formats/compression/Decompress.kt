package com.sappyoak.dsanalyzer.formats.compression

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue

public fun BinaryReader.isCompressed(): Boolean = getCompressionType() != CompressionType.None
public fun BinaryReader.decompress(): BinaryReader = when (getCompressionType()) {
    CompressionType.None -> this
    CompressionType.DCX -> BinaryReader.ofSegmentBytes(decompressDCX())
    CompressionType.DCP -> BinaryReader.ofSegmentBytes(decompressDCP())
    CompressionType.ZLIB -> BinaryReader.ofSegmentBytes(at(0) { readBytes(size.toInt()) }.inflate(null))
}

private fun BinaryReader.decompressDCX(): ByteArray {
    order = ByteOrder.BIG_ENDIAN

    when (val format = at(0x28) { readAscii(4) }) {
        "DFLT" -> Unit
        else -> throw BinaryFormatException("Unsupported DCX Compression $format", 0x28)
    }

    position = 0x18
    assertValue("DCS\u0000") { readAscii(4) }
    val uncompressedSize = readInt()

    position = 0x44
    assertValue("DCA\u0000") { readAscii(4) }
    skip(4)

    return readBytes((size - position).toInt()).inflate(uncompressedSize)
}

private fun BinaryReader.decompressDCP(): ByteArray {
    order = ByteOrder.BIG_ENDIAN

    when (val format = at(4) { readAscii(4) }) {
        "DFLT" -> Unit
        else -> throw BinaryFormatException("Unsupported DCP compression $format", 4)
    }

    position = 0x20
    assertValue("DCS\u0000") { readAscii(4) }

    val uncompressedSize = readInt()
    val compressedSize = readInt()

    return readBytes(compressedSize).inflate(uncompressedSize)
}