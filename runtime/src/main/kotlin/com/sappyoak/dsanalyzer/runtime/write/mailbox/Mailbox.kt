package com.sappyoak.dsanalyzer.runtime.write.mailbox

import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeMark
import kotlin.time.TimeSource

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.intAt
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.CallArch
import com.sappyoak.dsanalyzer.runtime.layout.MailboxLayout
import com.sappyoak.dsanalyzer.runtime.write.GameMutation
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.runtime.write.WriteKey
import com.sappyoak.dsanalyzer.shared.binary.littleEndian

private val DEFAULT_TIMEOUT: Duration = 10.seconds
private const val LEFT_WORLD = "The player left the world"

public val MAILBOX_KEY: WriteKey = WriteKey("hook:mailbox")

public sealed interface CallOutcome {
    public val id: Long

    public data class Done(override val id: Long, public val value: Long) : CallOutcome

    /** The world did not run the request in time, such as during a long load or on the main menu */
    public data class TimedOut(override val id: Long) : CallOutcome
    public data class Failed(override val id: Long, public val reason: String) : CallOutcome
}

private class Pending(val id: Long, val call: GameCall) {
    var sentAt: TimeMark? = null
}

@OptIn(GameMutation::class)
public class Mailbox internal constructor(
    private val layout: MailboxLayout,
    private val writer: GameWriter,
    private val timeout: Duration = DEFAULT_TIMEOUT
) {
    private val queue = ConcurrentLinkedQueue<Pending>()
    private val ids = AtomicLong(1)
    private val waiting = AtomicInteger(0)
    private var installed: InstalledMailbox? = null
    private var retired: Address? = null
    private var current: Pending? = null
    @Volatile private var inWorld = false

    /** Refused outside the world, where the game would only hold the call until it timed out */
    public fun submit(call: GameCall): Submission {
        if (!inWorld) return Submission.Refused("The player is not in the world")
        require(call.registers.keys.all { it.arch == layout.arch }) { "${call.label} uses registers of another architecture" }
        if (waiting.incrementAndGet() > QUEUE_LIMIT) {
            waiting.decrementAndGet()
            return Submission.Refused("$QUEUE_LIMIT calls are already waiting")
        }
        val id = ids.getAndIncrement()
        queue.add(Pending(id, call))
        return Submission.Queued(id)
    }

    /** Leaving the world fails every call the game has not started */
    public fun poll(memory: ProcessMemory, module: AddressRange, inWorld: Boolean): List<CallOutcome> {
        this.inWorld = inWorld
        val outcomes = mutableListOf<CallOutcome>()
        if (current == null && queue.isEmpty()) return outcomes
        val box = ready(memory, module, outcomes) ?: return drain(outcomes, "The mailbox could not be installed")

        current?.let { pending -> check(memory, box, pending, outcomes) }
        if (!inWorld) {
            withdraw(memory, box, outcomes)
            return failQueued(outcomes, LEFT_WORLD)
        }
        if (current == null) take()?.let { pending ->
            if (send(box, pending)) current = pending
            else outcomes += CallOutcome.Failed(pending.id, "The request could not be written")
        }
        return outcomes
    }

    /** Drops every request that has not started. Call before the hook is reverted */
    public fun cancelAll(memory: ProcessMemory) {
        while (take() != null) Unit
        retired?.let(writer::free)
        retired = null
        val box = installed?.slot
        if (box != null && current != null && memory.intAt(box) == MailboxSlot.REQUESTED) write(box, MailboxSlot.FREE)
        current = null
    }

    /** A sent call the game has not started; the clock gate holds it while the world is down */
    /**
     * Gives up a mailbox the game no longer enters. The record goes without being written back,
     * because the field may now hold whatever the game put there, and the block is freed only on the
     * next install, so the game cannot still be inside the cave when the memory goes
     */
    private fun retire() {
        val gone = installed ?: return
        writer.forget(MAILBOX_KEY)
        retired?.let(writer::free)
        retired = gone.block
        installed = null
    }

    private fun withdraw(memory: ProcessMemory, box: Address, outcomes: MutableList<CallOutcome>) {
        val pending = current ?: return
        if (memory.intAt(box) != MailboxSlot.REQUESTED || !write(box, MailboxSlot.FREE)) return
        outcomes += CallOutcome.Failed(pending.id, LEFT_WORLD)
        current = null
    }

    private fun ready(memory: ProcessMemory, module: AddressRange, outcomes: MutableList<CallOutcome>): Address? {
        installed?.takeIf { MAILBOX_KEY in writer.active && it.intact(memory) }?.let { return it.slot }
        if (installed != null) {
            current?.let { outcomes += CallOutcome.Failed(it.id, "The mailbox was replaced") }
            current = null
            retire()
        }
        installed = when (val layout = layout) {
            is MailboxLayout.Code -> installCodeMailbox(writer, memory, module, layout.site)
            is MailboxLayout.StepTable -> installStepTableMailbox(writer, memory, module, layout)
        }
        return installed?.slot
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
            else -> {
                outcomes += CallOutcome.Failed(pending.id, "The mailbox slot was lost")
                current = null
            }
        }
    }

    private fun send(box: Address, pending: Pending): Boolean {
        val call = pending.call
        val arch = layout.arch
        val request = littleEndian(MailboxSlot.RETURN + Long.SIZE_BYTES - MailboxSlot.TARGET)
        fun at(offset: Int) = offset - MailboxSlot.TARGET
        request.putLong(at(MailboxSlot.TARGET), call.target.value)
        call.registers.forEach { (register, value) -> request.putLong(at(register.offset), value) }
        val data = box + MailboxSlot.DATA.toLong()
        call.dataPointers.forEach { (register, offset) -> request.putLong(at(register.offset), data.value + offset) }
        request.putInt(at(MailboxSlot.argCount(arch)), call.stack.size)
        call.stack.forEachIndexed { i, value ->
            val offset = at(MailboxSlot.firstArg(arch) + i * MailboxSlot.argStride(arch))
            if (arch == CallArch.X86) request.putInt(offset, value.toInt()) else request.putLong(offset, value)
        }
        if (call.data.isNotEmpty() && !writer.fill(data, call.data)) return false
        if (!writer.fill(box + MailboxSlot.TARGET.toLong(), request.array())) return false
        pending.sentAt = TimeSource.Monotonic.markNow()
        return write(box, MailboxSlot.REQUESTED)
    }

    private fun result(memory: ProcessMemory, box: Address): Long {
        val bytes = memory.read(box + MailboxSlot.RETURN.toLong(), Long.SIZE_BYTES) ?: return 0
        val value = bytes.littleEndian().long
        return if (layout.arch == CallArch.X86) value and 0xFFFFFFFFL else value
    }

    private fun write(box: Address, state: Int): Boolean =
        writer.fill(box, littleEndian(Int.SIZE_BYTES).putInt(state).array())

    private fun drain(outcomes: MutableList<CallOutcome>, reason: String): List<CallOutcome> {
        current?.let { outcomes += CallOutcome.Failed(it.id, reason) }
        current = null
        return failQueued(outcomes, reason)
    }

    private fun failQueued(outcomes: MutableList<CallOutcome>, reason: String): List<CallOutcome> {
        while (true) outcomes += CallOutcome.Failed((take() ?: return outcomes).id, reason)
    }

    private fun take(): Pending? = queue.poll()?.also { waiting.decrementAndGet() }
}
