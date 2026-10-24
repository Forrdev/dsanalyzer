package com.sappyoak.dsanalyzer.tools.havok

import java.nio.file.Files
import java.nio.file.Path

/** The per-class constant. A member whose name reaches this would collide, so [emitClass] refuses it. */
private const val SIZE_CONSTANT = "ByteSize"

private val IDENTIFIER = Regex("[A-Za-z_][A-Za-z0-9_]*")

/**
 * The separators a reflected class name may carry that a Kotlin identifier may not. hk2015 registers
 * a nested class as `hkpExtendedMeshShape::TrianglesSubpart` and a template as `hkMatrix3Impl<float>`;
 * hk2010's DLRF registers the already-flattened C++ symbol instead, so splitting on these leaves
 * PTDE's output byte-identical and only ever changes Remastered's
 */
private val NAME_SEPARATORS = Regex("::|[<>,]|\\s+")

/**
 * Havok writes a few compound member names as one lowercase run, which capitalising alone would
 * leave as `Meshstorage`. These are respelled so every generated constant reads like the
 * hand-written offset objects. Nothing resolves a member *by* name — only its offset is used — so
 * the spelling is ours to choose rather than the binary's to dictate
 */
private val RESPELLED = mapOf(
    "meshstorage" to "MeshStorage",
    "shapestorage" to "ShapeStorage"
)

/** Everything the caller chose. Gradle passes all five, so nothing is defaulted here */
private class Options(
    val table: Path,
    val out: Path,
    val packageName: String,
    val source: String,
    val task: String
)

public fun main(args: Array<String>) {
    val options = parse(args)
    val table = readReflectionTable(options.table)

    val objectNames = objectNamesFor(table.classes.keys)
    val byFamily = table.classes.keys.sorted().groupBy { familyOf(it) }

    Files.createDirectories(options.out)
    var emitted = 0
    byFamily.toSortedMap().forEach { (family, names) ->
        val body = names.joinToString("\n\n") { emitClass(table, it, objectNames) }
        val file = options.out.resolve("Havok${family}Layouts.kt")
        Files.writeString(file, header(options, family, names.size, table) + body + "\n")
        emitted += names.size
        println("  ${file.fileName}  ${names.size} classes")
    }

    val members = table.classes.keys.sumOf { table.resolvedMembers(it).size }
    println("$emitted classes, $members resolved members, into ${options.out}")

    val rootless = table.classes.values.count { it.parent.isEmpty() }
    println("$rootless classes have no registered parent, which is expected rather than a failure")

    val flattened = objectNames.count { (name, objectName) -> !objectName.equals(name, ignoreCase = true) }
    if (flattened > 0) println("$flattened nested or templated names were flattened into identifiers")
}

private fun parse(args: Array<String>): Options {
    val values = args.associate { argument ->
        val separator = argument.indexOf('=')
        require(argument.startsWith("--") && separator > 2) {
            "Expected --key=value arguments, got '$argument'"
        }
        argument.substring(2, separator) to argument.substring(separator + 1)
    }

    fun required(key: String): String = values[key] ?: error("Missing --$key")

    return Options(
        table = Path.of(required("table")),
        out = Path.of(required("out")),
        packageName = required("package"),
        source = required("source"),
        task = required("task")
    )
}

/**
 * The Kotlin object name for every reflected class, rejecting the case where two distinct classes
 * would flatten onto the same identifier. Two objects of one name is a silent overwrite otherwise
 */
private fun objectNamesFor(names: Collection<String>): Map<String, String> {
    val objectNames = names.associateWith { name ->
        name.split(NAME_SEPARATORS)
            .filter { it.isNotEmpty() }
            .joinToString("") { it.pascal() }
    }

    objectNames.entries
        .groupBy({ it.value }, { it.key })
        .forEach { (objectName, sources) ->
            require(sources.size == 1) {
                "$objectName would be generated for ${sources.size} classes: ${sources.sorted()}"
            }
        }

    return objectNames
}

private fun familyOf(name: String): String = when {
    name.startsWith("hkp") -> "Physics"
    name.startsWith("hka") -> "Animation"
    name.startsWith("hkx") -> "Scene"
    name.startsWith("hkcd") -> "Collide"
    else -> "Core"
}

private fun header(options: Options, family: String, count: Int, table: ReflectionTable): String = """
    |// GENERATED FILE - do not edit. Regenerate with:
    |//
    |//     ./gradlew :tools:${options.task}
    |//
    |// Source: ${options.source}
    |//
    |// $family: $count of ${table.classes.size} reflected classes. Offsets include inherited
    |// members, flattened along the parent chain; the binary itself stores declared members only.
    |
    |@file:Suppress("ConstPropertyName", "ObjectPropertyName", "SpellCheckingInspection")
    |
    |package ${options.packageName}
    |
    |
    """.trimMargin()

private fun emitClass(table: ReflectionTable, name: String, objectNames: Map<String, String>): String {
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

    // Seeded with the size constant, so a member that lands on it is caught by the same check
    val taken = mutableSetOf(SIZE_CONSTANT)

    val lines = mutableListOf<String>()
    lines += doc
    lines += "public object ${objectNames.getValue(name)} {"
    lines += "    public const val $SIZE_CONSTANT: Int = 0x${klass.byteSize.toString(16)}"
    members.forEach { member ->
        val identifier = identifierFor(member)
        require(taken.add(identifier)) {
            "$name would generate $identifier twice; Havok names this one '${member.name}' at " +
                    "0x${member.offset.toString(16)}"
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

/**
 * Havok names its members in camelCase; the hand-written offset objects use PascalCase, so the
 * generated ones match them. Capitalising also makes a clash with a Kotlin hard keyword impossible,
 * since every one of those is lowercase — only a name that is no identifier at all still needs
 * escaping, and that is left verbatim so it still reads as the name the binary carries
 */
private fun identifierFor(member: HavokMember): String = when {
    member.name.isEmpty() -> "Unnamed0x${member.offset.toString(16)}"
    IDENTIFIER.matches(member.name) -> RESPELLED[member.name] ?: member.name.pascal()
    else -> "`${member.name}`"
}

private fun String.pascal(): String = replaceFirstChar { it.uppercaseChar() }
