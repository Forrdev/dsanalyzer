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
        size = 0x2A4
    )

    public const val Health: Int = 0xC
    public const val HealthModifiedMax: Int = 0x10
    public const val HealthMax: Int = 0x14
    public const val Stamina: Int = 0x28
    public const val StaminaModMax: Int = 0x2
    public const val StaminaMax: Int = 0x30
    public const val Vitality: Int = 0x38
    public const val Attunement: Int = 0x40
    public const val Endurance: Int = 0x48
    public const val Strength: Int = 0x50
    public const val Dexterity: Int = 0x58
    public const val Intelligence: Int = 0x60
    public const val Faith: Int = 0x68
    public const val Humanity: Int = 0x7C
    public const val Resistance: Int = 0x80
    public const val SoulLevel: Int = 0x88
    public const val Souls: Int = 0x8C
    public const val CharacterName: Int = 0xA0
    public const val Sex: Int = 0xC2
    public const val CharacterClass: Int = 0xC0
    public const val Physique: Int = 0xC7
    public const val WarriorsOfSunlightPoints: Int = 0xE5
    public const val DarkwraithPoints: Int = 0xE6
    public const val PathOfTheDragonPoints: Int = 0xE7
    public const val GravelordServantsPoints: Int = 0xE8
    public const val ForestHunterPoints: Int = 0xE9
    public const val DarkmoonBladePoints: Int = 0xEA
    public const val ChaosServantPoints: Int = 0xEB
    public const val Covenant: Int = 0x10B
    public const val InventoryIndexStart: Int = 0x188
    public const val EquipLeft1Index: Int = 0x1D4
    public const val EquipRight1Index: Int = 0x1D8
    public const val EquipLeft2Index: Int = 0x1DC
    public const val EquipRight2Index: Int = 0x1E0
    public const val EquipArrow1Index: Int = 0x1E4
    public const val EquipBolt1Index: Int = 0x1E8
    public const val EquipArrow2Index: Int = 0x1EC
    public const val EquipBolt2Index: Int = 0x1F0
    public const val EquipHelmetIndex: Int = 0x1F4
    public const val EquipChestIndex: Int = 0x1F8
    public const val EquipGloveIndex: Int = 0x1FC
    public const val EquipPantsIndex: Int = 0x200
    public const val EquipHairIndex: Int = 0x204
    public const val EquipRing1Index: Int = 0x208
    public const val EquipRing2Index: Int = 0x20C
    public const val EquipItem1Index: Int = 0x210
    public const val EquipItem2Index: Int = 0x214
    public const val EquipItem3Index: Int = 0x218
    public const val EquipItem4Index: Int = 0x21C
    public const val EquipItem5Index: Int = 0x220
    public const val Stance: Int = 0x230
    public const val EquipLeft1ID: Int = 0x24C
    public const val EquipRight1ID: Int = 0x250
    public const val EquipLeft2ID: Int = 0x254
    public const val EquipRight2ID: Int = 0x258
    public const val EquipArrow1ID: Int = 0x25C
    public const val EquipBolt1ID: Int = 0x260
    public const val EquipArrow2ID: Int = 0x264
    public const val EquipBolt2ID: Int = 0x268
    public const val EquipHelmetID: Int = 0x26C
    public const val EquipChestID: Int = 0x270
    public const val EquipGloveID: Int = 0x274
    public const val EquipPantsID: Int = 0x278
    public const val EquipHairID: Int = 0x27C
    public const val EquipRing1ID: Int = 0x280
    public const val EquipRing2ID: Int = 0x284
    public const val EquipItem1ID: Int = 0x288
    public const val EquipItem2ID: Int = 0x28C
    public const val EquipItem3ID: Int = 0x290
    public const val EquipItem4ID: Int = 0x294
    public const val EquipItem5ID: Int = 0x298
}
