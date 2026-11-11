package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GameTable

public data class HitInsLayout(
    public val areas: GameTable,
    public val groups: GameTable,
    public val instances: GameTable,
    public val partIndex: Int,
    public val msb: Int,
    public val tag: Int
)

public data class PartFilterLayout(
    public val partsList: Int,
    public val parts: GameTable,
    public val type: Int,
    public val indexInType: Int,
    public val typeParams: Int,
    public val collisionType: Int
)

public data class DebugDrawLayout(
    public val hitIns: HitInsLayout,
    public val partFilters: PartFilterLayout
)
