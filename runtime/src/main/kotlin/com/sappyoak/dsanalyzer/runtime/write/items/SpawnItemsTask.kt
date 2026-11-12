package com.sappyoak.dsanalyzer.runtime.write.items

import com.sappyoak.dsanalyzer.game.world.items.ItemCategory
import com.sappyoak.dsanalyzer.runtime.layout.CallArch
import com.sappyoak.dsanalyzer.runtime.layout.ItemLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.write.addressOf
import com.sappyoak.dsanalyzer.runtime.write.found
import com.sappyoak.dsanalyzer.runtime.write.mailbox.GameCall
import com.sappyoak.dsanalyzer.runtime.write.mailbox.conventionalCall
import com.sappyoak.dsanalyzer.runtime.write.task.CallTask
import com.sappyoak.dsanalyzer.runtime.write.task.TaskContext
import com.sappyoak.dsanalyzer.runtime.write.task.TaskStep
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask

/** The add call's answer, in its low 32 bits, when the inventory has no room */
private const val NO_ROOM: Long = 0xFFFF_FFFFL

/**
 * Adds [count] of item [id] to the player's inventory, then shows the game's item-get popup for it. Both
 * calls run on the remote mailbox. Done with the inventory slot the item went into
 */
public class SpawnItemTask internal constructor(
    private val category: ItemCategory,
    private val id: Int,
    private val count: Int,
    private val layout: ItemLayout,
    private val arch: CallArch
) : WriteTask {
    override val label: String get() = "Spawn ${category.name.lowercase()} $id x$count"

    private var add: CallTask? = null
    private var popup: CallTask? = null
    private var slot: Long = 0

    override fun step(context: TaskContext): TaskStep {
        popup?.let { showing ->
            val step = showing.step(context)
            return if (step is TaskStep.Done) TaskStep.Done(slot) else step
        }
        val adding = add ?: CallTask(addCall(context.memory), remote = true).also { add = it }
        val step = adding.step(context)
        if (step !is TaskStep.Done) return step
        if (step.value and NO_ROOM == NO_ROOM) return TaskStep.Failed("The inventory has no room for $label")
        slot = step.value
        popup = CallTask(popupCall(context.memory), remote = true)
        return TaskStep.Continue
    }

    private fun addCall(memory: GameMemory): GameCall {
        val inventory = memory.addressOf(layout.inventory) ?: error("The inventory could not be found")
        val arguments = listOf(inventory.value, category.code.toLong(), id.toLong(), count.toLong()) + layout.addFlags
        return conventionalCall(arch, memory.found(layout.add), label, arguments)
    }

    private fun popupCall(memory: GameMemory): GameCall {
        val owner = memory.found(layout.popupOwner).value
        val item = listOf(category.code.toLong(), id.toLong(), count.toLong())
        val register = layout.popupOwnerRegister
        val arguments = if (register == null) listOf(owner) + item else item
        val registers = if (register == null) emptyMap() else mapOf(register to owner)
        return conventionalCall(arch, memory.found(layout.popup), "$label popup", arguments, registers)
    }
}
