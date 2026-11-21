package com.sappyoak.dsanalyzer.lua.decompile

import com.sappyoak.dsanalyzer.lua.chunk.LuaFunction
import com.sappyoak.dsanalyzer.lua.ir.Expression
import com.sappyoak.dsanalyzer.lua.ir.Statement

/** The name 5.0 gives a vararg function's table of extra arguments, which it keeps as a local */
private const val VARARG_TABLE = "arg"

/** What a numeric for keeps beside its variable; never read by name */
private val LOOP_INTERNALS = listOf("(for limit)", "(for step)")

/**
 * The locals of a function stripped of debug info. luac gives every statement its temporaries from the first
 * register above the locals in scope and never leaves one waiting across statements, so each [LocalHint] is a
 * place the code broke that rule under the previous guess. A block's locals leave scope where the block ends.
 * Names come from [names], so they stay apart from those of the enclosing functions
 */
internal class RecoveredScope(
    function: LuaFunction,
    private val hints: Set<LocalHint>,
    private val names: NameSource,
    borrowedParameters: List<String>? = null
) : LocalScope {
    override val parameters: List<String> = borrowedParameters ?: List(function.paramCount) { names.parameter() }
    override val recovering: Boolean = true

    /** The local in each register, lowest first */
    private val inScope = (parameters + if (function.isVararg) listOf(VARARG_TABLE) else emptyList()).toMutableList()

    /** Hints already declared: one at a block's end is reached again by the code after the block */
    private val declaredHints = mutableSetOf<LocalHint>()

    override fun nameAt(register: Int, pc: Int): String? = inScope.getOrNull(register)

    override fun startsAt(pc: Int): Boolean = hints.any { it.startPc == pc }

    override fun declare(pc: Int, registers: Registers, out: MutableList<Statement>, pendingOnly: Boolean) {
        val starting = hints
            .filter { it.startPc == pc && it.register >= inScope.size && it !in declaredHints }
            .sortedBy { it.register }
        if (starting.isEmpty()) return
        if (pendingOnly && starting.none { registers.peek(it.register) != null }) return
        if (starting.withIndex().any { (index, hint) -> hint.register != inScope.size + index }) {
            unsupported("locals that do not follow the ones in scope")
        }

        declaredHints += starting
        val values = starting.map { registers.takeOrNull(it.register) }
        val declared = starting.map { names.local() }
        inScope += declared
        val initial = values.mapNotNull { it.declaredValue() }.dropLastWhile { it == Expression.Nil }
        out += Statement.LocalAssign(declared, initial)
    }

    override fun declareLoop(base: Int, bodyStart: Int): String {
        if (base != inScope.size) unsupported("a for loop above registers that are not locals")
        val variable = names.local()
        inScope += variable
        inScope += LOOP_INTERNALS
        return variable
    }

    override fun enter(): Int = inScope.size

    override fun exit(mark: Int) {
        while (inScope.size > mark) inScope.removeAt(inScope.lastIndex)
    }

    /** Recovered scopes only ever end with their blocks */
    override fun scoped(statements: List<Statement>, starts: List<Int>, scopeEnd: Int): List<Statement> = statements
}

/**
 * Names for a stripped chunk's parameters and locals. A nested function starts counting where its parent
 * stood, so none of its names can hide an upvalue it captured
 */
internal class NameSource(private var next: Int) {
    val position: Int get() = next

    fun parameter(): String = "a${next++}"

    fun local(): String = "l${next++}"
}
