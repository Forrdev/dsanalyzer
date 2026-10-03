package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.native.process.ProcessInfo
import com.sappyoak.dsanalyzer.runtime.GameConnection
import com.sappyoak.dsanalyzer.runtime.GameProcess
import com.sappyoak.dsanalyzer.runtime.pointers.FakeGame
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.resolvePointers
import com.sappyoak.dsanalyzer.runtime.ptde.AnimData
import com.sappyoak.dsanalyzer.runtime.ptde.CharPosData
import com.sappyoak.dsanalyzer.runtime.ptde.CharData
import com.sappyoak.dsanalyzer.runtime.ptde.CharMapData
import com.sappyoak.dsanalyzer.runtime.ptde.DeathCam
import com.sappyoak.dsanalyzer.runtime.ptde.EventFlags
import com.sappyoak.dsanalyzer.runtime.ptde.GameDataMan
import com.sappyoak.dsanalyzer.runtime.ptde.PlayerStats
import com.sappyoak.dsanalyzer.runtime.ptde.PTDEPointers
import com.sappyoak.dsanalyzer.runtime.ptde.WorldArea
import com.sappyoak.dsanalyzer.runtime.ptde.WorldState

internal val PTDE_PROCESS = GameProcess(ProcessInfo(4242, "DARKSOULS.exe"), GameEdition.PrepareToDie)

/** The version word sits at a fixed distance into the module */
private const val VERSION_AT = 0x80

/** One slot per signature, far enough apart for the longest pattern */
private const val SIGNATURES_AT = 0x1000
private const val SIGNATURE_STRIDE = 0x40

/** The globals the operands name */
private const val STATICS_AT = 0x2000
private const val STATIC_STRIDE = 0x10

/** Intermediate links, then the structure bodies themselves */
private const val LINKS_AT = 0x3000
internal const val CHARACTER_AT = 0x4000
internal const val PLACEMENT_AT = 0x5000
internal const val POSITION_AT = 0x6000
internal const val ANIMATION_AT = 0x6100
internal const val ATTRIBUTES_AT = 0x7000
internal const val WORLD_AT = 0x8000
internal const val AREA_AT = 0x9000
internal const val DEATH_CAM_AT = 0xA000
internal const val GAME_DATA_AT = 0xA100
internal const val FLAGS_AT = 0x20000

/** A second character structure, for proving a reload re-resolves the walk to it */
internal const val RELOADED_CHARACTER_AT = 0xB000

/**
 * A synthetic PTDE process laid out from the tables themselves.
 *
 * The signature bytes are planted by rendering each pattern back out, so the fixture cannot drift
 * away from the table it is testing, changing a pattern changes what gets planted
 */
internal class PTDEFixture {
    val game: FakeGame = FakeGame()

    init {
        game.int(VERSION_AT, STEAM_VERSION)
        plantSignatures()
        plantWalks()
    }

    fun session(): GameSession {
        val resolved = game.resolvePointers(PTDEPointers, game.module)
        return GameSession(PTDE_PROCESS, resolved, GameMemory(game, resolved))
    }

    /** The same process behind a connection, for anything testing the watch rather than a tick */
    fun connection(game: GameProcess = PTDE_PROCESS): GameConnection =
        GameConnection(game, this.game.module, FakeAttached(game.process, this.game))

    /** Fills the structures with values a running game would have */
    fun populate() {
        map(world = 10, area = 2)
        game.int(CHARACTER_AT + CharData.Health, 837)
        game.int(CHARACTER_AT + CharData.Stamina, 92)
        game.int(CHARACTER_AT + CharData.ChrType, 0)
        game.int(CHARACTER_AT + CharData.TeamType, 1)
        game.int(CHARACTER_AT + CharData.PlayRegion, 1_002_600)
        game.float(POSITION_AT + CharPosData.Position, 12.5f)
        game.float(POSITION_AT + CharPosData.Position + 4, -30f)
        game.float(POSITION_AT + CharPosData.Position + 8, 44f)
        game.float(POSITION_AT + CharPosData.Angle, 1.25f)
        game.float(ANIMATION_AT + AnimData.PlaySpeed, 2f)
        game.int(ATTRIBUTES_AT + PlayerStats.HealthMax, 1000)
        game.int(ATTRIBUTES_AT + PlayerStats.SoulLevel, 42)
        game.int(ATTRIBUTES_AT + PlayerStats.Souls, 55_000)
        game.write(ATTRIBUTES_AT + PlayerStats.Covenant, 3)
        game.float(WORLD_AT + WorldState.StablePosition, 12.5f)
        game.float(WORLD_AT + WorldState.StablePosition + 4, -30f)
        game.float(WORLD_AT + WorldState.StablePosition + 8, 44f)
        game.int(WORLD_AT + WorldState.LastBonfire, 1_012_960)
        game.int(GAME_DATA_AT + GameDataMan.InGameTimeMillis, 66_000)
    }

    /** Breaks the last link of the character walk, the way a load frees it */
    fun deallocateCharacter() {
        game.nullPointer(LINKS_AT + 0x100)
    }

    /** Points the character walk at [RELOADED_CHARACTER_AT], the way a load reallocates it */
    fun reallocateCharacter() {
        game.pointer(LINKS_AT + 0x100, RELOADED_CHARACTER_AT)
        game.pointer(RELOADED_CHARACTER_AT + CharData.CharMapDataPointer, PLACEMENT_AT)
    }

    fun map(world: Int, area: Int) {
        game.write(AREA_AT + WorldArea.World, world)
        game.write(AREA_AT + WorldArea.Area, area)
    }

    fun setFlag(flagId: Int, set: Boolean) {
        val at = checkNotNull(EventFlags.locate(flagId))
        game.int(FLAGS_AT + at.byteOffset, if (set) at.mask.toInt() else 0)
    }

    private fun plantSignatures() {
        distinctBases().forEachIndexed { slot, pointer ->
            val at = SIGNATURES_AT + slot * SIGNATURE_STRIDE
            val target = pointer.base.target
            require(target is SignatureTarget.Embedded) { "${pointer.base} is not planted by this fixture" }

            pointer.base.pattern.toString().split(' ').forEachIndexed { index, token ->
                if (token != WILDCARD) game.write(at + index, token.toInt(16))
            }
            game.pointer(at + target.at, STATICS_AT + slot * STATIC_STRIDE)
        }
    }

    /** Each walk laid out link by link, so the offsets in the tables are the ones exercised */
    private fun plantWalks() {
        val statics = distinctBases().withIndex().associate { (slot, pointer) ->
            pointer.base to STATICS_AT + slot * STATIC_STRIDE
        }

        // CharData1: static -> +0 -> +4 -> +0
        game.pointer(statics.getValue(CharData.Base), LINKS_AT)
        game.pointer(LINKS_AT + 4, LINKS_AT + 0x100)
        game.pointer(LINKS_AT + 0x100, CHARACTER_AT)
        game.pointer(CHARACTER_AT + CharData.CharMapDataPointer, PLACEMENT_AT)
        game.pointer(PLACEMENT_AT + CharMapData.PositionPointer, POSITION_AT)
        game.pointer(PLACEMENT_AT + CharMapData.AnimDataPointer, ANIMATION_AT)

        // CharData2: static -> +0 -> +8
        game.pointer(statics.getValue(PlayerStats.Pointer.base), LINKS_AT + 0x200)
        game.pointer(LINKS_AT + 0x208, ATTRIBUTES_AT)

        game.pointer(statics.getValue(WorldState.Pointer.base), WORLD_AT)
        game.pointer(statics.getValue(WorldArea.Pointer.base), AREA_AT)
        game.pointer(statics.getValue(DeathCam.Pointer.base), DEATH_CAM_AT)
        game.pointer(statics.getValue(GameDataMan.Pointer.base), GAME_DATA_AT)

        // EventFlags: static -> +0 -> +0
        game.pointer(statics.getValue(EventFlags.Pointer.base), LINKS_AT + 0x300)
        game.pointer(LINKS_AT + 0x300, FLAGS_AT)
    }

    private fun distinctBases(): List<GamePointer> = PTDEPointers.distinctBy { it.base }

    private companion object {
        const val WILDCARD = "??"

        /** The word the Steam build carries, which [PTDEBuild.of] should recognise */
        val STEAM_VERSION: Int = 0xFC293654.toInt()
    }
}