package com.sappyoak.dsanalyzer.native.windows

import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.ValueLayout.*

import com.sappyoak.dsanalyzer.native.ffi.NativeLibrary

/** `QUNS_RUNNING_D3D_FULL_SCREEN`, the one value of `QUERY_USER_NOTIFICATION_STATE` that matters here */
private const val RUNNING_D3D_FULLSCREEN = 3

internal object FullscreenState {
    private val shell = NativeLibrary.openOrNull("shell32")

    private val queryCall = shell?.downcall(
        "SHQueryUserNotificationState",
        FunctionDescriptor.of(JAVA_INT, ADDRESS)
    )

    /**
     * True when the foreground application has the display exclusively, null when unknown.
     *
     * Null rather than false on failure, because "we could not tell" and "it is fine" lead to
     * different messages: one offers a hint, the other promises something.
     */
    fun exclusiveFullscreen(): Boolean? {
        val call = queryCall ?: return null
        return Arena.ofConfined().use { arena ->
            val state = arena.allocate(JAVA_INT)
            val result = call.invokeExact(state) as Int
            if (result != 0) null else state.get(JAVA_INT, 0) == RUNNING_D3D_FULLSCREEN
        }
    }
}