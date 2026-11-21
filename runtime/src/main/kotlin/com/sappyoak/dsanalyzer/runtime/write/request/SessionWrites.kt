package com.sappyoak.dsanalyzer.runtime.write.request

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.atomic.AtomicLong

import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.PlayerGameDataLayout
import com.sappyoak.dsanalyzer.runtime.layout.WriteLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.snapshot.WorldPlace
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteOutcome
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteResult
import com.sappyoak.dsanalyzer.runtime.write.CheatWriter
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.runtime.write.UndoResult
import com.sappyoak.dsanalyzer.runtime.write.mailbox.Mailbox
import com.sappyoak.dsanalyzer.runtime.write.mailbox.RemoteMailbox
import com.sappyoak.dsanalyzer.runtime.write.mailbox.Submission
import com.sappyoak.dsanalyzer.runtime.write.task.TaskContext
import com.sappyoak.dsanalyzer.runtime.write.task.TaskRunner
import com.sappyoak.dsanalyzer.runtime.write.task.TaskStep
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask

private val logger = KotlinLogging.logger { }

/**
 * Applies each submitted request and keeps its ticket until it settles. Everything here runs on the
 * sampling thread: requests arrive through [WriteRequests], results leave through [takeResults]
 */
internal class SessionWrites(
    private val layout: WriteLayout,
    player: PlayerGameDataLayout,
    private val writer: GameWriter
) {
    private val callIds = AtomicLong(1)
    private val mailbox = Mailbox(layout.mailbox, writer, callIds)
    private val remote = RemoteMailbox(layout.mailbox.arch, writer, callIds)
    private val tasks = TaskRunner()
    private val requestTasks = RequestTasks(layout, player)
    private val cheats = CheatWriter(layout)

    /** Each running task's ticket and label, by the task runner's own id */
    private val pending = HashMap<Long, Pending>()
    private val settled = mutableListOf<WriteResult>()

    fun apply(ticket: Long, request: WriteRequest, memory: GameMemory, revertAll: () -> Int) {
        when (request) {
            is WriteRequest.SetCheat -> settle(
                ticket, request.label,
                if (cheats.set(request.flag, request.on, memory, writer)) WriteOutcome.Done()
                else WriteOutcome.Failed("This edition cannot write that flag")
            )
            is WriteRequest.Undo -> undo(ticket, request)
            WriteRequest.RevertAll -> settle(ticket, request.label, WriteOutcome.Done(revertAll().toLong()))
            else -> start(ticket, checkNotNull(requestTasks.taskFor(request)))
        }
    }

    fun afterSample(memory: GameMemory, module: AddressRange, inWorld: Boolean, place: WorldPlace) {
        val arrived = mailbox.poll(memory.memory, module, inWorld) + remote.poll(memory.memory, module, inWorld)
        val context = TaskContext(
            memory, writer, inWorld, place, layout.mailbox.arch,
            mailbox::submit, remote::submit, arrived.associateBy { it.id }
        )
        tasks.step(context).forEach { outcome ->
            pending.remove(outcome.id)?.let { settle(it.ticket, outcome.label, outcome.step.asOutcome()) }
        }
    }

    /** What settled since the last sample, each reported once */
    fun takeResults(): List<WriteResult> = settled.toList().also { settled.clear() }

    fun cancelAll(memory: ProcessMemory) {
        tasks.cancelAll()
        mailbox.cancelAll(memory)
        remote.cancelAll(memory)
        pending.values.forEach { settle(it.ticket, it.label, WriteOutcome.Failed("Every write was reverted")) }
        pending.clear()
    }

    private fun start(ticket: Long, task: WriteTask) {
        when (val submission = tasks.start(task)) {
            is Submission.Queued -> pending[submission.id] = Pending(ticket, task.label)
            is Submission.Refused -> settle(ticket, task.label, WriteOutcome.Failed(submission.reason))
        }
    }

    /** An undo whose inverse is a task starts that task, and settles when it does */
    private fun undo(ticket: Long, request: WriteRequest.Undo) {
        when (val result = writer.undo(request.id, request.force)) {
            is UndoResult.Started -> start(ticket, result.task)
            UndoResult.Done -> settle(ticket, request.label, WriteOutcome.Done())
            UndoResult.Stale -> refuse(ticket, request, "The game has changed it since; force to undo")
            UndoResult.Missing -> refuse(ticket, request, "No such write in the history")
            UndoResult.NotUndoable -> refuse(ticket, request, "It cannot be undone")
            UndoResult.Failed -> refuse(ticket, request, "The old value could not be written back")
        }
    }

    private fun refuse(ticket: Long, request: WriteRequest, reason: String) =
        settle(ticket, request.label, WriteOutcome.Failed(reason))

    private fun settle(ticket: Long, label: String, outcome: WriteOutcome) {
        settled += WriteResult(ticket, label, outcome)
        if (outcome is WriteOutcome.Failed) logger.atWarn {
            message = "Write failed"
            payload = mapOf("ticket" to ticket, "write" to label, "reason" to outcome.reason)
        } else logger.atInfo {
            message = "Write done"
            payload = mapOf("ticket" to ticket, "write" to label)
        }
    }
}

private class Pending(val ticket: Long, val label: String)

private fun TaskStep.asOutcome(): WriteOutcome = when (this) {
    is TaskStep.Done -> WriteOutcome.Done(value)
    is TaskStep.Failed -> WriteOutcome.Failed(reason)
    TaskStep.Continue -> WriteOutcome.Failed("Reported before it finished")
}
