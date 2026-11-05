package com.sappyoak.dsanalyzer.app.shell

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map

import com.sappyoak.dsanalyzer.app.nav.ResolveContext
import com.sappyoak.dsanalyzer.app.nav.WorkspaceResolveContext
import com.sappyoak.dsanalyzer.app.workspace.WorkspaceStores

internal fun WorkspaceStores.resolveContext(): Flow<ResolveContext> = combine(
    maps.state.distinctUntilChangedBy { it.maps to it.entities },
    scripts.state.map { it.scripts.toSet() }.distinctUntilChanged(),
    params.state.map { it.entries.keys }.distinctUntilChanged(),
    esds.state.distinctUntilChangedBy { it.installation to it.talk }
) { maps, scripts, params, esds ->
    WorkspaceResolveContext(
        maps = maps.maps.toSet(),
        scripts = scripts,
        params = params,
        esds = esds::has,
        entryForEntity = { id -> maps.entities[id].firstOrNull() }
    )
}

internal fun WorkspaceStores.flagIndexBuilt(): Flow<Boolean> =
    scripts.state.map { it.flagIndex.built }.distinctUntilChanged()
