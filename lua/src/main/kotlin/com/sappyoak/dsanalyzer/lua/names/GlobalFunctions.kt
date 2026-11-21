package com.sappyoak.dsanalyzer.lua.names

import com.sappyoak.dsanalyzer.lua.chunk.LuaConstant
import com.sappyoak.dsanalyzer.lua.chunk.LuaFunction
import com.sappyoak.dsanalyzer.lua.chunk.Opcode

/**
 * The global each of [function]'s nested functions is stored straight into, by index: `function Name(...)`
 * compiles to a CLOSURE, its captures, then a SETGLOBAL of the same register
 */
internal fun globalFunctionNames(function: LuaFunction): Map<Int, String> {
    val code = function.code
    return code.indices.filter { code[it].opcode == Opcode.CLOSURE }.mapNotNull { pc ->
        val closure = code[pc]
        val store = code.getOrNull(pc + 1 + function.functions[closure.bx].upvalueCount)
        if (store?.opcode != Opcode.SETGLOBAL || store.a != closure.a) return@mapNotNull null
        (function.constants[store.bx] as? LuaConstant.Text)?.let { closure.bx to it.value }
    }.toMap()
}
