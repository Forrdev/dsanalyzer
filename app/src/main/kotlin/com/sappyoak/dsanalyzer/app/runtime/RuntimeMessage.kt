package com.sappyoak.dsanalyzer.app.runtime

import com.sappyoak.dsanalyzer.app.store.FrequentMessage

public sealed interface RuntimeMessage {
    public data object Opened : RuntimeMessage
    public data class LinkChanged(public val link: LinkState) : RuntimeMessage, FrequentMessage

    public data object PlacedEnemiesRequested : RuntimeMessage
}