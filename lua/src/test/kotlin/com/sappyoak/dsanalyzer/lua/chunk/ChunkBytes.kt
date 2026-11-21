package com.sappyoak.dsanalyzer.lua.chunk

import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.Charset

private const val SBX_BIAS = 131071

internal fun abc(op: Opcode, a: Int, b: Int = 0, c: Int = 0): Int = op.ordinal or (c shl 6) or (b shl 15) or (a shl 24)
internal fun abx(op: Opcode, a: Int, bx: Int): Int = op.ordinal or (bx shl 6) or (a shl 24)
internal fun asbx(op: Opcode, a: Int, sbx: Int): Int = abx(op, a, sbx + SBX_BIAS)

/** A function to dump; constants are null, a Double or a String */
internal class TestFunction(
    val source: String? = null,
    val code: List<Int> = emptyList(),
    val constants: List<Any?> = emptyList(),
    val locals: List<LocalVariable> = emptyList(),
    val upvalueNames: List<String> = emptyList(),
    val functions: List<TestFunction> = emptyList(),
    val lines: List<Int> = emptyList(),
    val lineDefined: Int = 0,
    val params: Int = 0
)

/** Dumps [main] the way 5.0's ldump.c does, little-endian, with [sizeT]-byte lengths and strings in [charset] */
internal fun chunkBytes(
    main: TestFunction,
    sizeT: Int = 8,
    header: ByteArray = header(sizeT),
    charset: Charset = Charset.forName("Shift_JIS")
): ByteArray = Dump(sizeT, charset).apply { bytes(header); function(main) }.out.toByteArray()

internal fun header(sizeT: Int): ByteArray =
    byteArrayOf(0x1B, 'L'.code.toByte(), 'u'.code.toByte(), 'a'.code.toByte(), 0x50, 1, 4, sizeT.toByte()) +
            byteArrayOf(4, 6, 8, 9, 9, 8) +
            little(8) { putDouble(3.14159265358979323846E7) }

private fun little(size: Int, put: ByteBuffer.() -> Unit): ByteArray =
    ByteBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN).apply(put).array()

private class Dump(private val sizeT: Int, private val charset: Charset) {
    val out = ByteArrayOutputStream()

    fun bytes(bytes: ByteArray) = out.write(bytes)
    fun int(value: Int) = bytes(little(4) { putInt(value) })
    fun byte(value: Int) = out.write(value)

    fun string(text: String?) {
        val encoded = text?.toByteArray(charset)?.plus(0)
        val length = encoded?.size ?: 0
        bytes(if (sizeT == 8) little(8) { putLong(length.toLong()) } else little(4) { putInt(length) })
        encoded?.let(::bytes)
    }

    fun function(f: TestFunction) {
        string(f.source)
        int(f.lineDefined)
        byte(f.upvalueNames.size)
        byte(f.params)
        byte(0)
        byte(8)
        int(f.lines.size).also { f.lines.forEach(::int) }
        int(f.locals.size).also { f.locals.forEach { string(it.name); int(it.startPc); int(it.endPc) } }
        int(f.upvalueNames.size).also { f.upvalueNames.forEach(::string) }
        int(f.constants.size).also { f.constants.forEach(::constant) }
        int(f.functions.size).also { f.functions.forEach(::function) }
        int(f.code.size).also { f.code.forEach(::int) }
    }

    private fun constant(value: Any?) = when (value) {
        null -> byte(0)
        is Double -> { byte(3); bytes(little(8) { putDouble(value) }) }
        is String -> { byte(4); string(value) }
        else -> error("No constant type for $value")
    }
}
