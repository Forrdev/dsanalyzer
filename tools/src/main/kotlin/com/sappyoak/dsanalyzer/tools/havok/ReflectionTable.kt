package com.sappyoak.dsanalyzer.tools.havok

import java.nio.file.Files
import java.nio.file.Path

/**
 * One member exactly as the binary declares it
 *
 * [owner] is the class whose 'declaredMembers' array this came from, which is **not** necessarily the class
 * it is looked up on [ReflectTable.resolvedMembers]
 */
public data class HavokMember(
    val owner: String,
    val name: String,
    val offset: Int,
    val type: Int,
    val subtype: Int,
    val cArraySize: Int,
    val flags: Int
)

/**
 * one relected class
 *
 * [parent] is empty for a root, and also for a class whose base was never registered.
 */
public data class HavokClass(
    val name: String,
    val byteSize: Int,
    val parent: String,
    val declared: List<HavokMember>
)

public class ReflectionTable(public val classes: Map<String, HavokClass>) {
    public fun chainOf(name: String): List<String> {
        val chain = mutableListOf<String>()
        val seen = mutableSetOf<String>()
        var current: String? = name
        while (current != null && current.isNotEmpty() && seen.add(current)) {
            chain.add(current)
            current = classes[current]?.parent
        }
        return chain.asReversed()
    }

    public fun resolvedMembers(name: String): List<HavokMember> {
        val merged = LinkedHashMap<String, HavokMember>()
        chainOf(name).forEach { step ->
            classes[step]?.declared?.forEach { member ->
                merged[keyOf(member)] = member
            }
        }

        return merged.values.sortedWith(compareBy({ it.offset }, { it.name }))
    }
    private fun keyOf(member: HavokMember): String =
        if (member.name.isEmpty()) "@${member.offset}" else member.name
}

private const val CLASS_SECTION = "[CLASS]"
private const val MEMBER_SECTION = "[MEMBER]"

public fun readReflectionTable(path: Path): ReflectionTable {
    require(Files.isRegularFile(path)) {
        "No reflection table at $path. Generate it with tools/havok/ExtractHavokReflection.java."
    }

    val sizes = LinkedHashMap<String, Int>()
    val parents = LinkedHashMap<String, String>()
    val declared = LinkedHashMap<String, MutableList<HavokMember>>()
    var section = ""

    Files.readAllLines(path).forEachIndexed { index, raw ->
        // Trimmed only for the blank/comment/section tests. Trimming the row itself would eat the
        // trailing tab on a class with no parent
        val line = raw.trimEnd('\r', '\n')
        val probe = line.trim()
        when {
            probe.isEmpty() || probe.startsWith("#") -> Unit
            probe == CLASS_SECTION || probe == MEMBER_SECTION -> section = probe
            section == CLASS_SECTION -> {
                val f = line.split('\t')
                require(f.size == 3) { "${path.fileName}:${index + 1}: expected 3 class columns, got ${f.size}" }
                sizes[f[0]] = hex(f[1], path, index)
                parents[f[0]] = f[2]
            }
            section == MEMBER_SECTION -> {
                val f = line.split('\t')
                require(f.size == 7) { "${path.fileName}:${index + 1}: expected 7 member columns, got ${f.size}" }
                declared.getOrPut(f[0]) { mutableListOf() } += HavokMember(
                    owner = f[0],
                    name = f[1],
                    offset = hex(f[2], path, index),
                    type = dec(f[3], path, index),
                    subtype = dec(f[4], path, index),
                    cArraySize = dec(f[5], path, index),
                    flags = hex(f[6], path, index)
                )
            }
            else -> error("${path.fileName}:${index + 1}: row outside any section")
        }
    }

    require(sizes.isNotEmpty()) { "$path declared no classes" }

    val classes = sizes.keys.associateWith { name ->
        HavokClass(
            name = name,
            byteSize = sizes.getValue(name),
            parent = parents[name].orEmpty(),
            declared = declared[name].orEmpty()
        )
    }
    return ReflectionTable(classes)
}

private fun hex(value: String, path: Path, index: Int): Int =
    value.removePrefix("0x").toIntOrNull(16)
        ?: error("${path.fileName}:${index + 1}: '$value' is not a hex number")

private fun dec(value: String, path: Path, index: Int): Int =
    value.toIntOrNull()
        ?: error("${path.fileName}:${index + 1}: '$value' is not a number")