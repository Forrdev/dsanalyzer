package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object ChrIns {
    public const val ChrResPointer: Int = 0x30
    public const val ChrCtrlPointer: Int = 0x68
    public const val ComManipulatorPointer: Int = 0x70
    public const val NpcParamId: Int = 0xC8
    public const val ModelId: Int = 0xD0
    public const val SpecialEffectPointer: Int = 0x278
    public const val Flags1: Int = 0x284
    public const val HitInsPointer: Int = 0x370
    public const val Health: Int = 0x3E8
    public const val HealthMax: Int = 0x3EC
    public const val Stamina: Int = 0x3F8

    public const val Flags2: Int = 0x514
    public const val PlayerGameDataPointer: Int = 0x578

}

public object ChrInsFlags1 {
    public const val ForceSetOmission = 0x00000001
    public const val DisableHPGauge = 0x00000008
    public const val SetEventGenerate = 0x00000010
    public const val ForceUpdateNextFrame = 0x00000200
    public const val SetDisableGravity = 0x00004000
    public const val SetSuperArmor = 0x00010000
    public const val FirstPerson = 0x00100000
    public const val SetDrawEnable = 0x00800000
    public const val SetDeadMode = 0x02000000
}

public object ChrCtrl {
    public val Pointer: GamePointer = GamePointer(
        name = "ChrCtrl",
        base = DSRSignatures.WorldChrMan,
        offsets = listOf(0L, WorldChrMan.PlayerPointer.toLong(), ChrIns.ChrCtrlPointer.toLong()),
        lifetime = Lifetime.World,
        size = 0x2E0
    )

    public const val AnimDataPointer: Int = 0x18
    public const val AnimRequestMgrPointer: Int = 0x20
    public const val PositionPointer: Int = 0x28
    public const val Flags: Int = 0x104

}

/** The structure holding a chracter's live position */
public object ChrPosData {
    public const val Position: Int = 0x10
}

