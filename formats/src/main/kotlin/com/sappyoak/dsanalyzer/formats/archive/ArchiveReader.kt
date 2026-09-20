package com.sappyoak.dsanalyzer.formats.archive

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue
import com.sappyoak.dsanalyzer.shared.binary.readUInt
import java.nio.ByteOrder

private const val ARCHIVE_MAGIC = "BHD5"
private const val VERSION = 1
private const val BUCKET_RECORD_SIZE = 8L
private const val MAX_BUCKETS = 65_536
private const val MAX_ENTRIES_PER_BUCKET = 65_536

public fun readArchive(reader: BinaryReader): Archive {
    reader.position = 0
    reader.assertValue(ARCHIVE_MAGIC) { readFixedString(ARCHIVE_MAGIC.length) }
    reader.order = if (reader.readBoolean()) ByteOrder.LITTLE_ENDIAN else ByteOrder.BIG_ENDIAN
    reader.position = 0
    reader.assertValue(VERSION) { readInt() }
    reader.skip(4)

    val bucketCount = reader.readInt()
    val bucketsOffset = reader.readUInt().toLong()

    if (bucketCount !in 1..MAX_BUCKETS) {
        throw BinaryFormatException("Implausible archive bucket count $bucketCount", 0x10)
    }

    val entries = hashMapOf<UInt, ArchiveEntry>()
    for (bucket in 0 until bucketCount) {
        reader.at(bucketsOffset + bucket * BUCKET_RECORD_SIZE) { readBucket(entries) }
    }

    if (entries.isEmpty()) {
        throw BinaryFormatException("Archive header lists no files", bucketsOffset)
    }

    return Archive(entries)
}

private fun BinaryReader.readBucket(entries: MutableMap<UInt, ArchiveEntry>) {
    val bucketAt = position
    val entryCount = readInt()
    val entriesOffset = readUInt().toLong()

    if (entryCount !in 0..MAX_ENTRIES_PER_BUCKET) {
        throw BinaryFormatException("Implausible archive entry count $entryCount", bucketAt)
    }

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