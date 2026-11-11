package com.sappyoak.dsanalyzer.runtime.sampling

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.MailboxLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.runtime.write.UndoResult
import com.sappyoak.dsanalyzer.runtime.write.mailbox.CallOutcome
import com.sappyoak.dsanalyzer.runtime.write.mailbox.GameCall
import com.sappyoak.dsanalyzer.runtime.write.mailbox.Mailbox
import com.sappyoak.dsanalyzer.runtime.write.mailbox.Submission
import com.sappyoak.dsanalyzer.runtime.write.task.TaskContext
import com.sappyoak.dsanalyzer.runtime.write.task.TaskOutcome
import com.sappyoak.dsanalyzer.runtime.write.task.TaskRunner
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask

private const val OUTCOME_BUFFER = 256

public class SessionWrites internal constructor(layout: MailboxLayout, private val writer: GameWriter) {
    private val mailbox = Mailbox(layout, writer)
    private val tasks = TaskRunner()
    private val calls = MutableSharedFlow<CallOutcome>(extraBufferCapacity = OUTCOME_BUFFER, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    private val taskSteps = MutableSharedFlow<TaskOutcome>(extraBufferCapacity = OUTCOME_BUFFER, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    public val callOutcomes: SharedFlow<CallOutcome> = calls.asSharedFlow()
    public val taskOutcomes: SharedFlow<TaskOutcome> = taskSteps.asSharedFlow()

    /** Queues a call for the game's logic thread */
    public fun call(call: GameCall): Submission = mailbox.submit(call)

    public fun run(task: WriteTask): Submission = tasks.start(task)

    /** An undo whose inverse is a task starts that task  */
    public fun undo(id: Long, force: Boolean = false): UndoResult {
        val result = writer.undo(id, force)
        if (result is UndoResult.Started) tasks.start(result.task)
        return result
    }

    internal fun afterSample(memory: GameMemory, module: AddressRange, inWorld: Boolean) {
        val arrived = mailbox.poll(memory.memory, module)
        val context = TaskContext(memory, writer, inWorld, mailbox::submit, arrived.associateBy { it.id })
        tasks.step(context).forEach(taskSteps::tryEmit)
        arrived.forEach(calls::tryEmit)
    }

    internal fun cancelAll(memory: ProcessMemory) {
        tasks.cancelAll()
        mailbox.cancelAll(memory)
    }
}
