package com.sappyoak.dsanalyzer.app.state

import com.sappyoak.dsanalyzer.domain.Problem
import com.sappyoak.dsanalyzer.domain.ProblemSeverity

data class HealthState(val problems: List<Problem> = emptyList()) {
    val isHealthy: Boolean get() = atOrAbove(ProblemSeverity.Warning).isEmpty()

    fun below(severity: ProblemSeverity): List<Problem> =
        problems.filter { it.severity.value < severity.value }

    fun above(severity: ProblemSeverity): List<Problem> =
        problems.filter { it.severity.value > severity.value }


    fun atOrBelow(severity: ProblemSeverity): List<Problem> =
        problems.filter { it.severity.value <= severity.value }

    fun atOrAbove(severity: ProblemSeverity): List<Problem> =
        problems.filter { it.severity.value >= severity.value }
}

fun HealthState.below(severity: Int): List<Problem> = below(ProblemSeverity(severity))
fun HealthState.above(severity: Int): List<Problem> = above(ProblemSeverity(severity))
fun HealthState.atOrBelow(severity: Int): List<Problem> = atOrBelow(ProblemSeverity(severity))
fun HealthState.atOrAbove(severity: Int): List<Problem> = atOrAbove(ProblemSeverity(severity))