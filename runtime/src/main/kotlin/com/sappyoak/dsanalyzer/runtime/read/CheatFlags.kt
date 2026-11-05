package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.runtime.params.ChrInsFlags1
import com.sappyoak.dsanalyzer.runtime.params.ChrInsFlags2
import com.sappyoak.dsanalyzer.runtime.ptde.ChrCtrlFlags
import com.sappyoak.dsanalyzer.runtime.session.CheatFlag

internal enum class CheatWord { Flags1, Flags2, MapFlags; }

internal class CheatBit(val word: CheatWord, val mask: Int)

internal val CHEAT_BITS: Map<CheatFlag, CheatBit> = mapOf(
    CheatFlag.Invincible to CheatBit(CheatWord.Flags1, ChrInsFlags1.EnableInvincible),
    CheatFlag.DamageDisabled to CheatBit(CheatWord.Flags1, ChrInsFlags1.DisableDamage),
    CheatFlag.DeadModeSet to CheatBit(CheatWord.Flags1, ChrInsFlags1.SetDeadMode),
    CheatFlag.GravityDisabled to CheatBit(CheatWord.Flags1, ChrInsFlags1.SetDisableGravity),
    CheatFlag.NoDead to CheatBit(CheatWord.Flags2, ChrInsFlags2.NoDead),
    CheatFlag.NoDamage to CheatBit(CheatWord.Flags2, ChrInsFlags2.NoDamage),
    CheatFlag.NoHit to CheatBit(CheatWord.Flags2, ChrInsFlags2.NoHit),
    CheatFlag.NoMove to CheatBit(CheatWord.Flags2, ChrInsFlags2.NoMove),
    CheatFlag.NoAttack to CheatBit(CheatWord.Flags2, ChrInsFlags2.NoAttack),
    CheatFlag.NoUpdate to CheatBit(CheatWord.Flags2, ChrInsFlags2.NoUpdate),
    CheatFlag.NoStaminaConsume to CheatBit(CheatWord.Flags2, ChrInsFlags2.NoStaminaConsume),
    CheatFlag.NoMPConsume to CheatBit(CheatWord.Flags2, ChrInsFlags2.NoMPConsume),
    CheatFlag.NoGoodsConsume to CheatBit(CheatWord.Flags2, ChrInsFlags2.NoGoodsConsume),
    CheatFlag.MapCollisionDisabled to CheatBit(CheatWord.MapFlags, ChrCtrlFlags.DisableMapHit)
)

internal fun cheatsIn(flags1: Int, flags2: Int, mapFlags: Int): Set<CheatFlag> = buildSet {
    val words = mapOf(CheatWord.Flags1 to flags1, CheatWord.Flags2 to flags2, CheatWord.MapFlags to mapFlags)
    return CHEAT_BITS.filterTo(LinkedHashMap()) {
        (_, bit) -> words.getValue(bit.word) and bit.mask != 0
    }.keys
}
