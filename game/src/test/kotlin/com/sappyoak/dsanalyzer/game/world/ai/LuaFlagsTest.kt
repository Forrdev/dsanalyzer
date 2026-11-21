package com.sappyoak.dsanalyzer.game.world.ai

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly

import com.sappyoak.dsanalyzer.lua.ir.Branch
import com.sappyoak.dsanalyzer.lua.ir.Expression
import com.sappyoak.dsanalyzer.lua.ir.FunctionBody
import com.sappyoak.dsanalyzer.lua.ir.Statement

private val SCRIPT = LuaScriptId(LuaBinder.Event, "global_event.lua")

private fun body(vararg statements: Statement) = FunctionBody.Decompiled(emptyList(), false, statements.toList())
private fun set(flag: Expression, state: Expression) = Statement.CallStatement(
    Expression.MethodCall(Expression.Local("proxy"), "SetEventFlag", listOf(flag, state))
)
private fun number(value: Int) = Expression.Number(value.toDouble())
private val ON = Expression.Bool(true)
private val OFF = Expression.Bool(false)

/** A global function setting a flag inside an if, a nested anonymous function, and the main chunk itself */
private val MAIN = body(
    Statement.Assign(
        Expression.Global("OnMatchingError"),
        Expression.Function(
            body(
                Statement.If(listOf(Branch(Expression.Bool(true), listOf(set(number(4000), OFF)))), null),
                Statement.LocalAssign(listOf("later"), listOf(Expression.Function(body(set(number(4047), ON)))))
            )
        )
    ),
    set(number(4093), Expression.Local("state")),
    set(Expression.Local("flag"), ON)
)

class LuaFlagsTest : FunSpec({
    test("every SetEventFlag of a written-out id is found, inside the global function it belongs to") {
        luaFlagSets(mapOf(SCRIPT to MAIN)) shouldContainExactly listOf(
            4000 to LuaFlagSet(SCRIPT, "OnMatchingError", on = false),
            4047 to LuaFlagSet(SCRIPT, "OnMatchingError", on = true),
            4093 to LuaFlagSet(SCRIPT, null, on = null)
        )
    }
})
