package com.sappyoak.dsanalyzer.app.state

import kotlinx.serialization.Serializable
import java.nio.file.Path

import com.sappyoak.dsanalyzer.game.identity.GameBuildId
import com.sappyoak.dsanalyzer.game.identity.GameVersion
import com.sappyoak.dsanalyzer.game.install.InstallInspectionResult
import com.sappyoak.dsanalyzer.game.install.InstallKind
import com.sappyoak.dsanalyzer.shared.SInstant

@Serializable
data class SetupState(
    val gamePath: Path? = null,

    /**
     * Where the tool keeps its own files
     *
     * Asked about rather than assumed because the cache inside it can run to several gigabytes
     * and the right drive for that is something the user should decide
     */
    val dataPath: Path? = null,

    /**
     * The destination for extracted game files if the user decides to extract, by default this
     * will be set to a location inside the dataPath folder unless the user explicitly modifies it
     */
    val extractedPath: Path? = null,
    val installKind: InstallKind = InstallKind.Unknown,
    val extraction: ExtractionState = ExtractionState.NotStarted,
    val inspection: InstallInspectionResult? = null,
    /** Default install locations that exist */
    val suggestedPaths: List<String> = emptyList(),
    /** The estimates bytes that extraction would take up */
    val estimatedBytesToExtract: Long = 0,
    /** Known installations, tracked for switching without a restart */
    val installations: List<InstallationEntry> = emptyList(),
    val activeInstallation: String? = null,

    /**
     * Whether the recorded game path still resolves.
     *
     * Checked on load and after any change rather than derived on read for perf
     */
    val gamePathResolves: Boolean = false,
    /** The detected game, or null when detection could not decide */
    val version: GameVersion? = null,

    /**
     * True when nothing identifying could be sampled.
     *
     * Distinct from unknown version: an executable name can identify the game with certainty
     * while the data files themselves are unreadable, and that combination fails at the
     * first parse rather than at detection
     */
    val identityUnknown: Boolean = false,

    /** Whether the selected data path is writable */
    val dataPathWritable: Boolean = true,
    val freeSpaceBytes: Long = 0L
) {
    val installSummary: String? get() = inspection?.let { it.warning ?: it.summary }
    val readyToScan: Boolean get() = gamePath != null && installKind != InstallKind.Unknown
    val canExtract: Boolean get() = installKind == InstallKind.Packed

    val extractionRationale: String get() = buildString {
        append("This is optional -- the archives are read directly. ")
        append("Extracting avoids re-inflating on later scan and makes several diagnostics/displays easier and ")
        append("lets other tools read the files. ")
    }
}

@Serializable
data class InstallationEntry(
    val key: String,
    val path: Path,
    val version: GameVersion?,
    val buildId: GameBuildId?,
    val isActive: Boolean,
    val isAvailable: Boolean,
    val lastScannedAt: SInstant?,
    val cacheSizeBytes: Long
) {
    val displayName: String get() = buildString {
        append(version?.displayName ?: "Unknown")
        buildId?.let { append(" · $it")}
    }
}