package com.sappyoak.dsanalyzer.runtime.snapshot

/** What happens to a write when the tool detaches */
public enum class RevertPolicy {
    /** Toggles, patches, and hooks. Put back on detach */
    OnDetach,
    /** Edits the user keeps (position, stats, flags). Kept on detach, undone only by hand */
    Manual,
    /** Game calls with no meaningful direct inverse, shown for information only */
    Never;
}

/** One record of the write history */
public data class WriteEntry(
    public val id: Long,
    public val key: String,
    public val policy: RevertPolicy,
    public val label: String,
    public val undoable: Boolean
)

public data class WriteKey(public val name: String) {
    override fun toString(): String = name
}
