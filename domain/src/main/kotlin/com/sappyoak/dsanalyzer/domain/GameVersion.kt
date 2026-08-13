package com.sappyoak.dsanalyzer.domain

import kotlinx.serialization.Serializable

@Serializable
enum class GameVersion(
    val displayName: String,
    val shortName: String,
    val processName: String,
    val pointerSize: Int,
    val usesArchives: Boolean,
    val frameRate: Int
) {
    PTDE(
        displayName = "Prepare to Die Edition",
        shortName = "PTDE",
        processName = "DARKSOULS.exe",
        pointerSize = 4,
        usesArchives = true,
        frameRate = 30
    ),

    Remastered(
        displayName = "Remastered",
        shortName = "DSR",
        processName = "DarkSoulsRemastered.exe",
        pointerSize = 8,
        usesArchives = false,
        frameRate = 60
    );

    val isRemastered: Boolean get() = this == Remastered
    val millisPerFrame: Double get() = 1000.0 / frameRate

    fun framesFromMillis(millis: Long): Long = (millis * frameRate / 1000)
}