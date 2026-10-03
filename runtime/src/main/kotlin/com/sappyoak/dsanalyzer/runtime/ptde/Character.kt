package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object CharData {
    internal val Base: Signature = Signature.parse(
        name = "CharData1",
        pattern = "8B 15 ?? ?? ?? ?? F3 0F 10 44 24 30 52",
        target = SignatureTarget.Embedded(2)
    )

    public val Pointer: GamePointer = GamePointer(
        name = "CharData1",
        base = Base,
        offsets = listOf(0L, 4L, 0L),
        lifetime = Lifetime.World,
        size = 0x640
    )

    public const val CharMapDataPointer: Int = 0x28
    public const val ChrType: Int = 0x70
    public const val TeamType: Int = 0x74
    public const val ForcePlayAnimation: Int = 0xFC
    public const val Flags1: Int = 0x1FC
    public const val PlayRegion: Int = 0x284
    public const val Health: Int = 0x2D4
    public const val Stamina: Int = 0x2E4
    public const val Flags2: Int = 0x3C4
    public const val StoredItem: Int = 0x628
}

public object CharFlags1 {
    public const val SetDeadMode: Int = 0x02000000
    public const val DisableDamage: Int = 0x04000000
    public const val EnableInvincible: Int = 0x08000000
    public const val FirstPerson: Int = 0x00100000
    public const val SetDrawEnable: Int = 0x00800000
    public const val SetSuperArmor: Int = 0x00010000
    public const val SetDisableGravity: Int = 0x00004000
    public const val ForceUpdateNextFrame: Int = 0x00000200
    public const val SetEventGenerate: Int = 0x00000010
    public const val DisableHPGauge: Int = 0x00000008
}

public object CharFlags2 {
    public const val NoGoodsConsume: Int = 0x01000000
    public const val DrawCounter: Int = 0x00200000
    public const val DrawDirection: Int = 0x00004000
    public const val NoUpdate: Int = 0x00008000
    public const val NoMPConsume: Int = 0x00000800
    public const val NoStaminaConsume: Int = 0x00000400
    public const val NoMove: Int = 0x00000200
    public const val NoAttack: Int = 0x00000100
    public const val NoHit: Int = 0x00000080
    public const val NoDamage: Int = 0x00000040
    public const val NoDead: Int = 0x00000020
    public const val DrawHit: Int = 0x00000004
}
