package com.sappyoak.dsanalyzer.game.world.maps

import com.sappyoak.dsanalyzer.game.files.GamePath
import com.sappyoak.dsanalyzer.game.files.SplitBinderPaths

internal const val MAP_STUDIO_DIRECTORY = "/map/mapstudio"
internal const val MSB_EXTENSION = ".msb"

public enum class CollisionDetail(internal val prefix: Char) {
    High('h'),
    Low('l');
}

public val MapId.msbPath: GamePath get() = GamePath.of("$MAP_STUDIO_DIRECTORY$name$MSB_EXTENSION")
public val MapId.navmeshBinderPath: GamePath get() = GamePath.of("/map/$name/$name.nvmbnd.dcx")

public fun MapId.collisionPaths(detail: CollisionDetail): SplitBinderPaths {
    val name = this.name
    val stem = "/map/$name/{detail.prefix}${name.drop(1)}"
    return SplitBinderPaths(GamePath.of("$stem.hkxbhd"), GamePath.of("$stem.hkxbdt"))
}

