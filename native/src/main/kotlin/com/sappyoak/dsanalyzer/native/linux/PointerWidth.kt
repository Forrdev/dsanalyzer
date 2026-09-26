package com.sappyoak.dsanalyzer.native.linux

import java.lang.foreign.Arena
import java.lang.foreign.ValueLayout.JAVA_BYTE
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.file.Files
import java.nio.file.Path

import com.sappyoak.dsanalyzer.native.process.ProcessInfo
import com.sappyoak.dsanalyzer.shared.platform.PointerSize

private const val DOS_MAGIC = 0x5A4D
private const val PE_MAGIC = 0x00004550
private const val LFANEW = 0x3C
private const val DOS_HEADER_SIZE = 0x40L
private const val COFF_PREFIX = 6
private const val MACHINE_I386 = 0x014C
private const val MACHINE_AMD64 = 0x8664

private val ELF_MAGIC = byteArrayOf(0x7F, 'E'.code.toByte(), 'L'.code.toByte(), 'F'.code.toByte())
private const val ELF_CLASS = 4
private const val ELF_32 = 1
private const val ELF_64 = 2

/** Bytes read out of [pid], or null when the kernel refused or moved fewer than asked */
internal fun readAt(pid: Int, address: Long, length: Int): ByteArray? = Arena.ofConfined().use { arena ->
    val buffer = arena.allocate(length.toLong())
    if (LibC.read(pid, address, buffer) == length.toLong()) buffer.toArray(JAVA_BYTE) else null
}

/** Zero when a byte at [address] can be read, otherwise the errno that stopped it */
internal fun errnoReading(pid: Int, address: Long): Int = Arena.ofConfined().use { arena ->
    val moved = LibC.read(pid, address, arena.allocate(1))
    if (moved == 1L) 0 else (-moved).toInt()
}

/**
 * The width the machine field of the process's own PE says, for a game running under Wine.
 *
 * Null for a native process, which has no PE mapped under the name it is known by
 */
internal fun peWidth(process: ProcessInfo): PointerSize? {
    val module = mappedModules(process.pid)
        .firstOrNull { it.name.equals(process.executableName, ignoreCase = true) }
        ?: return null

    val base = module.range.start.value
    val dos = readAt(pid = process.pid, address = base, length = DOS_HEADER_SIZE.toInt())?.littleEndian()
        ?: return null
    if (dos.getShort(0).toInt() and 0xFFFF != DOS_MAGIC) return null

    val headers = dos.getInt(LFANEW).toLong().takeIf { it in DOS_HEADER_SIZE..module.range.size } ?: return null
    val coff = readAt(process.pid, base + headers, COFF_PREFIX)?.littleEndian() ?: return null
    if (coff.getInt(0) != PE_MAGIC) return null

    return when (coff.getShort(4).toInt() and 0xFFFF) {
        MACHINE_I386 -> PointerSize.IntPointer
        MACHINE_AMD64 -> PointerSize.LongPointer
        else -> null
    }
}

/** The width the class byte of the process's own executable says */
internal fun elfWidth(pid: Int): PointerSize? {
    val header = runCatching {
        Files.newInputStream(Path.of("/proc/$pid/exe")).use { it.readNBytes(ELF_CLASS + 1) }
    }.getOrNull() ?: return null

    if (!header.copyOfRange(0, ELF_MAGIC.size).contentEquals(ELF_MAGIC)) return null
    return when (header[ELF_CLASS].toInt()) {
        ELF_32 -> PointerSize.IntPointer
        ELF_64 -> PointerSize.LongPointer
        else -> null
    }
}

private fun ByteArray.littleEndian(): ByteBuffer = ByteBuffer.wrap(this).order(ByteOrder.LITTLE_ENDIAN)
