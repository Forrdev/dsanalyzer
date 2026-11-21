package com.sappyoak.dsanalyzer.app.data.ai

/** `function Name(` or `local function Name(` at the start of a line, the way the decompiler writes them */
private val DECLARATION = Regex("""^(?:local )?function ([A-Za-z_][A-Za-z0-9_.:]*)\(""")

/** A top-level function and the 0-based line its declaration is on */
public data class OutlineEntry(public val name: String, public val line: Int)

/** A decompiled script split into lines, with its top-level functions for jumping to */
public class SourceOutline(public val lines: List<String>, public val functions: List<OutlineEntry>) {
    /** The line [function] is declared on, matching a method's `T:m` by its bare `T.m` too */
    public fun lineOf(function: String): Int? =
        functions.firstOrNull { it.name == function || it.name.replace(':', '.') == function }?.line

    public companion object {
        public fun of(source: String): SourceOutline {
            val lines = source.trimEnd('\n').lines()
            val functions = lines.mapIndexedNotNull { index, line ->
                DECLARATION.find(line)?.let { OutlineEntry(it.groupValues[1], index) }
            }
            return SourceOutline(lines, functions)
        }
    }
}
