package com.sappyoak.dsanalyzer.formats.havok

import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

/** A type a fixture declares: members are (name, offset, member type name or null) */
internal class FixtureType(
    val name: String,
    val parent: String? = null,
    val size: Int? = null,
    val members: List<Triple<String, Int, String?>> = emptyList()
)

/** One run of the data block: [type] names a declared type, [isObject] marks a top-level object */
internal class FixtureItem(val type: String, val isObject: Boolean, val offset: Int, val count: Int)

/**
 * Writes a tagfile from declared types, items and pointer slots. A slot holds the 1-based index of
 * the item it points at, and is listed in PTCH under [slotType]; a word written into the data
 * without being declared a slot is not a pointer, which is what lets a fixture test that
 */
internal class TagfileWriter(private val types: List<FixtureType>, dataSize: Int) {
    private val data = ByteBuffer.allocate(dataSize).order(ByteOrder.LITTLE_ENDIAN)
    private val items = ArrayList<FixtureItem>()
    private val slots = ArrayList<Int>()

    fun item(item: FixtureItem): Int = items.size.also { items += item } + 1
    fun slot(at: Int, item: Int) = apply { data.putInt(at, item); slots += at }
    fun int(at: Int, value: Int) = apply { data.putInt(at, value) }
    fun floats(at: Int, vararg values: Float) = apply { values.forEachIndexed { i, v -> data.putFloat(at + i * 4, v) } }
    fun text(at: Int, value: String) = apply { value.toByteArray().forEachIndexed { i, b -> data.put(at + i, b) } }

    fun write(sdk: String = "20150100", patches: Boolean = true): ByteArray {
        val index = types.withIndex().associate { (i, type) -> type.name to i + 1 }
        val typeNames = types.map { it.name }
        val fieldNames = types.flatMap { type -> type.members.map { it.first } }.distinct()

        val names = packed { int(types.size + 1); types.forEach { int(typeNames.indexOf(it.name)); int(0) } }
        val bodies = packed {
            types.forEach { type ->
                int(index.getValue(type.name)); int(type.parent?.let(index::getValue) ?: 0)
                int((if (type.size != null) 0x08 else 0) or (if (type.members.isNotEmpty()) 0x20 else 0))
                type.size?.let { int(it); int(8) }
                if (type.members.isNotEmpty()) {
                    int(type.members.size)
                    type.members.forEach { (name, offset, memberType) ->
                        int(fieldNames.indexOf(name)); int(0); int(offset); int(memberType?.let(index::getValue) ?: 0)
                    }
                }
            }
        }

        val itemTable = ByteBuffer.allocate((items.size + 1) * 12).order(ByteOrder.LITTLE_ENDIAN).apply {
            putInt(0).putInt(0).putInt(0)
            items.forEach { putInt(index.getValue(it.type) or (if (it.isObject) 0x1000_0000 else 0x2000_0000)).putInt(it.offset).putInt(it.count) }
        }.array()
        val patchTable = ByteBuffer.allocate(8 + slots.size * 4).order(ByteOrder.LITTLE_ENDIAN).apply {
            putInt(1).putInt(slots.size); slots.forEach(::putInt)
        }.array()

        val type = section("TSTR", strings(typeNames)) + section("TNAM", names) + section("FSTR", strings(fieldNames)) + section("TBOD", bodies)
        val indexParts = section("ITEM", itemTable) + if (patches) section("PTCH", patchTable) else ByteArray(0)
        return section("TAG0", section("SDKV", sdk.toByteArray()) + section("DATA", data.array()) + section("TYPE", type) + section("INDX", indexParts))
    }

    private fun strings(values: List<String>): ByteArray = values.joinToString("") { "$it\u0000" }.toByteArray()

    private fun section(tag: String, body: ByteArray): ByteArray =
        ByteBuffer.allocate(8 + body.size).putInt(8 + body.size).put(tag.toByteArray()).put(body).array()

    /** Packed ints: one byte below 0x80, two below 0x4000, which every fixture value is */
    private fun packed(write: PackedWriter.() -> Unit): ByteArray = PackedWriter().apply(write).bytes()

    class PackedWriter {
        private val out = ByteArrayOutputStream()
        fun int(value: Int) {
            require(value in 0 until 0x4000) { "Fixture packed ints stop at 0x3FFF" }
            if (value < 0x80) out.write(value) else { out.write(0x80 or (value shr 8)); out.write(value and 0xFF) }
        }
        fun bytes(): ByteArray = out.toByteArray()
    }
}
