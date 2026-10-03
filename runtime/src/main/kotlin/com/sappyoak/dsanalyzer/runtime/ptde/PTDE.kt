package com.sappyoak.dsanalyzer.runtime.ptde

import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

/** The address the executable asks to be loaded at, which the version address is quoted against */
private const val PREFERRED_BASE = 0x400000L

public enum class PTDEBuild(private val version: UInt) {
    Steam(0xFC293654u),
    Debug(0xCE9634B4u),
    SteamworksBeta(0xE91B11E2u);

    public companion object {
        public const val VersionAddress: Long = 0x400080

        public fun of(version: UInt): PTDEBuild? = entries.firstOrNull { it.version == version }
    }
}

public fun ProcessMemory.ptdeBuild(module: AddressRange): PTDEBuild? {
    val at = module.start + (PTDEBuild.VersionAddress - PREFERRED_BASE)
    val word = read(at, Int.SIZE_BYTES) ?: return null
    return PTDEBuild.of(ByteBuffer.wrap(word).order(ByteOrder.LITTLE_ENDIAN).int.toUInt())
}

public val PTDEPointers: List<GamePointer> = listOf(
    CharData.Pointer,
    CharMapData.Pointer,
    CharPosData.Pointer,
    AnimData.Pointer,
    PlayerStats.Pointer,
    WorldState.Pointer,
    WorldArea.Pointer,
    DeathCam.Pointer,
    GameDataMan.Pointer,
    EventFlags.Pointer
)