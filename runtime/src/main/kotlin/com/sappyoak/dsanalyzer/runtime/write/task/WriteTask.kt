package com.sappyoak.dsanalyzer.runtime.write.task

import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.session.WorldPlace
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.runtime.write.mailbox.CallOutcome
import com.sappyoak.dsanalyzer.runtime.write.mailbox.GameCall
import com.sappyoak.dsanalyzer.runtime.write.mailbox.Submission

public sealed interface TaskStep {
    public data object Continue : TaskStep
    public data class Done(public val value: Long = 0) : TaskStep
    public data class Failed(public val reason: String) : TaskStep
}

/**
 * A write that takes more than one sample, such as a warp that waits for the load to end. [step] runs
 * once per sample on the sampling thread until it returns [TaskStep.Done] or [TaskStep.Failed]
 */
public interface WriteTask {
    public val label: String

    /** Tasks with the same key replace each other while they wait */
    public val mergeKey: String? get() = null

    public fun step(context: TaskContext): TaskStep
}

public class TaskContext internal constructor(
    public val memory: GameMemory,
    public val writer: GameWriter,
    /** The world is up and the player can be read, which is when the mailbox sends calls */
    public val inWorld: Boolean,
    public val place: WorldPlace,
    private val submit: (GameCall) -> Submission,
    private val outcomes: Map<Long, CallOutcome>
) {
    public fun call(call: GameCall): Submission = submit(call)

    /** The outcome of a call this task submitted, in the sample it arrived; null while it is pending */
    public fun outcome(id: Long): CallOutcome? = outcomes[id]
}

public data class TaskOutcome(public val id: Long, public val label: String, public val step: TaskStep)
