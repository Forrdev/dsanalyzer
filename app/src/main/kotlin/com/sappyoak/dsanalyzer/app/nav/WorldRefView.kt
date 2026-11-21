package com.sappyoak.dsanalyzer.app.nav

import com.sappyoak.dsanalyzer.game.ref.WorldRef

public fun WorldRef.view(): View.Data? = when (this) {
    is WorldRef.Map -> View.Data.Map(map)
    is WorldRef.Entry -> View.Data.MapEntry(map, kind, index)
    is WorldRef.EventFlag -> View.Data.EventFlag(flagId, seenIn)
    is WorldRef.ScriptEvent -> View.Data.ScriptEvent(script, eventId)
    is WorldRef.ParamRow -> View.Data.ParamRow(file, rowId)
    is WorldRef.Entity -> null
}
