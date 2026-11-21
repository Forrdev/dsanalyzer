package com.sappyoak.dsanalyzer.lua.print

import com.sappyoak.dsanalyzer.lua.ir.Expression
import com.sappyoak.dsanalyzer.lua.ir.Statement

/** A for loop's step when none is written */
private const val DEFAULT_STEP = 1.0

/** Writes statements one per line, nested blocks indented four spaces */
internal class StatementPrinter(private val functions: FunctionPrinter) {
    private val expressions = ExpressionPrinter(functions)

    /** At the top level a blank line sets each function declaration apart from its neighbours */
    fun block(statements: List<Statement>, indent: String): String = buildString {
        statements.forEachIndexed { index, statement ->
            val nextToFunction = declaresFunction(statement) || index > 0 && declaresFunction(statements[index - 1])
            if (indent.isEmpty() && index > 0 && nextToFunction) append('\n')
            append(statement(statement, indent)).append('\n')
        }
    }

    private fun declaresFunction(statement: Statement): Boolean = when (statement) {
        is Statement.LocalAssign -> statement.values.singleOrNull() is Expression.Function
        is Statement.Assign -> statement.value is Expression.Function && dottedName(statement.target) != null
        else -> false
    }

    private fun statement(statement: Statement, indent: String): String = indent + when (statement) {
        is Statement.LocalAssign -> local(statement, indent)
        is Statement.Assign -> assign(statement, indent)
        is Statement.CallStatement -> expressions.print(statement.call, indent)
        is Statement.Return ->
            if (statement.values.isEmpty()) "return" else "return ${expressions.list(statement.values, indent)}"
        is Statement.If -> ifStatement(statement, indent)
        is Statement.NumericFor -> numericFor(statement, indent)
        is Statement.Do -> "do\n" + block(statement.body, "$indent    ") + "${indent}end"
        Statement.Break -> "break"
    }

    private fun local(statement: Statement.LocalAssign, indent: String): String {
        val function = statement.values.singleOrNull() as? Expression.Function
        val name = statement.names.singleOrNull()
        return when {
            function != null && name != null -> functions.declaration("local function", name, function.body, indent)
            statement.values.isEmpty() -> "local ${statement.names.joinToString(", ")}"
            else -> "local ${statement.names.joinToString(", ")} = ${expressions.list(statement.values, indent)}"
        }
    }

    /** A function stored under a dotted global name reads as a `function` statement, with `:` when it takes self */
    private fun assign(statement: Statement.Assign, indent: String): String {
        val function = statement.value as? Expression.Function
        val name = function?.let { dottedName(statement.target) }
        if (function == null || name == null) {
            return "${expressions.print(statement.target, indent)} = ${expressions.print(statement.value, indent)}"
        }
        val method = statement.target is Expression.Index && functions.isMethod(function.body)
        val declared = if (method) name.substringBeforeLast('.') + ":" + name.substringAfterLast('.') else name
        return functions.declaration("function", declared, function.body, indent, method)
    }

    private fun ifStatement(statement: Statement.If, indent: String): String = buildString {
        statement.branches.forEachIndexed { index, branch ->
            if (index > 0) append(indent)
            append(if (index == 0) "if " else "elseif ")
            append(expressions.print(branch.condition, indent)).append(" then\n")
            append(block(branch.body, "$indent    "))
        }
        statement.otherwise?.let { append(indent).append("else\n").append(block(it, "$indent    ")) }
        append(indent).append("end")
    }

    private fun numericFor(statement: Statement.NumericFor, indent: String): String {
        val step = statement.step.takeUnless { it == Expression.Number(DEFAULT_STEP) }
        val range = listOfNotNull(statement.start, statement.limit, step)
        return "for ${statement.variable} = ${expressions.list(range, indent)} do\n" +
                block(statement.body, "$indent    ") + "${indent}end"
    }
}
