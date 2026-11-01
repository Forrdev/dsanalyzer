package com.sappyoak.dsanalyzer.game.ref

internal fun String.removePrefixOrNull(prefix: String): String? =
    if (startsWith(prefix)) substring(prefix.length) else null


internal fun requireSegment(value: String, what: String) {
    require(value.isNotEmpty()) { "An address $what cannot be empty" }
    require(value.none { it == '/' || it == '#' }) {
        "An address $what cannot contain '/' or '#': '$value'"
    }
}

internal fun requireSymbol(name: String, what: String) {
    require(name.isNotEmpty()) { "A $what citation needs a name" }
    require(name.none { it == '/' || it == '@' || it == '+' }) {
        "A $what name cannot contain '/', '@' or '+': '$name'"
    }
}

internal fun requireAddress(at: Long?) {
    require(at == null || at >= 0) { "An address cannot be negative: $at" }
}

internal const val HEX_DIGITS: Int = 8
internal const val HEX_PREFIX: String = "0x"
internal val Long.hex: String
    get() = HEX_PREFIX + toString(16).uppercase().padStart(HEX_DIGITS, '0')


internal fun String.hexOrNull(): Long? {
    val digits = removePrefixOrNull(HEX_PREFIX) ?: return null
    if (digits.isEmpty() || digits.any { it == '+' || it == '-' }) return null
    return digits.toLongOrNull(16)?.takeIf { it >= 0 }
}
