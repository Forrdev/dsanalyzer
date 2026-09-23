package com.sappyoak.dsanalyzer.formats.msb.part

import kotlin.math.abs

private const val STABLE_FOOTING_BASE = -10

public enum class CollisionHitFilter(public val id: Int) {
    NoHitHitNoFeetIK(0),
    NoHiHit1(1),
    NoHiHit2(2),
    NoHiHit3(3),
    NoHiHit4(4),
    NoHiHit5(5),
    NoHiHit6(6),
    NoHiHit7(7),
    Normal(8),
    Water(9),
    SolidForNpcsOnlyA(11),
    DeathCam(13),
    LethalFall(14),
    KillPlane(15),
    WaterB(16),
    GroupSwitch(17),
    SolidForNpcsOnlyB(19),
    LevelExitA(20),
    Slide(32),
    FallProtection(22),
    LevelExitB(23);
}

public val Part.Collision.hitFilter: CollisionHitFilter?
    get() = CollisionHitFilter.entries.firstOrNull { it.id == hitFilterId }

/** Place name id or null to take the name from the map's area and block */
public val Part.Collision.placeNameId: Int?
    get() = if (rawPlaceName == -1) null else abs(rawPlaceName)

/** The play region, when the field holds one rather than a stable footing flag */
public val Part.Collision.playRegionId: Int?
    get() = rawPlayRegion.takeIf { it > STABLE_FOOTING_BASE }

/**
 * The event flag that governs stable footing on this collision, the ground the
 * game records as the player's last safe position. Stored as '-flag - 10' where values
 * above -10 are play region ids instead
 */
public val Part.Collision.stableFootingFlag: Int?
    get() = if (rawPlayRegion > STABLE_FOOTING_BASE) null else STABLE_FOOTING_BASE - rawPlayRegion