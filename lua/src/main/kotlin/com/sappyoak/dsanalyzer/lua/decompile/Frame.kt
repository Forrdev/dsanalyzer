package com.sappyoak.dsanalyzer.lua.decompile

import com.sappyoak.dsanalyzer.lua.chunk.LuaFunction

/**
 * One function being rebuilt, its code, the locals guess in force, and its upvalue names, which for a
 * stripped function come from the locals its parent captured
 */
internal class Frame(val function: LuaFunction, val scope: LocalScope, val upvalueNames: List<String>) {
    fun upvalue(index: Int): String = upvalueNames.getOrNull(index) ?: unsupported("an upvalue without a name")
}
