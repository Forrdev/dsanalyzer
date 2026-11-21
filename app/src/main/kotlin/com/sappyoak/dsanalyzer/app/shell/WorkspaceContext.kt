package com.sappyoak.dsanalyzer.app.shell

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map

import com.sappyoak.dsanalyzer.app.data.ai.AiState
import com.sappyoak.dsanalyzer.app.nav.ResolveContext
import com.sappyoak.dsanalyzer.app.nav.WorkspaceResolveContext
import com.sappyoak.dsanalyzer.app.workspace.WorkspaceStores
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId

internal fun WorkspaceStores.resolveContext(): Flow<ResolveContext> = combine(
    maps.state.distinctUntilChangedBy { it.maps to it.entities },
    scripts.state.map { it.scripts.toSet() }.distinctUntilChanged(),
    params.state.map { it.entries.keys }.distinctUntilChanged(),
    esds.state.distinctUntilChangedBy { it.installation to it.talk },
    catalogs()
) { maps, scripts, params, esds, catalogs ->
    WorkspaceResolveContext(
        maps = maps.maps.toSet(),
        scripts = scripts,
        params = params,
        esds = esds::has,
        lua = catalogs.ai::has,
        animationSets = catalogs.animationSets,
        entryForEntity = { id -> maps.entities[id].firstOrNull() }
    )
}

/** The Lua and animation catalogs together, keeping the resolve context within combine's five flows */
private class Catalogs(val ai: AiState, val animationSets: Set<AnimationSetId>)

private fun WorkspaceStores.catalogs(): Flow<Catalogs> = combine(
    ai.state.distinctUntilChangedBy { it.binders },
    animations.state.map { it.sets.toSet() }.distinctUntilChanged()
) { ai, sets -> Catalogs(ai, sets) }

internal fun WorkspaceStores.flagIndexBuilt(): Flow<Boolean> =
    scripts.state.map { it.flagIndex.built }.distinctUntilChanged()
