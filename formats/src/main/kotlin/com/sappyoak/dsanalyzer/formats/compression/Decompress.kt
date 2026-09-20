package com.sappyoak.dsanalyzer.formats.compression

import java.lang.foreign.MemorySegment
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.MemorySegmentBinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue

public fun ByteArray.decompress(): ByteArray {
    val reader = MemorySegmentBinaryReader(MemorySegment.ofArray(this))
    val type = reader.getCompressionType()
    return reader.decompress(type)
}

public fun BinaryReader.isCompressed(): Boolean = getCompressionType() != CompressionType.None
public fun BinaryReader.decompress(): ByteArray = decompress(getCompressionType())

private fun BinaryReader.decompress(type: CompressionType): ByteArray {
    order = ByteOrder.BIG_ENDIAN
    position = 0

    return when (type) {
        CompressionType.DCX -> decompressDCX()
        CompressionType.DCP -> decompressDCP()
        CompressionType.ZLIB -> readBytes(remaining.toInt()).inflate(null)
        CompressionType.None -> readBytes(remaining.toInt())
    }
}

private fun BinaryReader.decompressDCX(): ByteArray {
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