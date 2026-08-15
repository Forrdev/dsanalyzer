package com.sappyoak.dsanalyzer.app.state.readiness

/**
 * Decides whether the tool is even in a usable state or not.
 * HealthState tracks problems that might have an effect on certain parts of the app,
 * the confidence of certain results, but don't fundamentally break the tool where as
 * these do
 */
sealed class Readiness {
    val isReady: Boolean get() = this is Ready

    data object Ready : Readiness()

    data class Blocked(val blockers: List<Blocker>) : Readiness() {
        val primary: Blocker get() = blockers.minByOrNull { it.kind.ordinal } ?: blockers.first()
    }
}

/** Something the must be resolved before the tool is usable */
data class Blocker(
    val kind: Kind,
    val summary: String,
    val consequences: String,
    val resolutions: List<Resolution>,
    val installationKey: String? = null
) {
    /** Ordered by how fundamental the problem is */
    enum class Kind {
        NoInstallation,
        InstallationMissing,
        UnknownVersion,
        UnreadableFiles,
        DataDirectoryUnwritable;
    }

    /** Something actional a user can do about a blocker */
    data class Resolution(
        val label: String,
        val isPrimary: Boolean = true
    )
}