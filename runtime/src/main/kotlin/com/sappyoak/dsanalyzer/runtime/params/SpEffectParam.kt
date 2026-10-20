package com.sappyoak.dsanalyzer.runtime.params

/** Through [SpEffectParam.Fields], the last field declared here. The row itself is longer */
internal const val SP_EFFECT_PARAM_SIZE: Int = 0x160

public object SpEffectParam {
    public const val MaxHpRate: Int = 0x10
    public const val UnlinkId: Int = 0x13E
    public const val StateInfo: Int = 0x156
    public const val Fields: Int = 0x15E
}

public object SpEffectFields {
    public const val HasMagnitude: Int = 0x10
    public const val HasDuration: Int = 0x20
}