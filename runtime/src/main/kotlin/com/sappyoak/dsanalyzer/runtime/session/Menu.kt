package com.sappyoak.dsanalyzer.runtime.session

public data class MenuSnapshot(
    /**
     * Whether a menu is covering the world as 'IsFullScreenMenu' computes it
     */
    public val isFullScreenOpen: Boolean,
    /**
     * The pause-menu family's screen, or 0 when that family is closed
     */
    public val screen: Int,
    public val defaultQuantity: Int
)