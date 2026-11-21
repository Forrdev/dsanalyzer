package com.sappyoak.dsanalyzer.app.data.scripts

import com.sappyoak.dsanalyzer.app.data.IndexQueue
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.data.GameData
import com.sappyoak.dsanalyzer.game.data.InstallationData
import com.sappyoak.dsanalyzer.game.world.flags.readFlags
import com.sappyoak.dsanalyzer.game.world.params.RowNameTranslations
import com.sappyoak.dsanalyzer.game.world.params.loadRowNameTranslations
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.text.TextLanguage

private val logger = KotlinLogging.logger {  }

/** The language flag names are read in, matching the params view */
private val NAME_LANGUAGE = TextLanguage.English

public class ScriptsEffects(
    private val data: InstallationData,
    private val queue: IndexQueue,
    /** Whether the setting asks for translated names; read when flags are named */
    private val translated: Flow<Boolean>
) : EffectRunner<ScriptsEffect, ScriptsMessage> {
    /** One load of each sort at a time, so switching scripts quickly cancels the load being replaced */
    override fun keyOf(effect: ScriptsEffect): Any = effect::class

    override fun execute(effect: ScriptsEffect): Flow<ScriptsMessage> = flow { emit(perform(effect)) }

    override fun onFailure(effect: ScriptsEffect, failure: Throwable): ScriptsMessage {
        val reason = failure.message ?: failure.toString()
        return when (effect) {
            is ScriptsEffect.IndexScripts -> ScriptsMessage.IndexFailed(reason)
            is ScriptsEffect.ReadFlags -> ScriptsMessage.NamingFailed(reason)
            else -> ScriptsMessage.Failed(reason)
        }
    }

    private suspend fun perform(effect: ScriptsEffect): ScriptsMessage {
        val game = data.of(effect.installation)
        return when (effect) {
            is ScriptsEffect.LoadCatalog -> ScriptsMessage.CatalogLoaded(game.catalog().scripts)
            is ScriptsEffect.LoadScript -> ScriptsMessage.ScriptLoaded(
                checkNotNull(game.script(effect.script)) { "${effect.script} has no event script" }
                    .summarize(game.scriptNames(effect.script))
            )
            is ScriptsEffect.IndexScripts -> queue.run("scripts") { game.indexing() }
            is ScriptsEffect.ReadFlags -> queue.run("flags") { game.flagReading() }
        }
    }

    private suspend fun GameData.indexing(): ScriptsMessage.ScriptsIndexed {
        val index = scriptIndex()
        return ScriptsMessage.ScriptsIndexed(flags = index.flags, calls = index.calls, unreadable = index.unreadable)
    }

    private suspend fun GameData.flagReading(): ScriptsMessage.FlagsRead {
        val rowNames = if (translated.first()) loadRowNameTranslations() else RowNameTranslations.None
        val reading = readFlags(NAME_LANGUAGE, rowNames)
        logger.atInfo {
            message = "Flags read"
            payload = mapOf(
                "named" to reading.names.size,
                "byKind" to reading.names.coverage(),
                "usedOutsideScripts" to reading.sites.flagIds.size
            )
        }
        return ScriptsMessage.FlagsRead(reading)
    }
}



public sealed interface ScriptsEffect {
    public val installation: Installation

    public data class LoadCatalog(override val installation: Installation) : ScriptsEffect

    public data class LoadScript(
        override val installation: Installation,
        public val script: ScriptId
    ) : ScriptsEffect

    /** Names every flag and finds where else each is used */
    public data class ReadFlags(override val installation: Installation) : ScriptsEffect

    /** Indexes every script of the installation, for flag uses and event calls */
    public data class IndexScripts(override val installation: Installation) : ScriptsEffect
}
