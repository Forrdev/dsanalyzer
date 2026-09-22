package com.sappyoak.dsanalyzer.formats.msb.part

import com.sappyoak.dsanalyzer.formats.msb.CollisionIndex
import com.sappyoak.dsanalyzer.formats.msb.EnvironmentIndex
import com.sappyoak.dsanalyzer.formats.msb.GroupMask
import com.sappyoak.dsanalyzer.formats.msb.PartIndex
import com.sappyoak.dsanalyzer.formats.msb.RegionIndex

public sealed interface Part {
    public val header: PartHeader

    public data class MapPiece(override val header: PartHeader) : Part
    public data class Object(override val header: PartHeader, public val data: ObjectData) : Part
    public data class DummyObject(override val header: PartHeader, public val data: ObjectData) : Part
    public data class Enemy(override val header: PartHeader, public val data: EnemyData) : Part
    public data class DummyEnemy(override val header: PartHeader, public val data: EnemyData) : Part

    public data class Player(override val header: PartHeader) : Part

    public data class Collision(
        override val header: PartHeader,
        public val hitFilterId: Int,
        public val soundSpaceType: Int,
        public val environment: EnvironmentIndex?,
        public val reflectPlaneHeight: Float,
        public val navmeshGroups: GroupMask,
        public val vagrantEntityIds: List<Int>,
        /** Place name shown on entering. Negative forces the banner*/
        public val rawPlaceName: Int,
        public val startsDisabled: Boolean,
        /** Entity id of the bonfire tied to this collision */
        public val bonfireEntityId: Int?,
        /** Play region id or an encoded stable footing flag */
        public val rawPlayRegion: Int,
        public val lockCamParamId1: Int,
        public val lockCamParamId2: Int
    ) : Part

    public data class Navmesh(override val header: PartHeader, public val navmeshGroups: GroupMask) : Part

    public data class ConnectCollision(
        override val header: PartHeader,
        public val collision: CollisionIndex?,
        public val connectedMap: List<Int>
    ) : Part
}

public data class ObjectData(
    public val drawParent: PartIndex?,
    public val breakTerm: Int,
    public val netSyncType: Int,
    public val initialAnimation: Int
)

public data class EnemyData(
    public val thinkParamId: Int,
    public val npcParamId: Int,
    public val talkId: Int,
    public val pointMoveType: Int,
    public val platoonId: Int,
    public val charaInitId: Int,
    /** Collision the enemy's drawing is tied to */
    public val drawParent: PartIndex?,
    /** The eight patrol point slots in order, null where a slot is unused */
    public val movePoints: List<RegionIndex?>,
    public val initialAnimation: Int,
    public val damageAnimation: Int
)