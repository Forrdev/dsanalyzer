package com.sappyoak.dsanalyzer.native.windows

import java.lang.foreign.MemorySegment

private const val WS_EX_TRANSPARENT = 0x00000020L
private const val WS_EX_TOOLWINDOW = 0x00000080L
private const val WS_EX_LAYERED = 0x00080000L
private const val WS_EX_NOACTIVATE = 0x08000000L

private const val SWP_NOACTIVATE = 0x0010

public class OverlaySurface(handle: Long) {
    private val window: MemorySegment = MemorySegment.ofAddress(handle)

    /**
     * Make the window ignore the mouse, refuse focus, and stay out of the taskbar.
     *
     * The four styles, and what each one is actually for:
     *
     * - `WS_EX_TRANSPARENT` — hit-testing falls through to whatever is underneath, so clicks reach
     *   the game. This is the one that makes it an overlay rather than a window in the way.
     * - `WS_EX_LAYERED` — required *for* `TRANSPARENT` to behave. Compose's own transparent window
     *   already sets it, so this is belt-and-braces rather than new, and it is deliberately OR-ed
     *   into the existing style instead of replacing it: clearing a style Compose relies on gives
     *   a window that is invisible for reasons that look like ours.
     * - `WS_EX_NOACTIVATE` — the window never takes focus, even if something tries to give it.
     * - `WS_EX_TOOLWINDOW` — keeps it out of the taskbar and the alt-tab list, where an invisible
     *   click-through window is only ever a nuisance.
     */
    public fun makeClickThrough(): Boolean {
        val wanted = WS_EX_TRANSPARENT or WS_EX_LAYERED or WS_EX_NOACTIVATE or WS_EX_TOOLWINDOW
        val current = User32.extendedStyle(window)
        if (current and wanted == wanted) return true

        User32.setExtendedStyle(window, current or wanted)
        return User32.extendedStyle(window) and wanted == wanted
    }

    /**
     * Put the window exactly over [bounds], above everything, without stealing focus.
     */
    public fun place(bounds: ScreenBounds): Boolean =
        !bounds.isEmpty && User32.placeTopmost(window, bounds, SWP_NOACTIVATE)

    public companion object {
        public fun named(title: String): OverlaySurface? =
            User32.findWindow(title)?.let { OverlaySurface(it.address()) }
    }
}