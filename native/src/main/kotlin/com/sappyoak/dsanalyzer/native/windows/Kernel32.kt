package com.sappyoak.dsanalyzer.native.windows

import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*
import java.lang.invoke.MethodHandle


import com.sappyoak.dsanalyzer.native.ffi.CallState
import com.sappyoak.dsanalyzer.native.ffi.NativeLibrary
import com.sappyoak.dsanalyzer.native.ffi.capturingLastError
import com.sappyoak.dsanalyzer.native.process.ProcessAccessException

private const val THREAD_CALL_FAILED = -1
private const val INVALID_HANDLE = -1L

internal object Kernel32 {
    private val library = NativeLibrary.open("kernel32")

    private val openProcessCall = library.capturingLastError(
        "OpenProcess",
        FunctionDescriptor.of(ADDRESS, JAVA_INT, JAVA_INT, JAVA_INT)
    )

    private val createSnapshotCall = library.capturingLastError(
        "CreateToolhelp32Snapshot",
        FunctionDescriptor.of(ADDRESS, JAVA_INT, JAVA_INT)
    )

    private val closeHandleCall = library.downcall(
        "CloseHandle",
        FunctionDescriptor.of(JAVA_INT, ADDRESS)
    )

    private val process32FirstCall = library.downcall(
        "Process32FirstW",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val process32NextCall = library.downcall(
        "Process32NextW",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val module32FirstCall = library.downcall(
        "Module32FirstW",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val module32NextCall = library.downcall(
        "Module32NextW",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val readProcessMemoryCall = library.downcall(
        "ReadProcessMemory",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS, JAVA_LONG, ADDRESS)
    )

    private val writeProcessMemoryCall = library.downcall(
        "WriteProcessMemory",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS, JAVA_LONG, ADDRESS)
    )

    private val virtualQueryExCall = library.downcall(
        "VirtualQueryEx",
        FunctionDescriptor.of(JAVA_LONG, ADDRESS, ADDRESS, ADDRESS, JAVA_LONG)
    )

    private val virtualAllocExCall = library.downcall(
        "VirtualAllocEx",
        FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS, JAVA_LONG, JAVA_INT, JAVA_INT)
    )

    private val virtualFreeExCall = library.downcall(
        "VirtualFreeEx",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, JAVA_LONG, JAVA_INT)
    )

    private val virtualProtectExCall = library.downcall(
        "VirtualProtectEx",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, JAVA_LONG, JAVA_INT, ADDRESS)
    )

    private val flushInstructionCacheCall = library.downcall(
        "FlushInstructionCache",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, JAVA_LONG)
    )

    private val getExitCodeProcessCall = library.downcall(
        "GetExitCodeProcess",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val isWow64ProcessCall = library.downcall(
        "IsWow64Process",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val thread32FirstCall = library.downcall(
        "Thread32First",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val thread32NextCall = library.downcall(
        "Thread32Next",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val openThreadCall = library.downcall(
        "OpenThread",
        FunctionDescriptor.of(ADDRESS, JAVA_INT, JAVA_INT, JAVA_INT)
    )

    private val suspendThreadCall = library.downcall(
        "SuspendThread",
        FunctionDescriptor.of(JAVA_INT, ADDRESS)
    )

    private val resumeThreadCall = library.downcall(
        "ResumeThread",
        FunctionDescriptor.of(JAVA_INT, ADDRESS)
    )

    private val getThreadContextCall = library.downcall(
        "GetThreadContext",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val wow64GetThreadContextCall = library.downcall(
        "Wow64GetThreadContext",
        FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS)
    )

    private val createRemoteThreadCall = library.downcall(
        "CreateRemoteThread",
        FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS, JAVA_LONG, ADDRESS, ADDRESS, JAVA_INT, ADDRESS)
    )

    public fun openProcess(access: Int, pid: Int): MemorySegment = capturing { state ->
        val handle = openProcessCall.invokeExact(state, access, 0, pid) as MemorySegment
        if (handle.address() == 0L) throw failure("OpenProcess", state)
        handle
    }

    public fun createSnapshot(flags: Int, pid: Int): MemorySegment = capturing { state ->
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

    /** The base of the new region, or 0 */
    public fun virtualAllocEx(process: MemorySegment, size: Long, allocationType: Int, protect: Int): Long =
        virtualAllocEx(process, 0L, size, allocationType, protect)

    /** As above, at exactly [address]; 0 if that range is not free */
    public fun virtualAllocEx(process: MemorySegment, address: Long, size: Long, allocationType: Int, protect: Int): Long =
        (virtualAllocExCall.invokeExact(
            process,
            MemorySegment.ofAddress(address),
            size,
            allocationType,
            protect
        ) as MemorySegment).address()

    public fun virtualFreeEx(process: MemorySegment, address: Long, freeType: Int): Boolean =
        virtualFreeExCall.invokeExact(process, MemorySegment.ofAddress(address), 0L, freeType) as Int != 0

    /** The protection the range had before, or null if it could not be changed */
    public fun virtualProtectEx(process: MemorySegment, address: Long, size: Long, protect: Int): Int? =
        Arena.ofConfined().use { arena ->
            val old = arena.allocate(JAVA_INT)
            val result = virtualProtectExCall.invokeExact(process, MemorySegment.ofAddress(address), size, protect, old) as Int
            if (result != 0) old.get(JAVA_INT, 0) else null
        }

    public fun flushInstructionCache(process: MemorySegment, address: Long, size: Long): Boolean =
        flushInstructionCacheCall.invokeExact(process, MemorySegment.ofAddress(address), size) as Int != 0

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

    fun thread32(snapshot: MemorySegment, entry: MemorySegment, first: Boolean): Boolean {
        val function = if (first) thread32FirstCall else thread32NextCall
        return function.invokeExact(snapshot, entry) as Int != 0
    }

    /** The thread's handle, or null if it could not be opened */
    fun openThread(access: Int, threadId: Int): MemorySegment? {
        val handle = openThreadCall.invokeExact(access, 0, threadId) as MemorySegment
        return handle.takeIf { it.address() != 0L }
    }

    fun suspendThread(thread: MemorySegment): Boolean = suspendThreadCall.invokeExact(thread) as Int != THREAD_CALL_FAILED

    fun resumeThread(thread: MemorySegment): Boolean = resumeThreadCall.invokeExact(thread) as Int != THREAD_CALL_FAILED

    /** Fills [context] for a thread of the same width as this process */
    fun getThreadContext(thread: MemorySegment, context: MemorySegment): Boolean =
        getThreadContextCall.invokeExact(thread, context) as Int != 0

    /** Fills [context] for a 32-bit thread running under WoW64 */
    fun wow64GetThreadContext(thread: MemorySegment, context: MemorySegment): Boolean =
        wow64GetThreadContextCall.invokeExact(thread, context) as Int != 0

    /** Starts a thread in [process] at [start] with parameter. */
    fun createRemoteThread(process: MemorySegment, start: Long, parameter: Long): MemorySegment? {
        val handle = createRemoteThreadCall.invokeExact(
            process,
            MemorySegment.NULL,
            0L,
            MemorySegment.ofAddress(start),
            MemorySegment.ofAddress(parameter),
            0,
            MemorySegment.NULL
        ) as MemorySegment
        return handle.takeIf { it.address() != 0L }
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

    private inline fun <T> capturing(call: (MemorySegment) -> T): T =
        Arena.ofConfined().use { arena -> call(arena.allocate(CallState.layout))}

    private fun failure(function: String, state: MemorySegment): ProcessAccessException {
        val code = CallState.WindowsLastError.read(state)
        return ProcessAccessException("$function failed (error $code)", code)
    }
}
