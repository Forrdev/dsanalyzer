package com.sappyoak.dsanalyzer.app.data.scripts

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.formats.emevd.Emevd
import com.sappyoak.dsanalyzer.formats.emevd.EventNames
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Emedf
import com.sappyoak.dsanalyzer.formats.emevd.emedf.decode
import com.sappyoak.dsanalyzer.formats.emevd.emedf.flagUses
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.InstallationFiles
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest
import com.sappyoak.dsanalyzer.game.world.maps.availableMaps
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.availableScripts
import com.sappyoak.dsanalyzer.game.world.scripts.loadEventNameTranslations
import com.sappyoak.dsanalyzer.game.world.scripts.loadInstructionDefinitions
import com.sappyoak.dsanalyzer.game.world.scripts.loadScript
import com.sappyoak.dsanalyzer.game.world.scripts.loadScriptNames
import com.sappyoak.dsanalyzer.game.world.scripts.translatedBy
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException

private val logger = KotlinLogging.logger {  }

public class ScriptsEffects(
    private val files: InstallationFiles
) : EffectRunner<ScriptsEffect, ScriptsMessage> {
    private val definitions by lazy { loadInstructionDefinitions() }

    private val translations by lazy { loadEventNameTranslations() }

    /** One load of each sort at a time, so switching scripts quickly cancels the load being replaced */
    override fun keyOf(effect: ScriptsEffect): Any = effect::class

    override fun execute(effect: ScriptsEffect): Flow<ScriptsMessage> = flow { emit(perform(effect)) }

    override fun onFailure(effect: ScriptsEffect, failure: Throwable): ScriptsMessage {
        val reason = failure.message ?: failure.toString()
        return if (effect is ScriptsEffect.IndexScripts) ScriptsMessage.IndexFailed(reason)
        else ScriptsMessage.Failed(reason)
    }

    private suspend fun perform(effect: ScriptsEffect): ScriptsMessage = when (effect) {
        is ScriptsEffect.LoadCatalog -> ScriptsMessage.CatalogLoaded(
            files.use(effect.installation) { game ->
                game.availableScripts(game.availableMaps(loadFileManifest(effect.installation.build.edition)))
            }
        )

        is ScriptsEffect.LoadScript -> ScriptsMessage.ScriptLoaded(
            files.use(effect.installation) { game ->
                game.contents(effect.script)
            }
        )

        is ScriptsEffect.IndexScripts -> files.use(effect.installation) { game ->
            game.indexScripts(effect.scripts)
        }
    }

    private fun GameFiles.contents(script: ScriptId): ScriptContents {
        val emevd = checkNotNull(loadScript(script)) { "$script has no event script" }
        val names = loadScriptNames(script)?.translatedBy(translations) ?: EventNames.Empty
        return emevd.summarize(script, names, definitions)
    }

    private fun GameFiles.indexScripts(scripts: List<ScriptId>): ScriptsMessage.ScriptsIndexed {
        val read = scripts.associateWith { script -> indexingOf(script) }
        val built = read.values.filterNotNull()

        return ScriptsMessage.ScriptsIndexed(
            flags = FlagIndex.of(
                found = built.flatMap { it.flagUses },
                scripts = built.size
            ),
            calls = EventCallIndex.of(
                found = built.flatMap { it.calls },
                needs = built.fold(emptyMap<EventKey, Int>()) { all, one -> all + one.needs },
                scripts = built.size
            ),
            unreadable = read.filterValues { it == null }.keys.toList()
        )
    }

    private fun GameFiles.indexingOf(script: ScriptId): ScriptIndexing? = try {
        loadScript(script)?.indexed(script, definitions)
    } catch (err: BinaryFormatException) {
        logger.atWarn {
            message = "Script left out of the indexes"
            cause = err
            payload = mapOf("script" to script.label)
        }
        null
    }
}

private fun Emevd.flagUses(script: ScriptId, definitions: Emedf): List<FoundFlagUse> =
    events.flatMap { event ->
        event.decode(definitions).flatMap { instruction ->
            instruction.flagUses(event.id).map { use -> FoundFlagUse(script, event.id, use) }
        }
    }

public sealed interface ScriptsEffect {
    public data class LoadCatalog(public val installation: Installation) : ScriptsEffect

    public data class LoadScript(
        public val installation: Installation,
        public val script: ScriptId
    ) : ScriptsEffect

    public data class IndexScripts(
        public val installation: Installation,
        public val scripts: List<ScriptId>
    ) : ScriptsEffect
}
