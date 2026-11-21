package com.sappyoak.dsanalyzer.runtime.write.task

import com.sappyoak.dsanalyzer.runtime.layout.FieldLayout
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteKey

/** A kept, undoable write of one field, run on the sampling thread so nothing else reads it half done */
public class FieldEditTask internal constructor(
    override val label: String,
    private val key: WriteKey,
    private val field: FieldLayout,
    private val bytes: ByteArray
) : WriteTask {
    override val mergeKey: String get() = key.name

    override fun step(context: TaskContext): TaskStep {
        val base = context.memory.addressOf(field.pointer)
        if (base.isNull) return TaskStep.Failed("${field.pointer} could not be found")
        val at = base + field.offset.toLong()
        val written = context.writer.edit(key, at, field.pointer.lifetime, bytes, label)
        return if (written) TaskStep.Done() else TaskStep.Failed("$label could not be written")
    }
}
