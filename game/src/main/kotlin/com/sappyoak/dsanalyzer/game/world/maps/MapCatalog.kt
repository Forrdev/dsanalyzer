package com.sappyoak.dsanalyzer.game.world.maps

public data class NamedMap(
    public val id: MapId,
    public val name: String,
    public val msbStem: String? = null
) {
    public val msbName: String get() = msbStem ?: id.name
}

private val MAPS: List<NamedMap> = listOf(
    NamedMap(MapId.of(10, 0), "Depths"),
    NamedMap(MapId.of(10, 1), "Undead Burg / Parish"),
    NamedMap(MapId.of(10, 2), "Firelink Shrine"),
    NamedMap(MapId.of(11, 0), "Painted World"),
    NamedMap(MapId.of(12, 0), "Darkroot Garden / Basin", msbStem = "m12_00_00_01"),
    NamedMap(MapId.of(12, 1), "Oolacile / Chasm of the Abyss"),
    NamedMap(MapId.of(13, 0), "Catacombs"),
    NamedMap(MapId.of(13, 1), "Tomb of the Giants"),
    NamedMap(MapId.of(13, 2), "Great Hollow / Ash Lake"),
    NamedMap(MapId.of(14, 0), "Blighttown / Quelaag's Domain"),
    NamedMap(MapId.of(14, 1), "Demon Ruins / Lost Izalith"),
    NamedMap(MapId.of(15, 0), "Sen's Fortress"),
    NamedMap(MapId.of(15, 1), "Anor Londo"),
    NamedMap(MapId.of(16, 0), "New Londo Ruins / Valley of Drakes"),
    NamedMap(MapId.of(17, 0), "Duke's Archives / Crystal Cave"),
    NamedMap(MapId.of(18, 0), "Kiln of the First Flame"),
    NamedMap(MapId.of(18, 1), "Undead Asylum")
)

private val BY_ID: Map<MapId, NamedMap> = MAPS.associateBy { it.id }
private val BY_MSB_NAME: Map<String, NamedMap> = MAPS.associateBy { it.msbName }

public val namedMaps: List<NamedMap> = MAPS

/** What this map is called, or its id when it is not known */
public val MapId.label: String get() = BY_ID[withoutVariant]?.name ?: name

public val MapId.named: NamedMap? get() = BY_ID[withoutVariant]

public val MapId.msbName: String get() = named?.msbName ?: name

public val MapId.canonical: MapId get() = BY_MSB_NAME[name]?.id ?: this