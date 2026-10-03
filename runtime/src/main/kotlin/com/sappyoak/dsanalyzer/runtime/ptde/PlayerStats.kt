package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object PlayerStats {
    public val Pointer: GamePointer = GamePointer(
        name = "CharData2",
        base = Signature.parse(
            name = "CharData2",
            pattern = "A1 ?? ?? ?? ?? 8B 40 34 53 32 DB 85 C0",
            target = SignatureTarget.Embedded(1)
        ),
        offsets = listOf(0L, 8L),
        lifetime = Lifetime.World,
        size = 0x240
    )

    public const val Health: Int = 0xC
    public const val HealthModifiedMax: Int = 0x10
    public const val HealthMax: Int = 0x14
    public const val Stamina: Int = 0x28
    public const val StaminaMax: Int = 0x30
    public const val Vitality: Int = 0x38
    public const val Endurance: Int = 0x48
    public const val Humanity: Int = 0x7C
    public const val SoulLevel: Int = 0x88
    public const val Souls: Int = 0x8C
    public const val Covenant: Int = 0x10B
    public const val Stance: Int = 0x230
}
