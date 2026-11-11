package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/** Through [GameMan.StableAngle] */
private const val GAME_MAN_SIZE: Int = 0xBC0

/** Through [GameDataMan.InGameTimeMillis] */
private const val GAME_DATA_MAN_SIZE: Int = 0xA8

/** Enough to cover [MenuMan.ArrayBase] and a reasonable run of  entries */
private const val MENU_MAN_SIZE: Int = 0x300

public object GameMan {
    public val Pointer: GamePointer = GamePointer(
        name = "GameMan",
        base = DSRSignatures.GameMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = GAME_MAN_SIZE
    )

    public const val WarpPosition: Int = 0xA80
    public const val WarpAngle: Int = 0xA90
    public const val LastBonfire: Int = 0xB34
    public const val StablePosition: Int = 0xBA0
    public const val StableAngle: Int = 0xBB4
}

public object GameDataMan {
    public val Pointer: GamePointer = GamePointer(
        name = "GameDataMan",
        base = DSRSignatures.GameDataMan,
        offsets = listOf(0L),
        lifetime = Lifetime.World,
        size = GAME_DATA_MAN_SIZE
    )

    public const val TrophyEquipDataPointer: Int = 0x08
    public const val PlayerGameDataPointer: Int = 0x10
    public const val LastBloodstainPosition: Int = 0x40
    public const val ProfileSummaryPointer: Int = 0x60
    public const val PcOptionDataPointer: Int = 0x68
    public const val QwcDataPointer: Int = 0x70
    public const val NewGameCount: Int = 0x78
    public const val InGameTimeMillis: Int = 0xA4
}

public object MenuMan {
    public val Pointer: GamePointer = GamePointer(
        name = "MenuMan",
        base = DSRSignatures.MenuMan,
        offsets = listOf(0L),
        lifetime = Lifetime.World,
        size = MENU_MAN_SIZE
    )

    public const val ArrayBase: Int = 0x30
}

public object PlayerGameData {
    public val Pointer: GamePointer = GamePointer(
        name = "PlayerGameData",
        base = DSRSignatures.GameDataMan,
        offsets = listOf(0L, 0x10L),
        lifetime = Lifetime.World,
        size = 0x120
    )

    public const val PlayerNo: Int = 0x10

    public const val Hp: Int = 0x14

    /** The effective max, which is what the in-game bar is drawn against */
    public const val MaxHp: Int = 0x18

    /** The max before equipment and effects */
    public const val BaseMaxHp: Int = 0x1C

    public const val Mp: Int = 0x20
    public const val BaseMaxMp: Int = 0x24
    public const val MaxMp: Int = 0x28

    public const val Sp: Int = 0x30
    public const val MaxSp: Int = 0x34
    public const val BaseMaxSp: Int = 0x38

    /** The eight levelled stats, stride 8 */
    public const val Stats: Int = 0x40
    public const val StatStride: Int = 0x08

    public const val Humanity: Int = 0x84
    public const val Durability: Int = 0x88
    public const val SoulLevel: Int = 0x90
    public const val Souls: Int = 0x94
    public const val SoulMemory: Int = 0x98

    public const val NameLocal: Int = 0xA8

    public const val Gender: Int = 0xCA
    public const val ArcheType: Int = 0xCE
    public const val Appearance: Int = 0xCF
    public const val Gift: Int = 0xD0
    public const val MultiplayerCount: Int = 0xD4
    public const val CoopSuccessCount: Int = 0xD8
    public const val ChaosServantContribution: Int = 0xE8
    public const val WarriorOfSunlightPoints: Int = 0xED
    public const val DarkwraithPoints: Int = 0xEE
    public const val PathOfTheDragonPoints: Int = 0xEF
    public const val GravelordServantPoints: Int = 0xF0
    public const val ForestHunterPoints: Int = 0xF1
    public const val DarkmoonBladePoints: Int = 0xF2
    public const val ChaosServantPoints: Int = 0xF3
    public const val Indictments: Int = 0xF4

    public const val PoisonResist: Int = 0x100
    public const val BloodResist: Int = 0x104
    public const val DiseaseResist: Int = 0x108
    public const val CurseResist: Int = 0x10C
    public const val CurrentCovenant: Int = 0x113
    public const val FaceType: Int = 0x114
    public const val HairType: Int = 0x115
    public const val HairColor: Int = 0x116
    public const val CurseLevel: Int = 0x117
    public const val InvadeType: Int = 0x118
    public const val WeaponMemory: Int = 0x119
    public const val EstusLevel: Int = 0x11A
    public const val NameRemote: Int = 0x12C

    public const val EquipLeft1Index: Int = 0x324
    public const val EquipRight1Index: Int = 0x328
    public const val EquipLeft2Index: Int = 0x32C
    public const val EquipRight2Index: Int = 0x330
    public const val EquipArrow1Index: Int = 0x334
    public const val EquipBolt1Index: Int = 0x338
    public const val EquipArrow2Index: Int = 0x33C
    public const val EquipBolt2Index: Int = 0x340
    public const val EquipHelmetIndex: Int = 0x344
    public const val EquipChestIndex: Int = 0x348
    public const val EquipGloveIndex: Int = 0x34C
    public const val EquipPantsIndex: Int = 0x350
    public const val EquipHairIndex: Int = 0x354
    public const val EquipRing1Index: Int = 0x358
    public const val EquipRing2Index: Int = 0x35C
    public const val EquipItem1Index: Int = 0x360
    public const val EquipItem2Index: Int = 0x364
    public const val EquipItem3Index: Int = 0x368
    public const val EquipItem4Index: Int = 0x36C
    public const val EquipItem5Index: Int = 0x370

    public const val EquipMagicData: Int = 0x418
}

public object EventFlags {
    /** A byte. Both the resolver and the setter return early while it is clear */
    public const val Ready: Int = 0x224
    /** Holds a pointer to the group array, which lives inline at `mgr + 0x28` */
    public const val GroupTable: Int = 0x218
    public const val GroupStride: Int = 0x18
    public const val AreaStride: Int = 0xA8
    public const val SectionStride: Int = 0x10
    public const val SectionsPerArea: Int = 10
    public const val BitArrayBytes: Int = 0x80
}
