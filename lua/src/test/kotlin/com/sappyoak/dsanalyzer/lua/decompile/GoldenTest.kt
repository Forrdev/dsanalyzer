package com.sappyoak.dsanalyzer.lua.decompile

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

import com.sappyoak.dsanalyzer.lua.chunk.readLuaChunk

/**
 * Each fixture is Lua written in the decompiler's own style next to what Lua 5.0.3's luac made of it
 * (with `Instruction` as a 32-bit int so the header matches DS1's), so reading the bytecode back must
 * give the source exactly. `stripped` was compiled with `-s`, as every PTDE script is, so its locals are
 * recovered from the code alone. A change to any expected source changes what installations' cached
 * scripts would hold, so bump [DECOMPILER_REVISION] with it
 */
private val GOLDEN = listOf("locals", "functions", "conditions", "loops", "scope", "stripped")

private fun resource(name: String): ByteArray =
    checkNotNull(GoldenTest::class.java.getResourceAsStream("/golden/$name")) { "Missing fixture $name" }.readBytes()

class GoldenTest : FunSpec({
    GOLDEN.forEach { name ->
        test("$name.luac reads back as $name.lua") {
            val decompiled = decompile(readLuaChunk(resource("$name.luac")))

            decompiled.failures shouldHaveSize 0
            decompiled.source shouldBe resource("$name.lua").decodeToString()
        }
    }

    test("a shape outside the rebuilt set stays as disassembly") {
        val decompiled = decompile(readLuaChunk(resource("unsupported.luac")))

        decompiled.failures shouldHaveSize 1
        decompiled.source shouldContain "-- decompile failed: a jump that is not a break or an if"
        decompiled.source shouldContain "JMP"
    }
})
