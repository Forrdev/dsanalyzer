package com.sappyoak.dsanalyzer.lua.disasm

import com.sappyoak.dsanalyzer.lua.chunk.LuaConstant
import com.sappyoak.dsanalyzer.lua.chunk.LuaFunction
import com.sappyoak.dsanalyzer.lua.chunk.RK_CONSTANT_BASE
import com.sappyoak.dsanalyzer.lua.chunk.isConstant

private val IDENTIFIER = Regex("[A-Za-z_][A-Za-z0-9_]*")

/** Names operands the way the source would: locals by their debug name, constants by value */
internal class Operands(private val function: LuaFunction, private val pc: Int) {
    /** A register, by its local's name when one is live there */
    fun r(register: Int): String = function.localName(register, pc) ?: "r$register"

    /** A register or, past [RK_CONSTANT_BASE], a constant */
    fun rk(operand: Int): String = if (isConstant(operand)) k(operand - RK_CONSTANT_BASE) else r(operand)

    fun k(index: Int): String = function.constants.getOrNull(index)?.toString() ?: "k$index?"

    /** A constant used as a global's name, written bare when it is one */
    fun global(index: Int): String = (function.constants.getOrNull(index) as? LuaConstant.Text)?.value ?: k(index)

    /** [table] indexed by an RK [key], as a field access when the key is a name */
    fun index(table: String, key: Int): String {
        val name = (function.constants.getOrNull(key - RK_CONSTANT_BASE) as? LuaConstant.Text)?.value
        return if (isConstant(key) && name != null && IDENTIFIER.matches(name)) "$table.$name" else "$table[${rk(key)}]"
    }

    fun u(index: Int): String = function.upvalueNames.getOrNull(index) ?: "u$index"

    /** Registers [from] to [to] inclusive, or "" for an empty range */
    fun range(from: Int, to: Int): String = (from..to).joinToString(", ") { r(it) }

    /** The pc a jump of [offset] lands on, counted from the instruction after this one */
    fun target(offset: Int): Int = pc + 1 + offset
}
