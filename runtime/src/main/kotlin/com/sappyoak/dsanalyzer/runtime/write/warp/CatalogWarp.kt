package com.sappyoak.dsanalyzer.runtime.write.warp

import com.sappyoak.dsanalyzer.game.world.warps.CatalogWarp
import com.sappyoak.dsanalyzer.runtime.session.SavedPosition

/** A listed warp's point where it has one, else its bonfire */
public fun CatalogWarp.destination(): WarpDestination =
    point?.let { WarpDestination.Point(SavedPosition(map, it.position, it.angle)) }
        ?: WarpDestination.Bonfire(bonfire)
