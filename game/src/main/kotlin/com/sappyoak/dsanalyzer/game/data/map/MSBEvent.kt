package com.sappyoak.dsanalyzer.game.data.map

import com.sappyoak.dsanalyzer.shared.geometry.Vec3
import kotlinx.serialization.Serializable

@Serializable
sealed class MSBEvent {
    abstract val name: String
    abstract val eventId: Int
    abstract val entityId: Int
    abstract val partName: String?
    abstract val regionName: String?

    @Serializable
    data class Treasure(
        override val name: String,
        override val eventId: Int,
        override val entityId: Int,
        override val partName: String?,
        override val regionName: String?,
        val treasurePartName: String?,
        val itemLots: List<Int>,
        val inChest: Boolean,
        val startDisabled: Boolean
    ) : MSBEvent()

    @Serializable
    data class Generator(
        override val name: String,
        override val eventId: Int,
        override val entityId: Int,
        override val partName: String?,
        override val regionName: String?,
        val spawnPartNames: List<String>,
        val spawnRegionNames: List<String>
    ) : MSBEvent()

    @Serializable
    data class ObjAct(
        override val name: String,
        override val eventId: Int,
        override val entityId: Int,
        override val partName: String?,
        override val regionName: String?,
        val objActEntityId: Int,
        val objActPartName: String?,
        val objActParamId: Int,
        val objActState: Int,
        val eventFlagId: Int
    ) : MSBEvent()

    @Serializable
    data class SpawnPoint(
        override val name: String,
        override val eventId: Int,
        override val entityId: Int,
        override val partName: String?,
        override val regionName: String?
    ) : MSBEvent()

    @Serializable
    data class MapOffset(
        override val name: String,
        override val eventId: Int,
        override val entityId: Int,
        override val partName: String?,
        override val regionName: String?,
        val position: Vec3,
        val degree: Float
    ) : MSBEvent()

    @Serializable
    data class Other(
        override val name: String,
        override val eventId: Int,
        override val entityId: Int,
        override val partName: String?,
        override val regionName: String?,
        val kind: String
    ) : MSBEvent()
}