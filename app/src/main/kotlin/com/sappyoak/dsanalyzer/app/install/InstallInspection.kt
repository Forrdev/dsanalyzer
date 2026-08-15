package com.sappyoak.dsanalyzer.app.install

import kotlinx.serialization.Serializable
import java.nio.file.Path

import com.sappyoak.dsanalyzer.domain.*
import com.sappyoak.dsanalyzer.shared.serialization.SPath

enum class InstallKind {
    Packed,
    Unpacked,
    Mixed,
    Unknown;
}

@Serializable
data class InstallInspection (
    val path: SPath,
    val kind: InstallKind,
    val version: GameVersion? = null,
    val identity: GameIdentity? = null,
    val detection: DetectionResult? = null,
    val archiveCount: Int = 0,
    val looseMarkersFound: Int = 0,
    val problemStr: String? = null
) {
    val isUsable: Boolean get() = kind != InstallKind.Unknown
    val needsExtraction: Boolean get() = kind == InstallKind.Packed

    val summary: String get() = when (kind) {
        InstallKind.Packed -> "${version?.displayName ?: "Packed " } - $archiveCount archive(s). Can be extracted"
        InstallKind.Unpacked -> "${version?.displayName ?: "Unpacked " } - unpacked game, reading loose files directly"
        InstallKind.Mixed -> "Found both loose and archive files, using loose files"
        InstallKind.Unknown -> problemStr ?: "Not a Dark Souls Installation"
    }

    val warning: String? get() = when {
        problemStr != null -> problemStr
        kind == InstallKind.Mixed ->
            "Loose files take priority over the archives. If they are from an old unpack or a " +
             "mod, tools will describe content the game is not running"
        else -> null
    }
}