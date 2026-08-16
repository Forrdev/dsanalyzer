package com.sappyoak.dsanalyzer.game.assets.containers

import com.sappyoak.dsanalyzer.shared.toReader

fun ByteArray.isBHD5(): Boolean =
    size >= 4 && decodeToString(0, 4) == BHD5HeaderReader.MAGIC_STR

object BHD5HeaderReader {
    const val MAGIC_STR = "BHD5"
    private const val MAX_BUCKETS = BinderHeader.MAX_FILES
    private const val MAX_FILES_PER_BUCKET = BinderHeader.MAX_FILES

    private const val BUCKET_RECORD_SIZE = 8
    private const val FILE_RECORD_SIZE = 16

    /**
     * Parses a header file. The companion .bdt is not needed until a file is actually read,
     * so an index can be built without holding gigabytes of data
     */
    fun read(bytes: ByteArray, name: String): BHD5Header? {
        if (!bytes.isBHD5() || bytes.size < 0x18) return null

        val endianFlag = bytes[4].toInt()
        val bigEndian = endianFlag == 0

        val reader = bytes.toReader(!bigEndian).seek(0)
        if (reader.i32() != 1) return null
        reader.i32() // file size

        val bucketCount = reader.i32()
        val bucketsOffset = reader.i32()

        if (bucketCount !in 1..MAX_BUCKETS) return null
        if (bucketsOffset <= 0 || bucketsOffset >= bytes.size) return null

        val records = hashMapOf<UInt, BHD5Record>()
        for (i in 0 until bucketCount) {
            val at = bucketsOffset + i * BUCKET_RECORD_SIZE
            if (at + BUCKET_RECORD_SIZE > bytes.size) break

            val bucketReader = reader.at(at)
            val fileCount = bucketReader.i32()
            val filesOffset = bucketReader.i32()
            if (fileCount !in 0..MAX_FILES_PER_BUCKET) continue
            if (fileCount == 0 || filesOffset <= 0) continue

            for (j in 0 until fileCount) {
                val fileAt = filesOffset + j * FILE_RECORD_SIZE
                if (fileAt + FILE_RECORD_SIZE > bytes.size) break

                val fileReader = reader.at(fileAt)
                val hash = fileReader.u32().toUInt()
                val paddedSize = fileReader.i32()
                val offset = fileReader.i64()

                if (paddedSize <= 0 || offset < 0) continue
                records[hash] = BHD5Record(hash, offset, paddedSize)
            }
        }

        if (records.isEmpty()) return null
        return BHD5Header(name, records)
    }
    /**
     * The DS1 path hash
     *
     * Paths are normalized to lowercase with forward slashes and a leading slash, then folded
     * with a multiply-by-37 accumulator. Every step matters: A path that differs only in case
     * or a missing leading slash hashes to something else entirely, and the archive reports
     * that as a file that does not exist rather than as a malformed query
     */
    fun hashPath(path: String): UInt {
        var normalized = path.trim().replace('\\', '/').lowercase()
        if (!normalized.startsWith("/")) normalized = "/$normalized"

        var hash = 0u
        for (c in normalized) {
            hash = hash * 17u + c.code.toUInt()
        }
        return hash
    }

}
class BHD5Header(
    val name: String,
    private val records: Map<UInt, BHD5Record>
) {
    val fileCount: Int get() = records.size

    fun find(path: String): BHD5Record? = records[BHD5HeaderReader.hashPath(path)]
    fun findByHash(hash: UInt): BHD5Record? = records[hash]

    operator fun contains(path: String): Boolean = find(path) != null
    operator fun contains(hash: UInt): Boolean = records[hash] != null

    fun hashes(): Set<UInt> = records.keys

    override fun toString(): String = "$name ($fileCount files)"
}
data class BHD5Record(
    val hash: UInt,
    val offset: Long,
    /**
     * Size as stored, rounded up for alignment
     *
     * The real size is not recorded in the record shape, so reads include padding. This
     * is harmless. Every format below this either carries its own length or tolerates trailing
     * bytes, and DCX in particular declares its uncompressed size independently]
     */
    val paddedSize: Int
)