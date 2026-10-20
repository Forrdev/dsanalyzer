package com.sappyoak.dsanalyzer.runtime.session

public data class MenuSnapshot(
    /** MenuMan's open-menu bitmask */
    public val mask: Int,
    public val boxKind: Int,
    /**
     * The pause-menu family's screen, or 0 when that family is closed
     */
    public val screen: Int,
    public val defaultQuantity: Int,
    public val isBlocking: Boolean
)