package com.sappyoak.dsanalyzer.runtime.params

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
