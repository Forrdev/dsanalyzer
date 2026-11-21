package com.sappyoak.dsanalyzer.lua.decompile

import com.sappyoak.dsanalyzer.lua.ir.Statement

/**
 * Which registers hold locals, and the `local` statements that bring them into scope. Debug info answers
 * directly ([DebugScope]). A stripped chunk's are worked out from how luac allocates registers ([RecoveredScope])
 */
internal interface LocalScope {
    val parameters: List<String>

    /** Whether a register that breaks luac's allocation rules means a local was missed, rather than a bad shape */
    val recovering: Boolean

    fun nameAt(register: Int, pc: Int): String?

    /** Whether a local comes into scope at [pc], which ends any statement before it */
    fun startsAt(pc: Int): Boolean

    /**
     * Declares the locals that come alive at [pc], taking their values from the temporaries that became them.
     * At a block's end only locals whose values were computed inside it belong to it
     */
    fun declare(pc: Int, registers: Registers, out: MutableList<Statement>, pendingOnly: Boolean = false)

    /** A numeric for's variable, with its hidden limit and step, coming into scope at [bodyStart] */
    fun declareLoop(base: Int, bodyStart: Int): String

    /** Marks where a block begins, so [exit] can drop the locals it declared */
    fun enter(): Int

    fun exit(mark: Int)

    /** Wraps statements in `do ... end` where a scope closes before [scopeEnd]; [starts] holds their first pcs */
    fun scoped(statements: List<Statement>, starts: List<Int>, scopeEnd: Int): List<Statement>
}

/** A register that holds a local from [startPc] on, learnt from a stripped function's code */
internal data class LocalHint(val register: Int, val startPc: Int)

/** Thrown when the code shows locals the current guess missed; the function is rebuilt with them */
internal class LocalsFound(val hints: Set<LocalHint>) : Exception(null, null, false, false)
