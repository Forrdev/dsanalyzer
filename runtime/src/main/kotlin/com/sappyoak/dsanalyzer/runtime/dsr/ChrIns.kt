package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.session.CHEAT_BITS
import com.sappyoak.dsanalyzer.runtime.session.CheatBit
import com.sappyoak.dsanalyzer.runtime.session.CheatFlag
import com.sappyoak.dsanalyzer.runtime.session.CheatWord

internal const val CHR_INS_SIZE: Int = 0x530

/** Through [ChrCtrl.Flags] */
internal const val CHR_CTRL_SIZE: Int = 0x108

/** Through [ChrPosData.Position] */
internal const val POSE_SIZE: Int = 0x20

public object ChrIns {
    public const val Handle: Int = 0x08
    public const val ChrResPointer: Int = 0x30
    public const val ChrCtrlPointer: Int = 0x68
    public const val ComManipulatorPointer: Int = 0x70

    public const val ModelName: Int = 0x88
    public const val ModelNameLength: Int = 0x98
    public const val NpcParamId: Int = 0xC8
    public const val ModelId: Int = 0xD0
    public const val ChrType: Int = 0xD4
    public const val TeamType: Int = 0xD8

    public const val PoiseCurrent: Int = 0x250
    public const val PoiseMax: Int = 0x254
    public const val PoiseTimer: Int = 0x25C
    public const val SpecialEffectPointer: Int = 0x278

    public const val Flags1: Int = 0x284

    public const val HpBarHidden: Int = 0x2A8
    public const val HpBarHiddenMask: Int = 0x08

    /** A copy of the posed position, X, Y and Z one after another */
    public const val Position: Int = 0x2C0

    public const val PlayRegion: Int = 0x354
    public const val HitInsPointer: Int = 0x370
    public const val Health: Int = 0x3E8
    public const val HealthMax: Int = 0x3EC
    public const val Stamina: Int = 0x3F8
    public const val PoisonCurrent: Int = 0x418
    public const val ToxicCurrent: Int = 0x41C
    public const val BleedCurrent: Int = 0x420
    public const val PoisonMax: Int = 0x428
    public const val ToxicMax: Int = 0x42C
    public const val BleedMax: Int = 0x430

    public const val Flags2: Int = 0x514
    public const val PlayerGameDataPointer: Int = 0x578
    public const val NpcParamPointer: Int = 0x580

    public const val AIPointer: Int = 0xAD0
}

public object ComManipulator {
    public const val TargetHandle: Int = 0x220
    public const val AIThinkPointer: Int = 0x230
    /** The embedded think owner which the think object points back at */
    public const val ThinkOwner: Int = 0x238
}

/** For the DSR flag bits that are different from PTDE */
public object DSRChrInsFlags1 {
    public const val SetDisableGravity: Int = 0x00002000
}

internal val DSRCheatBits: Map<CheatFlag, CheatBit> =
    CHEAT_BITS + (CheatFlag.GravityDisabled to CheatBit(CheatWord.ChrFlags1, DSRChrInsFlags1.SetDisableGravity))

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

    public const val ActionCtrlPointer: Int = 0x48
    public const val Flags: Int = 0x104

    public const val Warp: Int = 0x108
    public const val WarpPosition: Int = 0x110
    public const val WarpAngle: Int = 0x124
}

/** The structure holding a chracter's live position */
public object ChrPosData {
    public const val Angle: Int = 0x04
    public const val Position: Int = 0x10

    /** 'hkpCharacterProxy*' */
    public const val ProxyPointer: Int = 0x38
    public const val Velocity: Int = 0xD0
    public const val AirTimer: Int = 0x1B0
}

/** 'hkpCharacterProxy', the havok character the game moves */
public object CharacterProxy {
    public const val Velocity: Int = 0x60
}
