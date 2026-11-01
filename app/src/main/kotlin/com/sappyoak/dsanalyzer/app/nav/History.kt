package com.sappyoak.dsanalyzer.app.nav

@ConsistentCopyVisibility
public data class History private constructor(
    private val entries: List<View>,
    private val cursor: Int
) {
    public val current: View get() = entries[cursor]
    public val section: Section get() = current.section

    public val canGoBack: Boolean get() = cursor > 0
    public val canGoForward: Boolean get() = cursor < entries.lastIndex

    public val depth: Int get() = cursor

    /**
     * The most recent view in [section] at or behind the cursor, otherwise null if there is none
     */
    public fun lastIn(section: Section): View? =
        (cursor downTo 0).firstNotNullOfOrNull { i -> entries[i].takeIf { it.section == section } }


    public fun push(view: View): History {
        if (view == current) return this

        val kept = entries.take(cursor + 1) + view
        val trimmed = if (kept.size > MAX_ENTRIES) kept.takeLast(MAX_ENTRIES) else kept
        return History(trimmed, trimmed.lastIndex)
    }

    public fun back(): History = if (canGoBack) copy(cursor = cursor - 1) else this
    public fun forward(): History = if (canGoForward) copy(cursor = cursor + 1) else this

    public companion object {
        public const val MAX_ENTRIES: Int = 50

        public fun of(initial: View): History = History(listOf(initial), 0)
    }
}
