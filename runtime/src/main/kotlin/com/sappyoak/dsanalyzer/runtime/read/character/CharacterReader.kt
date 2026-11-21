package com.sappyoak.dsanalyzer.runtime.read.character

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.CharacterLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.TableView
import com.sappyoak.dsanalyzer.runtime.read.action.ActionReader
import com.sappyoak.dsanalyzer.runtime.read.effects.SpecialEffectReader
import com.sappyoak.dsanalyzer.runtime.snapshot.BONFIRE_MODEL
import com.sappyoak.dsanalyzer.runtime.snapshot.CharacterSnapshot

private const val NO_TARGET = -1
private const val PLAYER_OWN_STRUCTURES = 2

/** Room for a pointer of either width past the field it starts at */
private const val POINTER_ROOM = 8

internal class CharacterReader(
    private val chars: CharacterLayout,
    private val effects: SpecialEffectReader,
    private val details: PlayerDetailsReader,
    private val actions: ActionReader
) : CharacterSource {
    private val chr = chars.chr

    private val manager = StructView(chars.manager)
    private val playerSlots = TableView(chars.playerSlots)
    private val blocks = TableView(chars.blocks)
    private val characters = TableView(chars.blockCharacters)
    private val slot = StructView(chars.slot.character + POINTER_ROOM)
    private val one = CharacterView(chars)

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

        val world = details.world(game, deathCam = manager.boolean(chars.deathCam))
        if (!one.refresh(game.memory, manager.pointer(chars.player))) {
            return PlayerReading(null, world, if (world == null) 1 else 2)
        }

        val base = snapshotOf(game, posed = true, withEffects = true)
        return PlayerReading(
            player = details.read(game, one, base),
            world = world,
            present = PLAYER_OWN_STRUCTURES + details.present
        )
    }

    private fun snapshotOf(game: GameMemory, posed: Boolean, withEffects: Boolean): CharacterSnapshot =
        CharacterSnapshot(
            handle = one.handle,
            modelId = one.modelId,
            npcParamId = one.fields.int(chr.npcParamId),
            position = one.position(posed),
            health = one.fields.int(chr.health),
            healthMax = one.fields.int(chr.healthMax),
            stamina = one.fields.int(chr.stamina),
            characterType = one.fields.int(chr.chrType),
            teamType = one.fields.int(chr.teamType),
            targetHandle = one.target()?.takeIf { it != NO_TARGET },
            effects = if (withEffects) effects.read(game, one.effects) else null,
            action = actions.read(game.memory, one)
        )

    private inner class Walk(private val player: Address, private val allEffects: Boolean) {
        val found = ArrayList<CharacterSnapshot>()
        val refs = ArrayList<CharacterRef>()
        var playerHandle: Int? = null

        fun visit(game: GameMemory, at: Address) {
            if (!slot.refresh(game.memory, at)) return
            if (!one.refresh(game.memory, slot.pointer(chars.slot.character))) return

            if (one.address == player) {
                playerHandle = one.handle
                return
            }

            if (one.modelId == BONFIRE_MODEL) return

            refs.add(one.ref())
            found.add(snapshotOf(game, posed = false, withEffects = allEffects))
        }
    }
}
