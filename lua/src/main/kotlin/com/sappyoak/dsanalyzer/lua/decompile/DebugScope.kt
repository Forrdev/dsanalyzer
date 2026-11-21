package com.sappyoak.dsanalyzer.lua.decompile

import java.util.IdentityHashMap

import com.sappyoak.dsanalyzer.lua.chunk.LuaFunction
import com.sappyoak.dsanalyzer.lua.ir.Expression
import com.sappyoak.dsanalyzer.lua.ir.Statement

/** A numeric for keeps its index, limit and step in three registers */
private const val FOR_REGISTERS = 3

/** Turns debug info's local scopes into `local` statements, and into `do ... end` where a scope closes early */
internal class DebugScope(private val function: LuaFunction) : LocalScope {
    override val parameters: List<String> = function.locals.take(function.paramCount).map { it.name }
    override val recovering: Boolean = false

    /** Where each declaration's locals go out of scope, by identity since equal declarations can repeat */
    private val scopeEnds = IdentityHashMap<Statement, Int>()

    /** Parameters, including a vararg function's implicit `arg`, are declared by the function itself */
    private val declared = BooleanArray(function.locals.size) {
        it < function.paramCount + (if (function.isVararg) 1 else 0) && function.locals[it].startPc == 0
    }

    override fun nameAt(register: Int, pc: Int): String? = function.localName(register, pc)

    override fun startsAt(pc: Int): Boolean = function.locals.any { it.startPc == pc && it.endPc > pc }

    override fun declare(pc: Int, registers: Registers, out: MutableList<Statement>, pendingOnly: Boolean) {
        val starting = function.locals.indices.filter { function.locals[it].startPc == pc && !declared[it] }
            .map { it to registerOf(it, pc) }
        if (starting.isEmpty()) return
        if (pendingOnly && starting.none { (_, register) -> registers.peek(register) != null }) return

        val values = starting.map { (_, register) -> registers.takeOrNull(register) }
        starting.forEach { (local, _) -> declared[local] = true }
        val expressions = values.mapNotNull { it.declaredValue() }.dropLastWhile { it == Expression.Nil }
        val declaration = Statement.LocalAssign(starting.map { (local, _) -> function.locals[local].name }, expressions)
        scopeEnds[declaration] = starting.maxOf { (local, _) -> function.locals[local].endPc }
        out += declaration
    }

    override fun declareLoop(base: Int, bodyStart: Int): String {
        function.locals.indices
            .filter { function.locals[it].isLiveAt(bodyStart) || function.locals[it].startPc == bodyStart }
            .drop(base).take(FOR_REGISTERS)
            .forEach { declared[it] = true }
        return function.localName(base, bodyStart) ?: unsupported("a for loop without its variable")
    }

    /** Debug info already knows where every scope ends */
    override fun enter(): Int = 0

    override fun exit(mark: Int) = Unit

    /** Only an explicit `do` block ends a scope before its enclosing block does */
    override fun scoped(statements: List<Statement>, starts: List<Int>, scopeEnd: Int): List<Statement> {
        val out = mutableListOf<Statement>()
        var index = 0
        while (index < statements.size) {
            val closes = scopeEnds[statements[index]]?.takeIf { it < scopeEnd }
            if (closes == null) {
                out += statements[index++]
                continue
            }
            val last = (index until statements.size).last { starts[it] < closes }
            out += Statement.Do(scoped(statements.subList(index, last + 1), starts.subList(index, last + 1), closes))
            index = last + 1
        }
        return out
    }

    /**
     * The register of the local at [index] when it starts at [pc]: one per earlier local still in scope.
     * A scope that closes at this very pc still counts, as when every local ends at the final RETURN
     */
    private fun registerOf(index: Int, pc: Int): Int =
        function.locals.take(index).count { it.startPc <= pc && it.endPc >= pc }
}
