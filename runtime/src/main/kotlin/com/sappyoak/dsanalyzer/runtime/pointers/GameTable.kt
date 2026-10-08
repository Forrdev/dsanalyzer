package com.sappyoak.dsanalyzer.runtime.pointers

/** A table that was not sized deliberately, a failure condition for unbounded reads */
private const val MAX_LIMIT = 1 shl 20

/**
 * How a table's length is established and where its slots begin
 */
public sealed interface TableShape {
    /**
     * Three pointers, of which the first two bound the slots: '(end - begin) / slot width'
     */
    public data class Range(public val beginAt: Int, public val endAt: Int) : TableShape

    /**
     * A length read from [lengthAt], with the slots at [firstAt]
     * [lengthAt] is relative to the same base as [firstAt] rather than to the slots,
     * because the length is often a field of the object that *owns* the table instead of a header on
     * the table itself. [indirect] says whether [firstAt] holds the slots or holds a pointer to them
     */
    public data class Counted(
        public val lengthAt: Int,
        public val firstAt: Int,
        public val indirect: Boolean = false
    ) : TableShape

    /**
     * A length that is a property of the game rather than of memory, such as map areas.
     * [firstAt] and [indirect] mean the same as they do in [Counted]
     *
     * Prefer [Counted] whenever the game states a length
     */
    public data class Fixed(
        public val length: Int,
        public val firstAt: Int,
        public val indirect: Boolean = false
    ) : TableShape

    /**
     * A head pointer at [headAt], with each element's successor at [nextAt] inside it, ending at
     * null. This is for truly dynamic things where no length exists anywhere and the only way to know
     * how many entries there are is to walk. An example of this is active special effects
     */
    public data class Linked(public val headAt: Int, public val nextAt: Int) : TableShape

    /**
     * A FromSoft param list. A pointer to the list's name, a length at [lengthAt], and then that many
     * pointer slots inline from [firstAt]
     *
     * It is [Counted] in every respect expect the **last slot is the next list not an element*
     */
    public data class ParamList(public val lengthAt: Int, public val firstAt: Int) : TableShape
}

/**
 * What occupies a slot
 */
public sealed interface TableElements {
    /** The slot holds the element's address and is as wide as the process's own pointers */
    public data object Pointers : TableElements

    /** The element *is* the slot, [stride] bytes of it */
    public data class Inline(public val stride: Int) : TableElements
}


/**
 * A collection in a running game, how many there are, where they are, and what a slot holds.
 *
 * The sibling of [GamePointer]. A [GamePointer] is a static declaration, a fixed walk to one structure,
 * resolved the same way everytime. A table is traversed, its length lives in memory, its elements may be
 * pointers or structs, and reaching what matters can mean descending through several of them with a condition
 * in between.
 *
 * This describes the collection and nothing about what an element contains. An element's fields are
 * read with a [StructView] against its own [GamePointer]-style offsets, so the atomic-read contract
 * is unchanged.
 */
public class GameTable(
    public val name: String,
    public val shape: TableShape,
    public val elements: TableElements,
    /**
     * The most elements to resolve in one pass.
     * Not a guess at how big the table is, but a bound on how wrong a length can be before this stops
     * believing it. A length read mid-write, or a [TableShape.Linked] chain that loops, would otherwise
     * be an unbounded read on a sampling thread
     */
    public val limit: Int = DEFAULT_LIMIT
) {
    init {
        require(limit in 1..MAX_LIMIT) { "$name limits itself to $limit elements, which is not a usable bound" }
        if (elements is TableElements.Inline) {
            require(elements.stride > 0) { "$name has inline elements of ${elements.stride} bytes"}
        }
        requireOffsets()
    }

    /** The bytes between one slot and the next */
    public fun slotWidth(pointerSize: Int): Int = when (elements) {
        is TableElements.Pointers -> pointerSize
        is TableElements.Inline -> elements.stride
    }

    override fun toString(): String = name

    private fun requireOffsets() {
        val offsets = when (shape) {
            is TableShape.Range -> listOf(shape.beginAt, shape.endAt)
            is TableShape.Counted -> listOf(shape.lengthAt, shape.firstAt)
            is TableShape.Fixed -> listOf(shape.firstAt)
            is TableShape.Linked -> listOf(shape.headAt, shape.nextAt)
            is TableShape.ParamList -> listOf(shape.lengthAt, shape.firstAt)
        }
        require(offsets.all { it >= 0 }) { "$name reads $shape, which is behind the address it was given" }

        if (shape is TableShape.Range) {
            require(shape.beginAt != shape.endAt) { "$name bounds its slots with one offset twice" }
        }
        if (shape is TableShape.Fixed) {
            require(shape.length in 1..limit) { "$name fixes its length at ${shape.length}, past its own limit" }
        }
    }

    public companion object {
        public const val DEFAULT_LIMIT: Int = 2048
    }
}