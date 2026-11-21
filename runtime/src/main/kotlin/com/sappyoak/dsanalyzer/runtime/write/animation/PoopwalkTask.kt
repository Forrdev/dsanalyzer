package com.sappyoak.dsanalyzer.runtime.write.animation

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.pointerAt
import com.sappyoak.dsanalyzer.runtime.layout.AnimRequestLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimationLayout
import com.sappyoak.dsanalyzer.runtime.read.animation.AnimationReader
import com.sappyoak.dsanalyzer.runtime.snapshot.animation.AnimSlotSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.animation.AnimationSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.animation.AnimationTarget
import com.sappyoak.dsanalyzer.runtime.snapshot.animation.SlotState
import com.sappyoak.dsanalyzer.runtime.write.GameMutation
import com.sappyoak.dsanalyzer.runtime.write.task.TaskContext
import com.sappyoak.dsanalyzer.runtime.write.task.TaskStep
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask

/** How long inducing waits for the player to start a one-shot animation: ten seconds of samples */
private const val WAIT_SAMPLES = 300

/** What the stale-handle teardown needs: the slot's serial no longer matching the handle a channel holds */
private const val SERIAL_BUMP = 2
private const val SERIAL_MASK = 0xFFFF

/**
 * [induce] waits for a one-shot animation a channel holds, then moves its slot's serial on, so the teardown that
 * stops it skips the stop and drops the handle, leaving the slot playing with nothing able to reach it.
 * Curing zeroes every orphan's weight scale, which lets the pool's steal pass take the slot back. Both are one-shot pokes: nothing to undo
 */
@OptIn(GameMutation::class)
internal class PoopwalkTask(
    private val induce: Boolean,
    private val pool: AnimationLayout,
    requests: AnimRequestLayout
) : WriteTask {
    override val label: String = if (induce) "Induce a poopwalk" else "Cure the poopwalk"
    override val mergeKey: String = "poopwalk"

    private val reader = AnimationReader(pool, requests)
    private var waited = 0

    override fun step(context: TaskContext): TaskStep {
        if (!context.inWorld) return TaskStep.Failed("The poopwalk needs the player in the world")
        val source = reader.player(context.memory)
            ?: return TaskStep.Failed("The player's animation could not be found")
        val animation = reader.read(context.memory.memory, source, AnimationTarget.Player)
            ?: return TaskStep.Failed("The player's animation could not be read")
        val slots = context.memory.memory.pointerAt(source.pool + pool.slotsPointer.toLong())?.takeUnless { it.isNull }
            ?: return TaskStep.Failed("The player's animation slots could not be found")

        return if (induce) induce(context, animation, slots) else cure(context, animation, slots)
    }

    private fun induce(context: TaskContext, animation: AnimationSnapshot, slots: Address): TaskStep {
        val target = animation.slots.firstOrNull { it.isHeldOneShot && it !in animation.orphans }
            ?: return if (++waited < WAIT_SAMPLES) TaskStep.Continue
            else TaskStep.Failed("No one-shot animation started within ten seconds; roll or attack after asking")

        val serial = (target.handle.serial + SERIAL_BUMP) and SERIAL_MASK
        val at = slotAt(slots, target) + pool.slot.serial.toLong()
        val bytes = byteArrayOf(serial.toByte(), (serial shr Byte.SIZE_BITS).toByte())
        return if (context.writer.perform(at, bytes)) TaskStep.Done(target.index.toLong())
        else TaskStep.Failed("The slot's serial could not be written")
    }

    private fun cure(context: TaskContext, animation: AnimationSnapshot, slots: Address): TaskStep {
        val cured = animation.orphans.count { orphan ->
            val control = context.memory.memory.pointerAt(slotAt(slots, orphan) + pool.slot.control.toLong())
            control != null && !control.isNull &&
                    context.writer.perform(control + pool.control.weightScale.toLong(), ByteArray(Float.SIZE_BYTES))
        }
        return TaskStep.Done(cured.toLong())
    }

    private fun slotAt(slots: Address, slot: AnimSlotSnapshot): Address =
        slots + (slot.index * pool.slotStride).toLong()
}

private val AnimSlotSnapshot.isHeldOneShot: Boolean
    get() = state == SlotState.Playing && channel != null && control?.looping == false
