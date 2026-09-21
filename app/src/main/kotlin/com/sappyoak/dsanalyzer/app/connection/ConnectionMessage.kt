package com.sappyoak.dsanalyzer.app.connection

import com.sappyoak.dsanalyzer.game.GameEdition

public sealed interface ConnectionMessage {
    public data object Start : ConnectionMessage

    public data object ConnectRequested : ConnectionMessage
    public data object DisconnectRequested : ConnectionMessage

    public data object GameSearching : ConnectionMessage
    public data class GameConnected(
        public val pid: Int,
        public val edition: GameEdition
    ) : ConnectionMessage

    public data class GameRefused(
        public val executableName: String,
        public val reason: String
    ) : ConnectionMessage
}