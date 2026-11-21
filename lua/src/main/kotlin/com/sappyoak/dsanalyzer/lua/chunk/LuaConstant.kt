package com.sappyoak.dsanalyzer.lua.chunk

/** A constant as 5.0 dumps them: nil, a number or a string */
public sealed interface LuaConstant {
    public data object Nil : LuaConstant {
        override fun toString(): String = "nil"
    }

    public data class Number(public val value: Double) : LuaConstant {
        override fun toString(): String = luaNumber(value)
    }

    public data class Text(public val value: String) : LuaConstant {
        override fun toString(): String = luaQuoted(value)
    }
}

/** Integral values print without a fraction, the rest the way Lua's "%.14g" does */
public fun luaNumber(value: Double): String =
    if (value == Math.rint(value) && !value.isInfinite() && Math.abs(value) < 1e15) value.toLong().toString()
    else "%.14g".format(value).replace(Regex("""\.?0+(?=$|e)"""), "")

/**
 * A Lua string literal; anything unprintable, and any byte that did not decode, is written as a decimal
 * escape, padded to three digits when a digit follows it
 */
public fun luaQuoted(text: String): String = buildString {
    append('"')
    text.forEachIndexed { index, char ->
        when (char) {
            '"' -> append("\\\"")
            '\\' -> append("\\\\")
            '\n' -> append("\\n")
            '\r' -> append("\\r")
            '\t' -> append("\\t")
            else -> {
                val escaped = rawByte(char) ?: char.code.takeIf { char < ' ' || char == '\u007F' }
                if (escaped == null) append(char)
                else append('\\').append(if (text.getOrNull(index + 1) in '0'..'9') "%03d".format(escaped) else escaped)
            }
        }
    }
    append('"')
}
