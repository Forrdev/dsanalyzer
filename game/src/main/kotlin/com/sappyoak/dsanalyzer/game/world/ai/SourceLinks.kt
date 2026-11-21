package com.sappyoak.dsanalyzer.game.world.ai

/** What a name in a script's source opens */
public sealed interface SourceTarget {
    public data class Goal(public val id: Int) : SourceTarget
    public data class Logic(public val id: Int) : SourceTarget
    public data class Function(public val script: LuaScriptId, public val name: String) : SourceTarget

    /** An EzState id passed to a goal: the animation of that id in the script's character */
    public data class Animation(public val id: Long) : SourceTarget
}

/** A name in one line of source, from [start] up to [end], and what it opens */
public data class SourceLink(public val start: Int, public val end: Int, public val target: SourceTarget)

private val IDENTIFIER = Regex("[A-Za-z_][A-Za-z0-9_]*")

/** `goal:AddSubGoal(GOAL_X, lifetime, parameters...)` and its two siblings, which take the same arguments */
private val GOAL_CALL = Regex(":(?:AddSubGoal|AddTopGoal|AddSubGoal_Front)\\(")

/** The goal and its lifetime come before a goal's own parameters */
private const val GOAL_ARGUMENTS = 2

/** An id written out; -1, meaning none, is not one */
private val LITERAL_ID = Regex("\\d+")

/**
 * Finds the names in decompiled source that [index] knows: `GOAL_` and `LOGIC_ID_` constants open their goal
 * or logic, and global functions the script they are defined in. A map's binder is loaded on top of aiCommon,
 * so a function defined in several places opens the copy in the same binder first, then the common one.
 * A literal id passed to a goal parameter named as an EzState opens that animation
 */
public class SourceLinks(private val index: AiIndex) {
    private val goals = index.goals.values.mapNotNull { goal -> goal.constant?.let { it to goal.id } }.toMap()
    private val logics = index.logics.values.mapNotNull { logic -> logic.constant?.let { it to logic.id } }.toMap()
    private val ezStates = index.goals.values.mapNotNull { goal ->
        goal.constant?.let { it to goal.parameters.filter(GoalParameter::isEzState).map(GoalParameter::index) }
    }.filter { it.second.isNotEmpty() }.toMap()

    /** The links in [line] of [script]; names inside strings and comments, fields and methods are not */
    public fun linksIn(line: String, script: LuaScriptId): List<SourceLink> {
        val code = SourceCode(line)
        val names = IDENTIFIER.findAll(code.text)
            .filterNot { code.quoted[it.range.first] }
            .filterNot { code.isField(it.range.first) || code.isDeclared(it.range.first) }
            .mapNotNull { match ->
                target(match.value, script)?.let { SourceLink(match.range.first, match.range.last + 1, it) }
            }
        return (names + animations(code)).sortedBy { it.start }.toList()
    }

    /** The EzState ids written into the goal calls on a line */
    private fun animations(code: SourceCode): Sequence<SourceLink> = GOAL_CALL.findAll(code.text)
        .filterNot { code.quoted[it.range.first] }
        .flatMap { call ->
            val arguments = code.arguments(call.range.last)
            val goal = arguments.firstOrNull()?.let { code.text.substring(it.first, it.last + 1).trim() }
            ezStates[goal].orEmpty().mapNotNull { parameter -> arguments.getOrNull(GOAL_ARGUMENTS + parameter) }
        }
        .mapNotNull { argument ->
            val text = code.text.substring(argument.first, argument.last + 1)
            val id = text.trim().takeIf(LITERAL_ID::matches) ?: return@mapNotNull null
            val start = argument.first + text.indexOf(id)
            SourceLink(start, start + id.length, SourceTarget.Animation(id.toLong()))
        }

    private fun target(name: String, script: LuaScriptId): SourceTarget? {
        goals[name]?.let { return SourceTarget.Goal(it) }
        logics[name]?.let { return SourceTarget.Logic(it) }
        val definitions = index.definitionsOf(name)
        val definition = definitions.firstOrNull { it.binder == script.binder }
            ?: definitions.firstOrNull { it.binder == LuaBinder.Common }
            ?: definitions.firstOrNull()
        return definition?.let { SourceTarget.Function(it, name) }
    }
}
