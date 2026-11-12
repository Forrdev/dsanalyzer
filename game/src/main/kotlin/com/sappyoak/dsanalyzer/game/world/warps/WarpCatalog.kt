package com.sappyoak.dsanalyzer.game.world.warps

import java.io.BufferedReader

import com.sappyoak.dsanalyzer.game.bundled
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.shared.math.Vec3

private const val RESOURCE = "/definitions/ds1-warps.tsv"
private const val COMMENT = '#'
private const val SEPARATOR = '\t'
private const val BONFIRE_COLUMNS = 4
private const val POINT_COLUMNS = 8

/** Where a warp puts the player in its map, with [angle] the yaw in radians */
public data class WarpPoint(public val position: Vec3, public val angle: Float)

/**
 * One listed warp. The game warps to [bonfire], or loads [map] and spawns at [point] where it has one.
 * [map] is the one [bonfire]'s entity id names, and [point] is in that map
 */
public data class CatalogWarp(
    public val group: String,
    public val name: String,
    public val bonfire: Int,
    public val map: MapId,
    public val point: WarpPoint?
)

/** The bonfire and boss warps bundled with the tool. Both editions share them */
public class WarpCatalog(public val warps: List<CatalogWarp>) {
    /** Each group, in the order the list first names it */
    public val groups: List<String> get() = warps.map(CatalogWarp::group).distinct()

    public fun inGroup(group: String): List<CatalogWarp> = warps.filter { it.group == group }
}

public fun loadWarpCatalog(): WarpCatalog = bundled(RESOURCE, ::readWarpCatalog)

public fun readWarpCatalog(reader: BufferedReader): WarpCatalog = WarpCatalog(
    reader.lineSequence()
        .filter { it.isNotBlank() && it.first() != COMMENT }
        .map(::parseWarp)
        .toList()
)

/** The map a bonfire's entity id names: its first two digits are the area, the third the block */
public fun mapOfBonfire(bonfire: Int): MapId = MapId.of(bonfire / 100_000, bonfire / 10_000 % 10)

private fun parseWarp(line: String): CatalogWarp {
    val fields = line.split(SEPARATOR)
    require(fields.size == BONFIRE_COLUMNS || fields.size == POINT_COLUMNS) { "Malformed warp line: $line" }
    val bonfire = fields[2].toInt()
    val map = requireNotNull(MapId.parse(fields[3])) { "Malformed map in warp line: $line" }
    require(map == mapOfBonfire(bonfire)) { "Warp line's map is not its bonfire's: $line" }
    val point = if (fields.size == POINT_COLUMNS) {
        WarpPoint(Vec3(fields[4].toFloat(), fields[5].toFloat(), fields[6].toFloat()), fields[7].toFloat())
    } else null
    return CatalogWarp(fields[0], fields[1], bonfire, map, point)
}
