package com.sappyoak.dsanalyzer.native.process

/** Represents a platform-agnostic way of finding and attaching to processes */
public interface Processes {
    public fun list(): List<ProcessInfo>
    public fun attach(process: ProcessInfo): AttachedProcess
}