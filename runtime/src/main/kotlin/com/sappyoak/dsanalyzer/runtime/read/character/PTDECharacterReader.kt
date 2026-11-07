package com.sappyoak.dsanalyzer.runtime.read.character

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.TableView
import com.sappyoak.dsanalyzer.runtime.ptde.CHARACTER_SLOT_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.ChrPosData
import com.sappyoak.dsanalyzer.runtime.ptde.ChrCtrl
import com.sappyoak.dsanalyzer.runtime.ptde.ChrIns
import com.sappyoak.dsanalyzer.runtime.ptde.WorldBlock
import com.sappyoak.dsanalyzer.runtime.ptde.WorldChrMan
import com.sappyoak.dsanalyzer.runtime.read.effects.SpecialEffectReader
import com.sappyoak.dsanalyzer.runtime.ptde.CharacterSlot
import com.sappyoak.dsanalyzer.runtime.read.action.PTDEActionReader
import com.sappyoak.dsanalyzer.runtime.session.BONFIRE_MODEL
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.shared.math.Vec3

private const val NO_TARGET = -1
private const val PLAYER_OWN_STRUCTURES = 2

internal class PTDECharacterReader(layout: GameLayout) : CharacterSource {
    private val manager = StructView(WorldChrMan.Pointer)
    private val playerSlots = TableView(WorldChrMan.PlayerSlots)
    private val blocks = TableView(WorldChrMan.Blocks)
    private val characters = TableView(WorldBlock.Characters)
    private val slot = StructView(CHARACTER_SLOT_SIZE)
    private val one = StructView(ChrIns.Pointer.size)

    private val control = StructView(ChrCtrl.PositionPointer + Int.SIZE_BYTES)
    private val pose = StructView(ChrPosData.Position + VEC3_BYTES)

    private val effects = SpecialEffectReader(layout.specialEffect)
    private val actions = PTDEActionReader()
    private val details = PTDEPlayerDetailsReader(layout.playerGameData)

    override val playerStructures: Int = PLAYER_OWN_STRUCTURES + details.structures

    override fun read(game: GameMemory, allEffects: Boolean): CharacterReading {
        if (!manager.refresh(game)) return CharacterReading.Empty

        val walk = Walk(manager.pointer(WorldChrMan.PlayerPointer), allEffects)

        if (playerSlots.refresh(game.memory, manager.address)) {
            playerSlots.elements.forEach { walk.visit(game, it) }
        }

        if (blocks.refresh(game.memory, manager.address)) {
            blocks.elements.forEach { block ->
                if (characters.refresh(game.memory, block)) {
                    characters.elements.forEach { walk.visit(game, it) }
                }
            }
        }

        return CharacterReading(walk.found, walk.refs, walk.playerHandle)
    }

    override fun player(game: GameMemory): PlayerReading {
        if (!manager.refresh(game)) return PlayerReading.Empty

        val player = manager.pointer(WorldChrMan.PlayerPointer)
        if (player.isNull || !one.refresh(game.memory, player)) return PlayerReading(null, null, 1)

        val base = snapshotOf(game, posed = true, withEffects = true)
        val snapshot = details.read(game, one, base)
        return PlayerReading(
            player = snapshot,
            world = details.world(deathCam = manager.boolean(WorldChrMan.DeathCamActive)),
            present = PLAYER_OWN_STRUCTURES + details.present
        )
    }
    private fun snapshotOf(
        game: GameMemory,
        posed: Boolean,
        withEffects: Boolean
    ): CharacterSnapshot = CharacterSnapshot(
        handle = one.int(ChrIns.Handle),
        modelId = one.int(ChrIns.ModelId),
        npcParamId = one.int(ChrIns.NpcParamId),
        position = (if (posed) posedPosition(game) else null) ?: transformOf(one),
        health = one.int(ChrIns.Health),
        healthMax = one.int(ChrIns.HealthMax),
        stamina = one.int(ChrIns.Stamina),
        characterType = one.int(ChrIns.ChrType),
        teamType = one.int(ChrIns.TeamType),
        targetHandle = one.int(ChrIns.TargetHandle).takeIf { it != NO_TARGET },
        effects = if (withEffects) effects.read(game, one.pointer(ChrIns.SpecialEffectPointer)) else null,
        action = actions.read(game.memory, one)
    )

    private fun posedPosition(game: GameMemory): Vec3? {
        val controller = one.pointer(ChrIns.ChrCtrlPointer)
        if (controller.isNull || !control.refresh(game.memory, controller)) return null

        val position = control.pointer(ChrCtrl.PositionPointer)
        if (position.isNull || !pose.refresh(game.memory, position)) return null

        return pose.vec3(ChrPosData.Position)
    }

    private inner class Walk(
        private val player: Address,
        private val allEffects: Boolean
    ) {
        val found = ArrayList<CharacterSnapshot>()
        val refs = ArrayList<CharacterRef>()
        var playerHandle: Int? = null

        fun visit(game: GameMemory, at: Address) {
            if (!slot.refresh(game.memory, at)) return

            val character = slot.pointer(CharacterSlot.Character)
            if (character.isNull || !one.refresh(game.memory, character)) return

            if (character == player) {
                playerHandle = one.int(ChrIns.Handle)
                return
            }

            if (one.int(ChrIns.ModelId) == BONFIRE_MODEL) return

            refs.add(CharacterRef(
                handle = one.int(ChrIns.Handle),
                comManipulator = one.pointer(ChrIns.ComManipulatorPointer),
                controller = one.pointer(ChrIns.ChrCtrlPointer)
            ))
            found.add(snapshotOf(game, posed = false, withEffects = allEffects))
        }
    }
}

private const val VEC3_BYTES = 3 * Float.SIZE_BYTES

private fun transformOf(view: StructView): Vec3 = Vec3(
    view.float(ChrIns.PositionX),
    view.float(ChrIns.PositionY),
    view.float(ChrIns.PositionZ)
)
