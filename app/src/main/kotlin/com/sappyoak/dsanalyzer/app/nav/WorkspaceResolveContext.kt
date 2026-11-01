package com.sappyoak.dsanalyzer.app.nav

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId


public class WorkspaceResolveContext(
    private val maps: Set<MapId>,
    private val scripts: Set<ScriptId>,
    private val entryForEntity: (Int) -> View.Data.MapEntry?
) : ResolveContext {
    override fun hasMap(map: MapId): Boolean = map in maps
    override fun hasScript(script: ScriptId): Boolean = script in scripts
    override fun entryFor(entityId: Int): View.Data.MapEntry? = entryForEntity(entityId)

    public companion object {
        public fun empty(): WorkspaceResolveContext =
            WorkspaceResolveContext(emptySet(), emptySet(), { null })
    }
}
