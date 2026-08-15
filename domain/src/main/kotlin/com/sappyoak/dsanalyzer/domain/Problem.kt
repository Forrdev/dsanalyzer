package com.sappyoak.dsanalyzer.domain

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Problem(
    val kind: String,
    val severity: ProblemSeverity = ProblemSeverity.Warning,
    val summary: String,
    val consequence: String? = null,
    val resolutions: List<ProblemResolution> = emptyList(),
    val details: Map<String, String> = emptyMap()
) : Comparable<Problem> {
    val isCritical: Boolean get() = severity.isCritical
    val isError: Boolean get() = severity.isError
    val isWarning: Boolean get() = severity.isWarning
    val isDiagnostic: Boolean get() = severity.isDiagnostic

    val hasResolutions: Boolean get() = resolutions.isNotEmpty()
    val primaryResolution: ProblemResolution? get() = resolutions.firstOrNull { it.isPrimary }

    override fun compareTo(other: Problem): Int =
        severity.value.compareTo(other.severity.value)

    override fun toString(): String =
        "[$kind: ($severity)]: $summary"

    companion object {
        fun critical(
            kind: String,
            summary: String,
            consequence: String? = null,
            resolutions: List<ProblemResolution> = emptyList(),
            details: Map<String, String> = emptyMap()
        ): Problem = Problem(
            kind = kind,
            severity = ProblemSeverity.Critical,
            summary = summary,
            consequence,
            resolutions,
            details
        )

        fun error(
            kind: String,
            summary: String,
            consequence: String? = null,
            resolutions: List<ProblemResolution> = emptyList(),
            details: Map<String, String> = emptyMap()
        ): Problem = Problem(
            kind = kind,
            severity = ProblemSeverity.Error,
            summary = summary,
            consequence = consequence,
            resolutions,
            details
        )

        fun warning(
            kind: String,
            summary: String,
            consequence: String? = null,
            resolutions: List<ProblemResolution> = emptyList(),
            details: Map<String, String> = emptyMap()
        ): Problem = Problem(
            kind = kind,
            severity = ProblemSeverity.Warning,
            summary = summary,
            consequence = consequence,
            resolutions,
            details
        )
    }
}
@JvmInline
@Serializable
@SerialName("severity")
value class ProblemSeverity(val value: Int) {
    val isCritical: Boolean get() = value >= Critical.value
    val isError: Boolean get() = value in Error.value until Critical.value
    val isWarning: Boolean get() = value in Warning.value until Error.value
    val isDiagnostic: Boolean get() = value < Warning.value

    override fun toString() = value.toString()

    companion object {
        val Critical = ProblemSeverity(1000)
        val Error = ProblemSeverity(500)
        val Warning = ProblemSeverity(300)
    }
}

@Serializable
data class ProblemResolution(
    val label: String,
    val action: String? = null,
    val isPrimary: Boolean = true
)