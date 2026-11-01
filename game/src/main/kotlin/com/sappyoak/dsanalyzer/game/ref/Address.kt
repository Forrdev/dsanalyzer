package com.sappyoak.dsanalyzer.game.ref

public sealed interface Address {
    public fun toAddress(): String
}

public data class DataAddress(
    public val space: String,
    public val segments: List<String> = emptyList(),
    public val fragment: String? = null
) : Address {
    init {
        requireSegment(space, "space")
        segments.forEach { requireSegment(it, "segment") }
        require(fragment == null || (fragment.isNotEmpty() && !fragment.contains('/'))) {
            "A fragment cannot be empty or contain '/': '$fragment'"
        }
    }

    override fun toAddress(): String = buildString {
        append(DATA_SCHEME)
        append(space)
        segments.forEach { append('/').append(it) }
        fragment?.let { append('#').append(it) }
    }
}

internal const val DATA_SCHEME = "ds:/"

public fun parseAddress(text: String): Address? {
    val trimmed = text.trim()
    return when {
        trimmed.startsWith(DATA_SCHEME) -> parseDataAddress(trimmed)
        trimmed.startsWith(CODE_SCHEME) -> parseCodeRef(trimmed)
        trimmed.startsWith(HAVOK_SCHEME) -> parseHavokRef(trimmed)
        else -> null
    }
}

public fun parseDataAddress(text: String): DataAddress? {
    val body = text.trim().removePrefix(DATA_SCHEME)
    if (body.length == text.trim().length) return null

    val hash = body.indexOf('#')
    val path = if (hash < 0) body else body.substring(0, hash)
    val fragment = if (hash < 0) null else body.substring(hash + 1)
    if (fragment?.isEmpty() == true) return null

    val parts = path.split('/')
    if (parts.any { it.isEmpty() || it.contains('#') }) return null

    return DataAddress(parts.first(), parts.drop(1), fragment)
}

