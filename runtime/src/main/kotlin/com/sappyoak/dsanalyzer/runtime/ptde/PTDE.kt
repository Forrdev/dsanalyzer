package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

public enum class PTDEBuild(private val version: UInt) {
    Steam(0xFC293654u),
    Debug(0xCE9634B4u),
    SteamworksBeta(0xE91B11E2u);

    public companion object {
        public const val VersionAddress: Long = 0x400080

        public fun of(version: UInt): PTDEBuild? = entries.firstOrNull { it.version == version }
    }
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