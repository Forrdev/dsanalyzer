package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.TableView
import com.sappyoak.dsanalyzer.runtime.ptde.ChrCtrl
import com.sappyoak.dsanalyzer.runtime.ptde.ChrIns
import com.sappyoak.dsanalyzer.runtime.ptde.ChrPosData
import com.sappyoak.dsanalyzer.runtime.ptde.WorldCharacters
import com.sappyoak.dsanalyzer.shared.math.Vec3

/** No target. The game defaults the field to this rather than zeroing it */
private const val NO_TARGET = -1

public data class CharacterSnapshot(
    public val handle: Int,
    public val modelId: Int,
    public val npcParamId: Int,
    public val position: Vec3,
    public val health: Int,
    public val healthMax: Int,
    public val stamina: Int,
    public val characterType: Int,
    public val teamType: Int,
    /** The [handle] this character is targeting, or null */
    public val targetHandle: Int?,
    public val isPlayer: Boolean
) {
    public val alive: Boolean get() = health > 0
    public val modelName: String get() = "c%04d".format(modelId)
}

internal class CharacterReader {
    private val container = StructView(WorldCharacters.Pointer)
    private val all = TableView(WorldCharacters.All)
    private val one = StructView(ChrIns.Pointer.size)

    private val control = StructView(ChrCtrl.PositionPointer + Int.SIZE_BYTES)
    private val pose = StructView(ChrPosData.Position + VEC3_BYTES)

    fun read(game: GameMemory): List<CharacterSnapshot> {
        if (!container.refresh(game)) return emptyList()
        if (!all.refresh(game.memory, container.address)) return emptyList()

        return all.elements.mapIndexedNotNull { index, at -> characterAt(game, index, at) }
    }

    private fun characterAt(game: GameMemory, index: Int, at: Address): CharacterSnapshot? {
        if (!one.refresh(game.memory, at)) return null

        val target = one.int(ChrIns.TargetHandle)
        val isPlayer = index == 0
        return CharacterSnapshot(
            one.int(ChrIns.Handle),
            modelId = one.int(ChrIns.ModelId),
            npcParamId = one.int(ChrIns.NpcParamId),
            position = if (isPlayer) posedPosition(game) ?: transformOf(one) else transformOf(one),
            health = one.int(ChrIns.Health),
            healthMax = one.int(ChrIns.HealthMax),
            stamina = one.int(ChrIns.Stamina),
            characterType = one.int(ChrIns.ChrType),
            teamType = one.int(ChrIns.TeamType),
            targetHandle = target.takeIf { it != NO_TARGET },
            isPlayer = isPlayer
        )
    }

    /** The player's position from the structure that actually keeps it */
    private fun posedPosition(game: GameMemory): Vec3? {
        val controller = one.pointer(ChrIns.ChrCtrlPointer)
        if (controller.isNull || !control.refresh(game.memory, controller)) return null

        val position = control.pointer(ChrCtrl.PositionPointer)
        if (position.isNull || !pose.refresh(game.memory, position)) return null

        return pose.vec3(ChrPosData.Position)
    }
}

private const val VEC3_BYTES = 3 * Float.SIZE_BYTES

/**
 * The translation of the 3x4 transform, which is three floats '0x10' apart rather than a 'vec3'
 */
private fun transformOf(view: StructView): Vec3 = Vec3(
    view.float(ChrIns.PositionX),
    view.float(ChrIns.PositionY),
    view.float(ChrIns.PositionZ)
)