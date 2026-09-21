package com.sappyoak.dsanalyzer.native.memory

import com.sappyoak.dsanalyzer.native.process.ProcessMemory

/** One pointer's worth of scratch per thread, so resolving a chain allocates nothing */
private val SCRATCH: ThreadLocal<MemoryView> = ThreadLocal.withInitial { MemoryView(Long.SIZE_BYTES) }

/** A chain of pointers from a known base to a value */
public class PointerChain(
    public val base: Address,
    public val offsets: List<Long>
) {
    public fun resolve(memory: ProcessMemory): Address {
        if (offsets.isEmpty()) return base

        val scratch = SCRATCH.get()
        var pointer = follow(memory, scratch, base)
        for (index in 0 until offsets.lastIndex) {
            if (pointer.isNull) return Address.Null
            pointer = follow(memory, scratch, pointer + offsets[index])
        }
        return if (pointer.isNull) Address.Null else pointer + offsets.last()
    }

    public override fun toString(): String =
        "${base}${offsets.joinToString("") { " -> 0x${it.toString(16).uppercase()}" }}"

    private fun follow(memory: ProcessMemory, view: MemoryView, at: Address): Address =
        if (view.refresh(memory, at, memory.pointerSize.value)) view.pointer(0) else Address.Null

    public companion object {
        public fun of(base: Address, vararg offsets: Long): PointerChain =
            PointerChain(base, offsets.toList())
    }
}
