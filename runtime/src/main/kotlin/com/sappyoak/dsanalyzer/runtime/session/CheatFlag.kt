package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.runtime.ptde.CharFlags1
import com.sappyoak.dsanalyzer.runtime.ptde.CharFlags2
import com.sappyoak.dsanalyzer.runtime.ptde.CharMapData
import com.sappyoak.dsanalyzer.shared.math.Vec3

public enum class CheatFlag {
    Invincible,
    DamageDisabled,
    DeadModeSet,
    GravityDisabled,
    MapCollisionDisabled,
    NoDead,
    NoDamage,
    NoHit,
    NoMove,
    NoAttack,
    NoUpdate,
    NoStaminaConsume,
    NoMPConsume,
    NoGoodsConsume;
}

private val IN_FLAGS_1 = mapOf(
    CheatFlag.Invincible to CharFlags1.EnableInvincible,
    CheatFlag.DamageDisabled to CharFlags1.DisableDamage,
    CheatFlag.DeadModeSet to CharFlags1.SetDeadMode,
    CheatFlag.GravityDisabled to CharFlags1.SetDisableGravity
)

private val IN_FLAGS_2 = mapOf(
    CheatFlag.NoDead to CharFlags2.NoDead,
    CheatFlag.NoDamage to CharFlags2.NoDamage,
    CheatFlag.NoHit to CharFlags2.NoHit,
    CheatFlag.NoMove to CharFlags2.NoMove,
    CheatFlag.NoAttack to CharFlags2.NoAttack,
    CheatFlag.NoUpdate to CharFlags2.NoUpdate,
    CheatFlag.NoStaminaConsume to CharFlags2.NoStaminaConsume,
    CheatFlag.NoMPConsume to CharFlags2.NoMPConsume,
    CheatFlag.NoGoodsConsume to CharFlags2.NoGoodsConsume
)

/** Which flags are set across the three words that hold them */
internal fun cheatsIn(flags1: Int, flags2: Int, mapFlags: Int): Set<CheatFlag> = buildSet {
    IN_FLAGS_1.forEach { (flag, mask) -> if (flags1 and mask != 0) add(flag) }
    IN_FLAGS_2.forEach { (flag, mask) -> if (flags2 and mask != 0) add(flag) }
    if (mapFlags and CharMapData.DisableMapHit != 0) add(CheatFlag.MapCollisionDisabled)
}