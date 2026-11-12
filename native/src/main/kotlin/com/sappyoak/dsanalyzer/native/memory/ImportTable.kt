package com.sappyoak.dsanalyzer.native.memory

import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.shared.binary.littleEndian

private const val LFANEW = 0x3CL
private const val OPTIONAL_HEADER = 0x18L
private const val PE32_PLUS = 0x20B
private const val DIRECTORIES_32 = 0x60L
private const val DIRECTORIES_64 = 0x70L
private const val IMPORT_DIRECTORY = 0x08L

private const val DESCRIPTOR_SIZE = 0x14L
private const val ORIGINAL_FIRST_THUNK = 0x00L
private const val NAME = 0x0CL
private const val FIRST_THUNK = 0x10L
private const val HINT_SIZE = 2L

private const val MAX_DESCRIPTORS = 256
private const val MAX_THUNKS = 4096
private const val NAME_LIMIT = 64

/**
 * The slot in [module]'s import address table that holds [function] from [library], or null.
 * The game's loader fills the slot, so a call through it reaches the game's own copy of the function,
 * which in a WoW64 process is the 32-bit one
 */
public fun ProcessMemory.importSlot(module: AddressRange, library: String, function: String): Address? {
    val base = module.start
    val headers = base + (u32(base + LFANEW) ?: return null)
    val optional = headers + OPTIONAL_HEADER
    val wide = u16(optional) == PE32_PLUS
    val directory = optional + (if (wide) DIRECTORIES_64 else DIRECTORIES_32) + IMPORT_DIRECTORY
    val first = base + (u32(directory) ?: return null)
    val width = if (wide) Long.SIZE_BYTES.toLong() else Int.SIZE_BYTES.toLong()

    for (index in 0 until MAX_DESCRIPTORS) {
        val descriptor = first + index * DESCRIPTOR_SIZE
        val name = u32(descriptor + NAME) ?: return null
        if (name == 0L) return null
        if (!text(base + name).equals(library, ignoreCase = true)) continue

        val names = u32(descriptor + ORIGINAL_FIRST_THUNK)?.takeIf { it != 0L } ?: return null
        val slots = u32(descriptor + FIRST_THUNK) ?: return null
        val at = thunkIndex(base, base + names, width, function) ?: return null
        return base + slots + at * width
    }
    return null
}

/** The index of the thunk naming [function], skipping imports by ordinal */
private fun ProcessMemory.thunkIndex(base: Address, thunks: Address, width: Long, function: String): Long? {
    val ordinal = if (width == Long.SIZE_BYTES.toLong()) Long.MIN_VALUE else 0x8000_0000L
    for (index in 0 until MAX_THUNKS) {
        val thunk = read(thunks + index * width, width.toInt())?.let(::unsigned) ?: return null
        if (thunk == 0L) return null
        if (thunk and ordinal == 0L && text(base + thunk + HINT_SIZE) == function) return index.toLong()
    }
    return null
}

private fun ProcessMemory.u32(at: Address): Long? = read(at, Int.SIZE_BYTES)?.let(::unsigned)

private fun ProcessMemory.u16(at: Address): Int? = read(at, Short.SIZE_BYTES)?.let { it.littleEndian().short.toInt() and 0xFFFF }

private fun ProcessMemory.text(at: Address): String {
    val bytes = read(at, NAME_LIMIT) ?: return ""
    val end = bytes.indexOf(0).takeIf { it >= 0 } ?: bytes.size
    return String(bytes, 0, end, Charsets.US_ASCII)
}

private fun unsigned(bytes: ByteArray): Long = when (bytes.size) {
    Int.SIZE_BYTES -> bytes.littleEndian().int.toLong() and 0xFFFF_FFFFL
    else -> bytes.littleEndian().long
}
