package com.sappyoak.dsanalyzer.runtime.write.mailbox

import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeMark
import kotlin.time.TimeSource

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.intAt
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.CallArch
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteKey
import com.sappyoak.dsanalyzer.runtime.write.GameMutation
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.shared.binary.littleEndian

internal val DEFAULT_TIMEOUT: Duration = 10.seconds
private const val LEFT_WORLD = "The player left the world"

public sealed interface CallOutcome {
    public val id: Long

    public data class Done(override val id: Long, public val value: Long) : CallOutcome

    /** The game did not run the request in time, such as when it stalls */
    public data class TimedOut(override val id: Long) : CallOutcome
    public data class Failed(override val id: Long, public val reason: String) : CallOutcome
}

private class Pending(val id: Long, val call: GameCall) {
    var sentAt: TimeMark? = null
}

/**
 * The request protocol both mailboxes share: one call in the slot at a time, the rest waiting in order.
 * [ids] is shared between mailboxes so an outcome's id names one call only
 */
@OptIn(GameMutation::class)
internal class MailboxQueue(
    private val arch: CallArch,
    private val writer: GameWriter,
    private val ids: AtomicLong,
    private val timeout: Duration
) {
    private val queue = ConcurrentLinkedQueue<Pending>()
    private val waiting = AtomicInteger(0)
    private var current: Pending? = null

    /** Set from each sample; calls are refused outside the world, where the game could not run them */
    @Volatile var inWorld: Boolean = false

    val busy: Boolean get() = current != null || queue.isNotEmpty()

    fun submit(call: GameCall): Submission {
        if (!inWorld) return Submission.Refused("The player is not in the world")
        require(call.registers.keys.all { it.arch == arch }) { "${call.label} uses registers of another architecture" }
        if (waiting.incrementAndGet() > QUEUE_LIMIT) {
            waiting.decrementAndGet()
            return Submission.Refused("$QUEUE_LIMIT calls are already waiting")
        }
        val id = ids.getAndIncrement()
        queue.add(Pending(id, call))
        return Submission.Queued(id)
    }

    /** Reads the slot at [box], then sends the next call; leaving the world fails every call not started */
    fun step(memory: ProcessMemory, box: Address, outcomes: MutableList<CallOutcome>) {
        current?.let { pending -> check(memory, box, pending, outcomes) }
        if (!inWorld) {
            withdraw(memory, box, outcomes)
            failQueued(outcomes, LEFT_WORLD)
            return
        }
        if (current == null) take()?.let { pending ->
            if (send(box, pending)) current = pending
            else outcomes += CallOutcome.Failed(pending.id, "The request could not be written")
        }
    }

    /** Fails the call in the slot without touching it, for a slot that is gone */
    fun abandon(outcomes: MutableList<CallOutcome>, reason: String) {
        current?.let { outcomes += CallOutcome.Failed(it.id, reason) }
        current = null
    }

    fun drain(outcomes: MutableList<CallOutcome>, reason: String): List<CallOutcome> {
        abandon(outcomes, reason)
        return failQueued(outcomes, reason)
    }

    /** Drops every request that has not started */
    fun cancel(memory: ProcessMemory, box: Address?) {
        while (take() != null) Unit
        if (box != null && current != null && memory.intAt(box) == MailboxSlot.REQUESTED) write(box, MailboxSlot.FREE)
        current = null
    }

    private fun withdraw(memory: ProcessMemory, box: Address, outcomes: MutableList<CallOutcome>) {
        val pending = current ?: return
        if (memory.intAt(box) != MailboxSlot.REQUESTED || !write(box, MailboxSlot.FREE)) return
        outcomes += CallOutcome.Failed(pending.id, LEFT_WORLD)
        current = null
    }

    private fun check(memory: ProcessMemory, box: Address, pending: Pending, outcomes: MutableList<CallOutcome>) {
        when (memory.intAt(box)) {
            MailboxSlot.DONE -> {
                outcomes += CallOutcome.Done(pending.id, result(memory, box))
                write(box, MailboxSlot.FREE)
                writer.note(WriteKey("call:${pending.call.label}"), pending.call.label)
                current = null
            }
            MailboxSlot.RUNNING -> Unit
            MailboxSlot.REQUESTED -> if ((pending.sentAt?.elapsedNow() ?: Duration.ZERO) > timeout) {
                write(box, MailboxSlot.FREE)
                outcomes += CallOutcome.TimedOut(pending.id)
                current = null
            }
            else -> abandon(outcomes, "The mailbox slot was lost")
        }
    }

    private fun send(box: Address, pending: Pending): Boolean {
        val call = pending.call
        val data = box + MailboxSlot.DATA.toLong()
        val block = linkedData(call, arch, data) ?: return false
        if (block.isNotEmpty() && !writer.fill(data, block)) return false
        if (!writer.fill(box + MailboxSlot.TARGET.toLong(), requestBytes(call, arch, data))) return false
        pending.sentAt = TimeSource.Monotonic.markNow()
        return write(box, MailboxSlot.REQUESTED)
    }

    private fun result(memory: ProcessMemory, box: Address): Long {
        val bytes = memory.read(box + MailboxSlot.RETURN.toLong(), Long.SIZE_BYTES) ?: return 0
        val value = bytes.littleEndian().long
        return if (arch == CallArch.X86) value and 0xFFFFFFFFL else value
    }

    private fun write(box: Address, state: Int): Boolean =
        writer.fill(box, littleEndian(Int.SIZE_BYTES).putInt(state).array())

    private fun failQueued(outcomes: MutableList<CallOutcome>, reason: String): List<CallOutcome> {
        while (true) outcomes += CallOutcome.Failed((take() ?: return outcomes).id, reason)
    }

    private fun take(): Pending? = queue.poll()?.also { waiting.decrementAndGet() }
}
