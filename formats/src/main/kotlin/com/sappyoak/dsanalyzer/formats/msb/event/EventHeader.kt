package com.sappyoak.dsanalyzer.formats.msb.event

import com.sappyoak.dsanalyzer.formats.msb.PartIndex
import com.sappyoak.dsanalyzer.formats.msb.RegionIndex

public data class EventHeader(
    public val name: String,
    public val part: PartIndex?,
    public val region: RegionIndex?,
    public val entityId: Int?
)
