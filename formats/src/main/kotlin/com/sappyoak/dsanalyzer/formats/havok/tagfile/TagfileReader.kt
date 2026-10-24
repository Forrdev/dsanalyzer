package com.sappyoak.dsanalyzer.formats.havok.tagfile

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.formats.havok.HavokFile
import com.sappyoak.dsanalyzer.formats.havok.HavokObject
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

/** The only tagfile build Dark Souls Remastered ships */
private const val SDK_VERSION = "20150100"

/** An ITEM entry: the type index in the low 24 bits with flags above, then offset and count */
private const val ITEM_SIZE = 12
private const val ITEM_TYPE_MASK = 0x00FF_FFFF
private const val ITEM_IS_OBJECT = 0x1000_0000

/**
 * Reads a Remastered Havok tagfile: 'TAG0' holding SDKV, DATA, TYPE and INDX. The data block is
 * the objects' 64-bit memory image; TYPE describes every type in it; INDX lists the items the data
 * is cut into and, per pointer type, which slots hold pointers
 */
public fun readTagfile(reader: BinaryReader): HavokFile {
    reader.order = ByteOrder.LITTLE_ENDIAN
    val top = reader.sectionsIn(0, reader.size.toInt()).require("TAG0")
    val sections = reader.sectionsIn(top.start, top.end)

    val version = sections.require("SDKV").let { reader.at(it.start.toLong()) { readFixedString(it.end - it.start) } }
    if (version != SDK_VERSION) throw BinaryFormatException("Tagfile is SDK $version, not Remastered's $SDK_VERSION", top.start)

    val types = sections.require("TYPE").let { section ->
        val parts = reader.sectionsIn(section.start, section.end)
        readTagfileTypes(
            names = reader.bytesOf(parts.require("TNAM")),
            typeStrings = reader.stringsOf(parts.require("TSTR")),
            bodies = reader.bytesOf(parts.require("TBOD")),
            fieldStrings = reader.stringsOf(parts.require("FSTR"))
        )
    }

    val index = sections.require("INDX").let { reader.sectionsIn(it.start, it.end) }
    val items = reader.itemsOf(index.require("ITEM"))
    // A file whose data holds no pointers, such as one with only an empty root container, has no PTCH
    val slots = index["PTCH"]?.let { reader.slotsOf(it) }.orEmpty()

    val objects = items.filter { it.isObject }
        .map { HavokObject(it.offset, types.nameOf(it.type) ?: "?${it.type}") }
        .sortedBy { it.offset }

    return HavokFile(
        version = version,
        data = ItemData(reader.bytesOf(sections.require("DATA")), items, slots),
        objects = objects,
        root = items.firstOrNull { it.isObject }?.let { root -> objects.first { it.offset == root.offset } },
        types = types
    )
}

private fun BinaryReader.bytesOf(section: TagSection): ByteArray =
    at(section.start.toLong()) { readBytes(section.end - section.start) }

/** A string table: null-terminated names, numbered in the order they appear */
private fun BinaryReader.stringsOf(section: TagSection): List<String> =
    String(bytesOf(section), Charsets.US_ASCII).split('\u0000')

private fun BinaryReader.itemsOf(section: TagSection): List<TagItem> = at(section.start.toLong()) {
    List((section.end - section.start) / ITEM_SIZE) {
        val word = readInt()
        TagItem(word and ITEM_TYPE_MASK, word and ITEM_IS_OBJECT != 0, readInt(), readInt())
    }
}

/** Groups of a pointer type, a count, and that many slot offsets; the type is not needed here */
private fun BinaryReader.slotsOf(section: TagSection): Set<Int> = at(section.start.toLong()) {
    val slots = HashSet<Int>()
    while (position < section.end) {
        readInt()
        repeat(readInt()) { slots += readInt() }
    }
    slots
}
