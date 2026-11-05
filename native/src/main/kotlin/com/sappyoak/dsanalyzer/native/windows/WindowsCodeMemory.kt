package com.sappyoak.dsanalyzer.native.windows

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_BYTE

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.CodeMemory

private const val MEM_COMMIT_RESERVE = 0x1000 or 0x2000
private const val MEM_RELEASE = 0x8000
private const val PAGE_EXECUTE_READWRITE = 0x40

internal class WindowsCodeMemory(
    private val handle: MemorySegment,
    private val isOpen: () -> Boolean
) : CodeMemory {
    override fun allocate(size: Int): Address? {
        if (!isOpen() || size <= 0) return null
        val base = Kernel32.virtualAllocEx(handle, size.toLong(), MEM_COMMIT_RESERVE, PAGE_EXECUTE_READWRITE)
        return if (base != 0L) Address(base) else null
    }

    override fun free(address: Address): Boolean =
        isOpen() && Kernel32.virtualFreeEx(handle, address.value, MEM_RELEASE)

    override fun patch(address: Address, bytes: ByteArray): Boolean {
        if (!isOpen() || bytes.isEmpty()) return false
        val size = bytes.size.toLong()
        val previous = Kernel32.virtualProtectEx(handle, address.value, size, PAGE_EXECUTE_READWRITE) ?: return false
        val written = Arena.ofConfined().use { arena ->
            Kernel32.writeProcessMemory(handle, address.value, arena.allocateFrom(JAVA_BYTE, *bytes))
        }
        Kernel32.virtualProtectEx(handle, address.value, size, previous)
        return written && Kernel32.flushInstructionCache(handle, address.value, size)
    }
}
