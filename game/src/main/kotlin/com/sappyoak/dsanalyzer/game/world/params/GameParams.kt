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

public class GameParams internal constructor(
    public val tables: Map<GameParam<*>, TypedParam<*>>
) {
    /**
     * Throws for a param the loaded edition does not have — [GameParam.typeFor] is how to ask whether
     * it does. Every param [resolve] reaches is in both editions
     */
    @Suppress("UNCHECKED_CAST")
    public operator fun <R : ParamRow> get(param: GameParam<R>): TypedParam<R> = tables.getValue(param) as TypedParam<R>

    public fun resolve(ref: ParamRef, side: ParamSide): ParamRow? = tables.getValue(ref.kind.paramFor(side))[ref.id]
}

/** The param a reference of this kind lands in. Only an attack's depends on [side] */
public fun ParamKind.paramFor(side: ParamSide): GameParam<*> = when (this) {
    ParamKind.Attack -> if (side == ParamSide.Player) GameParam.AtkParamPc else GameParam.AtkParamNpc
    ParamKind.Bullet -> GameParam.Bullet
    ParamKind.SpEffect -> GameParam.SpEffectParam
    ParamKind.Weapon -> GameParam.EquipParamWeapon
    ParamKind.Protector -> GameParam.EquipParamProtector
    ParamKind.Accessory -> GameParam.EquipParamAccessory
    ParamKind.Goods -> GameParam.EquipParamGoods
    ParamKind.Magic -> GameParam.Magic
}

/** Whose attacks this param's references name: the player's own behaviors and spells, else everyone else's */
public val GameParam<*>.side: ParamSide
    get() = if (this == GameParam.BehaviorParamPc || this == GameParam.Magic) ParamSide.Player else ParamSide.Npc


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
