package com.sappyoak.dsanalyzer.app.install

import kotlinx.serialization.Serializable
import java.nio.file.Path
import kotlin.io.path.exists

import com.sappyoak.dsanalyzer.domain.GameAssetPaths
import com.sappyoak.dsanalyzer.domain.GameVersion

object GameDetector {
    private val SIGNALS: List<Signal> = listOf(
        Signal(
            description = "Executable name",
            confidence = DetectionResult.Confidence.Certain
        ) { root ->
            when {
                root.resolve("DarkSoulsRemastered.exe").exists() -> GameVersion.Remastered
                root.resolve("DARKSOULS.exe").exists() -> GameVersion.PTDE
                else -> null
            }
        },

        Signal(
            description = "Archive format",
            confidence = DetectionResult.Confidence.Strong
        ) { root ->
            if (root.resolve("dvdbnd0.bhd5").exists()) GameVersion.PTDE else null
        },

        Signal(
            description = "Remastered only content",
            confidence = DetectionResult.Confidence.Moderate
        ) { root ->
            if (GameAssetPaths.REMASTERED_ONLY_FILE_MARKERS.any { root.resolve(it).exists() }) {
                GameVersion.Remastered
            } else null
        }
    )

    fun detect(path: Path): DetectionResult {
        val parent = path.parent

        for (signal in SIGNALS) {
            for (root in listOfNotNull(path, parent)) {
                signal.check(root)?.let { version ->
                    return DetectionResult(
                        version = version,
                        signal = signal.description,
                        confidence = signal.confidence,
                        checkedIn = root
                    )
                }
            }
        }

        return DetectionResult(
            version = null,
            signal = "No identifying file found",
            confidence = DetectionResult.Confidence.None,
            checkedIn = path
        )
    }

    private class Signal(
        val description: String,
        val confidence: DetectionResult.Confidence,
        val check: (Path) -> GameVersion?
    )
}

@Serializable
data class DetectionResult(
    val version: GameVersion?,
    val signal: String,
    val confidence: Confidence,
    val checkedIn: Path
) {
    val isConfident: Boolean get() = confidence >= Confidence.Strong

    fun explain(): String = when {
        version == null ->
            "Could not identify the game. No executable, Steam library or archive was found in " +
             "$checkedIn or its parent -- please choose the version manually"
        confidence == Confidence.Moderate ->
            "Detected ${version.displayName} from $signal, which is a weaker signal than an " +
            "executable name. Worth confirming"
        else -> "Detected ${version.displayName} from $signal"
    }

    enum class Confidence {
        None,
        Moderate,
        Strong,
        Certain;
    }
}