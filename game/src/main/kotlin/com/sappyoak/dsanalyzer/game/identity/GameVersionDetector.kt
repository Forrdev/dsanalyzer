package com.sappyoak.dsanalyzer.game.identity

import kotlinx.serialization.Serializable
import kotlin.io.path.exists
import java.nio.file.Path

import com.sappyoak.dsanalyzer.game.assets.GameAssetPaths

class GameVersionDetector(private val signals: List<Signal> = DEFAULT_SIGNALS) {
    fun detect(path: Path): Outcome {
        for (signal in signals) {
            for (root in listOfNotNull(path, path.parent)) {
                signal.check(root)?.let { version -> Outcome(
                    version = version,
                    signal = signal.description,
                    confidence = signal.confidence,
                    checkedIn = root
                )}
            }
        }

        return Outcome(
            version = null,
            signal = "No identifying files found",
            confidence = Outcome.Confidence.None,
            checkedIn = path
        )
    }

    class Signal(
        val description: String,
        val confidence: Outcome.Confidence,
        val check: (Path) -> GameVersion?
    )

    @Serializable
    data class Outcome(
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

    companion object {
        private val DEFAULT_SIGNALS: List<Signal> = listOf(
            Signal(
                description = "Executable name",
                confidence = Outcome.Confidence.Certain
            ) { root ->
                GameVersion.entries.firstOrNull { root.resolve(it.processName).exists() }
            },

            Signal(
                description = "Archive format",
                confidence = Outcome.Confidence.Strong
            ) { root ->
              if (root.resolve("dvdbnd0,bhd5").exists() ) GameVersion.PTDE else null
            },

            Signal(
                description = "Remastered only content",
                confidence = Outcome.Confidence.Moderate
            ) { root ->
                if (GameAssetPaths.REMASTERED_ONLY_FILE_MARKERS.any { root.resolve(it).exists() }) {
                    GameVersion.Remastered
                } else null
            }
        )
    }
}
