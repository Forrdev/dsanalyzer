package com.sappyoak.dsanalyzer.app.connection

import kotlinx.serialization.Serializable

@Serializable
public data class ConnectionSettings(
    public val autoConnect: Boolean = false
)