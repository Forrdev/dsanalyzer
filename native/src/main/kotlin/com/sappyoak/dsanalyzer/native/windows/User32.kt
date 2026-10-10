package com.sappyoak.dsanalyzer.native.windows

import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

import com.sappyoak.dsanalyzer.native.ffi.NativeLibrary

/** `GetWindowLongPtrW`'s index for the extended style word */
private const val GWL_EXSTYLE = -20

/** `SetWindowPos`'s `hWndInsertAfter` sentinel for "above every non-topmost window" */
private const val HWND_TOPMOST = -1L

private const val MONITOR_DEFAULTTONEAREST = 2

internal object User32 {
    private val library = NativeLibrary.open("user32")

    private val findWindowCall = library.downcall(
        "FindWindowW",
        FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS)
    )

    private val getClientRectCall = library.downcall(
        "GetClientRect",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val getWindowRectCall = library.downcall(
        "GetWindowRect",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val clientToScreenCall = library.downcall(
        "ClientToScreen",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val getForegroundWindowCall = library.downcall(
        "GetForegroundWindow",
        FunctionDescriptor.of(ADDRESS)
    )

    private val isWindowCall = library.downcall(
        "IsWindow",
        FunctionDescriptor.of(JAVA_INT, ADDRESS)
    )

    // The Ptr variants, because the extended style word is pointer-width on 64-bit Windows and the
    // plain ones silently truncate it
    private val getWindowLongCall = library.downcall(
        "GetWindowLongPtrW",
        FunctionDescriptor.of(JAVA_LONG, ADDRESS, JAVA_INT)
    )

    private val setWindowLongCall = library.downcall(
        "SetWindowLongPtrW",
        FunctionDescriptor.of(JAVA_LONG, ADDRESS, JAVA_INT, JAVA_LONG)
    )

    private val setWindowPosCall = library.downcall(
        "SetWindowPos",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, JAVA_INT, JAVA_INT, JAVA_INT, JAVA_INT, JAVA_INT)
    )

    private val monitorFromWindowCall = library.downcall(
        "MonitorFromWindow",
        FunctionDescriptor.of(ADDRESS, ADDRESS, JAVA_INT)
    )

    private val getMonitorInfoCall = library.downcall(
        "GetMonitorInfoW",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    /** The first top-level window with this exact title, or null */
    fun findWindow(title: String): MemorySegment? = Arena.ofConfined().use { arena ->
        val handle = findWindowCall.invokeExact(MemorySegment.NULL, arena.wide(title)) as MemorySegment
        handle.takeIf { it.address() != 0L }
    }

    fun isWindow(window: MemorySegment): Boolean = (isWindowCall.invokeExact(window) as Int) != 0

    fun isForeground(window: MemorySegment): Boolean {
        val foreground = getForegroundWindowCall.invokeExact() as MemorySegment
        return foreground.address() == window.address()
    }

    /**
     * The client area in **screen** coordinates.
     *
     * Two calls rather than one because `GetClientRect` reports the client area relative to itself,
     * so its origin is always `(0, 0)` and it carries only the size. `ClientToScreen` supplies
     * where that origin actually sits. Using `GetWindowRect` instead
     * would include the border and title bar, and the mistake hides while testing
     * because the two agree exactly in borderless fullscreen.
     */
    fun clientBounds(window: MemorySegment): ScreenBounds? = Arena.ofConfined().use { arena ->
        val rect = arena.allocate(RECT)
        if ((getClientRectCall.invokeExact(window, rect) as Int) == 0) return null

        val origin = arena.allocate(POINT)
        if ((clientToScreenCall.invokeExact(window, origin) as Int) == 0) return null

        ScreenBounds(
            x = origin.intField(POINT, "x"),
            y = origin.intField(POINT, "y"),
            width = rect.intField(RECT, "right") - rect.intField(RECT, "left"),
            height = rect.intField(RECT, "bottom") - rect.intField(RECT, "top")
        )
    }

    /** The whole window including its frame, which only the fullscreen check wants */
    fun windowBounds(window: MemorySegment): ScreenBounds? = Arena.ofConfined().use { arena ->
        val rect = arena.allocate(RECT)
        if ((getWindowRectCall.invokeExact(window, rect) as Int) == 0) return null
        ScreenBounds.between(rect, RECT)
    }

    /** The display [window] sits most of the way inside, for comparing a window against its screen */
    fun monitorBounds(window: MemorySegment): ScreenBounds? = Arena.ofConfined().use { arena ->
        val monitor = monitorFromWindowCall.invokeExact(window, MONITOR_DEFAULTTONEAREST) as MemorySegment
        if (monitor.address() == 0L) return null

        val info = arena.allocate(MONITOR_INFO)
        info.set(JAVA_INT, MONITOR_INFO.offsetOf("cbSize"), MONITOR_INFO.byteSize().toInt())
        if ((getMonitorInfoCall.invokeExact(monitor, info) as Int) == 0) return null

        ScreenBounds.between(info.asSlice(MONITOR_INFO.offsetOf("rcMonitor"), RECT.byteSize()), RECT)
    }

    fun extendedStyle(window: MemorySegment): Long = getWindowLongCall.invokeExact(window, GWL_EXSTYLE) as Long

    /** Returns the previous value, which is zero both for "was zero" and for "failed" */
    fun setExtendedStyle(window: MemorySegment, style: Long): Long =
        setWindowLongCall.invokeExact(window, GWL_EXSTYLE, style) as Long

    /**
     * Move and resize [window], keeping it above every non-topmost window.
     *
     * `SWP_NOACTIVATE` is not optional. Without it every reposition pulls focus off the game, and a
     * game that loses focus while fullscreen can minimise itself
     */
    fun placeTopmost(window: MemorySegment, bounds: ScreenBounds, flags: Int): Boolean {
        val result = setWindowPosCall.invokeExact(
            window,
            MemorySegment.ofAddress(HWND_TOPMOST),
            bounds.x,
            bounds.y,
            bounds.width,
            bounds.height,
            flags
        ) as Int
        return result != 0
    }

    /** A null-terminated UTF-16 copy of [text], which is what the `W` entry points take */
    private fun Arena.wide(text: String): MemorySegment {
        val bytes = (text + '\u0000').toByteArray(Charsets.UTF_16LE)
        val segment = allocate(bytes.size.toLong())
        MemorySegment.copy(bytes, 0, segment, JAVA_BYTE, 0L, bytes.size)
        return segment
    }
}