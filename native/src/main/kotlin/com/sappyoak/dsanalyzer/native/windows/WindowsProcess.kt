package com.sappyoak.dsanalyzer.native.windows

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*
import java.util.concurrent.atomic.AtomicBoolean

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.MemoryRegion
import com.sappyoak.dsanalyzer.native.process.AttachedProcess
import com.sappyoak.dsanalyzer.native.process.ProcessInfo
import com.sappyoak.dsanalyzer.shared.binary.PointerSize

private const val STILL_ACTIVE = 259
private const val MEM_COMMIT = 0x1000
private const val PAGE_NOACCESS = 0x01
private const val PAGE_GUARD = 0x100

/**
 * An open handle to a Windows process
 *
 * Reads and writes can either go straight between the target and the caller's native buffer with no
 * staging copy, giving zero allocations, or read into/from a passed ByteArray buffer
 */
internal class WindowsProcess(
    override val info: ProcessInfo,
    private val handle: MemorySegment,
    override val pointerSize: PointerSize
) : AttachedProcess {
    private val closed = AtomicBoolean(false)

    override val isRunning: Boolean get() = !closed.get() && Kernel32.exitCode(handle) == STILL_ACTIVE

    override fun read(address: Address, length: Int): ByteArray? {
        if (closed.get() || length <= 0) return null
        return Arena.ofConfined().use { arena ->
            val buffer = arena.allocate(length.toLong())
            if (Kernel32.readProcessMemory(handle, address.value, buffer)) buffer.toArray(JAVA_BYTE)
            else null
        }
    }

    override fun read(address: Address, into: MemorySegment): Boolean {
        require(into.isNative) { "Reads need a native buffer" }
        if (closed.get() || into.byteSize() == 0L) return false
        return Kernel32.readProcessMemory(handle, address.value, into)
    }

    override fun write(address: Address, bytes: ByteArray): Boolean {
        if (closed.get() || bytes.isEmpty()) return false
        return Arena.ofConfined().use { arena ->
            Kernel32.writeProcessMemory(handle, address.value, arena.allocateFrom(JAVA_BYTE, *bytes))
        }
    }

    override fun write(address: Address, from: MemorySegment): Boolean {
        require(from.isNative) { "Writes need a native buffer" }
        if (closed.get() || from.byteSize() == 0L) return false
        return Kernel32.writeProcessMemory(handle, address.value, from)
    }

    override fun regions(range: AddressRange): List<MemoryRegion> = Arena.ofConfined().use { arena ->
        val info = arena.allocate(MEMORY_BASIC_INFORMATION)
        val regions = mutableListOf<MemoryRegion>()
        var cursor = range.start.value
        while (cursor < range.end.value && !closed.get()) {
            if (Kernel32.virtualQueryEx(handle, cursor, info) == 0L) break

            val base = info.addressField(MEMORY_BASIC_INFORMATION, "BaseAddress")
            val end = base + info.longField(MEMORY_BASIC_INFORMATION, "RegionSize")
            val start = maxOf(base, range.start.value)
            val clippedEnd = minOf(end, range.end.value)
            regions.add(MemoryRegion(
                range = AddressRange(Address(start), clippedEnd - start),
                readable = isReadable(info)
            ))
            cursor = end
        }

        regions
    }

    override fun close() {
        if (closed.compareAndSet(false, true)) {
            Kernel32.closeHandle(handle)
        }
    }

    private fun isReadable(info: MemorySegment): Boolean {
        val protect = info.intField(MEMORY_BASIC_INFORMATION, "Protect")
        return info.intField(MEMORY_BASIC_INFORMATION, "State") == MEM_COMMIT &&
                protect != 0 &&
                protect and PAGE_NOACCESS == 0 &&
                protect and PAGE_GUARD == 0
    }
}