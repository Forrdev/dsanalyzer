package com.sappyoak.dsanalyzer.lua.chunk

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.nio.ByteOrder
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException

private val CHILD = TestFunction(
    upvalueNames = listOf("range"),
    code = listOf(abc(Opcode.GETUPVAL, 0, 0), abc(Opcode.RETURN, 0, 2)),
    lineDefined = 3
)

private val MAIN = TestFunction(
    source = "@ai/test_battle.lua",
    constants = listOf(null, 2.5, "攻撃対象"),
    locals = listOf(LocalVariable("range", 1, 4)),
    functions = listOf(CHILD),
    lines = listOf(1, 1, 2, 2),
    code = listOf(
        abx(Opcode.LOADK, 0, 1),
        abx(Opcode.CLOSURE, 1, 0),
        abc(Opcode.MOVE, 0, 0),
        abc(Opcode.RETURN, 0, 1)
    )
)

class ChunkReaderTest : FunSpec({
    test("a DSR chunk with 8-byte lengths reads back") {
        val chunk = readLuaChunk(chunkBytes(MAIN, sizeT = 8))

        chunk.header shouldBe ChunkHeader(ByteOrder.LITTLE_ENDIAN, 8)
        with(chunk.main) {
            source shouldBe "@ai/test_battle.lua"
            constants shouldContainExactly
                    listOf(LuaConstant.Nil, LuaConstant.Number(2.5), LuaConstant.Text("攻撃対象"))
            locals shouldContainExactly listOf(LocalVariable("range", 1, 4))
            code.map { it.opcode } shouldContainExactly listOf(Opcode.LOADK, Opcode.CLOSURE, Opcode.MOVE, Opcode.RETURN)
            lineAt(2) shouldBe 2
        }
    }

    test("strings are read as Shift-JIS, or as UTF-8 when every one of them is valid UTF-8") {
        readLuaChunk(chunkBytes(MAIN)).charset shouldBe Charset.forName("Shift_JIS")

        val utf8 = readLuaChunk(chunkBytes(MAIN, charset = StandardCharsets.UTF_8))
        utf8.charset shouldBe StandardCharsets.UTF_8
        utf8.main.constants[2] shouldBe LuaConstant.Text("攻撃対象")
    }

    test("a PTDE chunk with 4-byte lengths reads the same") {
        readLuaChunk(chunkBytes(MAIN, sizeT = 4)).main.constants.size shouldBe 3
    }

    test("a nested function without its own source takes its parent's") {
        val child = readLuaChunk(chunkBytes(MAIN)).main.functions.single()

        child.source shouldBe "@ai/test_battle.lua"
        child.upvalueNames shouldContainExactly listOf("range")
        child.lineDefined shouldBe 3
    }

    test("locals name registers in the order they are live") {
        val main = readLuaChunk(chunkBytes(MAIN)).main

        main.localName(0, 0) shouldBe null
        main.localName(0, 1) shouldBe "range"
        main.localName(1, 1) shouldBe null
        main.localName(0, 4) shouldBe null
    }

    test("anything but a 5.0 chunk is refused") {
        val bytes = chunkBytes(MAIN)
        shouldThrow<BinaryFormatException> { readLuaChunk(bytes.copyOf().also { it[4] = 0x51 }) }
            .message shouldContain "0x51"
        shouldThrow<BinaryFormatException> { readLuaChunk(bytes.copyOf().also { it[0] = 0 }) }
    }

    test("unknown opcodes and trailing bytes are refused") {
        shouldThrow<BinaryFormatException> { readLuaChunk(chunkBytes(TestFunction(code = listOf(40)))) }
        shouldThrow<BinaryFormatException> { readLuaChunk(chunkBytes(MAIN) + 0) }
    }
})
