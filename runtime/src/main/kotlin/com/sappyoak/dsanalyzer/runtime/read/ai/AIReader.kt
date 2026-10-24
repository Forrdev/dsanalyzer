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

    fun read(memory: ProcessMemory, source: AISource): AISnapshot? {
        if (!readThink(memory, source.think, source.owner)) return null
        if (!actions.refresh(memory, source.actions)) return null

        return AISnapshot(
            thinkId = think.int(AIThink.ThinkId),
            targetHandle = targetOf(memory, source),
            requestedAction = actions.int(ActionCtrl.RequestedAction).takeIf { it != NONE },
            playingAction = actions.int(ActionCtrl.PlayingAction).takeIf { it != NONE },
            timers = List(AI_THINK_TIMER_COUNT) { think.float(AIThink.Timers + it * Float.SIZE_BYTES) },
            numbers = List(AI_THINK_NUMBER_COUNT) { think.int(AIThink.Numbers + it * Int.SIZE_BYTES) }
        )
    }

    fun locate(memory: ProcessMemory, character: CharacterRef): AISource? {
        if (character.comManipulator.isNull || character.controller.isNull) return null

        val at = pointerAt(memory, character.comManipulator + ComManipulator.AIThinkPointer.toLong())
        val owner = character.comManipulator + ComManipulator.ThinkOwner.toLong()
        if (at.isNull || !readThink(memory, at, owner)) return null

        val targeting = think.pointer(AIThink.TargetingSystemPointer)
        val actionCtrl = pointerAt(memory, character.controller + ChrCtrl.ActionCtrlPointer.toLong())
        if (targeting.isNull || actionCtrl.isNull) return null

        return AISource(character.handle, at, owner, targeting, actionCtrl)
    }

    fun targetOf(memory: ProcessMemory, source: AISource): Int? {
        var at = pointerAt(memory, source.targeting + TargetingSystem.Known.toLong())
        var special = false
        var hostile: Int? = null
        var hostileScore = Float.NEGATIVE_INFINITY
        var other: Int? = null

        for (step in 0 until NODE_LIMIT) {
            if (at.isNull || !node.refresh(memory, at)) break
            when (node.unsigned(TargetNode.Kind) and TargetNode.KIND_MASK) {
                TargetNode.FORCED -> return handleOfNode()
                TargetNode.SPECIAL -> special = true
                TargetNode.HOSTILE -> if (node.float(TargetNode.Score) > hostileScore) {
                    hostileScore = node.float(TargetNode.Score)
                    hostile = handleOfNode()
                }
                TargetNode.OTHER -> if (other == null) other = handleOfNode()
            }
            at = node.pointer(TargetNode.Next)
        }

        return if (special) null else hostile ?: other
    }

    private fun handleOfNode(): Int? = when (node.int(TargetNode.Shape)) {
        TargetNode.SINGLE -> node.int(TargetNode.SingleHandle)
        TargetNode.DOUBLE -> node.int(TargetNode.DoubleHandle)
        else -> null
    }?.takeIf { it != NONE && it != 0 }

    private fun readThink(memory: ProcessMemory, at: Address, owner: Address): Boolean =
        think.refresh(memory, at) && think.pointer(AIThink.OwnerPointer) == owner

    private fun pointerAt(memory: ProcessMemory, at: Address): Address =
        if (pointer.refresh(memory, at)) pointer.pointer(0) else Address.Null
}
