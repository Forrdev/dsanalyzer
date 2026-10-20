package com.sappyoak.dsanalyzer.runtime.session.collision

import com.sappyoak.dsanalyzer.formats.msb.part.CollisionHitFilter

public sealed interface HitFilter {
    public data class Known(public val filter: CollisionHitFilter) : HitFilter

    public data class Unnamed(public val id: Int) : HitFilter

    public data class Ambiguous(public val candidates: Set<CollisionHitFilter>) : HitFilter

    public data object Absent : HitFilter
}

public enum class Passability {
    Solid,
    Passable,
    Unknown;
}

public val HitFilter.passability: Passability
    get() = when (this) {
        is HitFilter.Known -> filter.passability
        // Every sibling group seen is death-vs-death or exit-vs-exit, so the group agrees even
        // where the member does not — but that is two maps, not a rule, so it is only taken when
        // the candidates genuinely agree
        is HitFilter.Ambiguous -> candidates.map { it.passability }.distinct().singleOrNull()
            ?: Passability.Unknown
        is HitFilter.Unnamed, HitFilter.Absent -> Passability.Unknown
    }

private val CollisionHitFilter.passability: Passability
    get() = when (this) {
        CollisionHitFilter.DeathCam,
        CollisionHitFilter.LethalFall,
        CollisionHitFilter.KillPlane,
        CollisionHitFilter.LevelExitA,
        CollisionHitFilter.LevelExitB,
        CollisionHitFilter.SolidForNpcsOnlyA,
        CollisionHitFilter.SolidForNpcsOnlyB -> Passability.Passable

        CollisionHitFilter.NoHiHitNoFeetIK,
        CollisionHitFilter.NoHiHit1,
        CollisionHitFilter.NoHiHit2,
        CollisionHitFilter.NoHiHit3,
        CollisionHitFilter.NoHiHit4,
        CollisionHitFilter.NoHiHit5,
        CollisionHitFilter.NoHiHit6,
        CollisionHitFilter.NoHiHit7,
        CollisionHitFilter.Normal,
        CollisionHitFilter.Slide,
        CollisionHitFilter.FallProtection -> Passability.Solid

        CollisionHitFilter.WaterA,
        CollisionHitFilter.WaterB,
        CollisionHitFilter.GroupSwitch,
        CollisionHitFilter.Unknown10,
        CollisionHitFilter.Unknown12,
        CollisionHitFilter.Unknown18 -> Passability.Unknown
    }