package com.sappyoak.dsanalyzer.runtime.read.ai

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.ptde.Goal
import com.sappyoak.dsanalyzer.runtime.ptde.GoalType
import com.sappyoak.dsanalyzer.runtime.session.ai.GoalSnapshot

private const val DEPTH_LIMIT = 8
private const val SUBGOAL_LIMIT = 16
private const val EXTRA_PARAM_LIMIT = 8
private const val NAME_LIMIT = 64

private data class Kind(val id: Int, val name: String)

internal class GoalReader {
    private val goal = StructView(Goal.SIZE)
    private val type = StructView(GoalType.SIZE)
    private val name = StructView(NAME_LIMIT)
    private val pointer = StructView(Int.SIZE_BYTES)
    private val param = StructView(Float.SIZE_BYTES)

    /** Goal types never change while a world is loaded, so each is read once */
    private val kinds = HashMap<Address, Kind>()

    fun read(
        memory: ProcessMemory,
        top: Address,
        think: Address
    ): GoalSnapshot? = node(memory, top, think, 0)

    fun forget() {
        kinds.clear()
    }

    private fun node(
        memory: ProcessMemory,
        at: Address,
        think: Address,
        depth: Int
    ): GoalSnapshot? {
        if (depth >= DEPTH_LIMIT || !goal.refresh(memory, at)) return null
        if (goal.pointer(Goal.ThinkPointer) != think || goal.pointer(Goal.SelfPointer) != at) return null

        val kind = kindOf(memory, goal.pointer(Goal.TypePointer)) ?: return null
        val life = goal.float(Goal.Life).takeIf { it >= 0f }
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
        val inline = List(Goal.PARAM_COUNT) { goal.float(Goal.Params + it * Float.SIZE_BYTES) }
        val begin = goal.pointer(Goal.ExtraParamsBegin)
        val count = ((goal.pointer(Goal.ExtraParamsEnd).value - begin.value) / Float.SIZE_BYTES).toInt()
        if (begin.isNull || count <= 0) return inline

        val extra = (0 until minOf(count, EXTRA_PARAM_LIMIT)).mapNotNull { i ->
            if (param.refresh(memory, begin + (i * Float.SIZE_BYTES).toLong())) param.float(0) else null
        }

        return inline + extra
    }

    private fun subgoalsOf(memory: ProcessMemory): List<Address> {
        val map = goal.pointer(Goal.SubgoalMap)
        val mapSize = goal.int(Goal.SubgoalMapSize)
        val offset = goal.int(Goal.SubgoalOffset)
        val count = goal.int(Goal.SubgoalCount)
        if (map.isNull || mapSize <= 0 || count <= 0 || offset < 0) return emptyList()

        return (0 until minOf(count, SUBGOAL_LIMIT)).mapNotNull { k ->
            val index = offset + k
            val block = pointer.pointerAt(memory, map + ((index / Goal.DEQUE_BLOCK) % mapSize * Int.SIZE_BYTES).toLong())
            if (block.isNull) null
            else pointer.pointerAt(memory, block + (index % Goal.DEQUE_BLOCK * Int.SIZE_BYTES).toLong()).takeIf { !it.isNull }
        }
    }

    private fun kindOf(memory: ProcessMemory, at: Address): Kind? {
        if (at.isNull) return null
        kinds[at]?.let { return it }
        if (!type.refresh(memory, at)) return null

        val length = type.int(GoalType.NameLength).coerceIn(0, NAME_LIMIT)
        val text = if (type.int(GoalType.NameCapacity) < GoalType.INLINE_CAPACITY) {
            type.ascii(GoalType.Name, minOf(length, GoalType.INLINE_CAPACITY))
        } else {
            if (name.refresh(memory, type.pointer(GoalType.Name))) return null
            name.ascii(0, length)
        }

        return Kind(type.int(GoalType.Id), text).also { kinds[at] = it }
    }
}
