package com.sappyoak.dsanalyzer.app.runtime

public sealed interface RuntimeMessage {
    public data object Opened : RuntimeMessage
    public data class LinkChanged(public val link: LinkState) : RuntimeMessage
}