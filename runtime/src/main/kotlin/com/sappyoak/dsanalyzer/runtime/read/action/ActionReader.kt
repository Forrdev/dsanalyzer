package com.sappyoak.dsanalyzer.runtime.read.action

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.EsdStateLayout
import com.sappyoak.dsanalyzer.runtime.layout.EventFieldsLayout
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.session.action.ActionSnapshot
import com.sappyoak.dsanalyzer.runtime.session.action.EventActionSnapshot

private const val NONE = -1

/** The damage animation the game stores for no hit */
private const val NO_ANIMATION = 99999

/** Room for a pointer of either width */
private const val POINTER_ROOM = 8

/** A character's ESD machines, the action it plays, the hit it last took, and its event fields */
internal class ActionReader(layout: GameLayout) {
    private val action = layout.action
    private val chrControl = layout.characters.chr.control
    private val events: EventFieldsLayout? = action.events

    private val actions = StructView(action.size)
    private val eventAction = StructView(events?.actionSize ?: Int.SIZE_BYTES)
    private val pointer = StructView(POINTER_ROOM)
    private val word = StructView(Int.SIZE_BYTES)

    fun read(memory: ProcessMemory, character: StructView): ActionSnapshot? {
        val controller = character.pointer(chrControl)
        if (controller.isNull) return null

        val actionCtrl = pointer.pointerAt(memory, controller + action.fromControl.toLong())
        if (actionCtrl.isNull || !actions.refresh(memory, actionCtrl)) return null

        return ActionSnapshot(
            esdStates = List(action.slotCount) { stateOf(memory, it) },
            playingAction = actions.int(action.playingAction).takeIf { it != NONE },
            requestedAction = actions.int(action.requestedAction).takeIf { it != NONE },
            esdFlags = actions.int(action.esdFlags),
            ladderState = actions.byte(action.ladderState).toInt().takeIf { it != NONE },
            damageAnimation = actions.int(action.damageAnimation).takeIf { it != NO_ANIMATION },
            damageLevel = actions.int(action.damageLevel),
            eventCommands = events?.let { e -> List(e.commandCount) { character.int(e.commands + it * Int.SIZE_BYTES) } }.orEmpty(),
            eventMessage = events?.let { character.int(it.message) },
            eventAction = events?.let { eventActionOf(memory, character.pointer(it.actionPointer), it) }
        )
    }

    private fun stateOf(memory: ProcessMemory, group: Int): Int? {
        val slot = action.slots + group * action.slotStride
        if (actions.byte(slot + action.slotEnabled).toInt() == 0) return null

        return when (val state = action.state) {
            is EsdStateLayout.Inline -> actions.int(slot + state.offset)
            is EsdStateLayout.ThroughMachine -> {
                val machine = actions.pointer(slot)
                if (machine.isNull) return null
                val current = pointer.pointerAt(memory, machine + state.offset.toLong())
                if (current.isNull || !word.refresh(memory, current)) null else word.int(0)
            }
        }
    }

    private fun eventActionOf(memory: ProcessMemory, at: Address, fields: EventFieldsLayout): EventActionSnapshot? {
        if (at.isNull || !eventAction.refresh(memory, at)) return null
        return EventActionSnapshot(
            type = eventAction.int(fields.actionType),
            first = eventAction.int(fields.actionFirst),
            second = eventAction.int(fields.actionSecond)
        )
    }
}
