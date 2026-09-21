package com.sappyoak.dsanalyzer.native.ffi

import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.ValueLayout.*
import io.kotest.matchers.shouldBe
import io.kotest.core.spec.style.FunSpec

import com.sappyoak.dsanalyzer.shared.platform.OS


private fun captureWindowsLastError(): Pair<Int, Int> = Arena.ofConfined().use { arena ->
    val setLastError = NativeLibrary.open("kernel32").downcallWindows(
        "SetLastError",
        FunctionDescriptor.ofVoid(JAVA_INT)
    )
    val state = arena.allocate(CallState.layout)
    setLastError.invoke(state, 1234)
    1234 to CallState.WindowsLastError.read(state)
}

private fun captureErrno(): Pair<Int, Int> = Arena.ofConfined().use { arena ->
    val close = NativeLibrary.open("libc.so.6").downcallLinux(
        "close",
        FunctionDescriptor.of(JAVA_INT, JAVA_INT)
    )
    val state = arena.allocate(CallState.layout)
    close.invokeExact(state, -1) as Int
    9 to CallState.Errno.read(state)
}

class NativeLibraryTest : FunSpec({
    val os = OS.current

    test("captures the error state as the call returns") {
        val expected = when {
            os.isWindows -> captureWindowsLastError()
            os.isLinux -> captureErrno()
            else -> return@test
        }

        expected.first.shouldBe(expected.second)
    }
})