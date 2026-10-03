package com.sappyoak.dsanalyzer.runtime.session

import kotlin.time.TimeSource

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.runtime.GameConnection
import com.sappyoak.dsanalyzer.runtime.GameProcess
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.ResolvedPointers
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.resolvePointers
import com.sappyoak.dsanalyzer.runtime.ptde.AnimData
import com.sappyoak.dsanalyzer.runtime.ptde.CharData
import com.sappyoak.dsanalyzer.runtime.ptde.CharMapData
import com.sappyoak.dsanalyzer.runtime.ptde.CharPosData
import com.sappyoak.dsanalyzer.runtime.ptde.DeathCam
import com.sappyoak.dsanalyzer.runtime.ptde.EventFlagBlock
import com.sappyoak.dsanalyzer.runtime.ptde.GameDataMan
import com.sappyoak.dsanalyzer.runtime.ptde.PlayerStats
import com.sappyoak.dsanalyzer.runtime.ptde.WorldArea
import com.sappyoak.dsanalyzer.runtime.ptde.WorldState

private const val FIRST_WORLD = 10

public class GameSession internal constructor(
    public val game: GameProcess,
    public val pointers: ResolvedPointers,
    private val memory: GameMemory
) {
    private val worldArea = StructView(WorldArea.Pointer)
    private val character = StructView(CharData.Pointer)
    private val position = StructView(CharPosData.Pointer)
    private val placement = StructView(CharMapData.Pointer)
    private val animation = StructView(AnimData.Pointer)
    private val attributes = StructView(PlayerStats.Pointer)
    private val worldState = StructView(WorldState.Pointer)
    private val deathCam = StructView(DeathCam.Pointer)
    private val gameData = StructView(GameDataMan.Pointer)
    private val flags = EventFlagBlock()

    private val rest = listOf(character, position, placement, animation, attributes, worldState, deathCam, gameData)

    private var lastMap: MapId? = null

    public fun isFlagSet(flagId: Int): Boolean? = flags.isSet(flagId)

    public fun sample(): RuntimeSnapshot {
        val started = TimeSource.Monotonic.markNow()

        val areaPresent = worldArea.refresh(memory)
        val map = if (areaPresent) currentMap() else null
        val reloaded = map != null && lastMap != null && map != lastMap
        if (reloaded) {
            memory.invalidate(Lifetime.World)
            flags.reset()
        }

        if (map != null) {
            lastMap = map
        }

        var present = if (areaPresent) 1 else 0
        present += rest.count { it.refresh(memory) }

        val changes = flags.refresh(memory)
        if (flags.isPresent) {
            present++
        }

        val time = if (gameData.isPresent) gameData.int(GameDataMan.InGameTimeMillis) else 0

        return RuntimeSnapshot(
            inGameTimeMillis = time,
            frame = GameDataMan.frameOf(time),
            map = map,
            reloaded = reloaded,
            player = player(),
            world = world(),
            flagChanges = changes,
            cost = SampleCost(started.elapsedNow(), present, STRUCTURES)
        )
    }

    private fun currentMap(): MapId? {
        val world = worldArea.unsigned(WorldArea.World)
        if (world < FIRST_WORLD) return null
        return WorldArea.mapId(world = world, area = worldArea.unsigned(WorldArea.Area))
    }
    private fun player(): PlayerSnapshot? {
        if (!character.isPresent || !position.isPresent) return null

        return PlayerSnapshot(
            position = position.vec3(CharPosData.Position),
            angle = position.float(CharPosData.Angle),
            health = character.int(CharData.Health),
            stamina = character.int(CharData.Stamina),
            characterType = character.int(CharData.ChrType),
            teamType = character.int(CharData.TeamType),
            playRegion = character.int(CharData.PlayRegion),
            animationSpeed = if (animation.isPresent) animation.float(AnimData.PlaySpeed) else null,
            cheats = cheatsIn(
                flags1 = character.int(CharData.Flags1),
                flags2 = character.int(CharData.Flags2),
                mapFlags = if (placement.isPresent) placement.int(CharMapData.Flags) else 0
            ),
            attributes = attributes()
        )
    }

    private fun attributes(): AttributeSnapshot? {
        if (!attributes.isPresent) return null

        return AttributeSnapshot(
            healthMax = attributes.int(PlayerStats.HealthMax),
            staminaMax = attributes.int(PlayerStats.StaminaMax),
            soulLevel = attributes.int(PlayerStats.SoulLevel),
            souls = attributes.int(PlayerStats.Souls),
            humanity = attributes.int(PlayerStats.Humanity),
            covenant = attributes.unsigned(PlayerStats.Covenant),
            stance = attributes.int(PlayerStats.Stance)
        )
    }

    private fun world(): WorldSnapshot? {
        if (!worldState.isPresent) return null

        return WorldSnapshot(
            stablePosition = worldState.vec3(WorldState.StablePosition),
            stableAngle = worldState.float(WorldState.StableAngle),
            lastBonfire = worldState.int(WorldState.LastBonfire),
            deathCam = deathCam.isPresent && deathCam.unsigned(DeathCam.Active) != 0
        )
    }

    private companion object {
        const val STRUCTURES = 10
    }
}

public fun GameConnection.openSession(pointers: List<GamePointer>): GameSession {
    val resolved = memory.resolvePointers(pointers, mainModule)
    return GameSession(game, resolved, GameMemory(memory, resolved))
}