package com.sappyoak.dsanalyzer.app.data.ai

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.ai.AiIndex
import com.sappyoak.dsanalyzer.game.world.ai.LuaBinder
import com.sappyoak.dsanalyzer.game.world.ai.LuaScriptId
import com.sappyoak.dsanalyzer.game.world.ai.LuaSource
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId

public sealed interface AiMessage {
    public data class Opened(public val installation: Installation) : AiMessage
    public data class CatalogLoaded(public val binders: Map<LuaBinder, List<String>>) : AiMessage
    public data class Indexed(public val index: AiIndex) : AiMessage
    public data class CharactersFound(public val byThink: Map<Int, Set<AnimationSetId>>) : AiMessage
    public data class ScriptSelected(public val script: LuaScriptId) : AiMessage
    public data class Loaded(public val script: LuaScriptId, public val source: LuaSource) : AiMessage
    public data class Failed(public val reason: String) : AiMessage
}
