package com.sappyoak.dsanalyzer.game.world.ai

import com.sappyoak.dsanalyzer.lua.chunk.Instruction
import com.sappyoak.dsanalyzer.lua.chunk.LuaFunction

/** One way a recompiled function differs from the game's, at the function's path */
internal data class Difference(val function: String, val what: String) {
    override fun toString(): String = "$function: $what"
}

/**
 * Everything that differs between [original] and [rebuilt], function by function. Line info and the source
 * name are left out: they follow formatting, not behaviour
 */
internal fun differences(original: LuaFunction, rebuilt: LuaFunction, path: String = "main"): List<Difference> {
    val own = buildList {
        codeDifference(original.code, rebuilt.code)?.let { add(it) }
        if (original.constants != rebuilt.constants) add("constants")
        if (original.locals != rebuilt.locals) add("locals")
        if (original.upvalueNames != rebuilt.upvalueNames) add("upvalue names")
        if (original.paramCount != rebuilt.paramCount || original.isVararg != rebuilt.isVararg) add("parameters")
        if (original.maxStackSize != rebuilt.maxStackSize) {
            add("stack size ${original.maxStackSize} vs ${rebuilt.maxStackSize}")
        }
        if (original.functions.size != rebuilt.functions.size) {
            add("${original.functions.size} vs ${rebuilt.functions.size} nested functions")
        }
    }.map { Difference(path, it) }

    return own + original.functions.zip(rebuilt.functions).flatMapIndexed { index, (a, b) ->
        differences(a, b, "$path/$index")
    }
}

/** The first instruction that differs, 1-based like a listing */
private fun codeDifference(original: List<Instruction>, rebuilt: List<Instruction>): String? {
    val pc = original.indices.firstOrNull { it >= rebuilt.size || original[it] != rebuilt[it] }
        ?: if (rebuilt.size > original.size) original.size else return null
    return "code from pc ${pc + 1}: ${original.getOrNull(pc).describe()} vs ${rebuilt.getOrNull(pc).describe()}"
}

private fun Instruction?.describe(): String =
    this?.let { "${it.opcode} ${it.a} ${it.b} ${it.c}" } ?: "end"
