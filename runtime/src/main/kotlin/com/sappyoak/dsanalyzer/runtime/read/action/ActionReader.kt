package com.sappyoak.dsanalyzer.runtime.read.action

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.ActionLayout
import com.sappyoak.dsanalyzer.runtime.layout.EsdStateLayout
import com.sappyoak.dsanalyzer.runtime.layout.EventFieldsLayout
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterView
import com.sappyoak.dsanalyzer.runtime.snapshot.action.ActionSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.action.EventActionSnapshot

private const val NONE = -1

/** The damage animation the game stores for no hit */
private const val NO_ANIMATION = 99999

/** Room for a pointer of either width */
private const val POINTER_ROOM = 8

/** A character's ESD machines, the action it plays, the hit it last took, and its event fields */
internal class ActionReader(private val action: ActionLayout) {
    private val events: EventFieldsLayout? = action.events

    private val actions = StructView(action.size)
    private val eventAction = StructView(events?.actionSize ?: Int.SIZE_BYTES)
    private val pointer = StructView(POINTER_ROOM)
    private val word = StructView(Int.SIZE_BYTES)

    fun read(memory: ProcessMemory, character: CharacterView): ActionSnapshot? {
        if (!actions.refresh(memory, action.ctrlOf(memory, character.controller, pointer))) return null
        val fields = character.fields

        return ActionSnapshot(
            esdStates = List(action.slotCount) { stateOf(memory, it) },
            playingAction = actions.int(action.playingAction).takeIf { it != NONE },
            requestedAction = actions.int(action.requestedAction).takeIf { it != NONE },
            esdFlags = actions.int(action.esdFlags),
            ladderState = actions.byte(action.ladderState).toInt().takeIf { it != NONE },
            damageAnimation = actions.int(action.damageAnimation).takeIf { it != NO_ANIMATION },
            damageLevel = actions.int(action.damageLevel),
            eventCommands = events?.let { e -> List(e.commandCount) { fields.int(e.commands + it * Int.SIZE_BYTES) } }.orEmpty(),
            eventMessage = events?.let { fields.int(it.message) },
            eventAction = events?.let { eventActionOf(memory, fields.pointer(it.actionPointer), it) }
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

/** The ActionCtrl the ChrCtrl at [controller] points to, read through [via]; null when either is missing */
internal fun ActionLayout.ctrlOf(memory: ProcessMemory, controller: Address, via: StructView): Address =
    if (controller.isNull) Address.Null else via.pointerAt(memory, controller + fromControl.toLong())
