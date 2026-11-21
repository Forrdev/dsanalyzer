package com.sappyoak.dsanalyzer.lua.chunk

import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.CharacterCodingException
import java.nio.charset.Charset
import java.nio.charset.CodingErrorAction
import java.nio.charset.StandardCharsets

/** Where a byte the charset cannot decode is kept: this plus the byte, in the private use area */
private const val RAW_BYTE_BASE = 0xF700

/** FromSoftware's AI scripts are Shift-JIS; the event scripts are UTF-8 */
private val SHIFT_JIS: Charset = Charset.forName("Shift_JIS")

/**
 * The encoding of the strings in [main], read byte for byte as Latin-1: UTF-8 when every string that is not
 * plain ASCII is valid UTF-8, otherwise Shift-JIS
 */
internal fun textCharset(main: LuaFunction): Charset {
    val texts = strings(main).map { it.toByteArray(StandardCharsets.ISO_8859_1) }
        .filter { bytes -> bytes.any { it < 0 } }
    return if (texts.isNotEmpty() && texts.all(::isUtf8)) StandardCharsets.UTF_8 else SHIFT_JIS
}

/**
 * [bytes] decoded without loss: a byte that does not decode, such as a Shift-JIS lead byte whose trail byte
 * was a backslash Lua read as an escape, becomes a character [rawByte] gives back
 */
internal fun decodeText(bytes: ByteArray, charset: Charset): String {
    val decoder = charset.newDecoder()
        .onMalformedInput(CodingErrorAction.REPORT)
        .onUnmappableCharacter(CodingErrorAction.REPORT)
    val input = ByteBuffer.wrap(bytes)
    val output = CharBuffer.allocate(bytes.size + 1)
    while (true) {
        val result = decoder.decode(input, output, true)
        when {
            result.isUnderflow -> break
            result.isError -> repeat(result.length()) { output.put(rawChar(input.get())) }
            else -> result.throwException()
        }
    }
    decoder.flush(output)
    return output.flip().toString()
}

/** [text] back as the bytes [decodeText] read it from */
public fun encodeText(text: String, charset: Charset): ByteArray {
    val out = ByteArrayOutputStream(text.length * 2)
    var start = 0
    text.forEachIndexed { index, char ->
        val byte = rawByte(char) ?: return@forEachIndexed
        out.writeBytes(text.substring(start, index).toByteArray(charset))
        out.write(byte)
        start = index + 1
    }
    out.writeBytes(text.substring(start).toByteArray(charset))
    return out.toByteArray()
}

private fun rawChar(byte: Byte): Char = (RAW_BYTE_BASE + (byte.toInt() and 0xFF)).toChar()

/** The byte [char] stands for when [decodeText] could not decode it */
public fun rawByte(char: Char): Int? = (char.code - RAW_BYTE_BASE).takeIf { it in 0..0xFF }

private fun strings(function: LuaFunction): List<String> =
    function.constants.filterIsInstance<LuaConstant.Text>().map { it.value } +
            function.locals.map { it.name } + function.upvalueNames + function.functions.flatMap(::strings)

private fun isUtf8(bytes: ByteArray): Boolean = try {
    StandardCharsets.UTF_8.newDecoder()
        .onMalformedInput(CodingErrorAction.REPORT)
        .onUnmappableCharacter(CodingErrorAction.REPORT)
        .decode(ByteBuffer.wrap(bytes))
    true
} catch (_: CharacterCodingException) {
    false
}
