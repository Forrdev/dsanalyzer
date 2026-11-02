package com.sappyoak.dsanalyzer.app.data.params

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.params.GameParams
import com.sappyoak.dsanalyzer.game.world.params.ParamNames

public sealed interface ParamsMessage {
    public data class Opened(public val installation: Installation) : ParamsMessage
    public data class Loaded(public val params: GameParams, public val names: ParamNames) : ParamsMessage
    public data class QueryChanged(public val query: String) : ParamsMessage

    public data class SearchEdited(public val search: ParamQuery) : ParamsMessage
    public data object SearchRequested : ParamsMessage
    public data class SearchCompleted(public val outcome: SearchOutcome) : ParamsMessage

    public data class Failed(public val reason: String) : ParamsMessage
}
