package com.sappyoak.dsanalyzer.game.data.map

import com.sappyoak.dsanalyzer.game.data.MapId
import com.sappyoak.dsanalyzer.shared.geometry.Transform
import kotlinx.serialization.Serializable

@Serializable
sealed class MSBPart {
    abstract val name: String
    abstract val modelName: String
    abstract val transform: Transform
    abstract val entityId: Int

    /**
     * Draw and display groups are 128-bit-masks controlling what renders and collides while
     * standing on a given collision
     */
    abstract val displayGroups: UIntArray
    abstract val drawGroups: UIntArray

    @Serializable
    class MapPiece(
        override val name: String,
        override val modelName: String,
        override val transform: Transform,
        override val entityId: Int,
        override val displayGroups: UIntArray,
        override val drawGroups: UIntArray
    ) : MSBPart()

    @Serializable
    class Object(
        override val name: String,
        override val modelName: String,
        override val transform: Transform,
        override val entityId: Int,
        override val displayGroups: UIntArray,
        override val drawGroups: UIntArray,
        val collisionName: String?,
        val initialAnimId: Int
    ) : MSBPart()

    @Serializable
    class Enemy(
        override val name: String,
        override val modelName: String,
        override val transform: Transform,
        override val entityId: Int,
        override val displayGroups: UIntArray,
        override val drawGroups: UIntArray,
        val thinkParamId: Int,
        val npcParamId: Int,
        val collisionName: String?
    ) : MSBPart()

    @Serializable
    class Player(
        override val name: String,
        override val modelName: String,
        override val transform: Transform,
        override val entityId: Int,
        override val displayGroups: UIntArray,
        override val drawGroups: UIntArray
    ) : MSBPart()

    @Serializable
    class Collision(
        override val name: String,
        override val modelName: String,
        override val transform: Transform,
        override val entityId: Int,
        override val displayGroups: UIntArray,
        override val drawGroups: UIntArray,
        val hitFilterId: Int,
        val nvmGroups: UIntArray,
        val mapNameId: Int,
        val disableStart: Boolean,
        val disableBonfireEntityId: Int,
        val playRegionId: Int,
        val lockCamParamId1: Int,
        val lockCamParamId2: Int
    ) : MSBPart()

    @Serializable
    class Navmesh(
        override val name: String,
        override val modelName: String,
        override val transform: Transform,
        override val entityId: Int,
        override val displayGroups: UIntArray,
        override val drawGroups: UIntArray,
        val nvmGroups: UIntArray
    ) : MSBPart()

    /**
     * Standing on the references collision streams in [targetMap]. This is the concrete
     * mechanism behind area transitions
     */
    @Serializable
    class ConnectionCollision(
        override val name: String,
        override val modelName: String,
        override val transform: Transform,
        override val entityId: Int,
        override val displayGroups: UIntArray,
        override val drawGroups: UIntArray,
        val collisionName: String,
        val targetMap: MapId
    ) : MSBPart()
}