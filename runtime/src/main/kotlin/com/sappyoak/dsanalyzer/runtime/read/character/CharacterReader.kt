package com.sappyoak.dsanalyzer.runtime.read.character

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.CharacterLayout
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.layout.TargetLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.TableView
import com.sappyoak.dsanalyzer.runtime.read.action.PTDEActionReader
import com.sappyoak.dsanalyzer.runtime.read.effects.SpecialEffectReader
import com.sappyoak.dsanalyzer.runtime.session.BONFIRE_MODEL
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.shared.math.Vec3

private const val NO_TARGET = -1
private const val PLAYER_OWN_STRUCTURES = 2

/** Room for a pointer of either width past the field it starts at */
private const val POINTER_ROOM = 8
private const val VEC3_BYTES = 3 * Float.SIZE_BYTES

internal class CharacterReader(
    layout: GameLayout,
    private val actions: PTDEActionReader?
) : CharacterSource {
    private val chars: CharacterLayout = layout.characters
    private val chr = chars.chr

    private val manager = StructView(chars.manager)
    private val playerSlots = TableView(chars.playerSlots)
    private val blocks = TableView(chars.blocks)
    private val characters = TableView(chars.blockCharacters)
    private val slot = StructView(chars.slot.character + POINTER_ROOM)
    private val one = StructView(chr.size)
    private val word = StructView(Int.SIZE_BYTES)

    private val control = StructView(chars.control.position + POINTER_ROOM)
    private val pose = StructView(chars.pose.position + VEC3_BYTES)

    private val effects = SpecialEffectReader(layout.specialEffect)
    private val details = PlayerDetailsReader(layout)

    override val playerStructures: Int = PLAYER_OWN_STRUCTURES + details.structures

    override fun read(game: GameMemory, allEffects: Boolean): CharacterReading {
        if (!manager.refresh(game)) return CharacterReading.Empty

        val walk = Walk(manager.pointer(chars.player), allEffects)

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

        val player = manager.pointer(chars.player)
        if (player.isNull || !one.refresh(game.memory, player)) return PlayerReading(null, null, 1)

        val base = snapshotOf(game, posed = true, withEffects = true)
        return PlayerReading(
            player = details.read(game, one, base),
            world = details.world(deathCam = manager.boolean(chars.deathCam)),
            present = PLAYER_OWN_STRUCTURES + details.present
        )
    }

    private fun snapshotOf(game: GameMemory, posed: Boolean, withEffects: Boolean): CharacterSnapshot =
        CharacterSnapshot(
            handle = one.int(chr.handle),
            modelId = one.int(chr.modelId),
            npcParamId = one.int(chr.npcParamId),
            position = (if (posed) posedPosition(game) else null) ?: transformPosition(),
            health = one.int(chr.health),
            healthMax = one.int(chr.healthMax),
            stamina = one.int(chr.stamina),
            characterType = one.int(chr.chrType),
            teamType = one.int(chr.teamType),
            targetHandle = targetOf(game)?.takeIf { it != NO_TARGET },
            effects = if (withEffects) effects.read(game, one.pointer(chr.specialEffect)) else null,
            action = actions?.read(game.memory, one)
        )

    private fun targetOf(game: GameMemory): Int? = when (val target = chr.target) {
        is TargetLayout.InCharacter -> one.int(target.offset)
        is TargetLayout.InManipulator -> {
            val manipulator = one.pointer(chr.comManipulator)
            if (!manipulator.isNull && word.refresh(game.memory, manipulator + target.offset.toLong())) word.int(0) else null
        }
    }

    private fun posedPosition(game: GameMemory): Vec3? {
        val controller = one.pointer(chr.control)
        if (controller.isNull || !control.refresh(game.memory, controller)) return null

        val position = control.pointer(chars.control.position)
        if (position.isNull || !pose.refresh(game.memory, position)) return null

        return pose.vec3(chars.pose.position)
    }

    private fun transformPosition(): Vec3 {
        val at = chr.position
        return Vec3(one.float(at.x), one.float(at.x + at.stride), one.float(at.x + 2 * at.stride))
    }

    private inner class Walk(private val player: Address, private val allEffects: Boolean) {
        val found = ArrayList<CharacterSnapshot>()
        val refs = ArrayList<CharacterRef>()
        var playerHandle: Int? = null

        fun visit(game: GameMemory, at: Address) {
            if (!slot.refresh(game.memory, at)) return

            val character = slot.pointer(chars.slot.character)
            if (character.isNull || !one.refresh(game.memory, character)) return

            if (character == player) {
                playerHandle = one.int(chr.handle)
                return
            }

            if (one.int(chr.modelId) == BONFIRE_MODEL) return

            refs.add(CharacterRef(
                handle = one.int(chr.handle),
                comManipulator = one.pointer(chr.comManipulator),
                controller = one.pointer(chr.control)
            ))
            found.add(snapshotOf(game, posed = false, withEffects = allEffects))
        }
    }
}
