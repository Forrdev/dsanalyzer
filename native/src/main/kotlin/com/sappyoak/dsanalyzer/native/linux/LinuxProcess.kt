package com.sappyoak.dsanalyzer.native.linux

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_BYTE
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.atomic.AtomicBoolean

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.MemoryRegion
import com.sappyoak.dsanalyzer.native.process.AttachedProcess
import com.sappyoak.dsanalyzer.native.process.ModuleInfo
import com.sappyoak.dsanalyzer.native.process.ProcessInfo
import com.sappyoak.dsanalyzer.shared.platform.PointerSize

/**
 * A process reached through /proc and process_vm_readv.
 *
 * There is no handle to hold here: the kernel checks the caller on every transfer, so closing only
 * stops this object being used further
 */
internal class LinuxProcess(
    override val info: ProcessInfo,
    override val pointerSize: PointerSize
) : AttachedProcess {
    private val closed = AtomicBoolean(false)
    private val entry = Path.of("/proc/${info.pid}")

    override val isRunning: Boolean get() = !closed.get() && Files.exists(entry)

    override fun modules(): List<ModuleInfo> = mappedModules(info.pid)

    override fun read(address: Address, length: Int): ByteArray? {
        if (closed.get() || length <= 0) return null
        return Arena.ofConfined().use { arena ->
            val buffer = arena.allocate(length.toLong())
            if (read(address, buffer)) buffer.toArray(JAVA_BYTE) else null
        }
    }

    override fun read(address: Address, into: MemorySegment): Boolean {
        require(into.isNative) { "Reads need a native buffer" }
        if (closed.get() || into.byteSize() == 0L) return false
        return LibC.read(info.pid, address.value, into) == into.byteSize()
    }

    override fun write(address: Address, bytes: ByteArray): Boolean {
        if (closed.get() || bytes.isEmpty()) return false
        return Arena.ofConfined().use { arena ->
            write(address, arena.allocateFrom(JAVA_BYTE, *bytes))
        }
    }

    override fun write(address: Address, from: MemorySegment): Boolean {
        require(from.isNative) { "Writes need a native buffer" }
        if (closed.get() || from.byteSize() == 0L) return false
        return LibC.write(info.pid, address.value, from) == from.byteSize()
    }

    override fun regions(range: AddressRange): List<MemoryRegion> =
        mappings(info.pid).mapNotNull { it.clippedTo(range) }

    override fun close() {
        closed.set(true)
    }
}

private fun ProcMapping.clippedTo(range: AddressRange): MemoryRegion? {
    val start = maxOf(this.range.start.value, range.start.value)
    val end = minOf(this.range.end.value, range.end.value)
    if (start >= end) return null

    return MemoryRegion(
        range = AddressRange(Address(start), end - start),
        readable = readable,
        executable = executable
    )
}