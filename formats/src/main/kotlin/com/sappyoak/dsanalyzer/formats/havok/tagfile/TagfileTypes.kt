package com.sappyoak.dsanalyzer.formats.havok.tagfile

import com.sappyoak.dsanalyzer.formats.havok.HavokTypes

/** Which optional parts a type body carries, in the order they follow */
private const val HAS_FORMAT = 0x01
private const val HAS_SUBTYPE = 0x02
private const val HAS_VERSION = 0x04
private const val HAS_SIZE = 0x08
private const val HAS_FLAGS = 0x10
private const val HAS_MEMBERS = 0x20
private const val HAS_INTERFACES = 0x40
private const val HAS_ATTRIBUTE = 0x80

/** One member: its offset from the start of the object, the whole object rather than the class that declares it */
internal class TagMember(val name: String, val offset: Int, val type: Int)

internal class TagType(val name: String) {
    var parent: Int = 0
    var size: Int? = null
    var members: List<TagMember> = emptyList()
}

/**
 * A tagfile's own descriptions of the types its data uses: names from TNAM and TSTR, bodies from
 * TBOD with member names from FSTR. Index 0 is no type. Template arguments are read past, since a
 * name is enough to find the classes a reader asks for
 */
internal class TagfileTypes(val types: List<TagType?>) : HavokTypes {
    /**
     * Template instances share a name ('hkArray'), so a name finds the first type of that name
     * with a body. A class lookup never asks for a template, so that is the class
     */
    private val byName: Map<String, Int> = types.withIndex()
        .filter { (_, type) -> type != null && (type.size != null || type.parent != 0) }
        .groupBy({ it.value!!.name }, { it.index })
        .mapValues { it.value.first() }

    fun nameOf(index: Int): String? = types.getOrNull(index)?.name

    /** A type with no size of its own, 'hkMatrix3f', takes its parent's, 'hkMatrix3Impl<float>' */
    override fun sizeOf(type: String): Int? = byName[type]?.let(::sizeAt)

    private fun sizeAt(index: Int): Int? {
        val type = types.getOrNull(index) ?: return null
        return type.size ?: if (type.parent == 0) null else sizeAt(type.parent)
    }

    override fun offsetOf(type: String, path: String): Int? {
        var current = byName[type] ?: return null
        var offset = 0
        for (name in path.split('.')) {
            val member = membersOf(current).firstOrNull { it.name == name } ?: return null
            offset += member.offset
            current = member.type
        }
        return offset
    }

    /** Members along the parent chain, the base class's first */
    private fun membersOf(index: Int): List<TagMember> {
        val type = types.getOrNull(index) ?: return emptyList()
        return (if (type.parent == 0) emptyList() else membersOf(type.parent)) + type.members
    }
}

internal fun readTagfileTypes(names: ByteArray, typeStrings: List<String>, bodies: ByteArray, fieldStrings: List<String>): TagfileTypes {
    val packed = PackedInts(names, 0)
    val count = packed.nextInt()
    val types = MutableList<TagType?>(count) { null }
    for (index in 1 until count) {
        types[index] = TagType(typeStrings[packed.nextInt()])
        repeat(packed.nextInt()) { packed.next(); packed.next() }
    }

    val body = PackedInts(bodies, 0)
    while (body.at < bodies.size) {
        val index = body.nextInt()
        if (index == 0) continue
        val type = checkNotNull(types.getOrNull(index)) { "Tagfile body for type $index, which TNAM never named" }
        type.parent = body.nextInt()
        val parts = body.nextInt()
        if (parts and HAS_FORMAT != 0) body.next()
        if (parts and HAS_SUBTYPE != 0) body.next()
        if (parts and HAS_VERSION != 0) body.next()
        if (parts and HAS_SIZE != 0) { type.size = body.nextInt(); body.next() }
        if (parts and HAS_FLAGS != 0) body.next()
        if (parts and HAS_MEMBERS != 0) type.members = List(body.nextInt()) {
            val name = fieldStrings[body.nextInt()]
            body.next() // flags
            TagMember(name, body.nextInt(), body.nextInt())
        }
        if (parts and HAS_INTERFACES != 0) repeat(body.nextInt()) { body.next(); body.next() }
        if (parts and HAS_ATTRIBUTE != 0) body.next()
    }
    return TagfileTypes(types)
}
