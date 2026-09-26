package com.sappyoak.dsanalyzer.native.process

import com.sappyoak.dsanalyzer.native.linux.LinuxProcesses
import com.sappyoak.dsanalyzer.native.windows.WindowsProcesses
import com.sappyoak.dsanalyzer.shared.platform.OS

/** Represents a platform-agnostic way of finding and attaching to processes */
public interface Processes {
    /** List of the currently running processes */
    public fun list(): List<ProcessInfo>

    /** Attempts to attach to [process], throwing [ProcessAccessException] when it cannot be opened */
    public fun attach(process: ProcessInfo): AttachedProcess

    /** Returns the list of current loaded modules for the process [pid] */
    public fun modules(pid: Int): List<ModuleInfo>

    public companion object {
        val Current: Processes by lazy(LazyThreadSafetyMode.PUBLICATION) {
            when {
                OS.current.isWindows -> WindowsProcesses()
                OS.current.isLinux -> LinuxProcesses()
                else -> throw ProcessAccessException("Process access is not supported on ${OS.current.name} yet")
            }
        }
    }
}