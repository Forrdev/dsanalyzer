package com.sappyoak.dsanalyzer.app.runtime.ui

import com.sappyoak.dsanalyzer.game.world.maps.label
import com.sappyoak.dsanalyzer.runtime.session.WorldPlace

internal val WorldPlace.label: String get() = when (this) {
    is WorldPlace.InWorld -> map.label
    is WorldPlace.Loading -> "load screen"
    WorldPlace.Unreadable -> "unknown"
}