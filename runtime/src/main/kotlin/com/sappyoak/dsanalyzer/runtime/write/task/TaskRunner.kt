package com.sappyoak.dsanalyzer.runtime.write.task

import com.sappyoak.dsanalyzer.runtime.write.mailbox.QUEUE_LIMIT
import com.sappyoak.dsanalyzer.runtime.write.mailbox.Submission
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeMark
import kotlin.time.TimeSource

private val DEFAULT_TIMEOUT: Duration = 30.seconds

private class Running(val id: Long, val task: WriteTask) {
    var startedAt: TimeMark? = null
}

/** Runs [WriteTask]s, one step each per sample. [start] is safe from any thread */
internal class TaskRunner(private val timeout: Duration = DEFAULT_TIMEOUT) {
    private val waiting = ConcurrentLinkedQueue<Running>()
    private val replaced = ConcurrentLinkedQueue<Running>()
    private val count = AtomicInteger(0)
    private val running = mutableListOf<Running>()
    private val ids = AtomicLong(1)

    fun start(task: WriteTask): Submission {
        task.mergeKey?.let { key -> waiting.removeIf { it.task.mergeKey == key && replaced.add(it) } }
        if (count.get() - replaced.size >= QUEUE_LIMIT) return Submission.Refused("$QUEUE_LIMIT tasks are already waiting or running")
        val id = ids.andIncrement
        waiting.add(Running(id, task))
        count.incrementAndGet()
        return Submission.Queued(id)
    }

    fun step(context: TaskContext): List<TaskOutcome> {
        val outcomes = mutableListOf<TaskOutcome>()
        while (true) {
            val gone = replaced.poll() ?: break
            outcomes.add(TaskOutcome(gone.id, gone.task.label, TaskStep.Failed("Replaced by a newer request")))
            count.decrementAndGet()
        }
        while (true) {
            running += waiting.poll()?.also { it.startedAt = TimeSource.Monotonic.markNow() } ?: break
        }

        val each = running.iterator()
        while (each.hasNext()) {
            val run = each.next()
            val step = if ((run.startedAt?.elapsedNow() ?: Duration.ZERO) > timeout) {
                TaskStep.Failed("Timed out")
            } else {
                runCatching { run.task.step(context) }.getOrElse { TaskStep.Failed(it.message ?: it.toString()) }
            }

            if (step != TaskStep.Continue) {
                outcomes.add(TaskOutcome(run.id, run.task.label, step))
                each.remove()
                count.decrementAndGet()
            }
        }

        return outcomes
    }

    fun cancelAll() {
        waiting.clear()
        replaced.clear()
        running.clear()
        count.set(0)
    }
}
