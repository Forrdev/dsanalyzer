package com.sappyoak.dsanalyzer.runtime.sampling

import kotlin.time.TimeSource

import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.runtime.GameProcess
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.ResolvedPointers
import com.sappyoak.dsanalyzer.runtime.pointers.resolveMissing
import com.sappyoak.dsanalyzer.runtime.read.CameraReader
import com.sappyoak.dsanalyzer.runtime.read.EventFlagBlock
import com.sappyoak.dsanalyzer.runtime.read.LoadQueueReader
import com.sappyoak.dsanalyzer.runtime.read.MenuReader
import com.sappyoak.dsanalyzer.runtime.read.PlacedPartsReader
import com.sappyoak.dsanalyzer.runtime.read.readersFor
import com.sappyoak.dsanalyzer.runtime.read.target.Focus
import com.sappyoak.dsanalyzer.runtime.read.target.FocusReader
import com.sappyoak.dsanalyzer.runtime.session.ai.AIScope
import com.sappyoak.dsanalyzer.runtime.read.ai.AITracker
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimationTarget
import com.sappyoak.dsanalyzer.runtime.read.animation.AnimationTracker
import com.sappyoak.dsanalyzer.runtime.session.BlockRoster
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.CheatFlag
import com.sappyoak.dsanalyzer.runtime.session.FocusRequest
import com.sappyoak.dsanalyzer.runtime.session.FocusSnapshot
import com.sappyoak.dsanalyzer.runtime.session.RuntimeFeature
import com.sappyoak.dsanalyzer.runtime.session.RuntimeSnapshot
import com.sappyoak.dsanalyzer.runtime.session.SampleCost
import com.sappyoak.dsanalyzer.runtime.session.WorldPlace
import com.sappyoak.dsanalyzer.runtime.write.CheatWriter
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.runtime.write.hpbar.HpBarHider

public class GameSession internal constructor(
    public val game: GameProcess,
    pointers: ResolvedPointers,
    private val memory: GameMemory,
    private val layout: GameLayout,
    private val module: AddressRange,
    public val writer: GameWriter,
    private val extensions: List<SessionExtension>
) {
    public var pointers: ResolvedPointers = pointers
        private set

    private val flags = EventFlagBlock(layout.eventFlags)

    private val readers = readersFor(game.edition, layout)
    private val cameraReader = CameraReader(layout.camera)
    private val menuReader = MenuReader(layout.menu)
    private val loadQueueReader = LoadQueueReader(layout.loadQueue)
    private val placedPartsReader = PlacedPartsReader(layout.placedParts)
    private val aiTracker = AITracker(AI_GRACE_PERIOD, layout)
    private val animationTracker = AnimationTracker(layout)
    private val cheats = CheatWriter(layout.writes)
    private val focusReader = FocusReader(layout)
    private val hpBars = HpBarHider(layout.hpBars)

    private var lastPlace: WorldPlace? = null
    private var wasLoaded: Boolean = false

    private var world = WorldTier()
    private var focus: Focus? = null
    private var followed: FocusSnapshot? = null
    private var sinceWorld = 0

    public val writes: SessionWrites = SessionWrites(layout.writes, layout.playerGameData, writer)
    public val supports: Set<RuntimeFeature> get() = readers.supported + layout.supported

    init {
        val context = SessionContext(game.edition, memory, writer, module, layout) { lastPlace }
        extensions.forEach { it.attached(context) }
    }

    public fun isFlagSet(flagId: Int): Boolean? = flags.isSet(flagId)

    public fun sample(
        aiScope: AIScope = AIScope.TargetingPlayer,
        animation: AnimationTarget = AnimationTarget.Player,
        focusRequest: FocusRequest = FocusRequest.Off
    ): RuntimeSnapshot {
        val started = TimeSource.Monotonic.markNow()

        val loaded = cameraReader.refresh(memory)

        val areaPresent = readers.place.refresh(memory)
        val menu = menuReader.read(memory)
        val place = readers.place.place()

        val reloaded = lastPlace != null && place != lastPlace
        // Leaving a load screen is a reload even on the same map: a death rebuilds every character, and
        // the old addresses would still read, as whatever the allocator put there next
        val returned = loaded && !wasLoaded
        if (reloaded || returned) forgetWorld()
        if (reloaded) flags.reset()
        lastPlace = place
        wasLoaded = loaded

        val player = readers.characters.player(memory)
        val changes = flags.refresh(memory)
        val present = listOf(loaded, areaPresent, readers.clock.refresh(memory), flags.isPresent)
            .count { it } + player.present

        focus = if (loaded) focusReader.select(memory, focusRequest, player.player, world.characters) else null
        refreshWorldTier(aiScope)

        val characters = aiTracker.read(memory.memory, world.ai, world.characters)
        val animated = if (loaded) animationTracker.read(memory, animation, world.others) else null
        followed = focusReader.snapshot(memory, focus, characters, world.others, animated)
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
            characters = characters,
            flagChanges = changes,
            animation = animated,
            focus = followed,
            cost = SampleCost(
                started.elapsedNow(),
                present,
                readers.characters.playerStructures + AROUND_PLAYER
            ),
            writes = writer.active,
            history = writer.records
        )
    }

    internal fun afterSample() {
        extensions.forEach(SessionExtension::afterSample)
        val handle = followed?.takeUnless { it.boss }?.character?.handle
        val hidden = handle?.let { world.others.firstOrNull { it.handle == handle } }
        hpBars.hide(hidden?.handle, hidden?.address, memory, writer)
        writes.afterSample(memory, module, wasLoaded, lastPlace ?: WorldPlace.Unreadable)
    }

    public fun readPlacedEnemies(): List<BlockRoster> = placedPartsReader.read(memory)

    public fun readCharacterEffects(): List<CharacterSnapshot> =
        readers.characters.read(memory, allEffects = true).characters

    public fun resolveMissing(declared: List<GamePointer>, module: AddressRange): ResolvedPointers? {
        val found = memory.memory.resolveMissing(pointers, declared, module) ?: return null
        pointers = found
        memory.adopt(found)
        return found
    }

    public fun setCheat(flag: CheatFlag, on: Boolean): Boolean = cheats.set(flag, on, memory, writer)

    public fun revertAll(): Int {
        extensions.forEach(SessionExtension::release)
        writes.cancelAll(memory.memory)
        return writer.revertAll()
    }

    private fun forgetWorld() {
        memory.invalidate(Lifetime.World)
        hpBars.forget(writer)
        focusReader.forget()
        world = WorldTier()
        sinceWorld = 0
    }

    private fun refreshWorldTier(scope: AIScope) {
        if (sinceWorld > 0) {
            sinceWorld--
            return
        }
        sinceWorld = WORLD_TIER_PERIOD - 1

        val reading = readers.characters.read(memory)
        world = WorldTier(
            loadQueue = loadQueueReader.read(memory),
            characters = reading.characters,
            others = reading.others,
            ai = aiTracker.select(memory.memory, scope, reading, focus?.handle)
        )
    }

    private companion object {
        /** The follow cam, the map, the clock and the flag block, beside the player's own structures */
        const val AROUND_PLAYER = 4

        /** Ticks between slow-tier reads: five times a second at 30hz, faster than a block loads */
        const val WORLD_TIER_PERIOD = 6

        const val AI_GRACE_PERIOD: Int = 15
    }
}
