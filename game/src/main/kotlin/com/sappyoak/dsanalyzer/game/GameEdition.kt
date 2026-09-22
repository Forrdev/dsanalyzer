package com.sappyoak.dsanalyzer.game

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.shared.platform.PointerSize

@Serializable
public enum class GameEdition(
    public val steamAppId: Int,
    public val executableName: String,
    public val pointerSize: PointerSize,
    public val storage: GameStorage,
    /**
     * Rate of the game's logic tick. This is the rate at which the game logic itself ticks. Deliberately not called
     * frame rate as rendering is separate from this, but in many cases they are the same
     */
    public val logicTickHz: Int
) {
    PrepareToDie(
        steamAppId = 211_420,
        executableName = "DARKSOULS.exe",
        pointerSize = PointerSize.IntPointer,
        storage = GameStorage.Archived,
        logicTickHz = 30
    ),

    Remastered(
        steamAppId = 570_940,
        executableName = "DarkSoulsRemastered.exe",
        pointerSize = PointerSize.LongPointer,
        storage = GameStorage.Loose,
        logicTickHz = 60
    );

    companion object {
        public fun forExecutableName(name: String): GameEdition? =
            entries.firstOrNull { it.executableName.equals(name, ignoreCase = true) }
    }
}

/** How an edition ships its data files */
public enum class GameStorage {
    /** Packed into BHD5 archives that index entires by a hash of their path */
    Archived,
    /** Unpacked on disk under their own paths */
    Loose;
}