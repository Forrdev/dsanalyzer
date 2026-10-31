package com.sappyoak.dsanalyzer.game.world.params

import com.sappyoak.dsanalyzer.formats.param.Param
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.TypedParam
import com.sappyoak.dsanalyzer.formats.param.refs.ParamKind
import com.sappyoak.dsanalyzer.formats.param.refs.ParamRef
import com.sappyoak.dsanalyzer.formats.param.typed
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.files.GameFiles

/** Whose attacks an id names: the player's rows ('AtkParam_Pc') or everyone else's ('AtkParam_Npc') */
public enum class ParamSide { Player, Npc }

public class GameParams internal constructor(private val tables: Map<GameParam<*>, TypedParam<*>>) {
    /**
     * Throws for a param the loaded edition does not have — [GameParam.typeFor] is how to ask whether
     * it does. Every param [resolve] reaches is in both editions
     */
    @Suppress("UNCHECKED_CAST")
    public operator fun <R : ParamRow> get(param: GameParam<R>): TypedParam<R> = tables.getValue(param) as TypedParam<R>

    public fun resolve(ref: ParamRef, side: ParamSide): ParamRow? = when (ref.kind) {
        ParamKind.Attack -> this[if (side == ParamSide.Player) GameParam.AtkParamPc else GameParam.AtkParamNpc][ref.id]
        ParamKind.Bullet -> this[GameParam.Bullet][ref.id]
        ParamKind.SpEffect -> this[GameParam.SpEffectParam][ref.id]
        ParamKind.Weapon -> this[GameParam.EquipParamWeapon][ref.id]
        ParamKind.Protector -> this[GameParam.EquipParamProtector][ref.id]
        ParamKind.Accessory -> this[GameParam.EquipParamAccessory][ref.id]
        ParamKind.Goods -> this[GameParam.EquipParamGoods][ref.id]
        ParamKind.Magic -> this[GameParam.Magic][ref.id]
    }
}

/**
 * Reads 'GameParam.parambnd' through the generated row classes, as [edition] lays them out
 *
 * Params that edition does not have are skipped rather than failing: Remastered carries three the
 * original never had. A param it *does* have and the binder does not is still an error, because that
 * is a broken installation rather than an edition difference
 */
public fun GameFiles.loadGameParams(edition: GameEdition): GameParams? = loadParamFiles()?.let { files ->
    GameParams(
        GameParam.entries
            .mapNotNull { param -> param.readFor(edition, files)?.let { param to it } }
            .toMap()
    )
}

private fun <R : ParamRow> GameParam<R>.readFor(
    edition: GameEdition,
    files: Map<String, Param>
): TypedParam<R>? {
    val type = typeFor(edition) ?: return null
    return checkNotNull(files[fileName]) { "GameParam.parambnd has no $fileName" }.typed(type)
}
