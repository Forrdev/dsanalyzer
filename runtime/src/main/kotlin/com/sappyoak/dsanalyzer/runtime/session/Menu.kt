package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.runtime.ptde.MenuOpenMask

public data class MenuSnapshot(
    /** MenuMan's open-menu bitmask */
    public val mask: Int,
    public val boxKind: Int,
    /**
     * The pause-menu family's screen, or 0 when that family is closed
     */
    public val screen: Int,
    public val defaultQuantity: Int
) {
    /**
     * Whether something is covering the majority of the world screen
     */
    public val isBlocking: Boolean get() = MenuOpenMask.blocks(mask, boxKind)
}