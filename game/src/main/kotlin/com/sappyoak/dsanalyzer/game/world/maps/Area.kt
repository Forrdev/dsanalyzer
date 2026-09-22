package com.sappyoak.dsanalyzer.game.world.maps

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
public value class AreaId(public val value: String) {
    override fun toString(): String = value
}

@Serializable
public data class Area(
    public val id: AreaId,
    public val name: String,
    public val maps: List<MapId>
)