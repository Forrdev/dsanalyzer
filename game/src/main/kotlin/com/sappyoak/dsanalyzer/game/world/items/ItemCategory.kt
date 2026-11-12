package com.sappyoak.dsanalyzer.game.world.items

import com.sappyoak.dsanalyzer.game.world.params.GameParam

/** The item kinds the game's inventory calls take, by the code in the top bits of an item reference */
public enum class ItemCategory(public val code: Int, public val param: GameParam<*>) {
    Weapon(0x0000_0000, GameParam.EquipParamWeapon),
    Armor(0x1000_0000, GameParam.EquipParamProtector),
    Ring(0x2000_0000, GameParam.EquipParamAccessory),
    Goods(0x4000_0000, GameParam.EquipParamGoods);
}
