package com.sappyoak.dsanalyzer.lua.ir

public sealed interface Expression {
    public data object Nil : Expression
    public data class Bool(public val value: Boolean) : Expression
    public data class Number(public val value: Double) : Expression
    public data class Text(public val value: String) : Expression

    public data class Local(public val name: String) : Expression
    public data class Global(public val name: String) : Expression
    public data class Upvalue(public val name: String) : Expression

    public data class Index(public val table: Expression, public val key: Expression) : Expression
    public data class Call(public val function: Expression, public val arguments: List<Expression>) : Expression
    public data class MethodCall(
        public val receiver: Expression,
        public val method: String,
        public val arguments: List<Expression>
    ) : Expression

    public data class Binary(
        public val operator: BinaryOperator,
        public val left: Expression,
        public val right: Expression
    ) : Expression

    public data class Unary(public val operator: UnaryOperator, public val operand: Expression) : Expression

    /** A table constructor, its items in the order the code builds them */
    public data class Table(public val items: List<TableItem>) : Expression

    public data class Function(public val body: FunctionBody) : Expression
}

public sealed interface TableItem {
    public data class Positional(public val value: Expression) : TableItem
    public data class Keyed(public val key: Expression, public val value: Expression) : TableItem
}

/** Binary operators with Lua 5.0's precedence, loosest first, and whether they group to the right */
public enum class BinaryOperator(
    public val symbol: String,
    public val precedence: Int,
    public val rightAssociative: Boolean
) {
    OR("or", 1, false),
    AND("and", 2, false),
    LT("<", 3, false),
    LE("<=", 3, false),
    GT(">", 3, false),
    GE(">=", 3, false),
    EQ("==", 3, false),
    NE("~=", 3, false),
    CONCAT("..", 4, true),
    ADD("+", 5, false),
    SUB("-", 5, false),
    MUL("*", 6, false),
    DIV("/", 6, false),
    POW("^", 8, true)
}

public enum class UnaryOperator(public val symbol: String) {
    NOT("not "),
    MINUS("-")
}

/** Unary operators bind tighter than everything but '^' */
public const val UNARY_PRECEDENCE: Int = 7
