package com.sappyoak.dsanalyzer.app.data.params

import com.sappyoak.dsanalyzer.formats.param.TypedParam
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.params.GameParam
import com.sappyoak.dsanalyzer.game.world.params.GameParams
import com.sappyoak.dsanalyzer.game.world.params.ParamNames

public data class ParamEntry(
    public val param: GameParam<*>,
    public val table: TypedParam<*>
)

public data class ParamsState(
    public val installation: Installation? = null,
    public val params: GameParams? = null,
    public val names: ParamNames = ParamNames.None,
    public val loading: Boolean = false,
    public val query: String = "",
    /** The cross-param search being edited, and the last one run */
    public val search: ParamQuery = ParamQuery(),
    public val searching: Boolean = false,
    public val searchOutcome: SearchOutcome? = null,
    public val problem: String? = null
) {
    /** Every loaded param by file name, in registry order */
    public val entries: Map<String, ParamEntry> by lazy {
        params?.tables.orEmpty().entries.associate { (param, table) -> param.fileName to ParamEntry(param, table) }
    }

    public val catalog: List<ParamSummary> by lazy {
        entries.map { (file, entry) -> ParamSummary(file, entry.table.rows.size) }
    }
}
