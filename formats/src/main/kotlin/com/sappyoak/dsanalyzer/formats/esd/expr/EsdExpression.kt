package com.sappyoak.dsanalyzer.formats.esd.expr

/** One decoded expression. The tree that game's stack machine evaluates */
public sealed interface EsdExpression {
    public data class IntValue(public val value: Int) : EsdExpression
    public data class FloatValue(public val value: Double) : EsdExpression
    public data class Text(public val value: String) : EsdExpression

    /** A call into the dispatcher by function id, which the bundled definitions name */
    public data class Call(public val function: Int, public val args: List<EsdExpression>) : EsdExpression
    public data class Binary(public val op: Op, public val left: EsdExpression, public val right: EsdExpression) : EsdExpression
    public data class Unary(public val op: Op, public val operand: EsdExpression) : EsdExpression

    public data class GetRegister(public val index: Int) : EsdExpression
    public data class SetRegister(public val index: Int, public val value: EsdExpression) : EsdExpression

    /** One of the arguments the state group was started with, at [index] */
    public data class GroupArg(public val index: EsdExpression) : EsdExpression

    /** The result of the last command, and whether it is still running */
    public data object CallResult : EsdExpression
    public data object CallOngoing : EsdExpression

    /** [expr] carrying what to do when it evaluates false */
    public data class Marked(public val expr: EsdExpression, public val ifFalse: IfFalse) : EsdExpression
}

public enum class IfFalse { Continue, Abort; }

public enum class Op(public val symbol: String) {
    Add("+"),
    Subtract("-"),
    Multiply("*"),
    Divide("/"),
    LessOrEqual("<="),
    GreaterOrEqual(">="),
    Less("<"),
    Greater(">"),
    Equal("=="), 
    NotEqual("!="),
    And("&&"),
    Or("||"),
    Negate("-"),
    Not("!");
}

/** A program decoded, or the reason and byte offset it could not be */
public sealed interface Decoded {
    public data class Expression(public val expression: EsdExpression) : Decoded
    public data class Undecodable(public val reason: String, public val at: Int) : Decoded
}
