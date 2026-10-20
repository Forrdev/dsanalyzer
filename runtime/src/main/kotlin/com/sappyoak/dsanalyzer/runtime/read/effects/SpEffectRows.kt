package com.sappyoak.dsanalyzer.runtime.read.effects

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.params.SpEffectFields
import com.sappyoak.dsanalyzer.runtime.params.SpEffectParam
import com.sappyoak.dsanalyzer.runtime.params.SP_EFFECT_PARAM_SIZE
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView


internal data class SpEffectRow(
    val at: Long,
    val stateInfo: Int,
    val fields: Int,
    val maxHpRate: Float,
    val unlinkId: Int
) {
    val hasMagnitude: Boolean get() = fields and SpEffectFields.HasMagnitude != 0
    val hasDuration: Boolean get() = fields and SpEffectFields.HasDuration != 0
}

internal class SpEffectRows {
    private val view = StructView(SP_EFFECT_PARAM_SIZE)
    private val byId = HashMap<Int, SpEffectRow>()
    private val moved = HashSet<Int>()

    val movedRows: Set<Int> get() = moved

    fun beginPass() {
        moved.clear()
    }

    fun of(game: GameMemory, effectId: Int, at: Address): SpEffectRow? {
        if (at.isNull) return null

        val known = byId[effectId]
        if (known != null) {
            if (known.at == at.value) return known
            moved.add(effectId)
        }

        if (!view.refresh(game.memory, at)) return null

        val row = SpEffectRow(
            at = at.value,
            stateInfo = view.unsigned(SpEffectParam.StateInfo),
            fields = view.unsigned(SpEffectParam.Fields),
            maxHpRate = view.float(SpEffectParam.MaxHpRate),
            unlinkId = view.short(SpEffectParam.UnlinkId).toInt() and 0xFFFF
        )
        byId[effectId] = row
        return row
    }
}