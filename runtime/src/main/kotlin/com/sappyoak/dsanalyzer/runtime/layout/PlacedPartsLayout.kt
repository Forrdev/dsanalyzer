package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable

public data class PlacedPartsLayout(
    public val manager: GamePointer,
    public val blocks: GameTable,
    public val blockSize: Int,
    /** In a block: its 'MsbResCap', null when the block is not loaded */
    public val mapData: Int,
    /** In the 'MsbResCap': the parts list */
    public val partsList: Int,
    public val parts: GameTable,
    public val partSize: Int,
    public val type: Int,
    public val enemyType: Int,
    public val name: Int,
    public val entityId: Int,
    public val position: Int,
    public val npcParamId: Int,
    public val thinkParamId: Int
)
