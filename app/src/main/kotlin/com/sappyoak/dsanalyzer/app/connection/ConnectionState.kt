package com.sappyoak.dsanalyzer.app.connection

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation

public data class ConnectionState(
    public val status: ConnectionStatus = ConnectionStatus.Off,
    public val target: Installation? = null
)

public sealed interface ConnectionStatus {
    public data object Off : ConnectionStatus
    public data object Searching : ConnectionStatus

    /** Asked to connect with no workspace open, so there is no installation to look for */
    public data object NoWorkspace : ConnectionStatus

    public data class Connected(
        public val pid: Int,
        public val edition: GameEdition
    ) : ConnectionStatus

    public data class Refused(
        public val executableName: String,
        public val reason: String
    ) : ConnectionStatus

    public data class Unavailable(public val reason: String) : ConnectionStatus
}

public val ConnectionStatus.watching: Boolean
    get() = this !is ConnectionStatus.Off && this !is ConnectionStatus.Unavailable
