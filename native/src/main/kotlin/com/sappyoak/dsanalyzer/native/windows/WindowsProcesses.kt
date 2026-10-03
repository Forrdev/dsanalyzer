package com.sappyoak.dsanalyzer.native.windows

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout.*

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.process.AttachedProcess
import com.sappyoak.dsanalyzer.native.process.ModuleInfo
import com.sappyoak.dsanalyzer.native.process.ProcessAccessException
import com.sappyoak.dsanalyzer.native.process.ProcessInfo
import com.sappyoak.dsanalyzer.native.process.Processes
import com.sappyoak.dsanalyzer.shared.platform.PointerSize

private const val SNAP_PROCESS = 0x2
private const val SNAP_MODULE = 0x8
private const val SNAP_MODULE_32 = 0x10

private const val ERROR_ACCESS_DENIED = 5
private const val ERROR_BAD_LENGTH = 24
private const val SNAPSHOT_ATTEMPTS = 5

private const val PROCESS_VM_OPERATION = 0x0008
private const val PROCESS_VM_READ = 0x0010
private const val PROCESS_VM_WRITE = 0x0020
private const val PROCESS_QUERY_INFORMATION = 0x0400

private const val ATTACH_ACCESS =
    PROCESS_VM_OPERATION or PROCESS_VM_READ or PROCESS_VM_WRITE or PROCESS_QUERY_INFORMATION

private class Snapshot(val handle: MemorySegment) : AutoCloseable {
    override fun close() {
        Kernel32.closeHandle(handle)
    }
}

internal class WindowsProcesses : Processes {
    override fun list(): List<ProcessInfo> = snapshot(SNAP_PROCESS, 0, PROCESS_ENTRY) { handle, entry, first ->
        if (!Kernel32.process32(handle, entry, first)) return@snapshot null
        ProcessInfo(
            pid = entry.intField(PROCESS_ENTRY, "th32ProcessID"),
            executableName = entry.wideString(PROCESS_ENTRY, "szExeFile")
        )
    }

    override fun attach(process: ProcessInfo): AttachedProcess {
        val handle = try {
            Kernel32.openProcess(ATTACH_ACCESS, process.pid)
        } catch (error: ProcessAccessException) {
            if (error.nativeCode != ERROR_ACCESS_DENIED) throw error
            throw ProcessAccessException(
                "Access to ${process.executableName} was denied, you may need to run this program elevated",
                error.nativeCode
            )
        }

        val pointerSize = if (Kernel32.isWow64(handle)) PointerSize.IntPointer else PointerSize.LongPointer
        return WindowsProcess(process, handle, pointerSize, ::modules)
    }

    override fun modules(pid: Int): List<ModuleInfo> = snapshot(SNAP_MODULE or SNAP_MODULE_32, pid, MODULE_ENTRY) { handle, entry, first ->
        if (!Kernel32.module32(handle, entry, first)) return@snapshot null
        ModuleInfo(
            name = entry.wideString(MODULE_ENTRY, "szModule"),
            range = AddressRange(
                Address(entry.addressField(MODULE_ENTRY, "modBaseAddr")),
                entry.intField(MODULE_ENTRY, "modBaseSize").toUInt().toLong()
            )
        )
    }
    /**
     * Walks a toolhelp snapshot. [next] reads one entry and returns null at the end.
     * Module snapshots fail with ERROR_BAD_LENGTH while the target is loading a library,
     * which clears on a retry
     */
    private fun <T : Any> snapshot(
        flags: Int,
        pid: Int,
        layout: StructLayout,
        next: (snapshot: MemorySegment, entry: MemorySegment, first: Boolean) -> T?
    ): List<T> = Snapshot(openSnapshot(flags, pid)).use { snapshot ->
        Arena.ofConfined().use { arena ->
            val entry = arena.allocate(layout)
            entry.set(JAVA_INT, 0, layout.byteSize().toInt())
            generateSequence(next(snapshot.handle, entry, true)) {
                next(snapshot.handle, entry, false)
            }.toList()
        }
    }

    private fun openSnapshot(flags: Int, pid: Int): MemorySegment {
        repeat(SNAPSHOT_ATTEMPTS - 1) {
            try {
                return Kernel32.createSnapshot(flags, pid)
            } catch (failure: ProcessAccessException) {
                if (failure.nativeCode != ERROR_BAD_LENGTH) throw failure
            }
        }

        return Kernel32.createSnapshot(flags, pid)
    }
}