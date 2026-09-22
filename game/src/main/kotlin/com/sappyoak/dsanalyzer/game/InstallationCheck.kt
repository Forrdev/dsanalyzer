package com.sappyoak.dsanalyzer.game

public sealed interface InstallationCheck {
    public data class Valid(public val installation: Installation) : InstallationCheck
    public data class Rejected(public val reason: RejectionReason, public val err: Throwable? = null) : InstallationCheck
}

public sealed interface RejectionReason {
    /** Nothing in the folder looks like a game executable */
    public data object NoExecutable : RejectionReason

    /** An executable was found, but not one this tool knows */
    public data class UnrecognizedExecutable(public val name: String) : RejectionReason

    /** The executable is recognized but expected archives/ files are absent */
    public data class MissingFiles(public val names: List<String>) : RejectionReason

    /** A real installation of an edition the tool does not handle yet */
    public data class EditionNotSupportedYet(public val edition: GameEdition) : RejectionReason
}