package com.sappyoak.dsanalyzer.runtime.write.warp

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.WarpLayout
import com.sappyoak.dsanalyzer.runtime.snapshot.SavedPosition
import com.sappyoak.dsanalyzer.runtime.write.GameMutation
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.runtime.write.mailbox.CallOutcome
import com.sappyoak.dsanalyzer.runtime.write.mailbox.GameCall
import com.sappyoak.dsanalyzer.runtime.write.mailbox.Submission
import com.sappyoak.dsanalyzer.runtime.write.task.TaskContext
import com.sappyoak.dsanalyzer.runtime.write.task.TaskStep
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask
import com.sappyoak.dsanalyzer.shared.binary.littleEndian
import com.sappyoak.dsanalyzer.shared.binary.putVec4
import com.sappyoak.dsanalyzer.shared.binary.toLittleEndian
import com.sappyoak.dsanalyzer.shared.math.Vec4

/** The warp kind that sends the player to what GameMan names: the last bonfire, or the point */
private const val GAME_MAN_DESTINATION: Long = 1
private const val W: Float = 1.0f

public sealed interface WarpDestination {
    public data class Bonfire(public val id: Int) : WarpDestination

    /** The game loads [saved]'s map and spawns there, so no bonfire is needed in that map */
    public data class Point(public val saved: SavedPosition) : WarpDestination
}

private enum class Phase { Send, Called, Leaving, Arriving }

public class WarpTask internal constructor(
    private val destination: WarpDestination,
    private val layout: WarpLayout
) : WriteTask {
    override val label: String get() = when (destination) {
        is WarpDestination.Bonfire -> "Warp to bonfire ${destination.id}"
        is WarpDestination.Point -> "Warp to ${destination.saved.map} ${destination.saved.position}"
    }
    override val mergeKey: String get() = "travel:warp"

    private var phase = Phase.Send
    private var sent: Long? = null
    /** The point's flag while this task has it set and the game has not started the warp */
    private var armed: Address? = null

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
        val gameMan = memory.addressOf(layout.lastBonfire.pointer)
        if (gameMan.isNull) return TaskStep.Failed("${layout.lastBonfire.pointer} could not be found")
        val owner = memory.addressOf(layout.owner)
        if (owner.isNull) return TaskStep.Failed("${layout.owner} could not be found")
        val function = memory.addressOf(layout.function)
        if (function.isNull) return TaskStep.Failed("${layout.function} could not be found")
        if (!context.writer.aim(gameMan)) return fail(context, "The destination could not be set")

        val call = GameCall(
            target = function,
            label = label,
            registers = buildMap {
                put(layout.ownerRegister, owner.value)
                layout.kindRegister?.let { put(it, GAME_MAN_DESTINATION) }
            },
            stack = if (layout.kindRegister == null) listOf(GAME_MAN_DESTINATION) else emptyList()
        )
        return when (val submission = context.call(call)) {
            is Submission.Refused -> fail(context, submission.reason)
            is Submission.Queued -> {
                sent = submission.id
                advance(true, Phase.Called)
            }
        }
    }

    /** A bonfire clears the point's flag, so a point left from an earlier warp cannot win */
    @GameMutation
    private fun GameWriter.aim(gameMan: Address): Boolean {
        val point = layout.point
        val flag = gameMan + point.flag.toLong()
        armed = flag.takeIf { destination is WarpDestination.Point }
        return when (destination) {
            is WarpDestination.Bonfire ->
                perform(flag, byteArrayOf(0)) &&
                        perform(gameMan + layout.lastBonfire.offset.toLong(), destination.id.toLittleEndian())
            is WarpDestination.Point -> {
                val saved = destination.saved
                perform(gameMan + point.map.toLong(), saved.map.packed.toLittleEndian()) &&
                        perform(gameMan + point.position.toLong(), vec4(saved.position.x, saved.position.y, saved.position.z)) &&
                        perform(gameMan + point.angle.toLong(), vec4(0f, saved.angle, 0f)) &&
                        perform(flag, byteArrayOf(1))
            }
        }
    }

    private fun called(context: TaskContext): TaskStep {
        val id = sent ?: return TaskStep.Failed("The warp was never sent")
        return when (val outcome = context.outcome(id)) {
            null -> TaskStep.Continue
            is CallOutcome.Done -> advance(true, Phase.Leaving).also { armed = null }
            is CallOutcome.TimedOut -> fail(context, "The warp was not run in time")
            is CallOutcome.Failed -> fail(context, outcome.reason)
        }
    }

    /** Clears a point this task set, so the next ordinary load does not spawn there */
    @OptIn(GameMutation::class)
    private fun fail(context: TaskContext, reason: String): TaskStep {
        armed?.let { context.writer.perform(it, byteArrayOf(0)) }
        armed = null
        return TaskStep.Failed(reason)
    }

    private fun advance(ready: Boolean, next: Phase): TaskStep {
        if (ready) phase = next
        return TaskStep.Continue
    }
}

/** Both vectors end in w = 1. The angle triplet is `(x, yaw, z)`, and the game reads only yaw */
private fun vec4(x: Float, y: Float, z: Float): ByteArray =
    littleEndian(4 * Float.SIZE_BYTES).putVec4(Vec4(x, y, z, W)).array()
