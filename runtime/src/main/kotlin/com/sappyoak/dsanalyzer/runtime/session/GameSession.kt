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
import com.sappyoak.dsanalyzer.runtime.read.EventFlagBlock
import com.sappyoak.dsanalyzer.runtime.read.LoadQueueReader
import com.sappyoak.dsanalyzer.runtime.read.MenuReader
import com.sappyoak.dsanalyzer.runtime.read.readersFor
import com.sappyoak.dsanalyzer.runtime.read.ai.AISource
import com.sappyoak.dsanalyzer.runtime.read.attack.AttackReader
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterReading
import com.sappyoak.dsanalyzer.runtime.read.character.PlayerReading
import com.sappyoak.dsanalyzer.runtime.session.ai.AIScope
import com.sappyoak.dsanalyzer.runtime.session.ai.AITracker
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimationTarget
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimationTracker
import com.sappyoak.dsanalyzer.runtime.session.attack.HitTracker
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionSnapshot
import com.sappyoak.dsanalyzer.runtime.write.CheatWriter
import com.sappyoak.dsanalyzer.runtime.write.GameWriter

public class GameSession internal constructor(
    public val game: GameProcess,
    pointers: ResolvedPointers,
    private val memory: GameMemory,
    layout: GameLayout,
    module: AddressRange,
    public val writer: GameWriter,
    private val extensions: List<SessionExtension>
) {
    public var pointers: ResolvedPointers = pointers
        private set

    private val flags = EventFlagBlock()

    private val readers = readersFor(game.edition, layout)
    private val cameraReader = CameraReader(layout.camera)
    private val menuReader = MenuReader()
    private val loadQueueReader = LoadQueueReader(layout.loadQueue)
    private val aiTracker = AITracker(AI_GRACE_PERIOD)
    private val attackReader = AttackReader()
    private val animationTracker = AnimationTracker(layout)
    private val hitTracker = HitTracker(HIT_LINGER_TICKS, HIT_SWEEP_LIMIT)
    private val cheats = CheatWriter.create(game.edition)

    private var lastPlace: WorldPlace? = null
    private var wasLoaded: Boolean = false

    private var world = WorldTier()
    private var sinceWorld = 0

    public val supports: Set<RuntimeFeature> get() = readers.supported + RuntimeFeature.Animation + RuntimeFeature.LoadQueue

    init {
        val context = SessionContext(game.edition, memory, writer, module) { lastPlace }
        extensions.forEach { it.attached(context) }
    }

    public fun isFlagSet(flagId: Int): Boolean? = flags.isSet(flagId)

    public fun sample(
        aiScope: AIScope = AIScope.TargetingPlayer,
        animation: AnimationTarget = AnimationTarget.Player
    ): RuntimeSnapshot {
        val started = TimeSource.Monotonic.markNow()

        val loaded = cameraReader.refresh(memory)

        val areaPresent = readers.place?.refresh(memory) ?: false
        val menu = menuReader.read(memory)
        val place = readers.place?.place() ?: WorldPlace.Unreadable


        val reloaded = lastPlace != null && place != lastPlace
        // Coming back from a load screen is a reload even when the map is the same one. A death
        // rebuilds every character without the place every changing, and the old addresses would
        // still read, as whatever the allocator put there next
        val returned = loaded && !wasLoaded
        if (reloaded || returned) forgetWorld()
        if (reloaded) flags.reset()
        lastPlace = place
        wasLoaded = loaded

        val player = readers.characters?.player(memory) ?: PlayerReading.Empty
        val changes = flags.refresh(memory)
        val present = listOf(loaded, areaPresent, readers.clock.refresh(memory), flags.isPresent)
            .count { it } + player.present

        refreshWorldTier(aiScope)

        return RuntimeSnapshot(
            inGameTimeMillis = readers.clock.timeMillis(),
            frame = readers.clock.frame(),
            place = place,
            loaded = loaded,
            reloaded = reloaded,
            player = if (loaded) player.player else null,
            world = player.world,
            camera = if (loaded) cameraReader.camera() else null,
            menu = menu,
            loadQueue = world.loadQueue,
            characters = aiTracker.read(memory.memory, world.ai, world.characters),
            flagChanges = changes,
            animation = if (loaded) animationTracker.read(memory, animation, world.others) else null,
            hits = hitTracker.update(attackReader.read(memory, world.tracked)),
            cost = SampleCost(started.elapsedNow(), present, (readers.characters?.playerStructures ?: 0) + AROUND_PLAYER),
            writes = writer.active
        )
    }

    internal fun afterSample() {
        extensions.forEach(SessionExtension::afterSample)
    }

    public fun readPlacedEnemies(): List<BlockRoster> = readers.placedParts?.read(memory) ?: emptyList()

    public fun readCollision(): CollisionSnapshot = readers.collision?.read(memory) ?: CollisionSnapshot.Empty

    public fun readCharacterEffects(): List<CharacterSnapshot> =
        readers.characters?.read(memory, allEffects = true)?.characters ?: emptyList()

    public fun resolveMissing(declared: List<GamePointer>, module: AddressRange): ResolvedPointers? {
        val found = memory.memory.resolveMissing(pointers, declared, module) ?: return null
        pointers = found
        memory.adopt(found)
        return found
    }

    public fun setCheat(flag: CheatFlag, on: Boolean): Boolean = cheats.set(flag, on, memory, writer)

    public fun revertAll(): Int {
        extensions.forEach(SessionExtension::release)
        return writer.revertAll()
    }

    private fun forgetWorld() {
        memory.invalidate(Lifetime.World)
        world = WorldTier()
        sinceWorld = 0
    }

    private fun refreshWorldTier(scope: AIScope) {
        if (sinceWorld > 0) {
            sinceWorld--
            return
        }
        sinceWorld = WORLD_TIER_PERIOD - 1

        val reading = readers.characters?.read(memory) ?: CharacterReading.Empty
        world = WorldTier(
            loadQueue = loadQueueReader.read(memory),
            characters = reading.characters,
            others = reading.others,
            ai = aiTracker.select(memory.memory, scope, reading)
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

        const val AI_GRACE_PERIOD: Int = 15

        const val HIT_LINGER_TICKS = 15
        const val HIT_SWEEP_LIMIT = 32
    }
}

public fun GameConnection.openSession(
    edition: EditionTables,
    extensions: List<SessionExtension> = emptyList()
): GameSession {
    val resolved = memory.resolvePointers(edition.pointers, mainModule)
    val writer = GameWriter(memory, code)
    return GameSession(game, resolved, GameMemory(memory, resolved), edition.layout, mainModule, writer, extensions)
}
