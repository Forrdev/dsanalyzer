package com.sappyoak.dsanalyzer.native.ffi

import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

import com.sappyoak.dsanalyzer.shared.platform.OS

private val STRLEN = FunctionDescriptor.of(JAVA_LONG, ADDRESS)

private val LIBRARY = NativeLibrary.builtin()

private fun captureWindowsLastError(): Pair<Int, Int> = Arena.ofConfined().use { arena ->
    val setLastError = NativeLibrary.open("kernel32").capturingLastError(
        "SetLastError",
        FunctionDescriptor.ofVoid(JAVA_INT)
    )
    val state = arena.allocate(CallState.layout)
    setLastError.invoke(state, 1234)
    1234 to CallState.WindowsLastError.read(state)
}

private fun captureErrno(): Pair<Int, Int> = Arena.ofConfined().use { arena ->
    val close = NativeLibrary.open("libc.so.6").capturingErrno(
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

    test("a capturing handle takes the state ahead of what its descriptor declares") {
        if ("strlen" !in LIBRARY) return@test

        val plain = LIBRARY.downcall("strlen", STRLEN)
        val capturing = LIBRARY.downcallCapturing("strlen", STRLEN, CallState.Errno)

        assertSoftly {
            plain.type().parameterCount() shouldBe 1
            capturing.type().parameterCount() shouldBe 2
            capturing.type().parameterType(0) shouldBe MemorySegment::class.java
            capturing.type().returnType() shouldBe plain.type().returnType()
        }
    }
})