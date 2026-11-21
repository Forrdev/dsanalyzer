package com.sappyoak.dsanalyzer.runtime.read.ai

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.AILayout
import com.sappyoak.dsanalyzer.runtime.layout.ActionLayout
import com.sappyoak.dsanalyzer.runtime.layout.TargetKinds
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.read.action.ctrlOf
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterRef
import com.sappyoak.dsanalyzer.runtime.snapshot.ai.AISnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.ai.AITarget

private const val NONE = -1

/** Further than any list the AI keeps, so a list that has come apart mid-read still ends */
private const val NODE_LIMIT = 64

/** Room for a pointer of either width */
internal const val POINTER_ROOM = 8

internal data class AISource(
    val handle: Int,
    val think: Address,
    val owner: Address,
    val targeting: Address,
    val actions: Address
)

internal class AIReader(ai: AILayout, private val action: ActionLayout) {
    private val think = ai.think
    private val targeting = ai.targeting

    private val pointer = StructView(POINTER_ROOM)
    private val thinkView = StructView(think.size)
    private val actions = StructView(action.size)
    private val node = StructView(targeting.nodeSize)
    private val goals = GoalReader(ai)

    fun read(memory: ProcessMemory, source: AISource): AISnapshot? {
        if (!readThink(memory, source.think, source.owner)) return null
        if (!actions.refresh(memory, source.actions)) return null

        return AISnapshot(
            thinkId = thinkView.int(think.thinkId),
            target = targetOf(memory, source),
            requestedAction = actions.int(action.requestedAction).takeIf { it != NONE },
            playingAction = actions.int(action.playingAction).takeIf { it != NONE },
            playingTime = actions.float(action.playingTime),
            timers = List(think.timerCount) { thinkView.float(think.timers + it * Float.SIZE_BYTES) },
            numbers = List(think.numberCount) { thinkView.int(think.numbers + it * Int.SIZE_BYTES) },
            goals = goals.read(memory, thinkView.pointer(think.topGoal), source.think)
        )
    }

    fun locate(memory: ProcessMemory, character: CharacterRef): AISource? {
        if (character.comManipulator.isNull || character.controller.isNull) return null

        val at = pointer.pointerAt(memory, character.comManipulator + think.fromManipulator.toLong())
        val owner = character.comManipulator + think.manipulatorOwner.toLong()
        if (at.isNull || !readThink(memory, at, owner)) return null

        val targets = thinkView.pointer(think.targeting)
        val actionCtrl = action.ctrlOf(memory, character.controller, pointer)
        if (targets.isNull || actionCtrl.isNull) return null

        return AISource(character.handle, at, owner, targets, actionCtrl)
    }

    fun targetOf(memory: ProcessMemory, source: AISource): AITarget {
        var at = pointer.pointerAt(memory, source.targeting + targeting.known.toLong())
        var alert: AITarget? = null
        var combat: AITarget? = null
        var weight = Float.NEGATIVE_INFINITY
        var sound: AITarget? = null

        for (step in 0 until NODE_LIMIT) {
            if (at.isNull || !node.refresh(memory, at)) break
            when (node.unsigned(targeting.nodeKind) and TargetKinds.KIND_MASK) {
                TargetKinds.URGENT_SOUND -> return entryTarget()
                TargetKinds.ALERT_SOUND -> if (alert == null) alert = entryTarget()
                TargetKinds.COMBAT -> if (node.float(targeting.nodeWeight) > weight) {
                    weight = node.float(targeting.nodeWeight)
                    combat = entryTarget()
                }
                TargetKinds.SOUND -> if (sound == null) sound = entryTarget()
            }
            at = node.pointer(targeting.nodeNext)
        }

        return alert ?: combat ?: sound ?: AITarget.None
    }

    private fun entryTarget(): AITarget = when (node.int(targeting.nodeShape)) {
        TargetKinds.CHARACTER -> node.int(targeting.nodeHandle).let { if (it == NONE) AITarget.None else AITarget.Character(it) }
        TargetKinds.PLACE -> AITarget.Sound
        else -> AITarget.None
    }

    private fun readThink(memory: ProcessMemory, at: Address, owner: Address): Boolean =
        thinkView.refresh(memory, at) && thinkView.pointer(think.owner) == owner
}
