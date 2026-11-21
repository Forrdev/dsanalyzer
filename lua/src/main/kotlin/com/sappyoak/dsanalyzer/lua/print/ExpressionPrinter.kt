package com.sappyoak.dsanalyzer.lua.print

import com.sappyoak.dsanalyzer.lua.chunk.luaNumber
import com.sappyoak.dsanalyzer.lua.chunk.luaQuoted
import com.sappyoak.dsanalyzer.lua.ir.Expression
import com.sappyoak.dsanalyzer.lua.ir.TableItem
import com.sappyoak.dsanalyzer.lua.ir.UNARY_PRECEDENCE
import com.sappyoak.dsanalyzer.lua.ir.UnaryOperator

/** Above every operator: names, literals, calls and constructors never need parentheses */
private const val ATOMIC = 9

/** A table constructor longer than this goes one item per line */
private const val INLINE_TABLE_LIMIT = 100

/** Writes expressions with only the parentheses Lua's precedence needs */
internal class ExpressionPrinter(private val functions: FunctionPrinter) {
    fun print(expression: Expression, indent: String): String = when (expression) {
        Expression.Nil -> "nil"
        is Expression.Bool -> expression.value.toString()
        is Expression.Number -> luaNumber(expression.value)
        is Expression.Text -> luaQuoted(expression.value)
        is Expression.Local -> expression.name
        is Expression.Global -> expression.name
        is Expression.Upvalue -> expression.name
        is Expression.Index -> prefix(expression.table, indent) + key(expression.key, indent)
        is Expression.Call -> prefix(expression.function, indent) + arguments(expression.arguments, indent)
        is Expression.MethodCall ->
            "${prefix(expression.receiver, indent)}:${expression.method}${arguments(expression.arguments, indent)}"
        is Expression.Binary -> binary(expression, indent)
        is Expression.Unary -> unary(expression, indent)
        is Expression.Table -> table(expression, indent)
        is Expression.Function -> functions.anonymous(expression.body, indent)
    }

    fun list(expressions: List<Expression>, indent: String): String =
        expressions.joinToString(", ") { print(it, indent) }

    private fun binary(expression: Expression.Binary, indent: String): String {
        val operator = expression.operator
        val leftNeeds = operator.precedence + if (operator.rightAssociative) 1 else 0
        val rightNeeds = operator.precedence + if (operator.rightAssociative) 0 else 1
        val left = operand(expression.left, leftNeeds, indent)
        return "$left ${operator.symbol} ${operand(expression.right, rightNeeds, indent)}"
    }

    private fun unary(expression: Expression.Unary, indent: String): String {
        val operand = operand(expression.operand, UNARY_PRECEDENCE, indent)
        // "--" would start a comment
        val gap = if (expression.operator == UnaryOperator.MINUS && operand.startsWith("-")) " " else ""
        return expression.operator.symbol + gap + operand
    }

    private fun operand(expression: Expression, needs: Int, indent: String): String =
        print(expression, indent).let { if (precedence(expression) < needs) "($it)" else it }

    private fun precedence(expression: Expression): Int = when (expression) {
        is Expression.Binary -> expression.operator.precedence
        is Expression.Unary -> UNARY_PRECEDENCE
        is Expression.Number -> if (expression.value < 0) UNARY_PRECEDENCE else ATOMIC
        else -> ATOMIC
    }

    /** What a call or index is applied to; anything but a name, index or call needs parentheses */
    private fun prefix(expression: Expression, indent: String): String = when (expression) {
        is Expression.Local, is Expression.Global, is Expression.Upvalue,
        is Expression.Index, is Expression.Call, is Expression.MethodCall -> print(expression, indent)
        else -> "(${print(expression, indent)})"
    }

    private fun key(key: Expression, indent: String): String =
        if (key is Expression.Text && isName(key.value)) ".${key.value}" else "[${print(key, indent)}]"

    private fun arguments(arguments: List<Expression>, indent: String): String = "(${list(arguments, indent)})"

    private fun table(table: Expression.Table, indent: String): String {
        if (table.items.isEmpty()) return "{}"
        val inner = "$indent    "
        val items = table.items.map { item(it, inner) }
        val inline = "{${items.joinToString(", ")}}"
        if (inline.length <= INLINE_TABLE_LIMIT && '\n' !in inline) return inline
        return items.joinToString(",\n", prefix = "{\n", postfix = "\n$indent}") { "$inner$it" }
    }

    private fun item(item: TableItem, indent: String): String = when (item) {
        is TableItem.Positional -> print(item.value, indent)
        is TableItem.Keyed -> {
            val key = item.key
            val name = if (key is Expression.Text && isName(key.value)) key.value else "[${print(key, indent)}]"
            "$name = ${print(item.value, indent)}"
        }
    }
}
