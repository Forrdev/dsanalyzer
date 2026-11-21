package com.sappyoak.dsanalyzer.runtime

import java.lang.foreign.MemorySegment
import java.nio.file.Path

import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.MemoryRegion
import com.sappyoak.dsanalyzer.native.process.AttachedProcess
import com.sappyoak.dsanalyzer.native.process.CodeMemory
import com.sappyoak.dsanalyzer.native.process.ModuleInfo
import com.sappyoak.dsanalyzer.native.process.ProcessAccessException
import com.sappyoak.dsanalyzer.native.process.ProcessInfo
import com.sappyoak.dsanalyzer.native.process.Processes
import com.sappyoak.dsanalyzer.shared.platform.PointerSize

internal val PTDE_MODULE = ModuleInfo("DARKSOULS.exe", AddressRange(Address(0x400000), 0x300000))

internal val PTDE_INSTALLATION = Installation(
    id = InstallationId("ptde"),
    root = Path.of("game"),
    executable = Path.of("game", "DARKSOULS.exe"),
    build = GameBuild(GameEdition.PrepareToDie)
)

internal class FakeProcesses(
    private val pointerSize: PointerSize = PointerSize.IntPointer,
    private val denied: Set<Int> = emptySet(),
    private val loaded: List<ModuleInfo> = listOf(PTDE_MODULE),
    /** Where each process's executable was loaded from, by pid; a missing pid's modules cannot be read */
    private val paths: Map<Int, String> = emptyMap()
) : Processes {
    val running = mutableListOf<ProcessInfo>()
    val attached = mutableListOf<FakeAttachedProcess>()

    override fun list(): List<ProcessInfo> = running.toList()
    override fun modules(pid: Int): List<ModuleInfo> {
        val process = running.first { it.pid == pid }
        val path = paths[pid] ?: throw ProcessAccessException("Access Denied", 5)
        return listOf(ModuleInfo(process.executableName, PTDE_MODULE.range, path))
    }
    override fun attach(process: ProcessInfo): AttachedProcess {
        if (process.pid in denied) throw ProcessAccessException("Access Denied", 5)
        return FakeAttachedProcess(process, pointerSize, loaded) { running.any { it.pid == process.pid } }
            .also(attached::add)
    }
}

internal class FakeAttachedProcess(
    override val info: ProcessInfo,
    override val pointerSize: PointerSize,
    private val loaded: List<ModuleInfo>,
    private val alive: () -> Boolean
) : AttachedProcess {
    var closed = false
        private set

    override val isRunning: Boolean get() = !closed && alive()
    override val code: CodeMemory? = null

    override fun modules(): List<ModuleInfo> = loaded

    override fun read(address: Address, length: Int): ByteArray? = null
    override fun read(address: Address, into: MemorySegment): Boolean = false
    override fun write(address: Address, bytes: ByteArray): Boolean = false
    override fun write(address: Address, from: MemorySegment): Boolean = false
    override fun regions(range: AddressRange): List<MemoryRegion> = emptyList()

    override fun close() {
        closed = true
    }
}
