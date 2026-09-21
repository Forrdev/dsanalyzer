package com.sappyoak.dsanalyzer.native.ffi

import java.lang.foreign.Linker
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.StructLayout
import java.lang.invoke.VarHandle

/** Per-platform error state a downcall can capture as it returns */
public enum class CallState(internal val captureName: String) {
    WindowsLastError("GetLastError"),
    Errno("errno");

    private val value: VarHandle by lazy {
        layout.varHandle(MemoryLayout.PathElement.groupElement(captureName))
    }

    public fun read(state: MemorySegment): Int = value.get(state, 0L) as Int

    public companion object {
        public val layout: StructLayout = Linker.Option.captureStateLayout()
    }
}