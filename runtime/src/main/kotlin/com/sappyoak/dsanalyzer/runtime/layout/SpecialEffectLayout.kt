package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GameTable

public data class SpecialEffectLayout(
    public val holderSize: Int,
    public val head: Int,
    public val owner: Int,
    public val stateInfoCounts: Int,
    public val chain: GameTable,
    public val nodeSize: Int,
    public val remaining: Int,
    public val total: Int,
    public val magnitude: Int,
    public val duration: Int,
    public val flags: Int,
    public val effectId: Int,
    public val paramRow: Int,
    public val excludedMask: Int,
    public val liveMask: Int
)