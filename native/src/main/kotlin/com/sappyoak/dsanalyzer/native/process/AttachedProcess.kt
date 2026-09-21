package com.sappyoak.dsanalyzer.native.process

public interface AttachedProcess : ProcessMemory, AutoCloseable {
    public val info: ProcessInfo
    public val isRunning: Boolean

    /** Enumerated on demand, since modules can load and unload while attached */
    public fun modules(): List<ModuleInfo>
}