package com.sappyoak.dsanalyzer.game.install

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.shared.SPath
import com.sappyoak.dsanalyzer.game.identity.*

@Serializable
data class InstallInspectionResult(
    val path: SPath,
    val kind: InstallKind,
    val version: GameVersion? = null,
    val identity: GameIdentity? = null,
    val detection: GameVersionDetector.Outcome? = null,
    val archiveCount: Int = 0,
    val looseFilesFound: Int = 0,
    val problem: String? = null
) {
    val isUsable: Boolean get() = kind != InstallKind.Unknown
    val needsExtraction: Boolean get() = kind == InstallKind.Packed

    val summary: String get() = when (kind) {
        InstallKind.Packed -> "${version?.displayName ?: "Packed " } - $archiveCount archive(s). Can be extracted"
        InstallKind.Unpacked -> "${version?.displayName ?: "Unpacked " } - unpacked game, reading loose files directly"
        InstallKind.Mixed -> "Found both loose and archive files, using loose files"
        InstallKind.Unknown -> problem ?: "Not a Dark Souls Installation"
    }

    val warning: String? get() = when {
        problem != null -> problem
        kind == InstallKind.Mixed ->
            "Loose files take priority over the archives. If they are from an old unpack or a " +
                    "mod, tools will describe content the game is not running"
        else -> null
    }
}