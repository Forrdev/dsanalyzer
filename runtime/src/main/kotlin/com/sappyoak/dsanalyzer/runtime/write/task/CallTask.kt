package com.sappyoak.dsanalyzer.runtime.write.task

import com.sappyoak.dsanalyzer.runtime.write.mailbox.CallOutcome
import com.sappyoak.dsanalyzer.runtime.write.mailbox.GameCall
import com.sappyoak.dsanalyzer.runtime.write.mailbox.Submission

/** One game call as a task: submits it, then waits for its outcome. [remote] runs it on the tool's own thread */
public class CallTask(private val call: GameCall, private val remote: Boolean = false) : WriteTask {
    override val label: String get() = call.label
    private var id: Long? = null

    override fun step(context: TaskContext): TaskStep {
        val sent = id ?: return when (val submission = submit(context)) {
            is Submission.Queued -> TaskStep.Continue.also { id = submission.id }
            is Submission.Refused -> TaskStep.Failed(submission.reason)
        }

        return when (val outcome = context.outcome(sent)) {
            null -> TaskStep.Continue
            is CallOutcome.Done -> TaskStep.Done(outcome.value)
            is CallOutcome.TimedOut -> TaskStep.Failed("${call.label} was not run in time")
            is CallOutcome.Failed -> TaskStep.Failed(outcome.reason)
        }
    }

    private fun submit(context: TaskContext): Submission = if (remote) context.callRemote(call) else context.call(call)
}

