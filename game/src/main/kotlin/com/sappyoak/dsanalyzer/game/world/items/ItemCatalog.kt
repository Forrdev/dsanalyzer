package com.sappyoak.dsanalyzer.game.world.items

import java.io.BufferedReader

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.bundled
import com.sappyoak.dsanalyzer.game.world.params.ParamNames

private const val COLUMNS = 6
private const val COMMENT = '#'
internal const val SEPARATOR = '\t'

/**
 * One item the inventory calls take. [maxUpgrade] is the highest level the params carry for it, added to
 * [id] for an upgraded one. [name] is the English one the catalog was generated with
 */
public data class CatalogItem(
    public val category: ItemCategory,
    public val id: Int,
    public val group: ItemGroup,
    public val maxCount: Int,
    public val maxUpgrade: Int,
    public val name: String
) {
    /** The name in [names]' language, or the English one where that text has none */
    public fun nameIn(names: ParamNames): String = names.nameOf(category.param, id) ?: name

    /** As one catalog line */
    public fun line(): String = listOf(category.name, id, group.name, maxCount, maxUpgrade, name).joinToString("$SEPARATOR")
}

/** Every item an edition's params name, generated once from its game files by `:tools:generateItemCatalog` */
public class ItemCatalog(public val items: List<CatalogItem>) {
    private val byKey = items.associateBy { it.category to it.id }

    public fun find(category: ItemCategory, id: Int): CatalogItem? = byKey[category to id]

    public fun inGroup(group: ItemGroup): List<CatalogItem> = items.filter { it.group == group }
}

/** Where an edition's catalog sits among the bundled resources */
public fun itemCatalogResource(edition: GameEdition): String = "/definitions/ds1-items-${edition.shortName}.tsv"

public fun loadItemCatalog(edition: GameEdition): ItemCatalog = bundled(itemCatalogResource(edition), ::readItemCatalog)

public fun readItemCatalog(reader: BufferedReader): ItemCatalog = ItemCatalog(
    reader.lineSequence()
        .filter { it.isNotBlank() && it.first() != COMMENT }
        .map(::parseItem)
        .toList()
)

private fun parseItem(line: String): CatalogItem {
    val fields = line.split(SEPARATOR, limit = COLUMNS)
    require(fields.size == COLUMNS) { "An item catalog line has $COLUMNS fields: $line" }
    return CatalogItem(
        category = ItemCategory.valueOf(fields[0]),
        id = fields[1].toInt(),
        group = ItemGroup.valueOf(fields[2]),
        maxCount = fields[3].toInt(),
        maxUpgrade = fields[4].toInt(),
        name = fields[5]
    )
}
