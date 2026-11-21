package com.sappyoak.dsanalyzer.game.world.flags

import com.sappyoak.dsanalyzer.formats.esd.CommandCall
import com.sappyoak.dsanalyzer.formats.esd.EsdCondition
import com.sappyoak.dsanalyzer.formats.esd.EsdState
import com.sappyoak.dsanalyzer.formats.esd.StateGroup
import com.sappyoak.dsanalyzer.formats.esd.expr.Decoded
import com.sappyoak.dsanalyzer.formats.esd.expr.EsdExpression
import com.sappyoak.dsanalyzer.formats.esd.expr.Op
import com.sappyoak.dsanalyzer.formats.esd.expr.decode

internal const val TALK_BANK = 1
internal const val ADD_TALK_LIST = 19

/** 'SetEventFlag(eventFlagId, newFlagState)' in a talk script; [TALK_FLAG_ON] is the state that sets it */
internal const val SET_TALK_FLAG = 11
internal const val TALK_FLAG_ON = 1

/** 'GetTalkListEntryResult', the slot of the menu entry the player picked */
private const val PICKED_ENTRY = 23

/** How far back a state is followed to the choice that leads to it */
private const val CHOICE_DEPTH = 3

/** A transition into a state: where it comes from, and the menu slot it answers, if it tests one */
private data class Transition(val from: Long, val slot: Int?)

/**
 * How one state group's states lead into each other. A talk script builds its menu in one state, waits
 * in another and branches on the picked slot in a third, so the option a flag answers is found by
 * walking back from the state that sets it
 */
internal class TalkGraph(group: StateGroup) {
    private val evaluated = group.states.values.flatMap { state ->
        state.conditions.flatMap { it.flattened() }.map { state.id to it }
    }
    private val pickRegisters = evaluated.flatMap { (_, condition) -> condition.expression()?.pickRegisters().orEmpty() }.toSet()

    private val into: Map<Long, List<Transition>> = evaluated
        .mapNotNull { (from, condition) ->
            condition.target?.let { it to Transition(from, condition.expression()?.pickedSlot(pickRegisters)) }
        }
        .groupBy({ it.first }, { it.second })

    /** Each menu slot's text id, from every 'AddTalkListData' in the group */
    private val options: Map<Int, Int> = group.states.values
        .flatMap { it.calls() }
        .filter { it.isTalk(ADD_TALK_LIST) }
        .mapNotNull { call -> call.int(0)?.let { slot -> call.int(1)?.let { slot to it } } }
        .distinctBy { it.first }
        .toMap()

    /** The text id of the menu option whose choice leads to [state] */
    fun choiceInto(state: Long): Int? {
        var frontier = listOf(state)
        val seen = mutableSetOf(state)
        repeat(CHOICE_DEPTH) {
            val transitions = frontier.flatMap { into[it].orEmpty() }
            transitions.firstNotNullOfOrNull { it.slot?.let(options::get) }?.let { return it }
            frontier = transitions.map { it.from }.filter(seen::add)
        }
        return null
    }

    /** The states that lead straight into [state] */
    fun before(state: Long): List<Long> = into[state].orEmpty().map { it.from }.distinct()
}

internal fun EsdState.calls(): List<CommandCall> =
    entryCommands + whileCommands + exitCommands + conditions.flatMap { it.calls() }

private fun EsdCondition.calls(): List<CommandCall> = passCommands + subconditions.flatMap { it.calls() }

private fun EsdCondition.flattened(): List<EsdCondition> = listOf(this) + subconditions.flatMap { it.flattened() }

internal fun CommandCall.isTalk(command: Int): Boolean = bank == TALK_BANK && id == command

internal fun CommandCall.int(index: Int): Int? =
    ((args.getOrNull(index)?.decode() as? Decoded.Expression)?.expression as? EsdExpression.IntValue)?.value

private fun EsdCondition.expression(): EsdExpression? = (evaluator.decode() as? Decoded.Expression)?.expression

/** Every expression a state evaluates: its conditions, at any depth, and its commands' arguments */
internal fun EsdState.expressions(): List<EsdExpression> =
    conditions.flatMap { it.flattened() }.mapNotNull { it.expression() } +
            calls().flatMap { call -> call.args.mapNotNull { (it.decode() as? Decoded.Expression)?.expression } }

/** Registers a condition loads with the picked slot, `r0 = GetTalkListEntryResult()` */
private fun EsdExpression.pickRegisters(): List<Int> = children().flatMap { it.pickRegisters() } +
        listOfNotNull((this as? EsdExpression.SetRegister)?.takeIf { it.value.isPick(emptySet()) }?.index)

/** The slot a condition compares the picked entry against, directly or through a register */
private fun EsdExpression.pickedSlot(registers: Set<Int>): Int? {
    if (this is EsdExpression.Binary && op == Op.Equal) {
        val slot = (right as? EsdExpression.IntValue)?.value
        if (slot != null && left.unmarked().isPick(registers)) return slot
    }
    return children().firstNotNullOfOrNull { it.pickedSlot(registers) }
}

private fun EsdExpression.isPick(registers: Set<Int>): Boolean = when (this) {
    is EsdExpression.Call -> function == PICKED_ENTRY
    is EsdExpression.SetRegister -> value.isPick(registers)
    is EsdExpression.GetRegister -> index in registers
    else -> false
}

private fun EsdExpression.unmarked(): EsdExpression = if (this is EsdExpression.Marked) expr.unmarked() else this

internal fun EsdExpression.children(): List<EsdExpression> = when (this) {
    is EsdExpression.Binary -> listOf(left, right)
    is EsdExpression.Unary -> listOf(operand)
    is EsdExpression.Marked -> listOf(expr)
    is EsdExpression.SetRegister -> listOf(value)
    is EsdExpression.Call -> args
    else -> emptyList()
}
