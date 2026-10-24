package com.sappyoak.dsanalyzer.formats.havok

import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.formats.FixtureWriter

internal const val PACKFILE_MAGIC_0 = 0x57E0E057
internal const val PACKFILE_MAGIC_1 = 0x10C0C010
internal const val PC_32_LAYOUT = 0x01000104
internal const val PC_64_LAYOUT = 0x01000108
internal const val CONTENTS_VERSION = "hk_2010.2.0-r1"

private const val HEADER_SIZE = 0x40
private const val SECTION_HEADER_SIZE = 0x30
private const val SECTION_TAG_SIZE = 20
private const val CLASS_SIGNATURE = 0x12345678
private const val CLASS_NAME_MARKER = 0x09
private const val CLASS_NAME_PREFIX = 5
private const val DATA_SECTION = 2
private const val ALIGNMENT = 16

/**
 * A packfile data section placed by hand: bytes at stated offsets, plus the fixups that say which
 * words are pointers and which offsets begin objects
 */
internal class DataSection(size: Int) {
    private val buffer = ByteBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN)
    val locals = ArrayList<Pair<Int, Int>>()
    val globals = ArrayList<Pair<Int, Int>>()
    val objects = ArrayList<Pair<Int, String>>()

    val bytes: ByteArray get() = buffer.array()

    fun obj(at: Int, className: String) = apply { objects += at to className }
    fun int(at: Int, value: Int) = apply { buffer.putInt(at, value) }
    fun short(at: Int, value: Int) = apply { buffer.putShort(at, value.toShort()) }
    fun byte(at: Int, value: Int) = apply { buffer.put(at, value.toByte()) }
    fun floats(at: Int, vararg values: Float) = apply { values.forEachIndexed { i, v -> buffer.putFloat(at + i * 4, v) } }
    fun string(at: Int, value: String) = apply { value.toByteArray().forEachIndexed { i, b -> buffer.put(at + i, b) } }

    /** A pointer to another object, which files store as a global fixup */
    fun pointer(at: Int, target: Int) = apply { globals += at to target }

    /** An 'hkArray' whose elements start at [target]: a local fixup, then the size */
    fun array(at: Int, target: Int, count: Int) = apply {
        locals += at to target
        buffer.putInt(at + 4, count)
        buffer.putInt(at + 8, count or Int.MIN_VALUE)
    }

    /** A 'char*', which files store as a local fixup */
    fun text(at: Int, target: Int, value: String) = apply {
        locals += at to target
        string(target, value)
    }
}

/**
 * Wraps [data] in a packfile: a header, then '__classnames__', an empty '__types__' and '__data__',
 * with the root object at data offset 0. [layout] and [magic] let a test state a file the reader
 * must turn away
 */
internal fun packfile(data: DataSection, layout: Int = PC_32_LAYOUT, magic: Int = PACKFILE_MAGIC_0): ByteArray {
    val names = data.objects.map { it.second }.distinct()
    val nameOffsets = HashMap<String, Int>()
    val classNames = FixtureWriter().apply {
        names.forEach { name ->
            int(CLASS_SIGNATURE).byte(CLASS_NAME_MARKER)
            nameOffsets[name] = position
            string(name)
        }
        while (position % ALIGNMENT != 0) byte(0xFF)
    }.toByteArray()

    val local = fixups(data.locals.map { intArrayOf(it.first, it.second) }, 2)
    val global = fixups(data.globals.map { intArrayOf(it.first, DATA_SECTION, it.second) }, 3)
    val virtual = fixups(data.objects.map { intArrayOf(it.first, 0, checkNotNull(nameOffsets[it.second])) }, 3)

    val namesStart = HEADER_SIZE + 3 * SECTION_HEADER_SIZE
    val dataStart = namesStart + classNames.size
    val localAt = data.bytes.size
    val globalAt = localAt + local.size
    val virtualAt = globalAt + global.size
    val end = virtualAt + virtual.size

    return ByteBuffer.allocate(dataStart + end).order(ByteOrder.LITTLE_ENDIAN).apply {
        putInt(magic).putInt(PACKFILE_MAGIC_1).putInt(0).putInt(8).putInt(layout)
        putInt(3).putInt(DATA_SECTION).putInt(0).putInt(0).putInt(checkNotNull(nameOffsets[names.first()]))
        put(CONTENTS_VERSION.toByteArray()).put(ByteArray(16 - CONTENTS_VERSION.length))
        putInt(0).putInt(-1)
        section("__classnames__", IntArray(7) { if (it == 0) namesStart else classNames.size })
        section("__types__", IntArray(7) { if (it == 0) dataStart else 0 })
        section("__data__", intArrayOf(dataStart, localAt, globalAt, virtualAt, end, end, end))
        put(classNames).put(data.bytes).put(local).put(global).put(virtual)
    }.array()
}

/** A section header: its tag, where it starts, then its tables and end relative to that start */
private fun ByteBuffer.section(tag: String, words: IntArray) {
    put(tag.toByteArray()).put(ByteArray(SECTION_TAG_SIZE - tag.length))
    words.forEach(::putInt)
}

/** A fixup table padded to 16 bytes with entries whose source is -1, as files pad theirs */
private fun fixups(entries: List<IntArray>, width: Int): ByteArray = FixtureWriter().apply {
    entries.forEach { entry -> entry.forEach(::int) }
    while (position % ALIGNMENT != 0) repeat(width) { int(-1) }
}.toByteArray()
