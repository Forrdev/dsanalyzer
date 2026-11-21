package com.sappyoak.dsanalyzer.lua.decompile

import java.nio.charset.Charset

import com.sappyoak.dsanalyzer.lua.chunk.LuaChunk
import com.sappyoak.dsanalyzer.lua.ir.Expression
import com.sappyoak.dsanalyzer.lua.ir.FunctionBody
import com.sappyoak.dsanalyzer.lua.ir.Statement
import com.sappyoak.dsanalyzer.lua.ir.TableItem
import com.sappyoak.dsanalyzer.lua.names.Borrowing
import com.sappyoak.dsanalyzer.lua.names.LuaNames
import com.sappyoak.dsanalyzer.lua.print.printChunk

/**
 * Changes whenever the same chunk would decompile to different source, so source kept from an older
 * decompiler is not reused. The golden tests show such changes: bump this with them
 */
public const val DECOMPILER_REVISION: Int = 1

/**
 * A chunk rebuilt as source, with the reason for every function that stayed as disassembly. [main] is the
 * rebuilt main function itself; [charset] is the chunk's own string encoding, which the source must be saved
 * in to compile back the same
 */
public class DecompiledChunk(
    public val main: FunctionBody,
    public val source: String,
    public val charset: Charset,
    public val failures: List<String>
)

/** Rebuilds [chunk]; stripped functions take what [names] has for them */
public fun decompile(chunk: LuaChunk, names: LuaNames = LuaNames.None): DecompiledChunk {
    val main = FunctionDecompiler(chunk.main, borrowing = Borrowing(names, chunk.charset)).decompile()
    return DecompiledChunk(main, printChunk(main), chunk.charset, failuresIn(main))
}

private fun failuresIn(body: FunctionBody): List<String> = when (body) {
    is FunctionBody.Failed -> listOf(body.reason)
    is FunctionBody.Decompiled -> body.statements.flatMap(::functionsIn).flatMap(::failuresIn)
}

/** The function bodies a statement holds, directly or inside its blocks and expressions */
private fun functionsIn(statement: Statement): List<FunctionBody> = when (statement) {
    is Statement.LocalAssign -> statement.values.flatMap(::functionsIn)
    is Statement.Assign -> functionsIn(statement.target) + functionsIn(statement.value)
    is Statement.CallStatement -> functionsIn(statement.call)
    is Statement.Return -> statement.values.flatMap(::functionsIn)
    is Statement.If -> statement.branches.flatMap { functionsIn(it.condition) + it.body.flatMap(::functionsIn) } +
            statement.otherwise.orEmpty().flatMap(::functionsIn)
    is Statement.NumericFor -> listOf(statement.start, statement.limit, statement.step).flatMap(::functionsIn) +
            statement.body.flatMap(::functionsIn)
    is Statement.Do -> statement.body.flatMap(::functionsIn)
    Statement.Break -> emptyList()
}

private fun functionsIn(expression: Expression): List<FunctionBody> = when (expression) {
    is Expression.Function -> listOf(expression.body)
    is Expression.Index -> functionsIn(expression.table) + functionsIn(expression.key)
    is Expression.Call -> functionsIn(expression.function) + expression.arguments.flatMap(::functionsIn)
    is Expression.MethodCall -> functionsIn(expression.receiver) + expression.arguments.flatMap(::functionsIn)
    is Expression.Binary -> functionsIn(expression.left) + functionsIn(expression.right)
    is Expression.Unary -> functionsIn(expression.operand)
    is Expression.Table -> expression.items.flatMap {
        when (it) {
            is TableItem.Positional -> functionsIn(it.value)
            is TableItem.Keyed -> functionsIn(it.key) + functionsIn(it.value)
        }
    }
    else -> emptyList()
}
