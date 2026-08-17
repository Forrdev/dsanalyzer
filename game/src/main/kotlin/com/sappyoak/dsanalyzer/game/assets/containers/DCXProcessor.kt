package com.sappyoak.dsanalyzer.game.assets.containers

import java.util.zip.Inflater

import com.sappyoak.dsanalyzer.shared.io.toReader

fun ByteArray.isDCXCompressed(): Boolean {
    if (size < 8) return false
    val magic = decodeToString(0, 4)
    return magic == DCXProcessor.DCX_MAGIC_STR || magic == DCXProcessor.DCP_MAGIC_STR
}

object DCXProcessor {
    private const val MAX_REASONABLE_SIZE = 512 * 1024 * 1024
    private const val OFFSET_DCS = 0x18
    private const val OFFSET_METHOD = 0x28
    private const val COMPRESSION_METHOD = "DFLT"
    private const val DFLT_PAYLOAD_OFFSET = 0x4C
    private const val DCS_MAGIC_STR = "DCS\u0000"

    const val DCX_MAGIC_STR = "DCX\u0000"
    const val DCP_MAGIC_STR = "DCP\u0000"

    fun process(bytes: ByteArray): Outcome {
        if (!bytes.isDCXCompressed()) return Outcome.Uncompressed(bytes)
        if (bytes.size < DFLT_PAYLOAD_OFFSET) {
            return Outcome.Failure("DCX shorter than its own header")
        }

        if (bytes.decodeToString(OFFSET_DCS, OFFSET_DCS + 4) != DCS_MAGIC_STR) {
            return Outcome.Failure(
                "Expected DCX block at 0x18, this is a DCX variant this processor does " +
                "not handle (EDGE, KRAK, and ZSTD all differ here)"
            )
        }

        val reader = bytes.toReader(false)
        reader.seek(OFFSET_DCS)
        val uncompressedSize = reader.i32()
        val compressedSize = reader.i32()

        reader.seek(OFFSET_METHOD)
        val method = reader.ascii(4)
        if (method != COMPRESSION_METHOD) {
            return Outcome.Failure(
                "Compression method '$method' is not supported. DS1 uses DFLT"
            )
        }

        if (uncompressedSize <= 0 || uncompressedSize > MAX_REASONABLE_SIZE) {
            return Outcome.Failure("Implausible uncompressed size: $uncompressedSize")
        }

        val payloadEnd = if (compressedSize > 0) {
            (DFLT_PAYLOAD_OFFSET + compressedSize).coerceAtMost(bytes.size)
        } else {
            bytes.size
        }

        if (payloadEnd <= DFLT_PAYLOAD_OFFSET) {
            return Outcome.Failure("DCX payload is empty")
        }

        val payload = reader.slice(DFLT_PAYLOAD_OFFSET, payloadEnd)
        val result = runCatching { inflate(payload, uncompressedSize) }
            .getOrElse { err ->
                return Outcome.Failure(
                    "Inflate failed. The payload is zlib-wrapped rather than raw deflate",
                    err
                )
            }

        if (result.size != uncompressedSize) {
            return Outcome.Failure(
                "Inflated ${result.size} bytes but the header declares $uncompressedSize. " +
                "The file is truncated or the payload offset is wrong"
            )
        }

        return Outcome.Decompressed(result)
    }

    private fun inflate(compressed: ByteArray, expectedSize: Int): ByteArray {
        val inflater = Inflater()

        try {
            inflater.setInput(compressed)
            val output = ByteArray(expectedSize)
            var written = 0

            while (written < expectedSize && !inflater.finished()) {
                val n = inflater.inflate(output, written, expectedSize - written)
                if (n == 0) {
                    // neither finished nor progressing means truncated input. Returning what
                    // there is would hand a parser a plausible looking partial file
                    if (inflater.needsInput() || inflater.needsDictionary()) break
                }
                written += n
            }

            return if (written == expectedSize) output else output.copyOf(written)
        } finally {
            inflater.end()
        }
    }

    sealed class Outcome {
        val bytesOrNull: ByteArray? get() = when (this) {
            is Decompressed -> data
            is Uncompressed -> data
            else -> null
        }

        class Decompressed(val data: ByteArray) : Outcome()
        class Uncompressed(val data: ByteArray) : Outcome()
        data class Failure(val reason: String, val error: Throwable? = null) : Outcome()
    }
}