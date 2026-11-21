package com.sappyoak.dsanalyzer.lua.ir

public sealed interface Statement {
    /** `local a, b = ...`; [values] may be shorter than [names], or empty for a bare declaration */
    public data class LocalAssign(public val names: List<String>, public val values: List<Expression>) : Statement

    public data class Assign(public val target: Expression, public val value: Expression) : Statement

    public data class CallStatement(public val call: Expression) : Statement

    public data class Return(public val values: List<Expression>) : Statement

    /** `if`, then any `elseif`s, as one list of tests with an optional final `else` */
    public data class If(public val branches: List<Branch>, public val otherwise: List<Statement>?) : Statement

    public data class NumericFor(
        public val variable: String,
        public val start: Expression,
        public val limit: Expression,
        public val step: Expression,
        public val body: List<Statement>
    ) : Statement

    /** `do ... end`, kept where locals go out of scope before their enclosing block ends */
    public data class Do(public val body: List<Statement>) : Statement

    public data object Break : Statement
}

public data class Branch(public val condition: Expression, public val body: List<Statement>)

/** A function's parameters and body, or why it could not be rebuilt */
public sealed interface FunctionBody {
    public val parameters: List<String>
    public val isVararg: Boolean

    public data class Decompiled(
        override val parameters: List<String>,
        override val isVararg: Boolean,
        public val statements: List<Statement>
    ) : FunctionBody

    /** Kept as its disassembly so nothing is guessed */
    public data class Failed(
        override val parameters: List<String>,
        override val isVararg: Boolean,
        public val reason: String,
        public val listing: String
    ) : FunctionBody
}
