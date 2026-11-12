package com.sappyoak.dsanalyzer.game.world.items

/** How a list of items is split for picking one, from each item param's own type field */
public enum class ItemGroup(public val label: String) {
    Weapon("Weapons"),
    Shield("Shields"),
    Catalyst("Catalysts and talismans"),
    Ranged("Bows and crossbows"),
    Ammo("Arrows and bolts"),
    Armor("Armor"),
    Hair("Hair styles"),
    Ring("Rings"),
    Consumable("Consumables"),
    KeyItem("Key items"),
    UpgradeMaterial("Upgrade materials"),
    Spell("Spells");
}
