package com.sappyoak.dsanalyzer.native.windows

/** Where the game is drawing, and whether anything drawn over it would be seen */
public data class Viewport(public val bounds: ScreenBounds, public val state: DisplayState)

public interface ViewportSource {
    /** The current viewport, or null when the game is not running */
    public fun read(): Viewport?
}

/** The live source: the game's window, re-found whenever it goes away */
public class GameWindowSource(private val title: String = GameWindow.PTDE_TITLE) : ViewportSource {
    private var window: GameWindow? = null

    override fun read(): Viewport? {
        val current = window?.takeIf { it.isAlive } ?: GameWindow.find(title)?.also { window = it }
        val bounds = current?.clientBounds

        if (bounds == null) {
            // Either it never existed or it has gone since. Drop it so the next read re-finds
            // rather than asking a dead handle forever
            window = null
            return null
        }

        return Viewport(bounds, current.displayState)
    }
}