package com.sappyoak.dsanalyzer.runtime.dsr

public object ChrIns {
    public const val ChrResPointer: Int = 0x30
    public const val ChrCtrlPointer: Int = 0x68
    public const val ComManipulatorPointer: Int = 0x70
    public const val NpcParamId: Int = 0xC8
    public const val ModelId: Int = 0xD0
    public const val SpecialEffectPointer: Int = 0x278
    public const val HitInsPointer: Int = 0x370
    public const val Health: Int = 0x3E8
    public const val HealthMax: Int = 0x3EC
    public const val Stamina: Int = 0x3F8
}


public object ChrCtrl {
    public const val PositionPointer: Int = 0x28
}

/** The structure holding a chracter's live position */
public object ChrPosData {
    public const val Position: Int = 0x10
}