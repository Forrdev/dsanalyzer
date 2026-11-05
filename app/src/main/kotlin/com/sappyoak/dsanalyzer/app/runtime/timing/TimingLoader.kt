package com.sappyoak.dsanalyzer.app.runtime.timing

import io.github.oshai.kotlinlogging.KotlinLogging

import com.sappyoak.dsanalyzer.formats.msb.model.ModelType
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.data.InstallationData
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId
import com.sappyoak.dsanalyzer.game.world.animations.loadTae
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException

private val logger = KotlinLogging.logger { }

private val ENEMY_MODEL = Regex("""^c(\d{4})$""")

/**
 * The human model who's animations come from several files picked by what is held, so one set of
 * windows would be wrong half the time. Left out until that choice is read from the game
 */
internal const val HUMAN_MODEL = 0

public class TimingLoader(private val data: InstallationData) {
    public suspend fun forMap(installation: Installation, map: MapId, known: Set<Int>): Map<Int, ModelTimings> {
        val game = data.of(installation)
        val models = game.msb(map)?.models.orEmpty()
            .filter { it.type == ModelType.Enemy }
            .mapNotNull { ENEMY_MODEL.matchEntire(it.name)?.groupValues?.get(1)?.toIntOrNull() }
            .filterNot { it == HUMAN_MODEL || it in known }
            .toSet()
        return game.read { it.read(models) }
    }

    public suspend fun forModels(installation: Installation, models: Set<Int>): Map<Int, ModelTimings> =
        data.of(installation).read { it.read(models) }

    private fun GameFiles.read(models: Set<Int>): Map<Int, ModelTimings> = models.associateWith { model ->
        try {
            timingsOf(loadTae(AnimationSetId.Character(model)))
        } catch (failure: BinaryFormatException) {
            logger.warn(failure) { "Could not read the animations of c$model" }
            ModelTimings.None
        }
    }
}
