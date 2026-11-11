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
import com.sappyoak.dsanalyzer.runtime.inject.SiteLookup
import com.sappyoak.dsanalyzer.runtime.inject.X86
import com.sappyoak.dsanalyzer.runtime.inject.locate
import com.sappyoak.dsanalyzer.runtime.layout.CallArch
import com.sappyoak.dsanalyzer.runtime.layout.MailboxLayout
import com.sappyoak.dsanalyzer.runtime.write.CodeEdit
import com.sappyoak.dsanalyzer.runtime.write.GameMutation
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.runtime.write.WriteKey
import com.sappyoak.dsanalyzer.shared.binary.littleEndian

private const val BLOCK_SIZE = 0x1000
private const val SLOT_AT = 0x800
private val DEFAULT_TIMEOUT: Duration = 10.seconds

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
    private var slot: Address? = null
    private var current: Pending? = null

    public fun submit(call: GameCall): Submission {
        require(call.registers.keys.all { it.arch == layout.arch }) { "${call.label} uses registers of another architecture" }
        if (waiting.incrementAndGet() > QUEUE_LIMIT) {
            waiting.decrementAndGet()
            return Submission.Refused("$QUEUE_LIMIT calls are already waiting")
        }
        val id = ids.getAndIncrement()
        queue.add(Pending(id, call))
        return Submission.Queued(id)
    }

    public fun poll(memory: ProcessMemory, module: AddressRange): List<CallOutcome> {
        val outcomes = mutableListOf<CallOutcome>()
        if (current == null && queue.isEmpty()) return outcomes
        val box = ready(memory, module) ?: return drain(outcomes, "The mailbox could not be installed")

        current?.let { pending -> check(memory, box, pending, outcomes) }
        if (current == null) take()?.let { pending ->
            if (send(box, pending)) current = pending
            else outcomes += CallOutcome.Failed(pending.id, "The request could not be written")
        }
        return outcomes
    }

    /** Drops every request that has not started. Call before the hook is reverted */
    public fun cancelAll(memory: ProcessMemory) {
        while (take() != null) Unit
        val box = slot
        if (box != null && current != null && memory.intAt(box) == MailboxSlot.REQUESTED) write(box, MailboxSlot.FREE)
        current = null
    }

    private fun ready(memory: ProcessMemory, module: AddressRange): Address? {
        if (slot != null && MAILBOX_KEY in writer.active) return slot
        slot = install(memory, module)
        return slot
    }

    private fun install(memory: ProcessMemory, module: AddressRange): Address? {
        val at = (memory.locate(layout.site, module) as? SiteLookup.Found)?.address ?: return null
        val block = writer.allocate(BLOCK_SIZE, at) ?: return null
        val box = block + SLOT_AT.toLong()
        val code = assembleMailbox(layout, block, box, at, layout.site.expected)
        val jump = X86.jumpPatch(at, block, layout.site.displaced)
        val installed = code.size <= SLOT_AT &&
                writer.fill(block, code) &&
                writer.fill(box, ByteArray(MailboxSlot.SIZE)) &&
                writer.patchCode(CodeEdit(MAILBOX_KEY, at, layout.site.expected, jump))
        if (!installed) writer.free(block)
        return if (installed) box else null
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
        request.putInt(at(MailboxSlot.argCount(arch)), call.stack.size)
        call.stack.forEachIndexed { i, value ->
            val offset = at(MailboxSlot.firstArg(arch) + i * MailboxSlot.argStride(arch))
            if (arch == CallArch.X86) request.putInt(offset, value.toInt()) else request.putLong(offset, value)
        }
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
        while (true) outcomes += CallOutcome.Failed((take() ?: return outcomes).id, reason)
    }

    private fun take(): Pending? = queue.poll()?.also { waiting.decrementAndGet() }
}
