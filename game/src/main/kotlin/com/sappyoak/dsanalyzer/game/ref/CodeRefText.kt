package com.sappyoak.dsanalyzer.game.ref

import com.sappyoak.dsanalyzer.game.GameEdition

/** The textual form of a [CodeRef] */

public fun parseCodeRef(text: String): CodeRef? {
    val body = text.trim().removePrefixOrNull(CODE_SCHEME) ?: return null

    val slash = body.indexOf('/')
    if (slash <= 0 || slash == body.length - 1) return null

    val binary = binaryOf(body.substring(0, slash)) ?: return null
    val target = targetOf(body.substring(slash + 1)) ?: return null
    return CodeRef(binary, target)
}

private fun binaryOf(token: String): BinaryVersion? {
    val at = token.indexOf('@')
    if (at <= 0 || at == token.length - 1) return null

    val edition = editionOf(token.substring(0, at)) ?: return null
    return BinaryVersion(edition, token.substring(at + 1))
}

private fun editionOf(token: String): GameEdition? = when (token) {
    "ptde" -> GameEdition.PrepareToDie
    "dsr" -> GameEdition.Remastered
    else -> null
}

private fun targetOf(path: String): CodeTarget? {
    val slash = path.indexOf('/')
    if (slash < 0) return path.hexOrNull()?.let { CodeTarget.Location(it) }
    if (slash == path.length - 1) return null

    val rest = path.substring(slash + 1)
    if (rest.contains('/')) return null

    return when (path.substring(0, slash)) {
        "fn" -> named(rest) { name, at -> CodeTarget.Function(name, at) }
        "global" -> named(rest) { name, at -> CodeTarget.Global(name, at) }
        "class" -> if (rest.isSymbol()) CodeTarget.Class(rest) else null
        "struct" -> field(rest)
        else -> null
    }
}

private fun named(token: String, build: (String, Long?) -> CodeTarget): CodeTarget? {
    val at = token.indexOf('@')
    if (at < 0) return if (token.isSymbol()) build(token, null) else null

    val name = token.substring(0, at)
    val address = token.substring(at + 1).hexOrNull() ?: return null
    return if (name.isSymbol()) build(name, address) else null
}

private fun field(token: String): CodeTarget? {
    val plus = token.indexOf('+')
    if (plus <= 0) return null

    val struct = token.substring(0, plus)
    val offset = token.substring(plus + 1).hexOrNull()?.takeIf { it <= Int.MAX_VALUE } ?: return null
    return if (struct.isSymbol()) CodeTarget.Field(struct, offset.toInt()) else null
}

private fun String.isSymbol(): Boolean =
    isNotEmpty() && none { it == '/' || it == '@' || it == '+' }
