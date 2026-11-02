package com.sappyoak.dsanalyzer.app.data

import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

public val View.Data.kind: DataKind?
    get() = when (this) {
        is View.Data.ParamSearch, is View.Data.Param, is View.Data.ParamRow -> DataKind.Params
        is View.Data.Map, is View.Data.MapEntry -> DataKind.Maps
        is View.Data.Esd, is View.Data.EsdState -> DataKind.Esd
        is View.Data.Script, is View.Data.ScriptEvent -> DataKind.Scripts
        is View.Data.EventFlag -> DataKind.EventFlags
        is View.Data.Overview -> null
    }

public val View.Data.shownMap: MapId?
    get() = when (this) {
        is View.Data.Map -> map
        is View.Data.MapEntry -> map
        else -> null
    }

public val View.Data.shownScript: ScriptId?
    get() = when (this) {
        is View.Data.Script -> script
        is View.Data.ScriptEvent -> script
        else -> null
    }

public val View.Data.shownParam: String?
    get() = when (this) {
        is View.Data.Param -> file
        is View.Data.ParamRow -> file
        else -> null
    }

public val View.Data.shownEsd: EsdSource?
    get() = when (this) {
        is View.Data.Esd -> source
        is View.Data.EsdState -> source
        else -> null
    }
