package com.sappyoak.dsanalyzer.native.windows

public sealed interface ViewportUpdate {
    /** The viewport moved, resized, or changed what it would show of an overlay */
    public data class Changed(public val viewport: Viewport) : ViewportUpdate

    /** The game's window has gone. Emitted once, not every tick afterwards */
    public data object Lost : ViewportUpdate
}

/** Watches the game's viewport and reports only when it changes */
public class WindowTracker(private val source: ViewportSource) {
    private var last: Viewport? = null

    /** The viewport as of the last change, or null if there has not been one */
    public val current: Viewport? get() = last

    /**
     * Read the viewport, returning an update only if it differs from the last one.
     *
     * [DisplayState] is part of the comparison, not just the rectangle: alt-tabbing away does not
     * move the window, but it does change whether the overlay should be drawn at all, and a
     * tracker that only watched geometry would never mention it.
     */
    public fun poll(): ViewportUpdate? {
        val seen = source.read()
        val previous = last

        if (seen == null) {
            if (previous == null) return null
            last = null
            return ViewportUpdate.Lost
        }

        if (seen == previous) return null
        last = seen
        return ViewportUpdate.Changed(seen)
    }
}