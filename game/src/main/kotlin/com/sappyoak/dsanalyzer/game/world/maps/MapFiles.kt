package com.sappyoak.dsanalyzer.game.world.maps

import com.sappyoak.dsanalyzer.formats.binder.OpenBinder
import com.sappyoak.dsanalyzer.formats.msb.MSB
import com.sappyoak.dsanalyzer.formats.msb.readMSB
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.openBinder

public fun GameFiles.loadMSB(map: MapId): MSB? = open(map.msbPath)?.let(::readMSB)
public fun GameFiles.loadMSB(name: String): MSB? = MapId.parse(name)?.let { loadMSB(it) }

/**
 * The map's collision meshes, as a binder of one '.hkx' per collision part
 * Current returns bytes as no HKX reader is implemented yet
 */
public fun GameFiles.openCollision(map: MapId, detail: CollisionDetail): OpenBinder? =
    openBinder(map.collisionPaths(detail))

/**
 * The map's navigation meshes, as a binder of one '.nvm' per navmesh part
 * Current returns bytes as not NVM reader is implemented yet
 */
public fun GameFiles.openNavmeshes(map: MapId): OpenBinder? = openBinder(map.navmeshBinderPath)