package com.sappyoak.dsanalyzer.runtime.write.flags

import com.sappyoak.dsanalyzer.runtime.layout.CallArch
import com.sappyoak.dsanalyzer.runtime.layout.FlagCallLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.write.found
import com.sappyoak.dsanalyzer.runtime.write.mailbox.GameCall
import com.sappyoak.dsanalyzer.runtime.write.mailbox.conventionalCall
import com.sappyoak.dsanalyzer.runtime.write.task.CallTask
import com.sappyoak.dsanalyzer.runtime.write.task.TaskContext
import com.sappyoak.dsanalyzer.runtime.write.task.TaskStep
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask

private const val FLAG_BIT: Long = 1

/**
 * Sets event flag [flag] to [value], or reads it when [value] is null, through the game's own calls on its
 * logic thread, so whatever the game ties to a flag change happens too. A read is done with 1 or 0
 */
public class EventFlagTask internal constructor(
    private val flag: Int,
    private val value: Boolean?,
    private val layout: FlagCallLayout,
    private val arch: CallArch
) : WriteTask {
    override val label: String get() = if (value == null) "Read flag $flag" else "Set flag $flag to $value"

    private var call: CallTask? = null

    override fun step(context: TaskContext): TaskStep {
        val running = call ?: CallTask(flagCall(context.memory)).also { call = it }
        val step = running.step(context)
        return if (step is TaskStep.Done && value == null) TaskStep.Done(step.value and FLAG_BIT) else step
    }

    private fun flagCall(memory: GameMemory): GameCall {
        val owner = memory.found(layout.owner).value
        val register = layout.ownerRegister
        val operands = listOf(flag.toLong()) + listOfNotNull(value?.let { if (it) 1L else 0L })
        val arguments = if (register == null) listOf(owner) + operands else operands
        val registers = buildMap {
            register?.let { put(it, owner) }
            put(layout.kindRegister, 0L)
        }
        val function = memory.found(if (value == null) layout.get else layout.set)
        return conventionalCall(arch, function, label, arguments, registers)
    }
}
