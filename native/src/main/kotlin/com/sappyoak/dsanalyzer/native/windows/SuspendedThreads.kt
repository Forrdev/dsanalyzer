package com.sappyoak.dsanalyzer.native.windows

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_LONG

private const val TH32CS_SNAPTHREAD = 0x4

/** THREAD_SUSPEND_RESUME | THREAD_GET_CONTEXT */
private const val THREAD_ACCESS = 0x0002 or 0x0008

/** x64 `CONTEXT`: 16-byte aligned, `ContextFlags` at 0x30, `Rip` at 0xF8; CONTEXT_CONTROL */
private const val CONTEXT_SIZE = 0x4D0L
private const val CONTEXT_ALIGN = 16L
private const val CONTEXT_FLAGS_AT = 0x30L
private const val CONTEXT_RIP_AT = 0xF8L
private const val CONTEXT_CONTROL = 0x00100001

/** `WOW64_CONTEXT`: `ContextFlags` at 0, `Eip` at 0xB8; WOW64_CONTEXT_CONTROL */
private const val WOW64_CONTEXT_SIZE = 0x2CCL
private const val WOW64_EIP_AT = 0xB8L
private const val WOW64_CONTEXT_CONTROL = 0x00010001

/**
 * Every thread of a process, suspended until [close]. A thread that exists or cannot be opened while
 * the set is built is left out
 */
internal class SuspendedThreads private constructor(
    private val threads: List<MemorySegment>,
    private val wow64: Boolean
) : AutoCloseable {

    fun instructionPointers(): List<Long> = Arena.ofConfined().use { arena ->
        threads.map { thread ->
            if (wow64) wow64Pointer(arena, thread) else nativePointer(arena, thread)
        }
    }

    override fun close() {
        threads.forEach { thread ->
            Kernel32.resumeThread(thread)
            Kernel32.closeHandle(thread)
        }
    }

    private fun nativePointer(arena: Arena, thread: MemorySegment): Long {
        val context = arena.allocate(CONTEXT_SIZE, CONTEXT_ALIGN)
        context.set(JAVA_INT, CONTEXT_FLAGS_AT, CONTEXT_CONTROL)
        return if (Kernel32.getThreadContext(thread, context)) context.get(JAVA_LONG, CONTEXT_RIP_AT) else -1L
    }

    private fun wow64Pointer(arena: Arena, thread: MemorySegment): Long {
        val context = arena.allocate(WOW64_CONTEXT_SIZE, Int.SIZE_BYTES.toLong())
        context.set(JAVA_INT, 0L, WOW64_CONTEXT_CONTROL)
        return if (Kernel32.wow64GetThreadContext(thread, context)) {
            context.get(JAVA_INT, WOW64_EIP_AT).toLong() and 0xFFFFFFFFL
        } else -1L
    }

    companion object {
        fun of(pid: Int, wow64: Boolean): SuspendedThreads {
            val snapshot = Kernel32.createSnapshot(TH32CS_SNAPTHREAD, 0)
            try {
                return SuspendedThreads(suspendAll(snapshot, pid), wow64)
            } finally {
                Kernel32.closeHandle(snapshot)
            }
        }

        private fun suspendAll(snapshot: MemorySegment, pid: Int): List<MemorySegment> = Arena.ofConfined().use { arena ->
            val entry = arena.allocate(THREAD_ENTRY)
            entry.set(JAVA_INT, THREAD_ENTRY.offsetOf("dwSize"), THREAD_ENTRY.byteSize().toInt())
            val suspended = mutableListOf<MemorySegment>()
            var more = Kernel32.thread32(snapshot, entry, first = true)
            while (more) {
                if (entry.intField(THREAD_ENTRY, "th32OwnerProcessID") == pid) {
                    suspend(entry.intField(THREAD_ENTRY, "th32ThreadID"))?.let(suspended::add)
                }
                more = Kernel32.thread32(snapshot, entry, first = false)
            }
            suspended
        }

        private fun suspend(threadId: Int): MemorySegment? {
            val thread = Kernel32.openThread(THREAD_ACCESS, threadId) ?: return null
            if (Kernel32.suspendThread(thread)) return thread
            Kernel32.closeHandle(thread)
            return null
        }
    }
}
