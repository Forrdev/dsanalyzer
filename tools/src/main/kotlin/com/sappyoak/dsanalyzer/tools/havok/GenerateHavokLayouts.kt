package com.sappyoak.dsanalyzer.tools.havok

import java.nio.file.Files
import java.nio.file.Path

private const val PACKAGE = "com.sappyoak.dsanalyzer.runtime.ptde.havok"
/** The per-class constant. A member of this name would collide, so [emitClass] refuses one. */
private const val SIZE_CONSTANT = "ByteSize"

/** Kotlin's hard keywords — the ones that cannot appear as a bare identifier. */
private val HARD_KEYWORDS = setOf(
    "as", "break", "class", "continue", "do", "else", "false", "for", "fun", "if", "in",
    "interface", "is", "null", "object", "package", "return", "super", "this", "throw",
    "true", "try", "typealias", "typeof", "val", "var", "when", "while"
)

private val IDENTIFIER = Regex("[A-Za-z_][A-Za-z0-9_]*")

public fun main(args: Array<String>) {
    val table = readReflectionTable(Path.of(args[0]))
    val out = Path.of(args[1])

    val byFamily = table.classes.keys.sorted().groupBy { familyOf(it) }

    Files.createDirectories(out)
    var emitted = 0
    byFamily.toSortedMap().forEach { (family, names) ->
        val body = names.joinToString("\n\n") { emitClass(table, it) }
        val file = out.resolve("Havok${family}Layouts.kt")
        Files.writeString(file, header(family, names.size, table) + body + "\n")
        emitted += names.size
        println("  ${file.fileName}  ${names.size} classes")
    }

    val members = table.classes.keys.sumOf { table.resolvedMembers(it).size }
    println("$emitted classes, $members resolved members, into $out")

    val rootless = table.classes.values.count { it.parent.isEmpty() }
    println("$rootless classes have no registered parent, which is expected rather than a failure")
}

private fun familyOf(name: String): String = when {
    name.startsWith("hkp") -> "Physics"
    name.startsWith("hka") -> "Animation"
    name.startsWith("hkx") -> "Scene"
    else -> "Core"
}

private fun header(family: String, count: Int, table: ReflectionTable): String = """
    |// GENERATED FILE - do not edit. Regenerate with:
    |//
    |//     ./gradlew :tools:generateHavokLayouts
    |//
    |// Source: tools/havok/reflection.tsv, extracted from DARKSOULS.exe (hk_2010.2.0-r1, 32-bit)
    |//
    |// $family: $count of ${table.classes.size} reflected classes. Offsets include inherited
    |// members, flattened along the parent chain; the binary itself stores declared members only.
    |
    |@file:Suppress("ConstPropertyName", "ObjectPropertyName", "SpellCheckingInspection")
    |
    |package $PACKAGE
    |
    |
    """.trimMargin()

private fun emitClass(table: ReflectionTable, name: String): String {
    val klass = table.classes.getValue(name)
    val members = table.resolvedMembers(name)
    val chain = table.chainOf(name).joinToString(" -> ") { "`$it`" }

    val doc = buildList {
        this += "/**"
        this += " * `$name`, 0x${klass.byteSize.toString(16)} bytes."
        if (table.chainOf(name).size > 1) {
            this += " *"
            this += " * $chain"
        }
        if (members.isEmpty()) {
            this += " *"
            this += " * Declares and inherits no reflected members: only its size is registered."
        }
        this += " */"
    }

    val lines = mutableListOf<String>()
    lines += doc
    lines += "public object ${name.replaceFirstChar { it.uppercaseChar() }} {"
    lines += "    public const val $SIZE_CONSTANT: Int = 0x${klass.byteSize.toString(16)}"
    members.forEach { member ->
        val identifier = identifierFor(member)
        require(identifier != SIZE_CONSTANT) {
            "$name declares a member named $SIZE_CONSTANT, which collides with the size constant"
        }
        val type = HavokTypeCode.describe(member.type, member.subtype, member.cArraySize)
        val from = if (member.owner == name) "" else ", from `${member.owner}`"
        lines += ""
        lines += "    /** `$type`$from */"
        lines += "    public const val $identifier: Int = 0x${member.offset.toString(16)}"
    }
    lines += "}"
    return lines.joinToString("\n")
}

private fun identifierFor(member: HavokMember): String = when {
    member.name.isEmpty() -> "unnamed0x${member.offset.toString(16)}"
    IDENTIFIER.matches(member.name) && member.name !in HARD_KEYWORDS -> member.name
    else -> "`${member.name}`"
}

