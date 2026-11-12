package com.sappyoak.dsanalyzer.native.windows

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_BYTE

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.CodeMemory
import com.sappyoak.dsanalyzer.native.process.CodePatch
import com.sappyoak.dsanalyzer.native.process.NEAR_SPAN
import com.sappyoak.dsanalyzer.native.process.ALLOCATION_STEP
import com.sappyoak.dsanalyzer.native.process.placeIn
import com.sappyoak.dsanalyzer.native.process.ProcessAccessException

private const val MEM_COMMIT_RESERVE = 0x1000 or 0x2000
private const val MEM_RELEASE = 0x8000
private const val MEM_FREE = 0x10000
private const val PAGE_EXECUTE_READWRITE = 0x40
private const val PAGE_READWRITE = 0x04

/** Times a batch waits for threads to leave the patched ranges before it gives up */
private const val BUSY_ATTEMPTS = 50
private const val BUSY_WAIT_MS = 1L

internal class WindowsCodeMemory(
    private val handle: MemorySegment,
    private val pid: Int,
    private val wow64: Boolean,
    private val isOpen: () -> Boolean
) : CodeMemory {
    override fun allocate(size: Int): Address? {
        if (!isOpen() || size <= 0) return null
        val base = Kernel32.virtualAllocEx(handle, size.toLong(), MEM_COMMIT_RESERVE, PAGE_EXECUTE_READWRITE)
        return if (base != 0L) Address(base) else null
    }

    override fun allocate(size: Int, near: Address): Address? {
        if (wow64) return allocate(size)
        if (!isOpen() || size <= 0) return null
        val low = maxOf(near.value - NEAR_SPAN, ALLOCATION_STEP)
        val high = near.value + NEAR_SPAN
        return Arena.ofConfined().use { arena ->
            val info = arena.allocate(MEMORY_BASIC_INFORMATION)
            claim(info, size.toLong(), near.value, low, high, upward = true)
                ?: claim(info, size.toLong(), near.value, low, high, upward = false)
        }
    }

    override fun free(address: Address): Boolean =
        isOpen() && Kernel32.virtualFreeEx(handle, address.value, MEM_RELEASE)

    override fun makeWritable(address: Address, size: Long): Boolean =
        isOpen() && Kernel32.virtualProtectEx(handle, address.value, size, PAGE_READWRITE) != null

    override fun startThread(entry: Address, parameter: Address): Boolean {
        if (!isOpen()) return false
        val thread = Kernel32.createRemoteThread(handle, entry.value, parameter.value) ?: return false
        Kernel32.closeHandle(thread)
        return true
    }

    override fun patch(patches: List<CodePatch>): Boolean {
        if (!isOpen() || patches.isEmpty()) return false
        repeat(BUSY_ATTEMPTS) {
            val written = try {
                SuspendedThreads.of(pid, wow64).use { threads ->
                    val busy = threads.instructionPointers().any { pointer -> patches.any { pointer in it } }
                    if (busy) null else writeAll(patches)
                }
            } catch (err: ProcessAccessException) {
                return false
            }
            if (written != null) return written
            Thread.sleep(BUSY_WAIT_MS)
        }
        return false
    }

    /** Walks regions away from [from] and commits the first free one a block fits in */
    private fun claim(
        info: MemorySegment,
        size: Long,
        from: Long,
        low: Long,
        high: Long,
        upward: Boolean
    ): Address? {
        var cursor = if (upward) from else from - 1
        while (cursor in low until high) {
            if (Kernel32.virtualQueryEx(handle, cursor, info) == 0L) return null
            val base = info.addressField(MEMORY_BASIC_INFORMATION, "BaseAddress")
            val end = base + info.longField(MEMORY_BASIC_INFORMATION, "RegionSize")
            if (info.intField(MEMORY_BASIC_INFORMATION, "State") == MEM_FREE) {
                val block = placeIn(base, end, size, low, high, fromTop = !upward)
                    ?.let { Kernel32.virtualAllocEx(handle, it, size, MEM_COMMIT_RESERVE, PAGE_EXECUTE_READWRITE) }
                if (block != null && block != 0L) return Address(block)
            }
            cursor = if (upward) end else base - 1
        }
        return null
    }

    private fun writeAll(patches: List<CodePatch>): Boolean {
        val originals = patches.map { read(it.address, it.bytes.size) ?: return false }
        patches.forEachIndexed { index, patch ->
            if (!write(patch.address, patch.bytes)) {
                (0 until index).forEach { done -> write(patches[done].address, originals[done]) }
                return false
            }
        }
        return true
    }

    private fun read(address: Address, length: Int): ByteArray? = Arena.ofConfined().use { arena ->
        val buffer = arena.allocate(length.toLong())
        if (Kernel32.readProcessMemory(handle, address.value, buffer)) buffer.toArray(JAVA_BYTE) else null
    }

    private fun write(address: Address, bytes: ByteArray): Boolean {
        val size = bytes.size.toLong()
        val previous = Kernel32.virtualProtectEx(handle, address.value, size, PAGE_EXECUTE_READWRITE) ?: return false
        val written = Arena.ofConfined().use { arena ->
            Kernel32.writeProcessMemory(handle, address.value, arena.allocateFrom(JAVA_BYTE, *bytes))
        }
        Kernel32.virtualProtectEx(handle, address.value, size, previous)
        return written && Kernel32.flushInstructionCache(handle, address.value, size)
    }
}
