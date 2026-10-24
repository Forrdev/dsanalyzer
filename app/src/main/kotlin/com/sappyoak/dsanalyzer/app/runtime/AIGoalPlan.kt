package com.sappyoak.dsanalyzer.app.runtime

import kotlin.math.floor

import com.sappyoak.dsanalyzer.runtime.session.ai.GoalSnapshot

private const val TOP_GOAL = 0

/**
 * The goals being worked on, from the script's battle goal down, then what is queued in the
 * order it will run
 */
internal fun GoalSnapshot.planLine(): String {
    val path = generateSequence(this) { it.active }.filterNot { it.id == TOP_GOAL }.toList()
    if (path.isEmpty()) return "nothing planned"

    val now = path.joinToString(" > ") { it.label() }
    val next = path.asReversed().flatMap { it.queued }.joinToString(", ") { it.label() }
    return if (next.isEmpty()) now else "$now, then $next"
}

private fun GoalSnapshot.label(): String {
    val first = params.firstOrNull()?.takeIf { it != 0f && it == floor(it) } ?: return name
    return "$name ${first.toInt()}"
}
