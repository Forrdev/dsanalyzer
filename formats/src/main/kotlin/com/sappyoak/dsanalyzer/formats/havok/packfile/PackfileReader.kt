package com.sappyoak.dsanalyzer.formats.havok.packfile

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.formats.havok.HavokFile
import com.sappyoak.dsanalyzer.formats.havok.HavokObject
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue

private const val MAGIC_0 = 0x57E0E057
private const val MAGIC_1 = 0x10C0C010
private const val FILE_VERSION = 8

/** Pointers of 4 bytes, little endian, no padding reuse, empty bases optimized away: PC, 32-bit */
private const val LAYOUT_RULES = 0x01000104

private const val HEADER_SIZE = 0x40
private const val SECTION_HEADER_SIZE = 0x30
private const val SECTION_TAG_SIZE = 20
private const val CONTENTS_VERSION_AT = 0x28
private const val CONTENTS_VERSION_SIZE = 16

private const val CLASSNAMES = "__classnames__"
private const val DATA = "__data__"

/** A class name entry: a 4-byte signature, a 0x09 marker, then the name */
private const val CLASS_NAME_MARKER = 0x09
private const val CLASS_NAME_PREFIX = 5

/** Fixup tables end on 16-byte boundaries, padded with entries whose source is -1 */
private const val UNUSED = -1

private class Section(
    val tag: String,
    val start: Int,
    val localFixups: Int,
    val globalFixups: Int,
    val virtualFixups: Int,
    val end: Int
)

/**
 * Reads a Dark Souls Havok packfile: hk_2010.2.0-r1, 32-bit, little endian. Only what is serialized
 * is here. A member the reflection marks 'SERIALIZE_IGNORED' (flag 0x400), such as
 * 'hkpShape::m_type' or a mesh subpart's 'vertexBase', is zero or stale in the file and is rebuilt
 * by the game's load; readers take the serialized source it is rebuilt from
 */
public fun readPackfile(reader: BinaryReader): HavokFile {
    reader.order = ByteOrder.LITTLE_ENDIAN
    reader.position = 0
    reader.assertValue(MAGIC_0) { readInt() }
    reader.assertValue(MAGIC_1) { readInt() }
    reader.skip(4)
    reader.assertValue(FILE_VERSION) { readInt() }
    val rules = reader.readInt()
    if (rules != LAYOUT_RULES) {
        throw BinaryFormatException("Packfile layout 0x${rules.toString(16)} is not 32-bit little endian", 0x10)
    }

    val sectionCount = reader.readInt()
    val contentsSection = reader.readInt()
    val contentsOffset = reader.readInt()
    reader.skip(8) // the contents class name's section and offset; the virtual fixups say the same
    val version = reader.at(CONTENTS_VERSION_AT.toLong()) { readFixedString(CONTENTS_VERSION_SIZE) }

    val sections = List(sectionCount) { index -> reader.sectionAt(HEADER_SIZE + index * SECTION_HEADER_SIZE) }
    val names = sections.firstOrNull { it.tag == CLASSNAMES }
        ?: throw BinaryFormatException("Packfile has no $CLASSNAMES section", HEADER_SIZE.toLong())
    val dataIndex = sections.indexOfFirst { it.tag == DATA }
    if (dataIndex < 0) throw BinaryFormatException("Packfile has no $DATA section", HEADER_SIZE.toLong())
    val data = sections[dataIndex]

    val classNames = reader.classNamesOf(names)
    val bytes = reader.at(data.start.toLong()) { readBytes(data.localFixups) }
    val objects = reader.virtualFixups(data, sections.indexOf(names), classNames)

    return HavokFile(
        version = version,
        data = FixupData(bytes, reader.localFixups(data) + reader.globalFixups(data, dataIndex)),
        objects = objects,
        root = objects.firstOrNull { contentsSection == dataIndex && it.offset == contentsOffset },
        types = null
    )
}

private fun BinaryReader.sectionAt(at: Int): Section = at(at.toLong()) {
    val tag = readFixedString(SECTION_TAG_SIZE)
    val start = readInt()
    val local = readInt()
    val global = readInt()
    val virtual = readInt()
    skip(8) // exports and imports, which Dark Souls files leave empty
    Section(tag, start, local, global, virtual, readInt())
}

/** Class names by their offset within the class names section, which is how virtual fixups name them */
private fun BinaryReader.classNamesOf(section: Section): Map<Int, String> {
    val names = HashMap<Int, String>()
    var at = 0
    while (at + CLASS_NAME_PREFIX <= section.localFixups) {
        val marker = at((section.start + at + 4).toLong()) { readByte().toInt() and 0xFF }
        if (marker != CLASS_NAME_MARKER) break
        val name = at((section.start + at + CLASS_NAME_PREFIX).toLong()) { readAscii() }
        names[at + CLASS_NAME_PREFIX] = name
        at += CLASS_NAME_PREFIX + name.length + 1
    }
    return names
}

/** Pointers within the data section: a source offset and a target offset, both in it */
private fun BinaryReader.localFixups(data: Section): Map<Int, Int> = entries(data, data.localFixups, data.globalFixups, 2)
    .associate { (source, target) -> source to target }

/** Pointers to the start of an object, which may sit in another section; only the data section's are kept */
private fun BinaryReader.globalFixups(data: Section, dataIndex: Int): Map<Int, Int> =
    entries(data, data.globalFixups, data.virtualFixups, 3)
        .filter { it[1] == dataIndex }
        .associate { it[0] to it[2] }

private fun BinaryReader.virtualFixups(data: Section, namesIndex: Int, classNames: Map<Int, String>): List<HavokObject> =
    entries(data, data.virtualFixups, data.end, 3)
        .filter { it[1] == namesIndex }
        .map { HavokObject(it[0], classNames[it[2]] ?: "?${it[2]}") }
        .sortedBy { it.offset }

private fun BinaryReader.entries(data: Section, from: Int, to: Int, width: Int): List<IntArray> {
    val stride = width * Int.SIZE_BYTES
    val found = ArrayList<IntArray>()
    position = (data.start + from).toLong()
    var at = from
    while (at + stride <= to) {
        val entry = IntArray(width) { readInt() }
        if (entry[0] != UNUSED) found += entry
        at += stride
    }
    return found
}
