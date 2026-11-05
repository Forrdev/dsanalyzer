package com.sappyoak.dsanalyzer.app.data.esd

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource

public fun reduceEsds(state: EsdsState, message: EsdsMessage): Transition<EsdsState, EsdsEffect> = when (message) {
    is EsdsMessage.Opened -> EsdsState(installation = message.installation)
        .with(EsdsEffect.LoadCatalog(message.installation))

    is EsdsMessage.CatalogLoaded -> state.copy(
        talk = message.talk,
        sources = listOf(EsdSource.Player) + message.talk.flatMap { (map, ids) -> ids.map { EsdSource.Talk(map, it) } }
    ).with()

    is EsdsMessage.SourceSelected -> {
        val installation = state.installation
        when {
            message.source == state.shown -> state.with()
            installation == null -> state.copy(problem = "No installation is open").with()
            else -> state.copy(shown = message.source, esd = null, loading = true, problem = null)
                .with(EsdsEffect.Load(installation, message.source))
        }
    }

    // A load that finishes after another ESD was chosen is dropped rather than shown under the wrong name
    is EsdsMessage.Loaded ->
        if (message.source == state.shown) {
            state.copy(esd = message.esd, definitions = message.definitions, loading = false).with()
        } else state.with()


    is EsdsMessage.Failed -> state.copy(loading = false, problem = message.reason).with()
}
