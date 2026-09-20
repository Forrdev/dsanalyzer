package com.sappyoak.dsanalyzer.formats.archive

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue
import com.sappyoak.dsanalyzer.shared.binary.readUInt

private const val ARCHIVE_MAGIC = "BHD5"
private const val MAX_BUCKETS = 65_536
private const val MAX_ENTRIES_PER_BUCKET = 65_536

public fun readArchive(reader: BinaryReader): Archive {
    reader.position = 0
    reader.assertValue(ARCHIVE_MAGIC) { readFixedString(ARCHIVE_MAGIC.length) }
    reader.order = if (reader.readBoolean()) ByteOrder.LITTLE_ENDIAN else ByteOrder.BIG_ENDIAN

    reader.skip(1)
    reader.assertValue(0) { readByte().toInt() }
    reader.assertValue(0) { readByte().toInt() }
    reader.assertValue(1) { readInt() }
    reader.skip(4)

    var is64Bit = false
    if (reader.size > 0x28) {
        reader.at(0x14) {
            val test1 = readInt()
            skip(4)
            val test2 = readInt()

            if (test1 == 0 && test2 == 0) {
                is64Bit = true
            }
        }
    }

    val bucketCount = if (is64Bit) reader.readLong() else reader.readUInt().toLong()
    val bucketsOffset = if (is64Bit) reader.readLong() else reader.readUInt().toLong()

    if (bucketCount !in 1..MAX_BUCKETS) {
        throw BinaryFormatException("Implausible archive bucket count $bucketCount", 0x10)
    }

    reader.position = bucketsOffset

    val entries = hashMapOf<UInt, ArchiveEntry>()
    for (bucket in 0 until bucketCount) {
        reader.readBucket(is64Bit, entries)
    }

    if (entries.isEmpty()) {
        throw BinaryFormatException("Archive header lists no files", bucketsOffset)
    }

    return Archive(entries)
}

private fun BinaryReader.readBucket(is64Bit: Boolean, entries: MutableMap<UInt, ArchiveEntry>) {
    val bucketAt = position
    val entryCount = readInt()

    if (entryCount !in 0..MAX_ENTRIES_PER_BUCKET) {
        throw BinaryFormatException("Implausible archive entry count $entryCount", bucketAt)
    }

    if (is64Bit) {
        assertValue(1) { readInt() }
    }

    val entriesOffset = if (is64Bit) readLong() else readUInt().toLong()

    if (entryCount == 0) return

    at(entriesOffset) {
        repeat(entryCount) {
            val recordAt = position
            val hash = readUInt()
            val paddedSize = readInt()
            val offset = readLong()

            val previous = entries.put(hash, ArchiveEntry(hash, offset, paddedSize))
            if (previous != null) {
                throw BinaryFormatException("Archive path hash $hash appears twice", recordAt)
            }
        }
    }
}
