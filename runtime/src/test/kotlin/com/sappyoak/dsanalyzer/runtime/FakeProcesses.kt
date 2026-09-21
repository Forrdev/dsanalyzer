package com.sappyoak.dsanalyzer.runtime

import java.lang.foreign.MemorySegment

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.MemoryRegion
import com.sappyoak.dsanalyzer.native.process.AttachedProcess
import com.sappyoak.dsanalyzer.native.process.ModuleInfo
import com.sappyoak.dsanalyzer.native.process.ProcessAccessException
import com.sappyoak.dsanalyzer.native.process.ProcessInfo
import com.sappyoak.dsanalyzer.native.process.Processes
import com.sappyoak.dsanalyzer.shared.platform.PointerSize

internal class FakeProcesses(
    private val pointerSize: PointerSize = PointerSize.IntPointer,
    private val denied: Set<Int> = emptySet()
) : Processes {
    val running = mutableListOf<ProcessInfo>()
    val attached = mutableListOf<FakeAttachedProcess>()

    override fun list(): List<ProcessInfo> = running.toList()
    override fun modules(pid: Int): List<ModuleInfo> = emptyList()
    override fun attach(process: ProcessInfo): AttachedProcess {
        if (process.pid in denied) throw ProcessAccessException("Access Denied", 5)
        return FakeAttachedProcess(process, pointerSize) { running.any { it.pid == process.pid } }
            .also(attached::add)
    }
}

internal class FakeAttachedProcess(
    override val info: ProcessInfo,
    override val pointerSize: PointerSize,
    private val alive: () -> Boolean
) : AttachedProcess {
    var closed = false
        private set

    override val isRunning: Boolean get() = !closed && alive()

    override fun read(address: Address, length: Int): ByteArray? = null
    override fun read(address: Address, into: MemorySegment): Boolean = false
    override fun write(address: Address, bytes: ByteArray): Boolean = false
    override fun write(address: Address, from: MemorySegment): Boolean = false
    override fun regions(range: AddressRange): List<MemoryRegion> = emptyList()

    override fun close() {
        closed = true
    }
}