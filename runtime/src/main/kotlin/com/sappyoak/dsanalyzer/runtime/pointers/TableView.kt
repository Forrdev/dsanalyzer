package com.sappyoak.dsanalyzer.runtime.pointers

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.MemoryView
import com.sappyoak.dsanalyzer.native.process.ProcessMemory

/**
 * One table resolved to the addresses of its elements
 *
 * The counterpart of [StructView] and deliberately a weaker premise. [StructView]
 * copies a structure so that every field comes from one instant. There is no equivalent here
 * because the table's length can change between being read and being used.
 *
 * So this resolves **addresses**, and each element is then fetched by whatever reads it, a
 * [StructView] per element to keep atomicity where it exists. What a caller gets is a list that was true
 * at one moment, which is the most this can offset. Treat a table that shrank between passes as a
 * reload rather than as an error
 *
 * Inline elements cost no reads at all beyond the length, since their addresses are arithmetic.
 * Pointer slots are fetched in one read rather than one per slot
 */
public class TableView(public val table: GameTable) {
    private val scalar = MemoryView(Long.SIZE_BYTES)
    private val slots: MemoryView by lazy(LazyThreadSafetyMode.NONE) {
        MemoryView(table.limit * Long.SIZE_BYTES)
    }

    private val found = ArrayList<Address>()

    /** Where the elements are, as of the last successful [refresh] */
    public val elements: List<Address> get() = found

    /** The length the game stated, which [elements] may be shorter than */
    public var length: Int = 0
        private set

    /**
     * Whether the game stated more elements than [GameTable.limit] allows, so
     * [elements] is a prefix. For a [TableShape.Linked] table this is also what a looping
     * chain looks like
     */
    public var truncated: Boolean = false
        private set

    /** Fetches the table, reporting whether it could be resolved at all */
    public fun refresh(memory: ProcessMemory, owner: Address): Boolean {
        found.clear()
        length = 0
        truncated = false
        if (owner.isNull) return false

        val shape = table.shape
        return if (shape is TableShape.Linked) walk(memory, owner, shape) else index(memory, owner)
    }

    /** Follows the chain until it ends, runs past the limit, or turns back on itself */
    private fun walk(memory: ProcessMemory, owner: Address, shape: TableShape.Linked): Boolean {
        var node = pointer(memory, owner + shape.headAt.toLong()) ?: return false

        while (!node.isNull) {
            if (found.size >= table.limit) {
                truncated = true
                break
            }

            found.add(node)

            val next = pointer(memory, node + shape.nextAt.toLong()) ?: break
            if (next == node) {
                truncated = true
                break
            }

            node = next
        }

        length = found.size
        return true
    }

    /** Resolves a counted, fixed, or bounded array of slots */
    private fun index(memory: ProcessMemory, owner: Address): Boolean {
        val width = table.slotWidth(memory.pointerSize.value)
        val stated = statedLength(memory, owner, width) ?: return false
        if (stated < 0) return false

        val first = firstSlot(memory, owner) ?: return false
        if (first.isNull) return false

        length = stated
        val taken = if (stated > table.limit) table.limit.also { truncated = true } else stated

        return when (table.elements) {
            is TableElements.Pointers -> readPointers(memory, first, taken, width)
            is TableElements.Inline -> {
                for (i in 0 until taken) {
                    found.add(first + (i.toLong() * width))
                }
                true
            }
        }
    }

    /** Every pointer slot in one read */
    private fun readPointers(memory: ProcessMemory, first: Address, taken: Int, width: Int): Boolean {
        if (taken == 0) return true
        if (!slots.refresh(memory, first, taken * width)) return false

        for (i in 0 until taken) {
            val element = slots.pointer(i * width)
            // a null slot is an absent element rather than a failure
            if (!element.isNull) found.add(element)
        }
        return true
    }

    private fun statedLength(memory: ProcessMemory, owner: Address, width: Int): Int? = when (val shape = table.shape) {
        is TableShape.Fixed -> shape.length
        is TableShape.Counted -> int(memory, owner + shape.lengthAt.toLong())
        is TableShape.Range -> {
            val begin = pointer(memory, owner + shape.beginAt.toLong())
            val end = pointer(memory, owner + shape.endAt.toLong())
            if (begin == null || end == null) null else ((end.value - begin.value) / width).toInt()
        }
        is TableShape.Linked -> null
        is TableShape.ParamList -> int(memory, owner + shape.lengthAt.toLong())?.minus(1)
    }

    private fun firstSlot(memory: ProcessMemory, owner: Address): Address? = when (val shape = table.shape) {
        is TableShape.Range -> pointer(memory, owner + shape.beginAt.toLong())
        is TableShape.Counted ->
            if (shape.indirect) pointer(memory, owner + shape.firstAt.toLong())
            else owner + shape.firstAt.toLong()
        is TableShape.Fixed ->
            if (shape.indirect) pointer(memory, owner + shape.firstAt.toLong())
            else owner + shape.firstAt.toLong()
        is TableShape.Linked -> null
        is TableShape.ParamList -> owner + shape.firstAt.toLong()
    }

    private fun pointer(memory: ProcessMemory, at: Address): Address? =
        if (scalar.refresh(memory, at, memory.pointerSize.value)) scalar.pointer(0) else null

    private fun int(memory: ProcessMemory, at: Address): Int? =
        if (scalar.refresh(memory, at, Int.SIZE_BYTES)) scalar.int(0) else null

}