package com.sappyoak.dsanalyzer.lua.decompile

import com.sappyoak.dsanalyzer.lua.ir.BinaryOperator
import com.sappyoak.dsanalyzer.lua.ir.Expression
import com.sappyoak.dsanalyzer.lua.ir.UnaryOperator

/**
 * The opposite of [expression]. Only `==` and `~=` swap; `not (a < b)` compiles differently from `b <= a`,
 * so ordered comparisons keep their `not`. `and` and `or` are pushed through by De Morgan, which luac
 * compiles the same as the negated whole
 */
internal fun negate(expression: Expression): Expression = when {
    expression is Expression.Binary && expression.operator == BinaryOperator.AND ->
        Expression.Binary(BinaryOperator.OR, negate(expression.left), negate(expression.right))
    expression is Expression.Binary && expression.operator == BinaryOperator.OR ->
        Expression.Binary(BinaryOperator.AND, negate(expression.left), negate(expression.right))
    expression is Expression.Unary && expression.operator == UnaryOperator.NOT -> expression.operand
    expression is Expression.Bool -> Expression.Bool(!expression.value)
    expression is Expression.Binary && expression.operator == BinaryOperator.EQ ->
        expression.copy(operator = BinaryOperator.NE)
    expression is Expression.Binary && expression.operator == BinaryOperator.NE ->
        expression.copy(operator = BinaryOperator.EQ)
    else -> Expression.Unary(UnaryOperator.NOT, expression)
}

/**
 * A comparison the way it was written. luac compiles `a > b` as `b < a` (and `>=` as `<=`) but still evaluates
 * `a` first, so a left operand computed after the right one, or a constant on the left of a computed value,
 * means the source had the operands the other way round. `==` keeps its operands in source order
 */
internal fun comparison(operator: BinaryOperator, left: Operand, right: Operand): Expression {
    val swapped = operator != BinaryOperator.EQ && when {
        left.register == null -> right.register != null
        right.register == null -> false
        else -> left.register > right.register
    }
    if (!swapped) return Expression.Binary(operator, left.expression, right.expression)
    val flipped = if (operator == BinaryOperator.LT) BinaryOperator.GT else BinaryOperator.GE
    return Expression.Binary(flipped, right.expression, left.expression)
}

/** A comparison operand and the register it was read from, or null for a constant */
internal class Operand(val expression: Expression, val register: Int?)
