package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.TableView
import com.sappyoak.dsanalyzer.runtime.ptde.ChrPosData
import com.sappyoak.dsanalyzer.runtime.ptde.ChrCtrl
import com.sappyoak.dsanalyzer.runtime.ptde.ChrIns
import com.sappyoak.dsanalyzer.runtime.ptde.WorldCharacters
import com.sappyoak.dsanalyzer.runtime.layout.SpecialEffectLayout
import com.sappyoak.dsanalyzer.runtime.read.effects.SpecialEffectReader
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.shared.math.Vec3

private const val NO_TARGET = -1

internal class CharacterReader(effectLayout: SpecialEffectLayout) {
    private val container = StructView(WorldCharacters.Pointer)
    private val all = TableView(WorldCharacters.All)
    private val one = StructView(ChrIns.Pointer.size)

    private val control = StructView(ChrCtrl.PositionPointer + Int.SIZE_BYTES)
    private val pose = StructView(ChrPosData.Position + VEC3_BYTES)

    private val effects = SpecialEffectReader(effectLayout)

    fun read(game: GameMemory, allEffects: Boolean = false): List<CharacterSnapshot> {
        if (!container.refresh(game)) return emptyList()
        if (!all.refresh(game.memory, container.address)) return emptyList()

        return all.elements.mapIndexedNotNull { index, at -> characterAt(game, index, at, allEffects) }
    }

    private fun characterAt(
        game: GameMemory,
        index: Int,
        at: Address,
        allEffects: Boolean
    ): CharacterSnapshot? {
        if (!one.refresh(game.memory, at)) return null

        val target = one.int(ChrIns.TargetHandle)

        val isPlayer = index == 0

        return CharacterSnapshot(
            handle = one.int(ChrIns.Handle),
            modelId = one.int(ChrIns.ModelId),
            npcParamId = one.int(ChrIns.NpcParamId),
            position = if (isPlayer) posedPosition(game) ?: transformOf(one) else transformOf(one),
            health = one.int(ChrIns.Health),
            healthMax = one.int(ChrIns.HealthMax),
            stamina = one.int(ChrIns.Stamina),
            characterType = one.int(ChrIns.ChrType),
            teamType = one.int(ChrIns.TeamType),
            targetHandle = target.takeIf { it != NO_TARGET },
            isPlayer = isPlayer,
            // Read off the ChrIns copy already in hand, so the holder costs no walk of its own
            effects = if (isPlayer || allEffects) {
                effects.read(game, one.pointer(ChrIns.SpecialEffectPointer))
            } else {
                null
            }
        )
    }

    private fun posedPosition(game: GameMemory): Vec3? {
        val controller = one.pointer(ChrIns.ChrCtrlPointer)
        if (controller.isNull || !control.refresh(game.memory, controller)) return null

        val position = control.pointer(ChrCtrl.PositionPointer)
        if (position.isNull || !pose.refresh(game.memory, position)) return null

        return pose.vec3(ChrPosData.Position)
    }
}

private const val VEC3_BYTES = 3 * Float.SIZE_BYTES

private fun transformOf(view: StructView): Vec3 = Vec3(
    view.float(ChrIns.PositionX),
    view.float(ChrIns.PositionY),
    view.float(ChrIns.PositionZ)
)
