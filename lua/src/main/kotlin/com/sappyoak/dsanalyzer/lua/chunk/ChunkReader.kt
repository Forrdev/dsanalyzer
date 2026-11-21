package com.sappyoak.dsanalyzer.lua.chunk

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

private const val TYPE_NIL = 0
private const val TYPE_NUMBER = 3
private const val TYPE_STRING = 4

public fun readLuaChunk(bytes: ByteArray): LuaChunk {
    val probe = readLuaChunk(bytes, StandardCharsets.ISO_8859_1)
    return readLuaChunk(bytes, textCharset(probe.main))
}

private fun readLuaChunk(bytes: ByteArray, charset: Charset): LuaChunk {
    val reader = BinaryReader.of(bytes)
    val header = reader.readChunkHeader()
    val main = FunctionReader(reader, header, charset).function(parentSource = null)
    if (reader.remaining != 0L) {
        throw BinaryFormatException("${reader.remaining} bytes after the main function", reader.position)
    }
    return LuaChunk(header, main, charset)
}

private class FunctionReader(
    private val reader: BinaryReader,
    private val header: ChunkHeader,
    private val charset: Charset
) {
    fun function(parentSource: String?): LuaFunction = with(reader) {
        val source = string() ?: parentSource
        val lineDefined = readInt()
        val upvalueCount = byte()
        val paramCount = byte()
        val isVararg = byte() != 0
        val maxStackSize = byte()

        val lines = list { readInt() }
        val locals = list { LocalVariable(string() ?: fail("A local has no name"), readInt(), readInt()) }
        val upvalueNames = list { string() ?: fail("An upvalue has no name") }
        val constants = list { constant() }
        val functions = list { function(source) }
        val code = list { instruction() }

        LuaFunction(
            source = source,
            lineDefined = lineDefined,
            upvalueCount = upvalueCount,
            paramCount = paramCount,
            isVararg = isVararg,
            maxStackSize = maxStackSize,
            lines = lines,
            locals = locals,
            upvalueNames = upvalueNames,
            constants = constants,
            functions = functions,
            code = code
        )
    }

    private fun constant(): LuaConstant = when (val type = byte()) {
        TYPE_NIL -> LuaConstant.Nil
        TYPE_NUMBER -> LuaConstant.Number(Double.fromBits(reader.readLong()))
        TYPE_STRING -> LuaConstant.Text(string() ?: fail("A string constant is null"))
        else -> fail("Constant of type $type")
    }

    private fun instruction(): Instruction {
        val raw = reader.readInt()
        if (!Instruction.isKnown(raw)) fail("Unknown opcode in 0x%08X".format(raw))
        return Instruction(raw)
    }

    /** A size_t length counting the terminating NUL, or 0 for no string at all */
    private fun string(): String? {
        val length = if (header.sizeTSize == Long.SIZE_BYTES) reader.readLong() else reader.readInt().toLong()
        if (length == 0L) return null
        if (length < 0 || length > reader.remaining) fail("String of $length bytes")
        val bytes = reader.readBytes(length.toInt())
        return decodeText(bytes.copyOf(bytes.size - 1), charset)
    }

    private fun <T> list(read: () -> T): List<T> {
        val count = reader.readInt()
        if (count < 0 || count > reader.remaining) fail("List of $count entries")
        return List(count) { read() }
    }

    private fun byte(): Int = reader.readByte().toInt() and 0xFF

    private fun fail(message: String): Nothing = throw BinaryFormatException(message, reader.position)
}
