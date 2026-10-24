package com.sappyoak.dsanalyzer.formats.havok.tagfile

import com.sappyoak.dsanalyzer.formats.havok.HavokData
import com.sappyoak.dsanalyzer.formats.havok.HkArray

/** 64-bit pointers, as every Remastered tagfile has */
private const val POINTER_SIZE = 8

/** One ITEM entry: a run of [count] values of [type] at [offset] in the data block */
internal class TagItem(val type: Int, val isObject: Boolean, val offset: Int, val count: Int)

/**
 * A tagfile's data block. A pointer slot holds the index of the item it points at, and only the
 * slots the PTCH table lists are pointers. An array's slot points at an item that carries the
 * count, so the size word in the data is not read
 */
internal class ItemData(
    bytes: ByteArray,
    private val items: List<TagItem>,
    private val slots: Set<Int>
) : HavokData(bytes, POINTER_SIZE) {
    override fun pointer(at: Int): Int? = itemAt(at)?.offset

    override fun array(at: Int): HkArray {
        val item = itemAt(at) ?: return HkArray.Empty
        return HkArray(item.offset, item.count)
    }

    /** Item 0 is the null item, which a null pointer names */
    private fun itemAt(at: Int): TagItem? {
        if (at !in slots) return null
        val index = int(at)
        return if (index == 0) null else items.getOrNull(index)
    }
}
