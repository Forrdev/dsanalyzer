package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.native.memory.AddressRange
import kotlin.time.TimeSource

import com.sappyoak.dsanalyzer.runtime.GameConnection
import com.sappyoak.dsanalyzer.runtime.GameProcess
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.ResolvedPointers
import com.sappyoak.dsanalyzer.runtime.pointers.resolveMissing
import com.sappyoak.dsanalyzer.runtime.pointers.resolvePointers
import com.sappyoak.dsanalyzer.runtime.read.CameraReader
import com.sappyoak.dsanalyzer.runtime.read.CharacterReader
import com.sappyoak.dsanalyzer.runtime.read.EventFlagBlock
import com.sappyoak.dsanalyzer.runtime.read.LoadQueueReader
import com.sappyoak.dsanalyzer.runtime.read.MenuReader
import com.sappyoak.dsanalyzer.runtime.read.PlaceReader
import com.sappyoak.dsanalyzer.runtime.read.PlacedPartsReader
import com.sappyoak.dsanalyzer.runtime.read.PlayerReader
import com.sappyoak.dsanalyzer.runtime.read.collision.CollisionReader
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionSnapshot

public class GameSession internal constructor(
    public val game: GameProcess,
    pointers: ResolvedPointers,
    private val memory: GameMemory,
    layout: GameLayout
) {
    public var pointers: ResolvedPointers = pointers
        private set

    private val flags = EventFlagBlock()

    private val cameraReader = CameraReader(layout.camera)
    private val placeReader = PlaceReader()
    private val menuReader = MenuReader()
    private val playerReader = PlayerReader()
    private val loadQueueReader = LoadQueueReader()
    private val characterReader = CharacterReader(layout.specialEffect)
    private val placedPartsReader = PlacedPartsReader()
    private val collisionReader = CollisionReader()

    private var lastPlace: WorldPlace? = null
    private var wasLoaded: Boolean = false

    private var world = WorldTier()
    private var sinceWorld = 0

    public fun isFlagSet(flagId: Int): Boolean? = flags.isSet(flagId)

    public fun sample(): RuntimeSnapshot {
        val started = TimeSource.Monotonic.markNow()

        val loaded = cameraReader.refresh(memory)

        val areaPresent = placeReader.refresh(memory)
        val menu = menuReader.read(memory)
        val place = placeReader.place()


        val reloaded = lastPlace != null && place != lastPlace
        // Coming back from a load screen is a reload even when the map is the same one. A death
        // rebuilds every character without the place every changing, and the old addresses would
        // still read, as whatever the allocator put there next
        val returned = loaded && !wasLoaded
        if (reloaded || returned) forgetWorld()
        if (reloaded) flags.reset()
        lastPlace = place
        wasLoaded = loaded

        var present = if (loaded) 1 else 0
        present += if (areaPresent) 1 else 0
        present += playerReader.refresh(memory)

        val changes = flags.refresh(memory)
        if (flags.isPresent) {
            present++
        }

        refreshWorldTier()

        return RuntimeSnapshot(
            inGameTimeMillis = playerReader.timeMillis(),
            frame = playerReader.frame(),
            place = place,
            loaded = loaded,
            reloaded = reloaded,
            player = if (loaded) playerReader.player() else null,
            world = playerReader.world(),
            camera = if (loaded) cameraReader.camera() else null,
            menu = menu,
            loadQueue = world.loadQueue,
            characters = world.characters,
            flagChanges = changes,
            cost = SampleCost(started.elapsedNow(), present, playerReader.structures + AROUND_PLAYER)
        )
    }

    public fun readPlacedEnemies(): List<BlockRoster> = placedPartsReader.read(memory)

    public fun readCollision(): CollisionSnapshot = collisionReader.read(memory)

    public fun readCharacterEffects(): List<CharacterSnapshot> =
        characterReader.read(memory, allEffects = true)

    public fun resolveMissing(declared: List<GamePointer>, module: AddressRange): ResolvedPointers? {
        val found = memory.memory.resolveMissing(pointers, declared, module) ?: return null
        pointers = found
        memory.adopt(found)
        return found
    }

    private fun forgetWorld() {
        memory.invalidate(Lifetime.World)
        world = WorldTier()
        sinceWorld = 0
    }

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

public fun GameConnection.openSession(edition: EditionTables): GameSession {
    val resolved = memory.resolvePointers(edition.pointers, mainModule)
    return GameSession(game, resolved, GameMemory(memory, resolved), edition.layout)
}
