package com.sappyoak.dsanalyzer.tas

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.inject.Hook
import com.sappyoak.dsanalyzer.tas.Mailbox.Field
import com.sappyoak.dsanalyzer.tas.Mailbox.Slot

private val NEVER = GateTrigger(Address.Null, emptyList(), mask = 0, value = 1)

/**
 * A condition the gate checks at the top of every frame: [base] walked through [offsets] as a
 * pointer chain, then `(value & mask) == this.value`, inverted by [negate]. A null link is false
 */
public class GateTrigger(
    public val base: Address,
    public val offsets: List<Int>,
    public val mask: Int = -1,
    public val value: Int = 0,
    public val negate: Boolean = false
) {
    init {
        require(offsets.size <= MAX_DEPTH) { "A trigger walks at most $MAX_DEPTH offsets" }
    }

    public companion object {
        public const val MAX_DEPTH: Int = 4

        /** True while the whole chain resolves to something non-null */
        public fun present(base: Address, offsets: List<Int>): GateTrigger = GateTrigger(base, offsets, negate = true)
    }
}

/**
 * Holds the game at the top of its main loop: counts frames, pauses, steps, and pauses itself the
 * frame an armed [GateTrigger] becomes true
 *
 * The stub runs on the game's main thread at the entry of `GameLoop_StepFrame`. The window's
 * messages are pumped on another thread, so a held game stays responsive
 */
public class FrameGate internal constructor(private val mailbox: Mailbox, private val hook: Hook) : AutoCloseable {
    /** Frames run since the gate was installed */
    public val frame: Int? get() = mailbox.read(Field.Frame)

    public val isPaused: Boolean get() = (mailbox.read(Field.Paused) ?: 0) != 0

    /** Held because an armed trigger fired, and not yet released */
    public val isTriggered: Boolean get() = mailbox.read(Field.TriggerFired) != mailbox.read(Field.TriggerRelease)

    /** Holds the game, dropping any steps granted earlier; a triggered hold becomes an ordinary pause */
    @Synchronized
    public fun pause(): Boolean = dropSteps() && mailbox.write(Field.Paused, 1) && release()

    @Synchronized
    public fun resume(): Boolean = mailbox.write(Field.Paused, 0) && release()

    /** Lets [frames] more frames run while held */
    @Synchronized
    public fun step(frames: Int = 1): Boolean {
        require(frames > 0) { "Step at least one frame" }
        val granted = mailbox.read(Field.StepsGranted) ?: return false
        return mailbox.write(Field.StepsGranted, granted + frames)
    }

    /**
     * Holds the game on the first frame [trigger] **becomes** true; one already true when armed has
     * to go false first. Fires once per arm. The condition goes into the bank the stub is not
     * reading, and the arm's one write switches to it, so no frame sees it half written
     */
    @Synchronized
    public fun pauseWhen(trigger: GateTrigger): Boolean = dropSteps() && arm(trigger)

    /** Arms a condition that can never hold: `x & 0 == 1` */
    @Synchronized
    public fun disarm(): Boolean = arm(NEVER)

    private fun arm(trigger: GateTrigger): Boolean {
        val next = (mailbox.read(Field.TriggerArm) ?: return false) + 1
        val offsets = Slot.Offsets.mapIndexed { i, slot -> slot to (trigger.offsets.getOrNull(i) ?: 0) }
        return mailbox.write(next, Slot.Base, trigger.base.value.toInt()) &&
                mailbox.write(next, Slot.Depth, trigger.offsets.size) &&
                offsets.all { (slot, offset) -> mailbox.write(next, slot, offset) } &&
                mailbox.write(next, Slot.Mask, trigger.mask) &&
                mailbox.write(next, Slot.Value, trigger.value) &&
                mailbox.write(next, Slot.Negate, if (trigger.negate) 1 else 0) &&
                mailbox.write(Field.TriggerArm, next)
    }

    /** Releases the game first, so the main thread is never left waiting on a gate that is gone */
    override fun close() {
        disarm()
        resume()
        hook.remove()
    }

    private fun dropSteps(): Boolean {
        val taken = mailbox.read(Field.StepsTaken) ?: return false
        return mailbox.write(Field.StepsGranted, taken)
    }

    private fun release(): Boolean {
        val fired = mailbox.read(Field.TriggerFired) ?: return false
        return mailbox.write(Field.TriggerRelease, fired)
    }
}
