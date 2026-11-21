package com.sappyoak.dsanalyzer.lua.print

import com.sappyoak.dsanalyzer.lua.ir.FunctionBody

private const val SELF = "self"

/** Writes function headers and bodies; a body that failed to decompile keeps its disassembly as comments */
internal class FunctionPrinter {
    private val statements = StatementPrinter(this)

    /** `function name(params) ... end`, with `name` already in the shape the declaration needs */
    fun declaration(
        keyword: String,
        name: String,
        body: FunctionBody,
        indent: String,
        method: Boolean = false
    ): String {
        val parameters = if (method) body.parameters.drop(1) else body.parameters
        return "$keyword $name(${parameterList(parameters, body.isVararg)})\n${body(body, "$indent    ")}${indent}end"
    }

    fun anonymous(body: FunctionBody, indent: String): String =
        "function(${parameterList(body.parameters, body.isVararg)})\n${body(body, "$indent    ")}${indent}end"

    /** A declaration under a dotted name can take `:` when its first parameter is self */
    fun isMethod(body: FunctionBody): Boolean = body.parameters.firstOrNull() == SELF

    fun body(body: FunctionBody, indent: String): String = when (body) {
        is FunctionBody.Decompiled -> statements.block(body.statements, indent)
        is FunctionBody.Failed -> buildString {
            appendLine("$indent-- decompile failed: ${body.reason}")
            body.listing.lines().filter { it.isNotBlank() }.forEach { appendLine("$indent-- $it") }
        }
    }

    private fun parameterList(parameters: List<String>, isVararg: Boolean): String =
        (parameters + if (isVararg) listOf("...") else emptyList()).joinToString(", ")
}
