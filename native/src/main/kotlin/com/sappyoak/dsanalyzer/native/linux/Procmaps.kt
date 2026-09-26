package com.sappyoak.dsanalyzer.native.linux

import java.nio.file.Files
import java.nio.file.Path

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.process.ModuleInfo

/** `7f3c1a400000-7f3c1a428000 r-xp 00000000 08:01 1234  /usr/lib/libc.so.6` */
private val LINE = Regex("""^(\p{XDigit}+)-(\p{XDigit}+) (\S{4})\s+\S+\s+\S+\s+\S+\s*(.*)$""")

/** A pseudo mapping like [heap] or [vvar], which is not backed by a file */
private const val PSEUDO_PREFIX = '['

/** One line of /proc/pid/maps */
internal data class ProcMapping(
    val range: AddressRange,
    val readable: Boolean,
    val executable: Boolean,
    /** The file this is mapped from, absent for anonymous and pseudo mappings */
    val path: String?
)

/** Every mapping the process has, in address order, or nothing once it is gone */
internal fun mappings(pid: Int): List<ProcMapping> =
    runCatching { Files.readAllLines(Path.of("/proc/$pid/maps")) }
        .getOrNull()
        ?.mapNotNull(::parse)
        .orEmpty()

/**
 * The files the process has mapped, as one module each.
 *
 * A file arrives as several mappings, one per section, so a module spans from the first to the last
 * of them. Wine maps a PE the same way, which is what makes the game's own module findable here
 */
internal fun mappedModules(pid: Int): List<ModuleInfo> = mappings(pid)
    .filter { it.path != null }
    .groupBy { it.path!! }
    .map { (path, parts) ->
        val start = parts.minOf { it.range.start.value }
        val end = parts.maxOf { it.range.end.value }
        ModuleInfo(name = path.substringAfterLast('/'), range = AddressRange(Address(start), end - start))
    }

private fun parse(line: String): ProcMapping? {
    val match = LINE.matchEntire(line) ?: return null
    val (start, end, permissions, path) = match.destructured

    return ProcMapping(
        range = AddressRange(
            start = Address(java.lang.Long.parseUnsignedLong(start, 16)),
            size = java.lang.Long.parseUnsignedLong(end, 16) - java.lang.Long.parseUnsignedLong(start, 16)
        ),
        readable = permissions[0] == 'r',
        executable = permissions[2] == 'x',
        path = path.takeIf { it.isNotBlank() && it[0] != PSEUDO_PREFIX }
    )
}