package com.sappyoak.dsanalyzer.runtime.session

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

public enum class CheatWord {
    ChrFlags1,
    ChrFlags2,
    MapFlags;
}

public class CheatBit(public val word: CheatWord, public val mask: Int)

public object ChrInsFlags1 {
    public const val ForceSetOmission: Int = 0x00000001
    public const val DisableHPGauge: Int = 0x00000008
    public const val SetEventGenerate: Int = 0x00000010
    public const val ForceUpdateNextFrame: Int = 0x00000200
    public const val SetDisableGravity: Int = 0x00004000
    public const val SetSuperArmor: Int = 0x00010000
    public const val FirstPerson: Int = 0x00100000
    public const val SetDrawEnable: Int = 0x00800000
    public const val EnableGetItem: Int = 0x01000000
    public const val SetDeadMode: Int = 0x02000000
    public const val DisableDamage: Int = 0x04000000
    public const val EnableInvincible: Int = 0x08000000
}

public object ChrInsFlags2 {
    public const val NoDead: Int = 0x00000020
    public const val NoDamage: Int = 0x00000040
    public const val NoHit: Int = 0x00000080
    public const val NoAttack: Int = 0x00000100
    public const val NoMove: Int = 0x00000200
    public const val NoStaminaConsume: Int = 0x00000400
    public const val NoMPConsume: Int = 0x00000800
    public const val NoUpdate: Int = 0x00008000
    public const val NoGoodsConsume: Int = 0x01000000
}

public object ChrMapFlags {
    public const val DisableMapHit: Int = 0x00000010
}

internal val CHEAT_BITS: Map<CheatFlag, CheatBit> = mapOf(
    CheatFlag.Invincible to CheatBit(CheatWord.ChrFlags1, ChrInsFlags1.EnableInvincible),
    CheatFlag.DamageDisabled to CheatBit(CheatWord.ChrFlags1, ChrInsFlags1.DisableDamage),
    CheatFlag.DeadModeSet to CheatBit(CheatWord.ChrFlags1, ChrInsFlags1.SetDeadMode),
    CheatFlag.GravityDisabled to CheatBit(CheatWord.ChrFlags1, ChrInsFlags1.SetDisableGravity),
    CheatFlag.NoDead to CheatBit(CheatWord.ChrFlags2, ChrInsFlags2.NoDead),
    CheatFlag.NoDamage to CheatBit(CheatWord.ChrFlags2, ChrInsFlags2.NoDamage),
    CheatFlag.NoHit to CheatBit(CheatWord.ChrFlags2, ChrInsFlags2.NoHit),
    CheatFlag.NoMove to CheatBit(CheatWord.ChrFlags2, ChrInsFlags2.NoMove),
    CheatFlag.NoAttack to CheatBit(CheatWord.ChrFlags2, ChrInsFlags2.NoAttack),
    CheatFlag.NoUpdate to CheatBit(CheatWord.ChrFlags2, ChrInsFlags2.NoUpdate),
    CheatFlag.NoStaminaConsume to CheatBit(CheatWord.ChrFlags2, ChrInsFlags2.NoStaminaConsume),
    CheatFlag.NoMPConsume to CheatBit(CheatWord.ChrFlags2, ChrInsFlags2.NoMPConsume),
    CheatFlag.NoGoodsConsume to CheatBit(CheatWord.ChrFlags2, ChrInsFlags2.NoGoodsConsume),
    CheatFlag.MapCollisionDisabled to CheatBit(CheatWord.MapFlags, ChrMapFlags.DisableMapHit)
)

internal fun cheatsIn(flags1: Int, flags2: Int, mapFlags: Int): Set<CheatFlag> = buildSet {
    val words = mapOf(CheatWord.ChrFlags1 to flags1, CheatWord.ChrFlags2 to flags2, CheatWord.MapFlags to mapFlags)
    return CHEAT_BITS.filterTo(LinkedHashMap()) {
            (_, bit) -> words.getValue(bit.word) and bit.mask != 0
    }.keys
}
