package com.sappyoak.dsanalyzer.tas.rng

import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.ptde.GameMan
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.tas.FrameGate
import com.sappyoak.dsanalyzer.tas.Mailbox

/** Upcoming AI-generator floats a view carries */
private const val LOOKAHEAD = 16

/** The most draws one frame is searched for; a populated area makes ~30 */
private const val MAX_DRAWS_PER_FRAME = 4096

/**
 * Reads, pins and logs the gameplay RNG. `GameMan` is found through its global every time, because
 * it is rebuilt on every load
 */
public class RngTools internal constructor(
    private val memory: ProcessMemory,
    private val writer: GameWriter,
    private val gate: FrameGate,
    private val mailbox: Mailbox,
    private val gameManGlobal: Address
) {
    public fun current(): RngState? {
        val gameMan = gameMan() ?: return null
        val words = memory.read(gameMan + GameMan.RngState.toLong(), 2 * Int.SIZE_BYTES)?.let(::ints) ?: return null
        val instance = gameMan + GameMan.Twister.toLong()
        val twister = memory.read(instance, GameMan.TwisterSize)?.let { TwisterState.decode(it, instance) } ?: return null
        return RngState(GameRngState(words[0], words[1]), twister)
    }

    /** Writes [state] into the game; only while the gate holds it, so no frame sees half of it */
    public fun pin(state: RngState): Boolean {
        if (!gate.isPaused && !gate.isTriggered) return false
        val gameMan = gameMan() ?: return false
        val words = ByteBuffer.allocate(2 * Int.SIZE_BYTES).order(ByteOrder.LITTLE_ENDIAN)
            .putInt(state.game.s1).putInt(state.game.s2).array()
        val instance = gameMan + GameMan.Twister.toLong()
        return writer.perform(gameMan + GameMan.RngState.toLong(), words) &&
                writer.perform(instance, state.twister.encode(instance))
    }

    /** The state now and the last finished frame's draws, from the gate's log */
    public fun view(): RngView? {
        val now = current() ?: return null
        val frame = gate.frame ?: return null
        val before = logged(frame - 1)
        val after = logged(frame)
        val gameDraws = if (before != null && after != null) before.game.drawsTo(after.game, MAX_DRAWS_PER_FRAME) else null
        val twisterDraws = if (before != null && after != null) {
            Math.floorMod(before.twisterLeft - after.twisterLeft, TWISTER_WORDS)
        } else {
            null
        }
        return RngView(now.game, now.twister.left, gameDraws, twisterDraws, now.game.upcoming(LOOKAHEAD))
    }

    private fun logged(frame: Int): LogEntry? {
        val words = mailbox.readLog(frame)?.let(::ints) ?: return null
        return if (words[0] == frame) LogEntry(GameRngState(words[1], words[2]), words[3]) else null
    }

    private fun gameMan(): Address? =
        memory.read(gameManGlobal, Int.SIZE_BYTES)?.let(::ints)?.single()
            ?.let { Address(it.toLong() and 0xFFFFFFFFL) }?.takeUnless { it.isNull }

    private class LogEntry(val game: GameRngState, val twisterLeft: Int)
}

private fun ints(bytes: ByteArray): IntArray {
    val buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
    return IntArray(bytes.size / Int.SIZE_BYTES) { buffer.int }
}
