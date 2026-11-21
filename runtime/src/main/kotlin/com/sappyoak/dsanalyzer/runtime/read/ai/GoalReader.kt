package com.sappyoak.dsanalyzer.runtime.read.ai

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.AILayout
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.snapshot.ai.GoalSnapshot

private const val DEPTH_LIMIT = 8
private const val SUBGOAL_LIMIT = 16
private const val EXTRA_PARAM_LIMIT = 8
private const val NAME_LIMIT = 64

private data class Kind(val id: Int, val name: String)

internal class GoalReader(layout: AILayout) {
    private val layout = layout.goal
    private val types = layout.goalType
    private val width = layout.pointerWidth

    private val goal = StructView(this.layout.size)
    private val type = StructView(types.size)
    private val name = StructView(NAME_LIMIT)
    private val pointer = StructView(POINTER_ROOM)
    private val param = StructView(Float.SIZE_BYTES)

    /** Goal types never change while a world is loaded, so each is read once */
    private val kinds = HashMap<Address, Kind>()

    fun read(
        memory: ProcessMemory,
        top: Address,
        think: Address
    ): GoalSnapshot? = node(memory, top, think, 0)

    private fun node(
        memory: ProcessMemory,
        at: Address,
        think: Address,
        depth: Int
    ): GoalSnapshot? {
        if (depth >= DEPTH_LIMIT || !goal.refresh(memory, at)) return null
        if (goal.pointer(layout.think) != think || goal.pointer(layout.self) != at) return null

        val kind = kindOf(memory, goal.pointer(layout.type)) ?: return null
        val life = goal.float(layout.life).takeIf { it >= 0f }
        val params = paramsOf(memory)
        val children = subgoalsOf(memory)

        return GoalSnapshot(
            id = kind.id,
            name = kind.name,
            life = life,
            params = params,
            subgoals = children.mapNotNull { node(memory, it, think, depth + 1) }
        )
    }

    private fun paramsOf(memory: ProcessMemory): List<Float> {
        val inline = List(layout.paramCount) { goal.float(layout.params + it * Float.SIZE_BYTES) }
        val begin = goal.pointer(layout.extraParamsBegin)
        val count = ((goal.pointer(layout.extraParamsEnd).value - begin.value) / Float.SIZE_BYTES).toInt()
        if (begin.isNull || count <= 0) return inline

        val extra = (0 until minOf(count, EXTRA_PARAM_LIMIT)).mapNotNull { i ->
            if (param.refresh(memory, begin + (i * Float.SIZE_BYTES).toLong())) param.float(0) else null
        }

        return inline + extra
    }

    private fun subgoalsOf(memory: ProcessMemory): List<Address> {
        val map = goal.pointer(layout.subgoalMap)
        val mapSize = goal.int(layout.subgoalMapSize)
        val offset = goal.int(layout.subgoalOffset)
        val count = goal.int(layout.subgoalCount)
        if (map.isNull || mapSize <= 0 || count <= 0 || offset < 0) return emptyList()

        return (0 until minOf(count, SUBGOAL_LIMIT)).mapNotNull { k ->
            val index = offset + k
            val block = pointer.pointerAt(memory, map + ((index / layout.dequeBlock) % mapSize * width).toLong())
            if (block.isNull) null
            else pointer.pointerAt(memory, block + (index % layout.dequeBlock * width).toLong()).takeIf { !it.isNull }
        }
    }

    private fun kindOf(memory: ProcessMemory, at: Address): Kind? {
        if (at.isNull) return null
        kinds[at]?.let { return it }
        if (!type.refresh(memory, at)) return null

        val length = type.int(types.nameLength).coerceIn(0, NAME_LIMIT)
        val text = if (type.int(types.nameCapacity) < types.inlineCapacity) {
            type.ascii(types.name, minOf(length, types.inlineCapacity))
        } else {
            if (!name.refresh(memory, type.pointer(types.name))) return null
            name.ascii(0, length)
        }

        return Kind(type.int(types.id), text).also { kinds[at] = it }
    }
}

