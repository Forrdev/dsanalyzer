package com.sappyoak.dsanalyzer.formats.havok.packfile

import com.sappyoak.dsanalyzer.formats.havok.HavokData
import com.sappyoak.dsanalyzer.formats.havok.HkArray

/** 'hkArray' is a pointer, its size, then its capacity and flags */
private const val ARRAY_SIZE_AT = 4

/** 32-bit pointers, as every PTDE packfile has */
private const val POINTER_SIZE = 4

/** A packfile's data section: a pointer is whatever the local or global fixup at its offset says */
internal class FixupData(bytes: ByteArray, private val pointers: Map<Int, Int>) : HavokData(bytes, POINTER_SIZE) {
    override fun pointer(at: Int): Int? = pointers[at]

    override fun array(at: Int): HkArray {
        val offset = pointer(at) ?: return HkArray.Empty
        return HkArray(offset, int(at + ARRAY_SIZE_AT))
    }
}
