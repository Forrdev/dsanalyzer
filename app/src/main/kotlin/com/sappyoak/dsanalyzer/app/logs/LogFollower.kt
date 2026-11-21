package com.sappyoak.dsanalyzer.app.logs

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.NoSuchFileException
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

private val POLL = 500.milliseconds

/**
 * New lines since the last batch. [restarted] means the file rolled over and what came before is gone;
 * [problem] is why the file could not be read on the latest try, null once it can be again
 */
public data class LogBatch(
    public val entries: List<LogEntry>,
    public val restarted: Boolean,
    public val problem: String? = null
)

/**
 * Follows the log file as it grows, from its start. A line is only read once its newline is written, so
 * a line logback is halfway through is picked up on the next poll rather than read broken.
 *
 * The size comes from an open channel, never from the file's attributes: on Windows those come from the
 * directory entry, which is not updated while logback holds the file open. A failed read is tried again
 * every poll, since logback rolling the file over can refuse it for a moment
 */
public class LogFollower(private val file: Path, private val poll: Duration = POLL) {
    public fun follow(): Flow<LogBatch> = flow {
        val cursor = Cursor()
        var problem: String? = null

        while (true) {
            val read = try {
                Result.success(open()?.use(cursor::readFrom))
            } catch (err: IOException) {
                Result.failure(err)
            }
            val now = read.exceptionOrNull()?.let { "${it::class.simpleName}: ${it.message}" }
            val batch = read.getOrNull()
            if (batch != null || now != problem) emit((batch ?: LogBatch(emptyList(), false)).copy(problem = now))
            problem = now
            delay(poll)
        }
    }.flowOn(Dispatchers.IO)

    /** Null until logback has created the file */
    private fun open(): FileChannel? = try {
        FileChannel.open(file, StandardOpenOption.READ)
    } catch (_: NoSuchFileException) {
        null
    }
}

/** How far into the file the lines have been read */
private class Cursor {
    private var offset = 0L
    private var sequence = 0L
    private var restarted = false

    /** The lines added since the last read, or null when there are none to report */
    fun readFrom(channel: FileChannel): LogBatch? {
        val size = channel.size()
        if (size < offset) {
            offset = 0L
            restarted = true
        }
        if (size <= offset) return null

        val (lines, consumed) = channel.completeLines(offset, size)
        offset += consumed
        val entries = lines.mapNotNull { parseLogLine(it, sequence++) }
        if (entries.isEmpty() && !restarted) return null
        return LogBatch(entries, restarted).also { restarted = false }
    }
}

/** The whole lines between [from] and [to], and how many bytes they took */
private fun FileChannel.completeLines(from: Long, to: Long): Pair<List<String>, Long> {
    val buffer = ByteBuffer.allocate((to - from).toInt())
    while (buffer.hasRemaining() && read(buffer, from + buffer.position()) > 0) Unit

    val bytes = buffer.array().copyOf(buffer.position())
    val end = bytes.lastIndexOf('\n'.code.toByte())
    if (end < 0) return emptyList<String>() to 0L
    val text = String(bytes, 0, end, Charsets.UTF_8)
    return text.split('\n').filter { it.isNotBlank() } to (end + 1).toLong()
}
