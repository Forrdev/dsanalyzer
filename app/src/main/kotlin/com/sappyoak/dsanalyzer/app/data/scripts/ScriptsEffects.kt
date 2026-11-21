package com.sappyoak.dsanalyzer.app.data.scripts

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

import com.sappyoak.dsanalyzer.app.data.IndexQueue
import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.data.GameData
import com.sappyoak.dsanalyzer.game.data.InstallationData
import com.sappyoak.dsanalyzer.game.world.flags.readFlags
import com.sappyoak.dsanalyzer.game.world.params.RowNameTranslations
import com.sappyoak.dsanalyzer.game.world.params.loadRowNameTranslations
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.index.EventCallIndex
import com.sappyoak.dsanalyzer.game.world.scripts.index.EventKey
import com.sappyoak.dsanalyzer.game.world.scripts.index.FlagIndex
import com.sappyoak.dsanalyzer.game.world.scripts.index.ScriptIndexing
import com.sappyoak.dsanalyzer.game.world.scripts.index.indexed
import com.sappyoak.dsanalyzer.game.world.text.TextLanguage
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException

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
            is ScriptsEffect.IndexScripts -> queue.run("scripts") { game.indexScripts(effect.scripts) }
            is ScriptsEffect.ReadFlags -> queue.run("flags") { game.flagReading(effect.calls) }
        }
    }

    private suspend fun GameData.indexScripts(scripts: List<ScriptId>): ScriptsMessage.ScriptsIndexed {
        val read = coroutineScope {
            scripts.map { script -> async(Dispatchers.Default) { script to indexingOf(script) } }.awaitAll().toMap()
        }
        val built = read.values.filterNotNull()

        val calls = EventCallIndex.of(
            found = built.flatMap { it.calls },
            needs = built.fold(emptyMap<EventKey, Int>()) { all, one -> all + one.needs },
            scripts = built.size
        )

        logger.atInfo {
            message = "Scripts indexed"
            payload = mapOf(
                "scripts" to built.size,
                "callSites" to calls.sites,
                "callerArgumentOffset" to calls.base
            )
        }

        return ScriptsMessage.ScriptsIndexed(
            flags = FlagIndex.of(found = built.flatMap { it.flagUses }, scripts = built.size),
            calls = calls,
            unreadable = read.filterValues { it == null }.keys.toList()
        )
    }

    private suspend fun GameData.flagReading(calls: EventCallIndex): ScriptsMessage.FlagsRead {
        val rowNames = if (translated.first()) loadRowNameTranslations() else RowNameTranslations.None
        val reading = readFlags(NAME_LANGUAGE, calls, rowNames)
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

    private suspend fun GameData.indexingOf(script: ScriptId): ScriptIndexing? = try {
        this.script(script)?.indexed()
    } catch (err: BinaryFormatException) {
        logger.atWarn {
            message = "Script left out of index"
            cause = err
            payload = mapOf("script" to  script.label)
        }
        null
    }
}

public sealed interface ScriptsEffect {
    public val installation: Installation

    public data class LoadCatalog(override val installation: Installation) : ScriptsEffect

    public data class LoadScript(
        override val installation: Installation,
        public val script: ScriptId
    ) : ScriptsEffect

    /** Names every flag and finds where else each is used, reading what [calls] says common events are handed */
    public data class ReadFlags(
        override val installation: Installation,
        public val calls: EventCallIndex
    ) : ScriptsEffect

    public data class IndexScripts(
        override val installation: Installation,
        public val scripts: List<ScriptId>
    ) : ScriptsEffect
}
