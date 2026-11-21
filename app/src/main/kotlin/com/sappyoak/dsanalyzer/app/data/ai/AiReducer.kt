package com.sappyoak.dsanalyzer.app.data.ai

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with

public fun reduceAi(state: AiState, message: AiMessage): Transition<AiState, AiEffect> = when (message) {
    is AiMessage.Opened -> AiState(installation = message.installation).with(
        AiEffect.LoadCatalog(message.installation),
        AiEffect.Index(message.installation),
        AiEffect.FindCharacters(message.installation)
    )

    is AiMessage.CatalogLoaded -> state.copy(binders = message.binders).with()

    is AiMessage.Indexed -> state.copy(index = message.index).with()

    is AiMessage.CharactersFound -> state.copy(thinkCharacters = message.byThink).with()

    is AiMessage.ScriptSelected -> {
        val installation = state.installation
        when {
            message.script == state.shown -> state.with()
            installation == null -> state.copy(problem = "No installation is open").with()
            else -> state.copy(shown = message.script, source = null, loading = true, problem = null)
                .with(AiEffect.Load(installation, message.script))
        }
    }

    // A script that finishes loading after another was chosen is dropped rather than shown under the wrong name
    is AiMessage.Loaded ->
        if (message.script == state.shown) state.copy(source = message.source, loading = false).with()
        else state.with()

    is AiMessage.Failed -> state.copy(loading = false, problem = message.reason).with()
}
