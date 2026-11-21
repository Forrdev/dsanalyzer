package com.sappyoak.dsanalyzer.game.world.ai

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.deleteIfExists
import kotlin.io.path.readBytes
import kotlin.io.path.writeText

import com.sappyoak.dsanalyzer.lua.chunk.LuaChunk
import com.sappyoak.dsanalyzer.lua.chunk.readLuaChunk
import com.sappyoak.dsanalyzer.lua.decompile.DecompiledChunk

/**
 * A Lua 5.0 `luac`, used only by tests to compile decompiled source back. It must be built with
 * `typedef unsigned int Instruction;` in `llimits.h`, the 4-byte instructions DS1's chunks have
 */
internal class Luac(private val binary: Path) {
    /**
     * Compiles [decompiled] saved in its chunk's own encoding, or returns the compiler's complaint. [strip]
     * leaves out debug info, to compare against a chunk shipped without it
     */
    fun compile(decompiled: DecompiledChunk, strip: Boolean): Result<LuaChunk> {
        val source = Files.createTempFile("decompiled", ".lua")
        val output = Files.createTempFile("decompiled", ".luac")
        return try {
            source.writeText(decompiled.source, decompiled.charset)
            val flags = if (strip) listOf("-s") else emptyList()
            val command = listOf(binary.toString()) + flags + listOf("-o", output.toString(), source.toString())
            val process = ProcessBuilder(command)
                .redirectErrorStream(true)
                .start()
            val message = process.inputStream.bufferedReader().readText()
            if (process.waitFor() == 0) Result.success(readLuaChunk(output.readBytes()))
            else Result.failure(IllegalStateException(message.trim()))
        } finally {
            source.deleteIfExists()
            output.deleteIfExists()
        }
    }
}
