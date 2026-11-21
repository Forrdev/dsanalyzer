package com.sappyoak.dsanalyzer.game.world.ai

import com.sappyoak.dsanalyzer.lua.ir.Expression
import com.sappyoak.dsanalyzer.lua.ir.FunctionBody
import com.sappyoak.dsanalyzer.lua.ir.Statement
import com.sappyoak.dsanalyzer.lua.ir.TableItem

/** `proxy:SetEventFlag(id, state)` in the event scripts, `ai:SetEventFlag(id, state)` in battle scripts */
private const val SET_EVENT_FLAG = "SetEventFlag"

/**
 * A flag a Lua script sets: in which global function ([function] is null in the main chunk) and to what,
 * when the state is written out
 */
public data class LuaFlagSet(public val script: LuaScriptId, public val function: String?, public val on: Boolean?)

/** Every flag id written out in a `SetEventFlag` call of [mains], however deeply nested */
public fun luaFlagSets(mains: Map<LuaScriptId, FunctionBody>): List<Pair<Int, LuaFlagSet>> =
    mains.flatMap { (script, main) -> FlagScan(script).body(main, function = null) }

private class FlagScan(private val script: LuaScriptId) {
    fun body(body: FunctionBody, function: String?): List<Pair<Int, LuaFlagSet>> =
        (body as? FunctionBody.Decompiled)?.statements.orEmpty().flatMap { statement(it, function) }

    private fun statements(list: List<Statement>, function: String?) = list.flatMap { statement(it, function) }

    private fun statement(statement: Statement, function: String?): List<Pair<Int, LuaFlagSet>> = when (statement) {
        is Statement.LocalAssign -> statement.values.flatMap { expression(it, function) }
        is Statement.Assign -> {
            val global = (statement.target as? Expression.Global)?.name
            val named = global?.takeIf { statement.value is Expression.Function }
            expression(statement.target, function) + expression(statement.value, named ?: function)
        }
        is Statement.CallStatement -> expression(statement.call, function)
        is Statement.Return -> statement.values.flatMap { expression(it, function) }
        is Statement.If -> statement.branches.flatMap { branch ->
            expression(branch.condition, function) + statements(branch.body, function)
        } + statements(statement.otherwise.orEmpty(), function)
        is Statement.NumericFor -> listOf(statement.start, statement.limit, statement.step)
            .flatMap { expression(it, function) } + statements(statement.body, function)
        is Statement.Do -> statements(statement.body, function)
        Statement.Break -> emptyList()
    }

    private fun expression(expression: Expression, function: String?): List<Pair<Int, LuaFlagSet>> = when (expression) {
        is Expression.MethodCall -> listOfNotNull(set(expression, function)) +
                (listOf(expression.receiver) + expression.arguments).flatMap { expression(it, function) }
        is Expression.Call -> (listOf(expression.function) + expression.arguments).flatMap { expression(it, function) }
        is Expression.Index -> expression(expression.table, function) + expression(expression.key, function)
        is Expression.Binary -> expression(expression.left, function) + expression(expression.right, function)
        is Expression.Unary -> expression(expression.operand, function)
        is Expression.Table -> expression.items.flatMap { item ->
            when (item) {
                is TableItem.Positional -> expression(item.value, function)
                is TableItem.Keyed -> expression(item.key, function) + expression(item.value, function)
            }
        }
        is Expression.Function -> body(expression.body, function)
        else -> emptyList()
    }

    private fun set(call: Expression.MethodCall, function: String?): Pair<Int, LuaFlagSet>? {
        if (call.method != SET_EVENT_FLAG) return null
        val flag = (call.arguments.firstOrNull() as? Expression.Number)?.value?.toInt() ?: return null
        return flag to LuaFlagSet(script, function, (call.arguments.getOrNull(1) as? Expression.Bool)?.value)
    }
}
