package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object GameMan {
    public val Pointer: GamePointer = GamePointer(
        name = "GameMan",
        base = PTDESignatures.GameMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0xB90
    )

    /** The AI and combat generator's two state words, `s1` then `s2` (`ptde-rng.md`) */
    public const val RngState: Int = 0x58

    /** The MT19937 instance, [TwisterSize] bytes: seed, `left`, a cursor into its own state, `state[624]` */
    public const val Twister: Int = 0x60
    public const val TwisterSize: Int = 0x9CC
    public const val TwisterLeft: Int = 0x04
    public const val TwisterCursor: Int = 0x08
    public const val TwisterState: Int = 0x0C

    public const val PointWarpMap: Int = 0xAD0
    public const val PointWarpPosition: Int = 0xAE0
    public const val PointWarpAngle: Int = 0xAF0
    public const val PointWarpFlag: Int = 0xB00
    public const val LastBonfire: Int = 0xB04

    /** X, Y and Z consecutively from here */
    public const val StablePosition: Int = 0xB70
    public const val StableAngle: Int = 0xB84
}

public object GameDataMan {
    public val Pointer: GamePointer = GamePointer(
        name = "GameDataMan",
        base = PTDESignatures.GameDataMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0x80
    )

    public const val PlayerGameDataPointer: Int = 0x08
    public const val NewGameCount: Int = 0x3C
    public const val InGameTimeMillis: Int = 0x68
}

public object WorldArea {
    public val Pointer: GamePointer = GamePointer(
        name = "WorldArea",
        base = PTDESignatures.WorldArea,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0xA2C
    )

    public const val Area: Int = 0xA12
    public const val World: Int = 0xA13
}

/** Where a module image identifies its build, from the image base */
internal object ImageHeader {
    /** Where the DOS stub records the PE header's offset */
    const val PeOffset: Long = 0x3C

    /** `TimeDateStamp`, measured from the start of the PE signature */
    const val TimeDateStamp: Long = 8

    /** The MSVC Rich header's encrypted `DanS` marker */
    const val RichMarker: Long = 0x80
}

public enum class PTDEVersion(
    private val timestamp: UInt?,
    private val richMarker: UInt
) {
    Steam(null, 0xFC293654u),
    Debug(null, 0xCE9634B4u),
    SteamworksBeta(0x54B6EFC0u, 0xE91B11E2u);

    public companion object {
        public fun of(timestamp: UInt, richMarker: UInt): PTDEVersion? =
            entries.firstOrNull { it.timestamp == timestamp }
                ?: entries.firstOrNull { it.richMarker == richMarker }
    }
}

/** A 'FileMan' capsule ring and the capsule state that means "read requested" or not yet completed */
public object FileRing {
    public const val Count: Int = 5
    public const val Head: Int = 0x20
    public const val Tail: Int = 0x24
    public const val Capacity: Int = 0x28
    public const val Slots: Int = 0x2C

    /** A capsule's state byte: 0 none, 1 requested, 2 read done, 3 finishing, 4 loaded */
    public const val CapsuleState: Int = 0x20
    public const val Requested: Int = 1
}
