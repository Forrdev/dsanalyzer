package com.sappyoak.dsanalyzer.app.data.ai.ui

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items

import com.sappyoak.dsanalyzer.app.data.ai.AiListKind
import com.sappyoak.dsanalyzer.app.data.ai.AiState
import com.sappyoak.dsanalyzer.app.data.shownAiList
import com.sappyoak.dsanalyzer.app.data.shownLuaBinder
import com.sappyoak.dsanalyzer.app.data.ui.ChildRow
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.game.world.ai.LuaBinder
import com.sappyoak.dsanalyzer.game.world.maps.label

/** The goal and logic lists, then each Lua binder with its script count */
internal fun LazyListScope.aiChildren(ai: AiState, view: View.Data, onFollow: (View) -> Unit) {
    items(AiListKind.entries, key = { "ai:$it" }) { kind ->
        ChildRow(
            label = kind.label,
            detail = ai.countOf(kind)?.toString() ?: "indexing",
            selected = view.shownAiList == kind,
            onClick = { onFollow(View.Data.AiList(kind)) }
        )
    }
    items(ai.binders.keys.toList(), key = { "lua:${it.label}" }) { binder ->
        ChildRow(
            label = binder.title,
            detail = ai.binders[binder]?.size?.toString(),
            selected = view.shownLuaBinder == binder,
            onClick = { onFollow(View.Data.LuaFiles(binder)) }
        )
    }
}

public val LuaBinder.title: String
    get() = when (this) {
        LuaBinder.Common -> "Common AI"
        LuaBinder.Event -> "Event scripts"
        is LuaBinder.Map -> map.label
    }
