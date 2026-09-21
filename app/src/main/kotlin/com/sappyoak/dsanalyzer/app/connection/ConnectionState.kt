package com.sappyoak.dsanalyzer.app.connection

import com.sappyoak.dsanalyzer.game.GameEdition

public data class ConnectionState(
    public val status: ConnectionStatus = ConnectionStatus.Off
)

public sealed interface ConnectionStatus {
    public data object Off : ConnectionStatus
    public data object Searching : ConnectionStatus

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
