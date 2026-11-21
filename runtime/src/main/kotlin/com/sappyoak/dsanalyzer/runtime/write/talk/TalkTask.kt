package com.sappyoak.dsanalyzer.runtime.write.talk

import com.sappyoak.dsanalyzer.native.memory.intAt
import com.sappyoak.dsanalyzer.native.memory.pointerAt
import com.sappyoak.dsanalyzer.runtime.layout.TalkLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.TableView
import com.sappyoak.dsanalyzer.runtime.write.mailbox.CallOutcome
import com.sappyoak.dsanalyzer.runtime.write.mailbox.GameCall
import com.sappyoak.dsanalyzer.runtime.write.mailbox.Submission
import com.sappyoak.dsanalyzer.runtime.write.task.TaskContext
import com.sappyoak.dsanalyzer.runtime.write.task.TaskStep
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask

/** How many loaded characters to try as the owner before giving up */
private const val OWNER_TRIES = 8

private enum class Phase { Owner, Send }

/**
 * Runs one talk command through the game's own dispatcher, on its logic thread.
 *
 * A command that reads its owner first resolves one: each loaded block's first entity id in turn,
 * until the game's own lookup returns a character. The command goes out on the next frame with that
 * owner. The owner's block would have to unload in that one frame for the command to see none.
 * The game does nothing with a command sent while another menu is fully open
 */
public class TalkTask internal constructor(
    private val command: TalkCommand,
    private val layout: TalkLayout
) : WriteTask {
    override val label: String get() = "Talk command ${command.id}"

    private var phase = if (command.needsOwner) Phase.Owner else Phase.Send
    private var candidates: ArrayDeque<Int>? = null
    private var trying = 0
    private var owner = 0
    private var sent: Long? = null

    override fun step(context: TaskContext): TaskStep {
        if (!context.inWorld) return TaskStep.Failed("The player is not in the world")
        val targets = targets(context.memory) ?: return TaskStep.Failed("The talk functions could not be found")
        sent?.let { id -> return outcome(context, id) }
        return when (phase) {
            Phase.Owner -> tryOwner(context, targets)
            Phase.Send -> {
                val call = dispatchCall(command, data(context, targets, owner), layout, targets, context.arch)
                submit(context, call)
            }
        }
    }

    private fun tryOwner(context: TaskContext, targets: TalkTargets): TaskStep {
        val queue = candidates ?: ArrayDeque(entityIds(context.memory)).also { candidates = it }
        trying = queue.removeFirstOrNull() ?: return TaskStep.Failed("No loaded character could own $label")
        return submit(context, ownerLookupCall(data(context, targets, trying), layout, targets, context.arch))
    }

    private fun submit(context: TaskContext, call: GameCall): TaskStep = when (val submission = context.call(call)) {
        is Submission.Refused -> TaskStep.Failed(submission.reason)
        is Submission.Queued -> TaskStep.Continue.also { sent = submission.id }
    }

    private fun outcome(context: TaskContext, id: Long): TaskStep = when (val outcome = context.outcome(id)) {
        null -> TaskStep.Continue
        is CallOutcome.TimedOut -> TaskStep.Failed("$label was not run in time")
        is CallOutcome.Failed -> TaskStep.Failed(outcome.reason)
        is CallOutcome.Done -> {
            sent = null
            when (phase) {
                Phase.Owner -> TaskStep.Continue.also {
                    if (outcome.value != 0L) {
                        owner = trying
                        phase = Phase.Send
                    }
                }
                Phase.Send -> TaskStep.Done(outcome.value)
            }
        }
    }

    private fun data(context: TaskContext, targets: TalkTargets, ownerId: Int): ByteArray =
        talkData(command, layout, targets, ownerId, context.arch)

    private fun targets(memory: GameMemory): TalkTargets? {
        val dispatcher = memory.addressOf(layout.dispatcher).takeUnless { it.isNull } ?: return null
        val lookup = memory.addressOf(layout.ownerLookup).takeUnless { it.isNull } ?: return null
        val event = memory.addressOf(layout.eventVTable).takeUnless { it.isNull } ?: return null
        val handler = layout.handlerVTable?.let { pointer -> memory.addressOf(pointer).takeUnless { it.isNull } ?: return null }
        return TalkTargets(dispatcher, lookup, event, handler)
    }

    /** The first entity id of each loaded block that has any */
    private fun entityIds(memory: GameMemory): List<Int> {
        val entities = layout.entities
        val blocks = TableView(entities.blocks)
        if (!blocks.refresh(memory.memory, memory.addressOf(entities.manager))) return emptyList()
        return blocks.elements.asSequence()
            .filter { (memory.memory.intAt(it + entities.count.toLong()) ?: 0) > 0 }
            .mapNotNull { block -> memory.memory.pointerAt(block + entities.entries.toLong())?.takeUnless { it.isNull } }
            .mapNotNull { table -> memory.memory.intAt(table) }
            .take(OWNER_TRIES)
            .toList()
    }
}
