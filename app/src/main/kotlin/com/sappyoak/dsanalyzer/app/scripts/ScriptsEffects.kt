package com.sappyoak.dsanalyzer.app.scripts

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.formats.emevd.EventNames
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.InstallationFiles
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest
import com.sappyoak.dsanalyzer.game.world.maps.availableMaps
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.availableScripts
import com.sappyoak.dsanalyzer.game.world.scripts.loadInstructionDefinitions
import com.sappyoak.dsanalyzer.game.world.scripts.loadScript
import com.sappyoak.dsanalyzer.game.world.scripts.loadScriptNames

public class ScriptsEffects(
    private val files: InstallationFiles
) : EffectRunner<ScriptsEffect, ScriptsMessage> {
    private val definitions by lazy { loadInstructionDefinitions() }

    /** One load of each sort at a time, so switching scripts quickly cancels the load being replaced */
    override fun keyOf(effect: ScriptsEffect): Any = effect::class

    override fun execute(effect: ScriptsEffect): Flow<ScriptsMessage> = flow { emit(perform(effect)) }

    override fun onFailure(effect: ScriptsEffect, failure: Throwable): ScriptsMessage =
        ScriptsMessage.Failed(failure.message ?: failure.toString())

    private suspend fun perform(effect: ScriptsEffect): ScriptsMessage = when (effect) {
        is ScriptsEffect.LoadCatalog -> ScriptsMessage.CatalogLoaded(
            files.use(effect.installation) { game ->
                withContext(Dispatchers.IO) {
                    game.availableScripts(game.availableMaps(loadFileManifest(effect.installation.build.edition)))
                }
            }
        )

        is ScriptsEffect.LoadScript -> ScriptsMessage.ScriptLoaded(
            files.use(effect.installation) { game ->
                withContext(Dispatchers.IO) { game.contents(effect.script) }
            }
        )
    }

    private fun GameFiles.contents(script: ScriptId): ScriptContents {
        val emevd = checkNotNull(loadScript(script)) { "$script has no event script" }
        return emevd.summarize(script, loadScriptNames(script) ?: EventNames.Empty, definitions)
    }
}

public sealed interface ScriptsEffect {
    public data class LoadCatalog(public val installation: Installation) : ScriptsEffect

    public data class LoadScript(
        public val installation: Installation,
        public val script: ScriptId
    ) : ScriptsEffect
}