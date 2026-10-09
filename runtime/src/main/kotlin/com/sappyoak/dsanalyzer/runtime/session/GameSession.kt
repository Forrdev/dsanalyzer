package com.sappyoak.dsanalyzer.runtime.session

import kotlin.time.TimeSource

import com.sappyoak.dsanalyzer.game.world.maps.exists
import com.sappyoak.dsanalyzer.runtime.GameConnection
import com.sappyoak.dsanalyzer.runtime.GameProcess
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.ResolvedPointers
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.resolvePointers
import com.sappyoak.dsanalyzer.runtime.ptde.EventFlagBlock
import com.sappyoak.dsanalyzer.runtime.ptde.FollowCam
import com.sappyoak.dsanalyzer.runtime.ptde.GameDataMan
import com.sappyoak.dsanalyzer.runtime.ptde.WorldArea

public class GameSession internal constructor(
    public val game: GameProcess,
    public val pointers: ResolvedPointers,
    private val memory: GameMemory
) {
    private val followCam = StructView(FollowCam.Pointer)
    private val worldArea = StructView(WorldArea.Pointer)
    private val flags = EventFlagBlock()

    private val playerReader = PlayerReader()
    private val loadQueueReader = LoadQueueReader()
    private val characterReader = CharacterReader()
    private val placedPartsReader = PlacedPartsReader()

    /** Where the previous tick was, which is what a reload is detected as a change of */
    private var lastPlace: WorldPlace? = null

    /**
     * The slow  tier, kept between the ticks that skip it
     */
    private var world = WorldTier()
    private var sinceWorld = 0


    public fun sample(): RuntimeSnapshot {
        val started = TimeSource.Monotonic.markNow()
        val loaded = followCam.refresh(memory)
        val areaPresent = worldArea.refresh(memory)
        val place = place(areaPresent)

        val reloaded = lastPlace != null && place != lastPlace
        if (reloaded) {
            memory.invalidate(Lifetime.World)
            flags.reset()
            world = WorldTier()
            sinceWorld = 0
        }
        lastPlace = place

        var present = if (loaded) 1 else 0
        present += if (areaPresent) 1 else 0
        present += playerReader.refresh(memory)

        val changes = flags.refresh(memory)
        if (flags.isPresent) {
            present++
        }

        val time = playerReader.timeMillis()
        refreshWorldTier()

        return RuntimeSnapshot(
            inGameTimeMillis = time,
            frame = GameDataMan.frameOf(time),
            place = place,
            loaded = loaded,
            reloaded = reloaded,
            player = if (loaded) playerReader.player() else null,
            world = playerReader.world(),
            camera = if (loaded) cameraOf(followCam) else null,
            loadQueue = world.loadQueue,
            characters = world.characters,
            flagChanges = changes,
            cost = SampleCost(started.elapsedNow(), present, playerReader.structures + AROUND_PLAYER)
        )
    }

    /** Reads every enemy the loaded blocks place, instantiated or not */
    public fun readPlacedEnemies(): List<BlockRoster> = placedPartsReader.read(memory)

    public fun isFlagSet(flagId: Int): Boolean? = flags.isSet(flagId)

    private fun refreshWorldTier() {
        if (sinceWorld > 0) {
            sinceWorld--
            return
        }

        sinceWorld = WORLD_TIER_PERIOD - 1
        world = WorldTier(
            loadQueue = loadQueueReader.read(memory),
            characters = characterReader.read(memory)
        )
    }

    private fun place(areaPresent: Boolean): WorldPlace {
        if (!areaPresent) return WorldPlace.Unreadable

        val map = WorldArea.mapId(
            world = worldArea.unsigned(WorldArea.World),
            area = worldArea.unsigned(WorldArea.Area)
        )

        return if (map.exists) WorldPlace.InWorld(map) else WorldPlace.Loading(map)
    }

    private companion object {
        /** The follow cam, the map, and the flag block, beside the player's own structures */
        const val AROUND_PLAYER = 3

        /**
         * Ticks between slow-tier reads. Six against a 30hz logic tick is five times a second,
         * which is faster than a block can load and faster than anyone can read a list
         */
        const val WORLD_TIER_PERIOD = 6
    }
}

/** The slow tier's last reading, held between the ticks that do not refresh it */
private data class WorldTier(
    val loadQueue: LoadQueueSnapshot? = null,
    val characters: List<CharacterSnapshot> = emptyList()
)

public fun GameConnection.openSession(pointers: List<GamePointer>): GameSession {
    val resolved = memory.resolvePointers(pointers, mainModule)
    return GameSession(game, resolved, GameMemory(memory, resolved))
}