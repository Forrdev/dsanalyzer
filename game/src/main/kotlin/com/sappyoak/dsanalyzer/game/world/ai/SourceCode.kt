package com.sappyoak.dsanalyzer.game.world.ai

private val DECLARATION = Regex("""\bfunction\s+$""")

/** The code part of a line, before any `--` comment, and which of its characters sit inside string literals */
internal class SourceCode(line: String) {
    val text: String
    val quoted: BooleanArray

    init {
        val quoted = BooleanArray(line.length)
        var quote: Char? = null
        var at = 0
        var end = line.length
        while (at < line.length) {
            val char = line[at]
            if (quote == null && line.startsWith("--", at)) {
                end = at
                break
            }
            when {
                quote != null && char == '\\' -> quoted[at++] = true
                quote != null && char == quote -> quote = null
                quote == null && (char == '"' || char == '\'') -> quote = char
            }
            if (quote != null || char == '"' || char == '\'') quoted[at] = true
            at++
        }
        text = line.substring(0, end)
        this.quoted = quoted
    }

    /** `t.name` and `t:name` are fields and methods, not globals */
    fun isField(at: Int): Boolean = text.getOrNull(at - 1).let { it == '.' || it == ':' }

    /** The name a `function` declaration gives, which needs no link to itself */
    fun isDeclared(at: Int): Boolean = DECLARATION.containsMatchIn(text.substring(0, at))

    /**
     * The ranges of the arguments of the call whose `(` is at [open], split at its own commas only: not those
     * inside nested calls, tables or strings. Empty when the call does not close on this line
     */
    fun arguments(open: Int): List<IntRange> {
        val ranges = mutableListOf<IntRange>()
        var depth = 0
        var start = open + 1
        for (at in start until text.length) {
            if (quoted[at]) continue
            when (text[at]) {
                '(', '{' -> depth++
                ')', '}' -> if (depth == 0) return ranges + listOf(start until at) else depth--
                ',' -> if (depth == 0) ranges += (start until at).also { start = at + 1 }
            }
        }
        return emptyList()
    }
}
