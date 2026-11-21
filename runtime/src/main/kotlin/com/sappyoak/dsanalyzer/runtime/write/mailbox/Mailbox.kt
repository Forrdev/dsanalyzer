package com.sappyoak.dsanalyzer.runtime.write.mailbox

import java.util.concurrent.atomic.AtomicLong
import kotlin.time.Duration

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.MailboxLayout
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteKey
import com.sappyoak.dsanalyzer.runtime.write.GameMutation
import com.sappyoak.dsanalyzer.runtime.write.GameWriter

public val MAILBOX_KEY: WriteKey = WriteKey("hook:mailbox")

/** Calls on the game's logic thread, from a hook the game enters once per frame */
@OptIn(GameMutation::class)
public class Mailbox internal constructor(
    private val layout: MailboxLayout,
    private val writer: GameWriter,
    ids: AtomicLong,
    timeout: Duration = DEFAULT_TIMEOUT
) {
    private val queue = MailboxQueue(layout.arch, writer, ids, timeout)
    private var installed: InstalledMailbox? = null
    private var retired: Address? = null

    /** Refused outside the world, where the game would only hold the call until it timed out */
    public fun submit(call: GameCall): Submission = queue.submit(call)

    /** Leaving the world fails every call the game has not started */
    public fun poll(memory: ProcessMemory, module: AddressRange, inWorld: Boolean): List<CallOutcome> {
        queue.inWorld = inWorld
        val outcomes = mutableListOf<CallOutcome>()
        if (!queue.busy) return outcomes
        val box = ready(memory, module, outcomes) ?: return queue.drain(outcomes, "The mailbox could not be installed")
        queue.step(memory, box, outcomes)
        return outcomes
    }

    /** Drops every request that has not started. Call before the hook is reverted */
    public fun cancelAll(memory: ProcessMemory) {
        retired?.let(writer::free)
        retired = null
        queue.cancel(memory, installed?.slot)
    }

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

    private fun ready(memory: ProcessMemory, module: AddressRange, outcomes: MutableList<CallOutcome>): Address? {
        installed?.takeIf { MAILBOX_KEY in writer.active && it.intact(memory) }?.let { return it.slot }
        if (installed != null) {
            queue.abandon(outcomes, "The mailbox was replaced")
            retire()
        }
        installed = when (val layout = layout) {
            is MailboxLayout.Code -> installCodeMailbox(writer, memory, module, layout.site)
            is MailboxLayout.StepTable -> installStepTableMailbox(writer, memory, module, layout)
        }
        return installed?.slot
    }
}
