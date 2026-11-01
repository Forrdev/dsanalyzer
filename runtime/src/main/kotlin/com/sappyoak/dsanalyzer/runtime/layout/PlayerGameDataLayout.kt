package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

public data class PlayerGameDataLayout(
    public val pointer: GamePointer,
    public val vitals: VitalsLayout,
    public val stats: StatBlockLayout,
    public val progress: ProgressLayout,
    public val identity: IdentityLayout,
    public val resists: ResistBlockLayout,

    /**
     * '1' one-hand, '2' two-hand left, '3' two-hand right
     *
     * 'null' where the offset has not been measured for that edition. It sits past the first
     * embedded sub-object, so it cannot be derived from the other edition
     */
    public val stance: Int?
)

public data class VitalsLayout(
    public val hp: Int,
    public val maxHp: Int,
    public val baseMaxHp: Int,
    public val mp: Int,
    public val baseMaxMp: Int,
    public val maxMp: Int,
    public val sp: Int,
    public val maxSp: Int,
    public val baseMaxSp: Int
)

public data class StatBlockLayout(
    public val first: Int,
    public val stride: Int,
    public val count: Int
) {
    init {
        require(count == Stat.entries.size) { "expected ${Stat.entries.size} stats, got $count" }
    }

    public fun offsetOf(stat: Stat): Int = first + stat.ordinal * stride
}

public enum class Stat {
    Vitality,
    Attunement,
    Endurance,
    Strength,
    Dexterity,
    Intelligence,
    Faith,
    Resistance
}

public data class ProgressLayout(
    /** The engine's 'HeroPoint' */
    public val humanity: Int,

    /** A character field clamped '1..99', not equipment durability, which lives per-item */
    public val durability: Int,

    public val soulLevel: Int,
    public val souls: Int,
    public val soulMemory: Int,

    /**
     * Not in the engine's debug menu in either edition, so it carries none of the corroboration the
     * rest of this table does. 'null' where it has not been measured
     */
    public val covenant: Int?
)

public data class IdentityLayout(
    public val gender: Int,

    /** Starting class */
    public val archeType: Int,

    public val appearance: Int,

    /** Starting gift */
    public val gift: Int
)


public data class ResistBlockLayout(
    public val poison: Int,
    public val blood: Int,
    public val disease: Int,
    public val curse: Int
)
