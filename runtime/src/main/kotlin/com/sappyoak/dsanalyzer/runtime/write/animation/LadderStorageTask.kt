package com.sappyoak.dsanalyzer.runtime.write.animation


import com.sappyoak.dsanalyzer.native.memory.pointerAt
import com.sappyoak.dsanalyzer.runtime.layout.EventActionLayout
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteKey
import com.sappyoak.dsanalyzer.runtime.write.task.TaskContext
import com.sappyoak.dsanalyzer.runtime.write.task.TaskStep
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask
import com.sappyoak.dsanalyzer.shared.binary.littleEndian

private val KEY = WriteKey("ladder-storage")
private const val LADDER_CLIMB = 1
private const val NONE = 0

/**
 * Puts the player into ladder storage at [rung] of [count], or takes it out when [rung] is null. A kept
 * edit, so undoing it puts back whatever the record held. Any stagger ends storage in game
 */
internal class LadderStorageTask(
    private val rung: Int?,
    private val count: Int,
    private val layout: EventActionLayout?
) : WriteTask {
    override val label: String = if (rung == null) "Leave ladder storage" else "Ladder storage, rung $rung of $count"
    override val mergeKey: String = KEY.name

    override fun step(context: TaskContext): TaskStep {
        val layout = layout ?: return TaskStep.Failed("This edition's event-action record is not mapped")
        if (!context.inWorld) return TaskStep.Failed("Ladder storage needs the player in the world")

        val chr = context.memory.addressOf(layout.chr)
        if (chr.isNull) return TaskStep.Failed("The player could not be found")
        val record = context.memory.memory.pointerAt(chr + layout.record.toLong())?.takeUnless { it.isNull }
            ?: return TaskStep.Failed("The player has no event-action record")

        val start = layout.type
        val values = littleEndian(layout.second + Int.SIZE_BYTES - start)
            .putInt(0, if (rung == null) NONE else LADDER_CLIMB)
            .putInt(layout.first - start, rung ?: 0)
            .putInt(layout.second - start, if (rung == null) 0 else count)
        val written = context.writer.edit(KEY, record + start.toLong(), layout.chr.lifetime, values.array(), label)
        return if (written) TaskStep.Done() else TaskStep.Failed("The event-action record could not be written")
    }
}
