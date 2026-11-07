package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable

public data class LoadQueueLayout(
    public val pointer: GamePointer,
    public val currentMap: Int,
    public val previousMap: Int,
    public val warpTarget: Int,
    public val blocks: GameTable,
    public val blockSize: Int,

    public val blockInfos: GameTable,
    public val blockInfoSize: Int,
    public val packedMapId: Int,

    public val suppress: Int,
    public val forceLoad: Int,
    public val requested: Int,
    public val latched: Int,
    public val wantLoaded: Int,
    public val stage: Int,
    public val pinLoaded: Int?,
    public val pinOther: Int?,
    public val groupMasks: Int?
)
