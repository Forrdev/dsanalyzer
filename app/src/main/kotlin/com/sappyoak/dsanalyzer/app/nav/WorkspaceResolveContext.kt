package com.sappyoak.dsanalyzer.app.nav

import com.sappyoak.dsanalyzer.game.world.ai.LuaScriptId
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

public class WorkspaceResolveContext(
    private val maps: Set<MapId>,
    private val scripts: Set<ScriptId>,
    private val params: Set<String>,
    private val esds: (EsdSource) -> Boolean,
    private val lua: (LuaScriptId) -> Boolean,
    private val animationSets: Set<AnimationSetId>,
    private val entryForEntity: (Int) -> View.Data.MapEntry?
) : ResolveContext {
    override fun hasMap(map: MapId): Boolean = map in maps
    override fun hasScript(script: ScriptId): Boolean = script in scripts
    override fun hasParam(file: String): Boolean = file in params
    override fun hasEsd(source: EsdSource): Boolean = esds(source)
    override fun hasLua(script: LuaScriptId): Boolean = lua(script)
    override fun hasAnimationSet(set: AnimationSetId): Boolean = set in animationSets

    override fun entryFor(entityId: Int): View.Data.MapEntry? = entryForEntity(entityId)

    public companion object {
        public fun empty(): WorkspaceResolveContext =
            WorkspaceResolveContext(emptySet(), emptySet(), emptySet(), { false }, { false }, emptySet(), { null })
    }
}
