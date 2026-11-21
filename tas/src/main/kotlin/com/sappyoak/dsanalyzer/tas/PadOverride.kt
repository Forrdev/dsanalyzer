package com.sappyoak.dsanalyzer.tas

import com.sappyoak.dsanalyzer.runtime.inject.Hook
import com.sappyoak.dsanalyzer.tas.Mailbox.Companion.RING_CAPACITY
import com.sappyoak.dsanalyzer.tas.Mailbox.Field

public enum class PadMode(internal val code: Int) { Off(0), Play(1), Record(2) }

/** How far the current run has got: [frames] played or recorded, out of [total] when playing */
public data class PadProgress(public val mode: PadMode, public val frames: Int, public val total: Int?)

/**
 * A run that lost step with the game: by gate frame [frame], [expected] records were due and only
 * [used] had been. A poll of the pad failed, or the ring ran dry (playing) or full (recording)
 */
public data class PadFault(public val frame: Int, public val expected: Int, public val used: Int)

/**
 * Plays a [Movie] into the player's pad, or records the pad into one, a record per frame
 *
 * The stub runs right after each successful poll of the player's pad (an XInput one), so whatever
 * the frame does with the pad afterwards sees the movie. [playerPad] finds that device; it is
 * handed to the stub before every run and re-checked every [pump], in case the game rebuilds it.
 *
 * Records stream through the mailbox's ring: the stub owns every record from its cursor up to the
 * tool's limit, the tool everything else, and [pump] keeps the ring topped up (playing) or drained
 * (recording). A run starts at the stub's next poll, so starting one while the [FrameGate] holds
 * the game puts its first record on an exact frame.
 *
 * The stub counts polls, the movie counts frames. [pump] compares the two: a playback that falls
 * behind is stopped with a [fault], a recording that does keeps going with one
 */
public class PadOverride internal constructor(
    private val mailbox: Mailbox,
    private val hook: Hook,
    private val playerPad: () -> Int?
) : AutoCloseable {
    private var mode = PadMode.Off
    private var device: Int? = null
    private var epoch = mailbox.read(Field.PadEpoch) ?: 0
    private var movie: Movie? = null
    private val recorded = mutableListOf<PadFrame>()

    /** Records handed over this run: written to the ring when playing, read out of it when recording */
    private var moved = 0

    /** Why the last run lost step, until the next one starts */
    @get:Synchronized
    public var fault: PadFault? = null
        private set

    @get:Synchronized
    public val progress: PadProgress get() = PadProgress(mode, cursor() ?: 0, movie?.size)

    @Synchronized
    public fun play(movie: Movie): Boolean {
        if (!halt()) return false
        this.movie = movie
        moved = 0
        return topUp(cursor = 0) && start(PadMode.Play, limit = moved)
    }

    @Synchronized
    public fun record(): Boolean {
        if (!halt()) return false
        recorded.clear()
        moved = 0
        return start(PadMode.Record, limit = RING_CAPACITY)
    }

    /** Hands the pad back; when recording, the movie recorded so far */
    @Synchronized
    public fun stop(): Movie? {
        val wasRecording = mode == PadMode.Record
        halt()
        // After halting: a frame already inside the stub may still have added one
        if (wasRecording) drain()
        return if (wasRecording) Movie(recorded.toList()) else null
    }

    /** Moves records between the tool and the ring; a finished or faulted movie hands the pad back */
    @Synchronized
    public fun pump() {
        if (mode == PadMode.Off || !handOverDevice()) return
        val cursor = cursor() ?: return
        if (mode == PadMode.Play && cursor >= (movie?.size ?: 0)) {
            halt()
            return
        }
        if (fault == null) fault = behind()
        when (mode) {
            PadMode.Off -> Unit
            PadMode.Play -> when {
                fault != null -> halt()
                topUp(cursor) -> mailbox.write(Field.PadLimit, moved)
            }
            PadMode.Record -> if (drain()) mailbox.write(Field.PadLimit, moved + RING_CAPACITY)
        }
    }

    override fun close() {
        halt()
        hook.remove()
    }

    /** The stub's cursor for this run; 0 until the stub has seen the run start */
    private fun cursor(): Int? {
        val seen = mailbox.read(Field.PadSeen) ?: return null
        return if (seen == epoch) mailbox.read(Field.PadCursor) else 0
    }

    /**
     * Every frame before the gate's current one has used a record, so the cursor is never under
     * `frame - first`. Judged only when the frame did not move during the reads
     */
    private fun behind(): PadFault? {
        if (mailbox.read(Field.PadSeen) != epoch) return null
        val frame = mailbox.read(Field.Frame) ?: return null
        val first = mailbox.read(Field.PadFirstFrame) ?: return null
        val used = mailbox.read(Field.PadCursor) ?: return null
        if (mailbox.read(Field.Frame) != frame) return null
        val due = frame - first
        return if (used < due) PadFault(frame, due, used) else null
    }

    private fun start(next: PadMode, limit: Int): Boolean {
        val ok = handOverDevice() &&
                mailbox.write(Field.PadLimit, limit) &&
                mailbox.write(Field.PadEpoch, epoch + 1).also { if (it) epoch++ } &&
                mailbox.write(Field.PadMode, next.code)
        if (ok) {
            mode = next
            fault = null
        }
        return ok
    }

    /** Tells the stub which device is the player's when that has changed */
    private fun handOverDevice(): Boolean {
        val current = playerPad() ?: return false
        if (current == device) return true
        val ok = mailbox.write(Field.PadDevice, current)
        if (ok) device = current
        return ok
    }

    private fun halt(): Boolean {
        mode = PadMode.Off
        return mailbox.write(Field.PadMode, PadMode.Off.code)
    }

    /** Writes the movie's next records into the ring, as far as [cursor] leaves room */
    private fun topUp(cursor: Int): Boolean {
        val frames = movie?.frames ?: return false
        while (moved < frames.size && moved - cursor < RING_CAPACITY) {
            if (!mailbox.writeRecord(moved, frames[moved].encode())) return false
            moved++
        }
        return true
    }

    /** Reads every record the stub has filled since the last drain */
    private fun drain(): Boolean {
        val cursor = cursor() ?: return false
        while (moved < cursor) {
            recorded += PadFrame.decode(mailbox.readRecord(moved) ?: return false)
            moved++
        }
        return true
    }
}
