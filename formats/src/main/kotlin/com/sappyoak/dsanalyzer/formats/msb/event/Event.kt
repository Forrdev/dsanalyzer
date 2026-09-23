package com.sappyoak.dsanalyzer.formats.msb.event

import com.sappyoak.dsanalyzer.formats.msb.PartIndex
import com.sappyoak.dsanalyzer.formats.msb.RegionIndex
import com.sappyoak.dsanalyzer.shared.math.Vec3

public sealed interface Event {
    public val header: EventHeader

    public data class Light(
        override val header: EventHeader,
        public val pointLightId: Int
    ) : Event

    public data class Sound(
        override val header: EventHeader,
        public val soundType: Int,
        public val soundId: Int
    ) : Event

    public data class Sfx(
        override val header: EventHeader,
        public val effectId: Int
    ) : Event

    public data class Wind(
        override val header: EventHeader,
        public val vectorMin: Vec3,
        public val vectorMax: Vec3,
        public val swingCycles: List<Float>,
        public val swingPowers: List<Float>
    ) : Event

    /** An item pickup, placed on [part] */
    public data class Treasure(
        override val header: EventHeader,
        public val part: PartIndex?,
        public val itemLots: List<Int>,
        public val inChest: Boolean,
        public val startsDisabled: Boolean
    ) : Event

    /** Spawns enemies from [spawnParts] at [spawnPoints] */
    public data class Generator(
        override val header: EventHeader,
        public val maxNum: Int,
        public val genType: Int,
        public val limitNum: Int,
        public val minGenNum: Int,
        public val maxGenNum: Int,
        public val minInterval: Float,
        public val maxInterval: Float,
        public val initialSpawnCount: Int,
        /** Four slots, null where unused */
        public val spawnPoints: List<RegionIndex?>,
        /** Thirty two slots, null where unused */
        public val spawnParts: List<PartIndex?>
    ) : Event

    /** A developer message placed in the map */
    public data class Message(
        override val header: EventHeader,
        public val messageId: Int,
        public val hidden: Boolean
    ) : Event

    /** An interactable object action such as a level or door */
    public data class ObjAct(
        override val header: EventHeader,
        public val objActEntityId: Int?,
        public val part: PartIndex?,
        public val paramId: Int,
        /** 0 default, 1 door, 2 loop */
        public val state: Int,
        public val eventFlagId: Int
    ) : Event

    public data class SpawnPoint(
        override val header: EventHeader,
        public val region: RegionIndex?
    ) : Event

    public data class MapOffset(
        override val header: EventHeader,
        public val position: Vec3,
        public val degree: Float
    ) : Event

    public data class Navmesh(
        override val header: EventHeader,
        public val region: RegionIndex?
    ) : Event

    /** Environment light settings, which collisions select through their environment index */
    public data class Environment(
        override val header: EventHeader,
    ) : Event

    /** An NPC invasion */
    public data class PseudoMultiplayer(
        override val header: EventHeader,
        public val hostEntityId: Int?,
        public val eventFlagId: Int,
        public val activateGoodsId: Int
    ) : Event
}