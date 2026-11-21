package com.sappyoak.dsanalyzer.lua.decompile

import com.sappyoak.dsanalyzer.lua.chunk.LuaFunction
import com.sappyoak.dsanalyzer.lua.disasm.disassemble
import com.sappyoak.dsanalyzer.lua.ir.FunctionBody
import com.sappyoak.dsanalyzer.lua.names.Borrowing

/** How many times a stripped function is rebuilt with more locals before giving up, per register it uses */
private const val ROUNDS_PER_REGISTER = 4

/**
 * Rebuilds one function; nested functions are rebuilt on their own and fail on their own. A function stripped
 * of debug info takes the locals of a named copy compiled the same, if [borrowing] has one that rebuilds;
 * otherwise it is rebuilt again each time its code shows a local the previous guess missed, keeping only the
 * parameter names of the global function [global]'s named copy. [upvalueNames] are what the enclosing function
 * captured, and [firstName] where its generated names start
 */
internal class FunctionDecompiler(
    private val function: LuaFunction,
    private val upvalueNames: List<String> = function.upvalueNames,
    private val firstName: Int = 1,
    private val borrowing: Borrowing = Borrowing.None,
    private val global: String? = null
) {
    private val stripped = function.lines.isEmpty() && function.code.isNotEmpty()

    fun decompile(): FunctionBody {
        if (!stripped) return Rebuild(function, DebugScope(function), function.upvalueNames, null, borrowing).run()
        borrowed()?.let { return it }
        val parameters = borrowing.parametersOf(global, function.paramCount)
        var hints = emptySet<LocalHint>()
        repeat(ROUNDS_PER_REGISTER * (function.maxStackSize + 1)) {
            try {
                val names = NameSource(firstName)
                val scope = RecoveredScope(function, hints, names, parameters)
                return Rebuild(function, scope, upvalueNames, names, borrowing).run()
            } catch (found: LocalsFound) {
                if (hints.containsAll(found.hints)) return failed(hints, "locals that could not be placed")
                hints = hints + found.hints
            }
        }
        return failed(hints, "too many locals to place")
    }

    /** The function rebuilt with a named copy's locals; its upvalues keep the names the enclosing function gave */
    private fun borrowed(): FunctionBody? {
        val named = function.withLocals(borrowing.localsOf(function) ?: return null)
        return Rebuild(named, DebugScope(named), upvalueNames, null, borrowing).run() as? FunctionBody.Decompiled
    }

    private fun failed(hints: Set<LocalHint>, reason: String): FunctionBody {
        val parameters = borrowing.parametersOf(global, function.paramCount)
        val scope = RecoveredScope(function, hints, NameSource(firstName), parameters)
        return FunctionBody.Failed(scope.parameters, function.isVararg, reason, disassemble(function))
    }
}
