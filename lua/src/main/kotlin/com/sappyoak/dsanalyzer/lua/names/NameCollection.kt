package com.sappyoak.dsanalyzer.lua.names

import java.nio.charset.Charset

import com.sappyoak.dsanalyzer.lua.chunk.LocalVariable
import com.sappyoak.dsanalyzer.lua.chunk.LuaChunk
import com.sappyoak.dsanalyzer.lua.chunk.LuaFunction

/**
 * The names [chunks] keep in their debug info, for [LuaNames]. Where copies disagree the most common names
 * win, the earliest on a tie; functions without debug info or without locals add nothing
 */
public fun collectNames(chunks: List<LuaChunk>): LuaNames {
    val locals = LinkedHashMap<String, MutableList<List<LocalVariable>>>()
    val parameters = LinkedHashMap<String, MutableList<List<String>>>()

    fun collect(function: LuaFunction, charset: Charset, global: String?) {
        if (function.lines.isNotEmpty() && function.locals.isNotEmpty()) {
            locals.getOrPut(functionKey(function, charset)) { mutableListOf() } += function.locals
            if (global != null && function.paramCount > 0) {
                val names = function.locals.take(function.paramCount).map { it.name }
                parameters.getOrPut(global) { mutableListOf() } += names
            }
        }
        val globals = globalFunctionNames(function)
        function.functions.forEachIndexed { index, child -> collect(child, charset, globals[index]) }
    }

    chunks.forEach { collect(it.main, it.charset, global = null) }
    return LuaNames(locals.mapValues { mostCommon(it.value) }, parameters.mapValues { mostCommon(it.value) })
}

private fun <T> mostCommon(values: List<T>): T = values.groupingBy { it }.eachCount().maxBy { it.value }.key
