package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.runtime.ptde.ChrInsFlags1
import com.sappyoak.dsanalyzer.runtime.ptde.ChrInsFlags2
import com.sappyoak.dsanalyzer.runtime.ptde.ChrCtrlFlags

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
    CheatFlag.Invincible to ChrInsFlags1.EnableInvincible,
    CheatFlag.DamageDisabled to ChrInsFlags1.DisableDamage,
    CheatFlag.DeadModeSet to ChrInsFlags1.SetDeadMode,
    CheatFlag.GravityDisabled to ChrInsFlags1.SetDisableGravity
)

private val IN_FLAGS_2 = mapOf(
    CheatFlag.NoDead to ChrInsFlags2.NoDead,
    CheatFlag.NoDamage to ChrInsFlags2.NoDamage,
    CheatFlag.NoHit to ChrInsFlags2.NoHit,
    CheatFlag.NoMove to ChrInsFlags2.NoMove,
    CheatFlag.NoAttack to ChrInsFlags2.NoAttack,
    CheatFlag.NoUpdate to ChrInsFlags2.NoUpdate,
    CheatFlag.NoStaminaConsume to ChrInsFlags2.NoStaminaConsume,
    CheatFlag.NoMPConsume to ChrInsFlags2.NoMPConsume,
    CheatFlag.NoGoodsConsume to ChrInsFlags2.NoGoodsConsume
)

/** Which flags are set across the three words that hold them */
internal fun cheatsIn(flags1: Int, flags2: Int, mapFlags: Int): Set<CheatFlag> = buildSet {
    IN_FLAGS_1.forEach { (flag, mask) -> if (flags1 and mask != 0) add(flag) }
    IN_FLAGS_2.forEach { (flag, mask) -> if (flags2 and mask != 0) add(flag) }
    if (mapFlags and ChrCtrlFlags.DisableMapHit != 0) add(CheatFlag.MapCollisionDisabled)
}