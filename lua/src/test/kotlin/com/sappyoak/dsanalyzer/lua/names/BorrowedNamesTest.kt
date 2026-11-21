package com.sappyoak.dsanalyzer.lua.names

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.maps.shouldContainExactly
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.lua.chunk.LuaChunk
import com.sappyoak.dsanalyzer.lua.chunk.LuaFunction
import com.sappyoak.dsanalyzer.lua.chunk.readLuaChunk
import com.sappyoak.dsanalyzer.lua.decompile.decompile

/**
 * `named.luac` is the source of `stripped.luac` compiled with its debug info; `variant.luac` is that source
 * stripped with the bodies of `Update` and `Make` changed, so only their parameters can be borrowed
 */
private fun chunk(name: String): LuaChunk = readLuaChunk(
    checkNotNull(BorrowedNamesTest::class.java.getResourceAsStream("/golden/$name")) { "Missing fixture $name" }
        .readBytes()
)

private fun text(name: String): String =
    checkNotNull(BorrowedNamesTest::class.java.getResourceAsStream("/golden/$name")).readBytes().decodeToString()

private fun LuaFunction.keys(chunk: LuaChunk): List<String> =
    listOf(functionKey(this, chunk.charset)) + functions.flatMap { it.keys(chunk) }

class BorrowedNamesTest : FunSpec({
    val named = chunk("named.luac")
    val names = collectNames(listOf(named))

    test("a function keeps its key with or without debug info") {
        val stripped = chunk("stripped.luac")

        stripped.main.keys(stripped) shouldBe named.main.keys(named)
    }

    test("names are collected for functions with locals, and parameters for global functions") {
        names.locals.keys shouldHaveSize 4
        names.parameters shouldContainExactly mapOf(
            "Act" to listOf("ai", "goal", "paramTbl"),
            "Update" to listOf("ai", "goal"),
            "Make" to listOf("base")
        )
    }

    test("a stripped chunk compiled the same reads back as its named source") {
        decompile(chunk("stripped.luac"), names).source shouldBe decompile(named).source
    }

    test("a changed function keeps only its parameter names") {
        decompile(chunk("variant.luac"), names).source shouldBe text("variant.lua")
    }
})
