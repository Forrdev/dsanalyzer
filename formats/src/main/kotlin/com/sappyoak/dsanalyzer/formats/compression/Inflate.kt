package com.sappyoak.dsanalyzer.formats.compression

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import java.io.ByteArrayInputStream
import java.util.zip.InflaterInputStream

/** Inflates ZLIB wrapped data */
internal fun ByteArray.inflate(expectedSize: Int?): ByteArray = InflaterInputStream(ByteArrayInputStream(this)).use { stream ->
    if (expectedSize == null) {
        return@use stream.readBytes()
    }

    val output = stream.readNBytes(expectedSize)
    if (output.size != expectedSize) {
        throw BinaryFormatException(
            "Declared $expectedSize uncompressed bytes but only ${output.size} inflated",
            0
        )
    }

    if (stream.read() != -1) {
        throw BinaryFormatException("More than the declared $expectedSize bytes inflated", 0)
    }

    output
}