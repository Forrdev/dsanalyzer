package com.sappyoak.dsanalyzer.app.workspace

import com.sappyoak.dsanalyzer.app.maps.MapsStore
import com.sappyoak.dsanalyzer.app.runtime.RuntimeStore
import com.sappyoak.dsanalyzer.app.scripts.ScriptsStore

/**
 * The stores a workspace's tabs read from
 */
public class WorkspaceStores(
    public val maps: MapsStore,
    public val scripts: ScriptsStore,
    public val runtime: RuntimeStore
)