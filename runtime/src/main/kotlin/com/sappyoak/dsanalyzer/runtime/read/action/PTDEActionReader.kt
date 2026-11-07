package com.sappyoak.dsanalyzer.runtime.read.action

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.ptde.ActionCtrl
import com.sappyoak.dsanalyzer.runtime.ptde.ChrCtrl
import com.sappyoak.dsanalyzer.runtime.ptde.ChrEventAction
import com.sappyoak.dsanalyzer.runtime.ptde.ChrIns
import com.sappyoak.dsanalyzer.runtime.session.action.ActionSnapshot
import com.sappyoak.dsanalyzer.runtime.session.action.EventActionSnapshot

private const val NONE = -1
private const val MACHINE_GROUPS = 2

internal class PTDEActionReader {
    private val actions = StructView(ActionCtrl.RequestedAction + Int.SIZE_BYTES)
    private val eventAction = StructView(ChrEventAction.SIZE)
    private val word = StructView(Int.SIZE_BYTES)

    fun read(memory: ProcessMemory, character: StructView): ActionSnapshot? {
        val controller = character.pointer(ChrIns.ChrCtrlPointer)
        if (controller.isNull) return null

        val actionCtrl = word.pointerAt(memory, controller + ChrCtrl.ActionCtrlPointer.toLong())
        if (actionCtrl.isNull || !actions.refresh(memory, actionCtrl)) return null

        return ActionSnapshot(
            esdStates = List(MACHINE_GROUPS) { stateOf(memory, it) },
            playingAction = actions.int(ActionCtrl.PlayingAction).takeIf { it != NONE },
            requestedAction = actions.int(ActionCtrl.RequestedAction).takeIf { it != NONE },
            esdFlags = actions.int(ActionCtrl.EsdFlags),
            ladderState = actions.byte(ActionCtrl.LadderState).toInt().takeIf { it != NONE },
            damageAnimation = actions.int(ActionCtrl.DamageAnimation).takeIf { it != ActionCtrl.NO_ANIMATION },
            damageLevel = actions.int(ActionCtrl.DamageLevel),
            eventCommands = List(ChrIns.EventCommandCount) {
                character.int(ChrIns.EventCommands + it * Int.SIZE_BYTES)
            },
            eventMessage = character.int(ChrIns.EventMessage),
            eventAction = eventActionOf(memory, character.pointer(ChrIns.EventActionPointer))
        )
    }
    private fun stateOf(memory: ProcessMemory, group: Int): Int? {
        val slot = ActionCtrl.MachineSlots + group * ActionCtrl.MachineSlotStride
        if (actions.byte(slot + ActionCtrl.MachineSlotEnabled).toInt() == 0) return null

        val machine = actions.pointer(slot)
        if (machine.isNull) return null

        val state = word.pointerAt(memory, machine + ActionCtrl.MachineState.toLong())
        if (state.isNull || !word.refresh(memory, state)) return null
        return word.int(0)
    }

    private fun eventActionOf(memory: ProcessMemory, at: Address): EventActionSnapshot? {
        if (at.isNull || !eventAction.refresh(memory, at)) return null
        return EventActionSnapshot(
            type = eventAction.int(ChrEventAction.Type),
            first = eventAction.int(ChrEventAction.First),
            second = eventAction.int(ChrEventAction.Second)
        )
    }
}
