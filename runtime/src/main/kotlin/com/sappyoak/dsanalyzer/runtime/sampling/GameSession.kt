package com.sappyoak.dsanalyzer.runtime.sampling

import kotlin.time.TimeSource

import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.runtime.edition.Edition
import com.sappyoak.dsanalyzer.runtime.GameProcess
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.ResolvedPointers
import com.sappyoak.dsanalyzer.runtime.pointers.resolveMissing
import com.sappyoak.dsanalyzer.runtime.read.CameraReader
import com.sappyoak.dsanalyzer.runtime.read.ClockSource
import com.sappyoak.dsanalyzer.runtime.read.EventFlagBlock
import com.sappyoak.dsanalyzer.runtime.read.LoadQueueReader
import com.sappyoak.dsanalyzer.runtime.read.MenuReader
import com.sappyoak.dsanalyzer.runtime.read.PlacedPartsReader
import com.sappyoak.dsanalyzer.runtime.read.action.ActionReader
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterReader
import com.sappyoak.dsanalyzer.runtime.read.character.PlayerDetailsReader
import com.sappyoak.dsanalyzer.runtime.read.effects.SpecialEffectReader
import com.sappyoak.dsanalyzer.runtime.read.place.PlaceReader
import com.sappyoak.dsanalyzer.runtime.snapshot.BlockRoster
import com.sappyoak.dsanalyzer.runtime.snapshot.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.RuntimeFeature
import com.sappyoak.dsanalyzer.runtime.snapshot.RuntimeSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.SampleCost
import com.sappyoak.dsanalyzer.runtime.snapshot.WatchedFlag
import com.sappyoak.dsanalyzer.runtime.snapshot.WorldPlace
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.runtime.write.SessionContext
import com.sappyoak.dsanalyzer.runtime.write.SessionExtension
import com.sappyoak.dsanalyzer.runtime.write.request.SessionWrites
import com.sappyoak.dsanalyzer.runtime.write.request.WriteRequest

public class GameSession internal constructor(
    public val game: GameProcess,
    pointers: ResolvedPointers,
    private val memory: GameMemory,
    private val layout: GameLayout,
    private val module: AddressRange,
    private val writer: GameWriter,
    private val extensions: List<SessionExtension>,
    edition: Edition
) {
    public var pointers: ResolvedPointers = pointers
        private set

    private val flags = EventFlagBlock(layout.eventFlags)
    private val place = PlaceReader(layout.place)
    private val clock = ClockSource(layout.clock, game.edition.logicTickHz)
    private val characters = CharacterReader(
        chars = layout.characters,
        effects = SpecialEffectReader(layout.specialEffect),
        details = PlayerDetailsReader(layout.characters, layout.playerGameData, layout.writes.cheatBits),
        actions = ActionReader(layout.action)
    )

    private val cameraReader = CameraReader(layout.camera)
    private val menuReader = MenuReader(layout.menu)
    private val loadQueueReader = LoadQueueReader(layout.loadQueue)
    private val placedPartsReader = PlacedPartsReader(layout.placedParts)

    private var lastPlace: WorldPlace? = null
    private var wasLoaded: Boolean = false
    private var world = newWorld()
    private val writes = SessionWrites(layout.writes, layout.playerGameData, writer)
    public val supports: Set<RuntimeFeature> = edition.supports

    init {
        val context = SessionContext(game.edition, memory, writer, module, layout, edition.draw) { lastPlace }
        extensions.forEach { it.attached(context) }
    }

    public fun isFlagSet(flagId: Int): Boolean? = flags.isSet(flagId)

    public fun sample(options: SampleOptions = SampleOptions()): RuntimeSnapshot {
        val started = TimeSource.Monotonic.markNow()

        val loaded = cameraReader.refresh(memory)

        val areaPresent = place.refresh(memory)
        val menu = menuReader.read(memory)
        val place = place.place()

        val reloaded = lastPlace != null && place != lastPlace
        // Leaving a load screen is a reload even on the same map: a death rebuilds every character, and
        // the old addresses would still read, as whatever the allocator put there next
        val returned = loaded && !wasLoaded
        if (reloaded || returned) forgetWorld()
        if (reloaded) flags.reset()
        lastPlace = place
        wasLoaded = loaded

        val player = characters.player(memory)
        val changes = flags.refresh(memory)
        val present = listOf(loaded, areaPresent, clock.refresh(memory), flags.isPresent)
            .count { it } + player.present

        val reading = world.read(memory, loaded, player.player, options)
        return RuntimeSnapshot(
            inGameTimeMillis = clock.timeMillis(),
            frame = clock.frame(),
            place = place,
            loaded = loaded,
            reloaded = reloaded,
            player = if (loaded) player.player else null,
            world = player.world,
            camera = if (loaded) cameraReader.camera() else null,
            menu = menu,
            loadQueue = reading.loadQueue,
            characters = reading.characters,
            flagChanges = changes,
            animation = reading.animation,
            focus = reading.focus,
            watchedFlag = options.watchedFlag?.let { WatchedFlag(it, isFlagSet(it)) },
            cost = SampleCost(
                started.elapsedNow(),
                present,
                characters.playerStructures + AROUND_PLAYER
            ),
            writes = writer.active,
            history = writer.records,
            writeResults = writes.takeResults()
        )
    }

    internal fun afterSample() {
        extensions.forEach(SessionExtension::afterSample)
        writes.afterSample(memory, module, wasLoaded, lastPlace ?: WorldPlace.Unreadable)
    }

    public fun readPlacedEnemies(): List<BlockRoster> = placedPartsReader.read(memory)

    public fun readCharacterEffects(): List<CharacterSnapshot> =
        characters.read(memory, allEffects = true).characters

    public fun resolveMissing(declared: List<GamePointer>, module: AddressRange): ResolvedPointers? {
        val found = memory.memory.resolveMissing(pointers, declared, module) ?: return null
        pointers = found
        memory.adopt(found)
        return found
    }

    internal fun apply(ticket: Long, request: WriteRequest) = writes.apply(ticket, request, memory, ::revertAll)

    internal fun revertAll(): Int {
        extensions.forEach(SessionExtension::release)
        writes.cancelAll(memory.memory)
        return writer.revertAll()
    }

    /** Drops every walk and everything kept about the world that just went away */
    private fun forgetWorld() {
        memory.invalidate(Lifetime.World)
        world = newWorld()
    }

    private fun newWorld(): WorldReads = WorldReads(layout, characters, loadQueueReader)

    private companion object {
        /** The follow cam, the map, the clock and the flag block, beside the player's own structures */
        const val AROUND_PLAYER = 4
    }
}
