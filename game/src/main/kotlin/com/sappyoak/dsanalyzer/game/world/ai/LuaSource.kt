package com.sappyoak.dsanalyzer.game.world.ai

import com.sappyoak.dsanalyzer.lua.decompile.DecompiledChunk

/** A script as source, with the reason for every function that stayed as disassembly */
public data class LuaSource(public val source: String, public val failures: List<String>) {
    public companion object {
        public fun of(decompiled: DecompiledChunk): LuaSource = LuaSource(decompiled.source, decompiled.failures)
    }
}
