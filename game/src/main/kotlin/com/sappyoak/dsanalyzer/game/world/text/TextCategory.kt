package com.sappyoak.dsanalyzer.game.world.text

/** One string file inside one of the containers by the id the container indexes it under */
public data class FmgRef(
    public val archive: TextArchive,
    public val fmgId: Int
)

/**
 * The string files that name things, and the DLC file that overrides each one
 *
 * ** The patch is not options ** AOTA adds and rewrites entries, and it does so in a separate file rather
 * than by editing the base
 */
public enum class TextCategory(
    public val base: FmgRef,
    public val patch: FmgRef?,
    public val label: String
) {
    ItemNames(FmgRef(TextArchive.Item, 10), FmgRef(TextArchive.Menu, 111), "Item names"),
    WeaponNames(FmgRef(TextArchive.Item, 11), FmgRef(TextArchive.Menu, 115), "Weapon names"),
    ArmorNames(FmgRef(TextArchive.Item, 12), FmgRef(TextArchive.Menu, 117), "Armour names"),
    AccessoryNames(FmgRef(TextArchive.Item, 13), FmgRef(TextArchive.Menu, 113), "Ring names"),
    SpellNames(FmgRef(TextArchive.Item, 14), FmgRef(TextArchive.Menu, 118), "Spell names"),
    FeatureNames(FmgRef(TextArchive.Item, 15), null, "Feature names"),
    NpcNames(FmgRef(TextArchive.Item, 18), FmgRef(TextArchive.Menu, 119), "NPC names"),
    PlaceNames(FmgRef(TextArchive.Item, 19), FmgRef(TextArchive.Menu, 120), "Place names"),
    Conversations(FmgRef(TextArchive.Menu, 1), FmgRef(TextArchive.Menu, 104), "Conversations"),
    EventText(FmgRef(TextArchive.Menu, 30), FmgRef(TextArchive.Menu, 101), "Event text");

    public companion object {
        public val naming: List<TextCategory> = entries.filter { it != Conversations && it != EventText }
    }
}