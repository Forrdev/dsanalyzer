package com.sappyoak.dsanalyzer.app.runtime

import kotlin.math.floor

import com.sappyoak.dsanalyzer.app.runtime.timing.AttackWindow
import com.sappyoak.dsanalyzer.app.runtime.timing.word
import com.sappyoak.dsanalyzer.runtime.session.ai.GoalSnapshot

private const val TOP_GOAL = 0

/**
 * The goals being worked on, from the script's battle goal down, then what is queued in the
 * order it will run
 */
internal fun GoalSnapshot.planLine(windowsOf: (Int) -> List<AttackWindow> = { emptyList() }): String {
    val path = generateSequence(this) { it.active }.filterNot { it.id == TOP_GOAL }.toList()
    if (path.isEmpty()) return "nothing planned"

    val now = path.joinToString(" > ") { it.label() }
    val next = path.asReversed().flatMap { it.queued }.joinToString(", ") { goal ->
        val first = goal.action()?.let(windowsOf)?.firstOrNull()
        goal.label() + first?.let { " (${it.kind.word} at ${"%.2f".format(it.start)}s)" }.orEmpty()
    }
    return if (next.isEmpty()) now else "$now, then $next"
}

private fun GoalSnapshot.label(): String {
    val first = params.firstOrNull()?.takeIf { it != 0f && it == floor(it) } ?: return name
    return "$name ${first.toInt()}"
}

private fun GoalSnapshot.action(): Int? = params.firstOrNull()?.takeIf { it != 0f && it == floor(it) }?.toInt()
