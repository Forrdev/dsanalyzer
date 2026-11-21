package com.sappyoak.dsanalyzer.lua.print

import com.sappyoak.dsanalyzer.lua.ir.Expression

private val IDENTIFIER = Regex("[A-Za-z_][A-Za-z0-9_]*")

private val KEYWORDS = setOf(
    "and", "break", "do", "else", "elseif", "end", "false", "for", "function", "if", "in",
    "local", "nil", "not", "or", "repeat", "return", "then", "true", "until", "while"
)

/** Whether [text] can be written bare, as a field name or after a dot */
internal fun isName(text: String): Boolean = IDENTIFIER.matches(text) && text !in KEYWORDS

/** `A.B.c` for a global indexed by names, which a `function` statement can be declared under */
internal fun dottedName(target: Expression): String? = when (target) {
    is Expression.Global -> target.name.takeIf(::isName)
    is Expression.Index -> (target.key as? Expression.Text)?.value?.takeIf(::isName)
        ?.let { key -> dottedName(target.table)?.let { "$it.$key" } }
    else -> null
}
