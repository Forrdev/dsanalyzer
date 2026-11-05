package com.sappyoak.dsanalyzer.app.data.scripts

import com.sappyoak.dsanalyzer.app.data.IndexQueue
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.formats.emevd.emedf.flagUses
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.data.DecodedScript
import com.sappyoak.dsanalyzer.game.data.GameData
import com.sappyoak.dsanalyzer.game.data.InstallationData
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException

private val logger = KotlinLogging.logger {  }

public class ScriptsEffects(
    private val data: InstallationData,
    private val queue: IndexQueue,
) : EffectRunner<ScriptsEffect, ScriptsMessage> {
    /** One load of each sort at a time, so switching scripts quickly cancels the load being replaced */
    override fun keyOf(effect: ScriptsEffect): Any = effect::class

    override fun execute(effect: ScriptsEffect): Flow<ScriptsMessage> = flow { emit(perform(effect)) }

    override fun onFailure(effect: ScriptsEffect, failure: Throwable): ScriptsMessage {
        val reason = failure.message ?: failure.toString()
        return if (effect is ScriptsEffect.IndexScripts) ScriptsMessage.IndexFailed(reason)
        else ScriptsMessage.Failed(reason)
    }

    private suspend fun perform(effect: ScriptsEffect): ScriptsMessage {
        val game = data.of(effect.installation)
        return when (effect) {
            is ScriptsEffect.LoadCatalog -> ScriptsMessage.CatalogLoaded(game.catalog().scripts)
            is ScriptsEffect.LoadScript -> ScriptsMessage.ScriptLoaded(
                checkNotNull(game.script(effect.script)) { "${effect.script} has no event script" }
                    .summarize(game.scriptNames(effect.script))
            )
            is ScriptsEffect.IndexScripts -> queue.run { game.indexFlags(effect.scripts) }
        }
    }

    private suspend fun GameData.indexFlags(scripts: List<ScriptId>): ScriptsMessage.ScriptsIndexed {
        val read = scripts.associateWith { script -> flagUsesIn(script) }
        return ScriptsMessage.ScriptsIndexed(
            flags = FlagIndex.of(
                found = read.values.filterNotNull().flatten(),
                scripts = read.count { (_, uses) -> uses != null }
            ),
            unreadable = read.filterValues { it == null }.keys.toList()
        )
    }


    private suspend fun GameData.flagUsesIn(script: ScriptId): List<FoundFlagUse>? = try {
        this.script(script)?.flagUses()
    } catch (err: BinaryFormatException) {
        logger.atWarn {
            message = "Script left out of the flag index"
            cause = err
            payload = mapOf("script" to script.label)
        }
        null
    }
}

private fun DecodedScript.flagUses(): List<FoundFlagUse> =
    events.flatMap { (eventId, instructions) ->
        instructions.flatMap { instruction ->
            instruction.flagUses(eventId).map { use -> FoundFlagUse(script, eventId, use) }
        }
    }

public sealed interface ScriptsEffect {
    public val installation: Installation

    public data class LoadCatalog(override val installation: Installation) : ScriptsEffect

    public data class LoadScript(
        override val installation: Installation,
        public val script: ScriptId
    ) : ScriptsEffect

    public data class IndexScripts(
        override val installation: Installation,
        public val scripts: List<ScriptId>
    ) : ScriptsEffect
}
