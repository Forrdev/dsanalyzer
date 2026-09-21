package com.sappyoak.dsanalyzer.native.windows

import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*
import java.lang.invoke.MethodHandle


import com.sappyoak.dsanalyzer.native.ffi.CallState
import com.sappyoak.dsanalyzer.native.ffi.NativeLibrary
import com.sappyoak.dsanalyzer.native.ffi.downcallWindows
import com.sappyoak.dsanalyzer.native.process.ProcessAccessException

private const val INVALID_HANDLE = -1L

internal object Kernel32 {
    private val library = NativeLibrary.open("kernel32")

    private val openProcessCall = library.downcallWindows(
        "OpenProcess",
        FunctionDescriptor.of(ADDRESS, JAVA_INT, JAVA_INT, JAVA_INT)
    )

    private val createSnapshotCall = library.downcallWindows(
        "CreateToolhelp32Snapshot",
        FunctionDescriptor.of(ADDRESS, JAVA_INT, JAVA_INT)
    )

    private val closeHandleCall = library.downcallWindows(
        "CloseHandle",
        FunctionDescriptor.of(JAVA_INT, ADDRESS)
    )

    private val process32FirstCall = library.downcallWindows(
        "Process32FirstW",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val process32NextCall = library.downcallWindows(
        "Process32NextW",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val module32FirstCall = library.downcallWindows(
        "Module32FirstW",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val module32NextCall = library.downcallWindows(
        "Module32NextW",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val readProcessMemoryCall = library.downcallWindows(
        "ReadProcessMemory",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS, JAVA_LONG, ADDRESS)
    )

    private val writeProcessMemoryCall = library.downcallWindows(
        "WriteProcessMemory",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS, JAVA_LONG, ADDRESS)
    )

    private val virtualQueryExCall = library.downcallWindows(
        "VirtualQueryEx",
        FunctionDescriptor.of(JAVA_LONG, ADDRESS, ADDRESS, ADDRESS, JAVA_LONG)
    )

    private val getExitCodeProcessCall = library.downcallWindows(
        "GetExitCodeProcess",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val isWow64ProcessCall = library.downcallWindows(
        "IsWow64Process",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    public fun openProcess(access: Int, pid: Int): MemorySegment = Arena.ofConfined().use { arena ->
        val state = arena.allocate(CallState.layout)
        val handle = openProcessCall.invokeExact(state, access, 0, pid) as MemorySegment
        if (handle.address() == 0L) throw failure("OpenProcess", state)
        handle
    }

    public fun createSnapshot(flags: Int, pid: Int): MemorySegment = Arena.ofConfined().use { arena ->
        val state = arena.allocate(CallState.layout)
        val handle = createSnapshotCall.invokeExact(state, flags, pid) as MemorySegment
        if (handle.address() == INVALID_HANDLE) throw failure("CreateToolhelp32Snapshot", state)
        handle
    }

    public fun closeHandle(handle: MemorySegment): Boolean {
        val result = closeHandleCall.invokeExact(handle) as Int
        return result != 0
    }

    public fun process32(snapshot: MemorySegment, entry: MemorySegment, first: Boolean): Boolean {
        val function = if (first) process32FirstCall else process32NextCall
        val result = function.invokeExact(snapshot, entry) as Int
        return result != 0
    }

    public fun module32(snapshot: MemorySegment, entry: MemorySegment, first: Boolean): Boolean {
        val function = if (first) module32FirstCall else module32NextCall
        val result = function.invokeExact(snapshot, entry) as Int
        return result != 0
    }

    public fun readProcessMemory(process: MemorySegment, address: Long, into: MemorySegment): Boolean =
        transfer(readProcessMemoryCall, process, address, into)

    public fun writeProcessMemory(process: MemorySegment, address: Long, from: MemorySegment): Boolean =
        transfer(writeProcessMemoryCall, process, address, from)

    public fun virtualQueryEx(process: MemorySegment, address: Long, info: MemorySegment): Long =
        virtualQueryExCall.invokeExact(
            process,
            MemorySegment.ofAddress(address),
            info,
            info.byteSize()
        ) as Long

    public fun exitCode(process: MemorySegment): Int? = Arena.ofConfined().use { arena ->
        val code = arena.allocate(JAVA_INT)
        val result = getExitCodeProcessCall.invokeExact(process, code) as Int
        if (result != 0) code.get(JAVA_INT, 0) else null
    }

    /** Returns true for 32-bit process running on 64-bit Windows */
    public fun isWow64(process: MemorySegment): Boolean = Arena.ofConfined().use { arena ->
        val result = arena.allocate(JAVA_INT)
        val completed = isWow64ProcessCall.invokeExact(process, result) as Int
        completed != 0 && result.get(JAVA_INT, 0) != 0
    }

    private fun transfer(
        function: MethodHandle,
        process: MemorySegment,
        address: Long,
        local: MemorySegment
    ): Boolean {
        val remote = MemorySegment.ofAddress(address)
        val result = function.invokeExact(process, remote, local, local.byteSize(), MemorySegment.NULL) as Int
        return result != 0
    }

    private fun failure(function: String, state: MemorySegment): ProcessAccessException {
        val code = CallState.WindowsLastError.read(state)
        return ProcessAccessException("$function failed (error $code)", code)
    }
}