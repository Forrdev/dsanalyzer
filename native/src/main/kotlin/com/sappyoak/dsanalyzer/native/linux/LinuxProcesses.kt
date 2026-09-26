package com.sappyoak.dsanalyzer.native.linux

import java.nio.file.Files
import java.nio.file.Path

import com.sappyoak.dsanalyzer.native.process.AttachedProcess
import com.sappyoak.dsanalyzer.native.process.ModuleInfo
import com.sappyoak.dsanalyzer.native.process.ProcessAccessException
import com.sappyoak.dsanalyzer.native.process.ProcessInfo
import com.sappyoak.dsanalyzer.native.process.Processes
import com.sappyoak.dsanalyzer.shared.platform.PointerSize

private val PROC = Path.of("/proc")
private const val EXE_SUFFIX = ".exe"
private const val EPERM = 1

internal class LinuxProcesses : Processes {
    override fun list(): List<ProcessInfo> = Files.list(PROC).use { entries ->
        entries.toList().mapNotNull { entry ->
            entry.fileName.toString().toIntOrNull()?.let { pid ->
                executableName(pid)?.let { ProcessInfo(pid, it) }
            }
        }
    }

    override fun modules(pid: Int): List<ModuleInfo> = mappedModules(pid)

    /**
     * There is nothing to open, so this checks that the target can actually be read and that its
     * pointer width is known, rather than handing back something that fails on every read later
     */
    override fun attach(process: ProcessInfo): AttachedProcess {
        if (!LibC.available) {
            throw ProcessAccessException("This C library has no process_vm_readv to read ${process.executableName} with")
        }

        val pointerSize = pointerWidth(process)
            ?: throw ProcessAccessException("The pointer width of ${process.executableName} could not be determined")

        probe(process)
        return LinuxProcess(process, pointerSize)
    }

    private fun probe(process: ProcessInfo) {
        val readable = mappings(process.pid).firstOrNull { it.readable }
            ?: throw ProcessAccessException("${process.executableName} has no readable memory")

        when (val errno = errnoReading(process.pid, readable.range.start.value)) {
            0 -> Unit
            EPERM -> throw ProcessAccessException(
                "Reading ${process.executableName} was refused. A process that is not your own needs " +
                        "CAP_SYS_PTRACE, and kernel.yama.ptrace_scope has to allow it",
                errno
            )
            else -> throw ProcessAccessException(
                "${process.executableName} could not be read (errno $errno)",
                errno
            )
        }
    }
}

/**
 * How wide the process's pointers are.
 *
 * A game under Wine is a PE mapped into a Linux process, and current Wine can host a 32 bit PE in a
 * 64 bit process, so only the PE header itself answers this. Everything else is a native ELF, where
 * the class byte of its own executable does
 */
private fun pointerWidth(process: ProcessInfo): PointerSize? =
    peWidth(process) ?: elfWidth(process.pid)

/**
 * The name the process is recognized by.
 *
 * Wine runs a game as `wine <path>\GAME.exe`, and an edition is matched on the PE's name, so the
 * first argument naming one wins over the loader running it. comm covers a process with no command
 * line at all, and the kernel truncates that to fifteen characters
 */
private fun executableName(pid: Int): String? {
    val arguments = text(pid, "cmdline")
        ?.split('\u0000')
        ?.filter { it.isNotBlank() }
        ?.map { it.substringAfterLast('/').substringAfterLast('\\') }
        .orEmpty()

    return arguments.firstOrNull { it.endsWith(EXE_SUFFIX, ignoreCase = true) }
        ?: arguments.firstOrNull()
        ?: text(pid, "comm")?.trim()?.ifBlank { null }
}

private fun text(pid: Int, name: String): String? =
    runCatching { Files.readString(PROC.resolve("$pid/$name")) }.getOrNull()