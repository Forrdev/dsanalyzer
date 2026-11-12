package com.sappyoak.dsanalyzer.runtime.write.mailbox

import java.util.concurrent.atomic.AtomicLong
import kotlin.time.Duration

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.importSlot
import com.sappyoak.dsanalyzer.native.memory.intAt
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.CallArch
import com.sappyoak.dsanalyzer.runtime.write.GameMutation
import com.sappyoak.dsanalyzer.runtime.write.GameWriter

private const val KERNEL32 = "KERNEL32.dll"
private const val SLEEP = "Sleep"

/** How long [RemoteMailbox.cancelAll] waits for the thread to end before it leaves the block in place */
private const val STOP_ATTEMPTS = 50
private const val STOP_WAIT_MS = 10L

@OptIn(GameMutation::class)
public class RemoteMailbox internal constructor(
    private val arch: CallArch,
    private val writer: GameWriter,
    ids: AtomicLong,
    timeout: Duration = DEFAULT_TIMEOUT
) {
    private val queue = MailboxQueue(arch, writer, ids, timeout)
    private var block: Address? = null

    /** Refused outside the world, as on the game-thread mailbox */
    public fun submit(call: GameCall): Submission = queue.submit(call)

    /** Leaving the world fails every call the thread has not started */
    public fun poll(memory: ProcessMemory, module: AddressRange, inWorld: Boolean): List<CallOutcome> {
        queue.inWorld = inWorld
        val outcomes = mutableListOf<CallOutcome>()
        if (!queue.busy) return outcomes
        val box = ready(memory, module) ?: return queue.drain(outcomes, "The remote mailbox could not be started")
        queue.step(memory, box, outcomes)
        return outcomes
    }

    /**
     * Drops every request that has not started and stops the thread. The block is freed only once the
     * thread answers that it ended; a thread still inside a call keeps its memory
     */
    public fun cancelAll(memory: ProcessMemory) {
        val start = block ?: return queue.cancel(memory, null)
        val box = start + SLOT_AT
        queue.cancel(memory, box)
        block = null
        if (writer.fillDword(box + MailboxSlot.CONTROL.toLong(), MailboxSlot.STOP) && stopped(memory, box)) writer.free(start)
    }

    private fun stopped(memory: ProcessMemory, box: Address): Boolean {
        repeat(STOP_ATTEMPTS) {
            if (memory.intAt(box + MailboxSlot.CONTROL.toLong()) == MailboxSlot.STOPPED) return true
            Thread.sleep(STOP_WAIT_MS)
        }
        return false
    }

    private fun ready(memory: ProcessMemory, module: AddressRange): Address? {
        block?.let { return it + SLOT_AT }
        val sleep = memory.importSlot(module, KERNEL32, SLEEP) ?: return null
        val start = writer.allocate(BLOCK_SIZE) ?: return null
        val box = start + SLOT_AT
        val code = threadBody(arch, start, sleep)
        val started = code.size <= SLOT_AT &&
                writer.fill(start, code) &&
                writer.fill(box, ByteArray(MailboxSlot.SIZE)) &&
                writer.startThread(start, box)
        if (!started) {
            writer.free(start)
            return null
        }
        block = start
        return box
    }
}
