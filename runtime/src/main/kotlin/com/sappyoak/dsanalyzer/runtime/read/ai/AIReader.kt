package com.sappyoak.dsanalyzer.runtime.read.ai

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.ptde.ActionCtrl
import com.sappyoak.dsanalyzer.runtime.ptde.AIThink
import com.sappyoak.dsanalyzer.runtime.ptde.AI_THINK_NUMBER_COUNT
import com.sappyoak.dsanalyzer.runtime.ptde.AI_THINK_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.AI_THINK_TIMER_COUNT
import com.sappyoak.dsanalyzer.runtime.ptde.ChrCtrl
import com.sappyoak.dsanalyzer.runtime.ptde.ComManipulator
import com.sappyoak.dsanalyzer.runtime.ptde.TargetNode
import com.sappyoak.dsanalyzer.runtime.ptde.TargetingSystem
import com.sappyoak.dsanalyzer.runtime.read.CharacterRef
import com.sappyoak.dsanalyzer.runtime.session.ai.AISnapshot
import com.sappyoak.dsanalyzer.runtime.session.ai.AITarget

private const val NONE = -1

/** Further than any list the AI keeps, so a list that has come apart mid-read still ends */
private const val NODE_LIMIT = 64

internal data class AISource(
    val handle: Int,
    val think: Address,
    val owner: Address,
    val targeting: Address,
    val actions: Address
)

internal class AIReader {
    private val pointer = StructView(Int.SIZE_BYTES)
    private val think = StructView(AI_THINK_SIZE)
    private val actions = StructView(ActionCtrl.RequestedAction + Int.SIZE_BYTES)
    private val node = StructView(TargetNode.SIZE)
    private val goals = GoalReader()

    fun read(memory: ProcessMemory, source: AISource): AISnapshot? {
        if (!readThink(memory, source.think, source.owner)) return null
        if (!actions.refresh(memory, source.actions)) return null

        return AISnapshot(
            thinkId = think.int(AIThink.ThinkId),
            target = targetOf(memory, source),
            requestedAction = actions.int(ActionCtrl.RequestedAction).takeIf { it != NONE },
            playingAction = actions.int(ActionCtrl.PlayingAction).takeIf { it != NONE },
            timers = List(AI_THINK_TIMER_COUNT) { think.float(AIThink.Timers + it * Float.SIZE_BYTES) },
            numbers = List(AI_THINK_NUMBER_COUNT) { think.int(AIThink.Numbers + it * Int.SIZE_BYTES) },
            goals = goals.read(memory, think.pointer(AIThink.TopGoalPointer), source.think)
        )
    }

    fun locate(memory: ProcessMemory, character: CharacterRef): AISource? {
        if (character.comManipulator.isNull || character.controller.isNull) return null

        val at = pointer.pointerAt(memory, character.comManipulator + ComManipulator.AIThinkPointer.toLong())
        val owner = character.comManipulator + ComManipulator.ThinkOwner.toLong()
        if (at.isNull || !readThink(memory, at, owner)) return null

        val targeting = think.pointer(AIThink.TargetingSystemPointer)
        val actionCtrl = pointer.pointerAt(memory, character.controller + ChrCtrl.ActionCtrlPointer.toLong())
        if (targeting.isNull || actionCtrl.isNull) return null

        return AISource(character.handle, at, owner, targeting, actionCtrl)
    }

    fun targetOf(memory: ProcessMemory, source: AISource): AITarget {
        var at = pointer.pointerAt(memory, source.targeting + TargetingSystem.Known.toLong())
        var alert: AITarget? = null
        var combat: AITarget? = null
        var weight = Float.NEGATIVE_INFINITY
        var sound: AITarget? = null

        for (step in 0 until NODE_LIMIT) {
            if (at.isNull || !node.refresh(memory, at)) break
            when (node.unsigned(TargetNode.Kind) and TargetNode.KIND_MASK) {
                TargetNode.URGENT_SOUND -> return entryTarget()
                TargetNode.ALERT_SOUND -> if (alert == null) alert = entryTarget()
                TargetNode.COMBAT -> if (node.float(TargetNode.Weight) > weight) {
                    weight = node.float(TargetNode.Weight)
                    combat = entryTarget()
                }
                TargetNode.SOUND -> if (sound == null) sound = entryTarget()
            }
            at = node.pointer(TargetNode.Next)
        }

        return alert ?: combat ?: sound ?: AITarget.None
    }

    fun forget() {
        goals.forget()
    }

    private fun entryTarget(): AITarget = when (node.int(TargetNode.Shape)) {
        TargetNode.CHARACTER -> node.int(TargetNode.Handle).let { if (it == NONE) AITarget.None else AITarget.Character(it) }
        TargetNode.PLACE -> AITarget.Sound
        else -> AITarget.None
    }

    private fun readThink(memory: ProcessMemory, at: Address, owner: Address): Boolean =
        think.refresh(memory, at) && think.pointer(AIThink.OwnerPointer) == owner
}
