package com.sappyoak.dsanalyzer.runtime.ptde

import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
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

    /**
     * The two bytes as the map id the rest of the tool addresses things by.
     *
     * [world] is taken as the leading number and [area] as the second, so 10 and 2 is m10_02_00_00
     */
    public fun mapId(world: Int, area: Int): MapId = MapId.of(area = world, block = area)
}

/** Where the DOS stub records the PE header's offset */
private const val E_LFANEW_AT = 0x3CL

/** `TimeDateStamp`, measured from the start of the PE signature */
private const val TIME_DATE_STAMP_AT = 8L

/** The MSVC Rich header's encrypted `DanS` marker */
private const val RICH_MARKER_AT = 0x80L

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

public fun ProcessMemory.ptdeVersion(module: AddressRange): PTDEVersion? {
    val lfanew = dwordAt(module.start + E_LFANEW_AT) ?: return null
    val timestamp = dwordAt(module.start + lfanew.toLong() + TIME_DATE_STAMP_AT) ?: return null
    val richMarker = dwordAt(module.start + RICH_MARKER_AT) ?: return null
    return PTDEVersion.of(timestamp, richMarker)
}

private fun ProcessMemory.dwordAt(at: Address): UInt? = read(at, Int.SIZE_BYTES)
    ?.let { ByteBuffer.wrap(it).order(ByteOrder.LITTLE_ENDIAN).int.toUInt() }
