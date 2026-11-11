package com.sappyoak.dsanalyzer.runtime.write.task

import com.sappyoak.dsanalyzer.runtime.write.mailbox.CallOutcome
import com.sappyoak.dsanalyzer.runtime.write.mailbox.GameCall
import com.sappyoak.dsanalyzer.runtime.write.mailbox.Submission

/** One game call as a task: submits it, then waits for its outcome */
public class CallTask(private val call: GameCall) : WriteTask {
    override val label: String get() = call.label
    private var id: Long? = null

    override fun step(context: TaskContext): TaskStep {
        val sent = id ?: return when (val submission = context.call(call)) {
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
}
