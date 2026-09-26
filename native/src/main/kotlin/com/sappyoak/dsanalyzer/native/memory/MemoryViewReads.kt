package com.sappyoak.dsanalyzer.native.memory

import com.sappyoak.dsanalyzer.native.process.ProcessMemory

/** One pointer's worth of scratch per thread, so reading a single value allocates nothing */
private val SCRATCH: ThreadLocal<MemoryView> = ThreadLocal.withInitial { MemoryView(Long.SIZE_BYTES) }

/** The pointer stored at [at] in the target's own width, or null when it cannot be read */
internal fun ProcessMemory.pointerAt(at: Address): Address? {
    val view = SCRATCH.get()
    return if (view.refresh(this, at, pointerSize.value)) view.pointer(0) else null
}