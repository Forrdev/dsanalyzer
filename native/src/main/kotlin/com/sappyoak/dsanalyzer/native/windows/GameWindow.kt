package com.sappyoak.dsanalyzer.native.windows

import java.lang.foreign.MemorySegment

/** What the overlay can expect of the display right now */
public enum class DisplayState {
    /** The game is in front and compositing normally, so an overlay will be seen */
    Overlayable,

    /** The game is in front but holds the display exclusively, so nothing will be drawn over it */
    ExclusiveFullscreen,

    /** Something else is in front. Not a fault — the overlay should simply hide */
    NotForeground,

    /** The exclusivity check is unavailable, so an invisible overlay cannot be explained */
    Unknown
}

/**
 * The game's own top-level window, found by title.
 */
public class GameWindow internal constructor(private val handle: MemorySegment) {
    /** False once the game exits, after which this instance should be dropped and re-found */
    public val isAlive: Boolean get() = User32.isWindow(handle)

    public val isForeground: Boolean get() = User32.isForeground(handle)

    public val clientBounds: ScreenBounds? get() = User32.clientBounds(handle)


    public val displayState: DisplayState
        get() = when {
            !isForeground -> DisplayState.NotForeground
            else -> when (FullscreenState.exclusiveFullscreen()) {
                true -> DisplayState.ExclusiveFullscreen
                false -> DisplayState.Overlayable
                null -> DisplayState.Unknown
            }
        }

    public companion object {
        /**
         * PTDE's window title. Remastered uses `DARK SOULS™: REMASTERED`, so this becomes a
         * per-edition value the moment the DSR work lands rather than a constant.
         */
        public const val PTDE_TITLE: String = "DARK SOULS"

        public fun find(title: String = PTDE_TITLE): GameWindow? =
            User32.findWindow(title)?.let(::GameWindow)
    }
}