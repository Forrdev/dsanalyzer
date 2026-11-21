package com.sappyoak.dsanalyzer.app.data.ai

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.ai.AiIndex
import com.sappyoak.dsanalyzer.game.world.ai.LuaBinder
import com.sappyoak.dsanalyzer.game.world.ai.LuaScriptId
import com.sappyoak.dsanalyzer.game.world.ai.LuaSource
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId

/** The two lists the AI index offers in the navigator */
public enum class AiListKind(public val label: String) {
    Goals("Goals"),
    Logic("Logic")
}

public data class AiState(
    public val installation: Installation? = null,
    /** Every Lua binder with its scripts, shared binders first */
    public val binders: Map<LuaBinder, List<String>> = emptyMap(),
    /** Null until every AI script has been decompiled once */
    public val index: AiIndex? = null,
    /** The characters placed with each NpcThinkParam row, once every map has been read */
    public val thinkCharacters: Map<Int, Set<AnimationSetId>> = emptyMap(),
    public val shown: LuaScriptId? = null,
    public val source: LuaSource? = null,
    public val loading: Boolean = false,
    public val problem: String? = null
) {
    public fun has(script: LuaScriptId): Boolean = binders[script.binder]?.contains(script.name) == true

    public fun countOf(kind: AiListKind): Int? = when (kind) {
        AiListKind.Goals -> index?.goals?.size
        AiListKind.Logic -> index?.logics?.size
    }
}
