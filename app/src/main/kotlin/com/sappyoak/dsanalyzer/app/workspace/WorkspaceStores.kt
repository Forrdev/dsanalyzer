package com.sappyoak.dsanalyzer.app.workspace

import com.sappyoak.dsanalyzer.app.analysis.AnalysisStore
import com.sappyoak.dsanalyzer.app.data.esd.EsdsStore
import com.sappyoak.dsanalyzer.app.data.maps.MapsStore
import com.sappyoak.dsanalyzer.app.data.params.ParamsStore
import com.sappyoak.dsanalyzer.app.runtime.RuntimeStore
import com.sappyoak.dsanalyzer.app.data.scripts.ScriptsStore

/**
 * The stores a workspace's tabs read from
 */
public class WorkspaceStores(
    public val analysis: AnalysisStore,
    public val params: ParamsStore,
    public val esds: EsdsStore,
    public val maps: MapsStore,
    public val scripts: ScriptsStore,
    public val runtime: RuntimeStore
)
