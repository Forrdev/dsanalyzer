package com.sappyoak.dsanalyzer.native.linux

import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*
import java.lang.invoke.MethodHandle

import com.sappyoak.dsanalyzer.native.ffi.CallState
import com.sappyoak.dsanalyzer.native.ffi.NativeLibrary
import com.sappyoak.dsanalyzer.native.ffi.downcallLinux

/** struct iovec, a pointer to a buffer and how much of it to move */
private val IOVEC: MemoryLayout = MemoryLayout.structLayout(
    ADDRESS.withName("iov_base"),
    JAVA_LONG.withName("iov_len")
)

private val LENGTH_OFFSET = ADDRESS.byteSize()

/**
 * ssize_t process_vm_readv(pid_t, const struct iovec*, unsigned long, const struct iovec*,
 *                          unsigned long, unsigned long)
 */
private val DESCRIPTOR: FunctionDescriptor =
    FunctionDescriptor.of(JAVA_LONG, JAVA_INT, ADDRESS, JAVA_LONG, ADDRESS, JAVA_LONG, JAVA_LONG)


/**
 * Cross process memory access without ptrace, which needs no attach and leaves the target running.
 *
 * Whether one process may do this to another is up to the kernel, the caller has to own the target and
 * where Yama is enforcing, be allowed to trace it
 */
internal object LibC {
    private val library = NativeLibrary.builtin()

    val available: Boolean = "process_vm_readv" in library

    private val readCall: MethodHandle by lazy {
        library.downcallLinux("process_vm_readyv", DESCRIPTOR)
    }

    private val writeCall: MethodHandle by lazy {
        library.downcallLinux("process_vm_writev", DESCRIPTOR)
    }

    /** Bytes copied out of [pid] or the negated errno the kernel refused with */
    fun read(
        pid: Int,
        address: Long,
        into: MemorySegment
    ): Long = transfer(readCall, pid, address, into)

    /** Bytes copied into [pid] or the negated errno the kernel refused with */
    fun write(
        pid: Int,
        address: Long,
        from: MemorySegment
    ): Long = transfer(writeCall, pid, address, from)

    private fun transfer(call: MethodHandle, pid: Int, address: Long, local: MemorySegment): Long =
        Arena.ofConfined().use { arena ->
            val state = arena.allocate(CallState.layout)
            val here = iovec(arena, local.address(), local.byteSize())
            val there = iovec(arena, address, local.byteSize())
            val moved = call.invokeExact(state, pid, here, 1L, there, 1L, 0L) as Long
            if (moved < 0) -CallState.Errno.read(state).toLong() else moved
        }

    private fun iovec(arena: Arena, address: Long, length: Long): MemorySegment =
        arena.allocate(IOVEC).apply {
            set(ADDRESS, 0, MemorySegment.ofAddress(address))
            set(JAVA_LONG, LENGTH_OFFSET, length)
        }
}