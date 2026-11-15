package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

/** The game's floating enemy HP bars, its boss bars, and the character flag that keeps a bar away */
public data class HpBarLayout(
    /** Every slot, back to back */
    public val enemies: GamePointer,
    public val enemy: EnemyBarLayout,
    /** Null where the boss slots are not measured */
    public val bosses: BossBarLayout?,
    /** The 'DisableHpGauge' bit, in the character */
    public val hideFlag: Int,
    public val hideMask: Int
)

public data class EnemyBarLayout(
    public val count: Int,
    public val size: Int,
    public val handle: Int,
    public val screen: Int,
    public val shown: Int,
    public val damage: Int,
    public val timers: Int,
    public val visible: Int
)

public data class BossBarLayout(
    public val pointer: GamePointer,
    public val count: Int,
    public val size: Int,
    public val handle: Int
)
