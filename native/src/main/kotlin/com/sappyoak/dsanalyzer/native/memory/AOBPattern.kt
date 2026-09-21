package com.sappyoak.dsanalyzer.native.memory

private const val WILDCARD = "??"
private val SPLIT_REGEX = "\\s+".toRegex()

/**
 * A byte signature with wildcards
 */
public class AOBPattern private constructor(
    private val bytes: ByteArray,
    private val fixed: BooleanArray
) {
    public val length: Int get() = bytes.size

    private val anchor: Int = fixed.indexOfFirst { it }

    public fun findIn(buffer: ByteArray): List<Int> {
        val matches = mutableListOf<Int>()
        val anchorByte = bytes[anchor]
        for (start in 0..buffer.size - length) {
            if (buffer[start + anchor] == anchorByte && matchesAt(buffer, start)) {
                matches.add(start)
            }
        }
        return matches
    }

    override fun toString(): String = bytes.indices.joinToString(" ") {
        if (fixed[it]) "%02X".format(bytes[it]) else WILDCARD
    }

    private fun matchesAt(buffer: ByteArray, start: Int): Boolean =
        bytes.indices.all { !fixed[it] || buffer[start + it] == bytes[it] }

    public companion object {

        public fun parse(text: String): AOBPattern {
            val tokens = text.trim().split(SPLIT_REGEX).filter { it.isNotEmpty() }
            require(tokens.isNotEmpty()) { "Empty pattern" }

            val fixed = BooleanArray(tokens.size) { tokens[it] != WILDCARD && tokens[it] != "?" }
            require(fixed.any { it }) { "A pattern on only wildcards matches everywhere" }

            val bytes = ByteArray(tokens.size) { index ->
                if (!fixed[index]) return@ByteArray 0
                val token = tokens[index]
                require(token.length == 2) { "$token is not a byte" }
                token.toInt(16).toByte()
            }

            return AOBPattern(bytes, fixed)
        }
    }

}