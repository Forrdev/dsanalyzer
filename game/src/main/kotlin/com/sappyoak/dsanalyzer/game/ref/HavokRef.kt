package com.sappyoak.dsanalyzer.game.ref

internal const val HAVOK_SCHEME = "havok:"

public data class HavokRef(
    public val version: String,
    public val className: String,
    public val member: String? = null
) : Address {
    init {
        require(version.isNotEmpty()) { "A Havok citation needs a version" }
        require(version.none { it == '/' }) { "A havok version cannot contain '/': '$version'" }
        require(className.isNotEmpty()) { "A Havok citation needs a class" }
        require(className.none { it == '/' || it == '.' }) {
            "A Havok class name cannot contain '/' or '.', which separate an address's parts: '$className'"
        }
        require(member == null || (member.isNotEmpty() && member.none { it == '/' || it == '.' })) {
            "A Havok member name cannot be empty or contain '/', or '.': '$member'"
        }
    }

    override fun toAddress(): String =
        "$HAVOK_SCHEME$version/$className" + (member?.let { ".$it" } ?: "")
}

public fun parseHavokRef(text: String): HavokRef? {
    val body = text.trim().removePrefixOrNull(HAVOK_SCHEME) ?: return null

    val slash = body.indexOf('/')
    if (slash <= 0 || slash == body.length - 1) return null

    val version = body.substring(0, slash)
    val rest = body.substring(slash + 1)
    if (rest.contains('/')) return null

    val dot = rest.lastIndexOf('.')
    val className = if (dot < 0) rest else rest.substring(0, dot)
    val member = if (dot < 0) null else rest.substring(dot + 1)

    if (className.isEmpty() || member?.isEmpty() == true) return null

    return HavokRef(version, className, member)
}
