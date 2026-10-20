package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

public data class EventFlagLayout(
    public val pointer: GamePointer,
    public val readyAt: Int,
    public val groupTableAt: Int,
    public val groupTableIndirect: Boolean,
    public val groupStride: Int,
    public val areaStride: Int,
    public val sectionStride: Int,
    public val bitArrayBytes: Int
)