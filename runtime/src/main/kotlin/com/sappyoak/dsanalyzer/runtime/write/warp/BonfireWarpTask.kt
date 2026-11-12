package com.sappyoak.dsanalyzer.runtime.write.warp

import com.sappyoak.dsanalyzer.runtime.layout.BonfireWarpLayout
import com.sappyoak.dsanalyzer.runtime.write.GameMutation
import com.sappyoak.dsanalyzer.runtime.write.mailbox.CallOutcome
import com.sappyoak.dsanalyzer.runtime.write.mailbox.GameCall
import com.sappyoak.dsanalyzer.runtime.write.mailbox.Submission
import com.sappyoak.dsanalyzer.runtime.write.task.TaskContext
import com.sappyoak.dsanalyzer.runtime.write.task.TaskStep
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask

/** The warp kind that sends the player to the bonfire GameMan names */
private const val LAST_BONFIRE: Long = 1

private enum class Phase { Send, Called, Leaving, Arriving }

/**
 * Warps to [bonfireId] by writing it as the game's own last bonfire and asking the game to warp there.
 * The task is done once the load has taken the player out of the world and put them back, so whoever
 * asked for the warp learns when it finished rather than when it started
 */
public class BonfireWarpTask internal constructor(
    private val bonfireId: Int,
    private val layout: BonfireWarpLayout
) : WriteTask {
    override val label: String get() = "Warp to bonfire $bonfireId"
    override val mergeKey: String get() = "travel:warp"

    private var phase = Phase.Send
    private var sent: Long? = null

    override fun step(context: TaskContext): TaskStep = when (phase) {
        Phase.Send -> send(context)
        Phase.Called -> called(context)
        Phase.Leaving -> advance(!context.inWorld, Phase.Arriving)
        Phase.Arriving -> if (context.inWorld) TaskStep.Done() else TaskStep.Continue
    }

    @OptIn(GameMutation::class)
    private fun send(context: TaskContext): TaskStep {
        if (!context.inWorld) return TaskStep.Failed("The player is not in the world")
        val memory = context.memory
        val field = memory.addressOf(layout.lastBonfire.pointer)
        if (field.isNull) return TaskStep.Failed("${layout.lastBonfire.pointer} could not be found")
        val owner = memory.addressOf(layout.owner)
        if (owner.isNull) return TaskStep.Failed("${layout.owner} could not be found")
        val function = memory.addressOf(layout.function)
        if (function.isNull) return TaskStep.Failed("${layout.function} could not be found")
        if (!context.writer.fillDword(field + layout.lastBonfire.offset.toLong(), bonfireId)) {
            return TaskStep.Failed("The bonfire could not be set")
        }

        val call = GameCall(
            target = function,
            label = label,
            registers = buildMap {
                put(layout.ownerRegister, owner.value)
                layout.kindRegister?.let { put(it, LAST_BONFIRE) }
            },
            stack = if (layout.kindRegister == null) listOf(LAST_BONFIRE) else emptyList()
        )
        return when (val submission = context.call(call)) {
            is Submission.Refused -> TaskStep.Failed(submission.reason)
            is Submission.Queued -> {
                sent = submission.id
                advance(true, Phase.Called)
            }
        }
    }

    private fun called(context: TaskContext): TaskStep {
        val id = sent ?: return TaskStep.Failed("The warp was never sent")
        return when (val outcome = context.outcome(id)) {
            null -> TaskStep.Continue
            is CallOutcome.Done -> advance(true, Phase.Leaving)
            is CallOutcome.TimedOut -> TaskStep.Failed("The warp was not run in time")
            is CallOutcome.Failed -> TaskStep.Failed(outcome.reason)
        }
    }

    private fun advance(ready: Boolean, next: Phase): TaskStep {
        if (ready) phase = next
        return TaskStep.Continue
    }
}
