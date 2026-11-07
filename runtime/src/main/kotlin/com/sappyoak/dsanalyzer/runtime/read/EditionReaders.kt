package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterSource
import com.sappyoak.dsanalyzer.runtime.read.character.PTDECharacterReader
import com.sappyoak.dsanalyzer.runtime.read.collision.CollisionReader
import com.sappyoak.dsanalyzer.runtime.read.place.DSRPlaceReader
import com.sappyoak.dsanalyzer.runtime.read.place.PlaceSource
import com.sappyoak.dsanalyzer.runtime.read.place.PTDEPlaceReader
import com.sappyoak.dsanalyzer.runtime.read.player.DSRPlayerReader
import com.sappyoak.dsanalyzer.runtime.read.player.PTDEPlayerReader
import com.sappyoak.dsanalyzer.runtime.read.player.PlayerSource
import com.sappyoak.dsanalyzer.runtime.session.RuntimeFeature

internal class EditionReaders(
    val place: PlaceSource?,
    val player: PlayerSource?,
    val characters: CharacterSource?,
    val placedParts: PlacedPartsReader?,
    val collision: CollisionReader?
) {
    val supported: Set<RuntimeFeature> = buildSet {
        if (place != null) add(RuntimeFeature.Place)
        if (player != null) add(RuntimeFeature.Clock)
        if (player?.providesSnapshot == true) add(RuntimeFeature.PlayerState)
        if (characters != null) add(RuntimeFeature.Characters)
        if (placedParts != null) add(RuntimeFeature.PlacedParts)
        if (collision != null) add(RuntimeFeature.Collision)
    }
}

internal fun readersFor(edition: GameEdition, layout: GameLayout): EditionReaders = when (edition) {
    GameEdition.PrepareToDie -> EditionReaders(
        place = PTDEPlaceReader(),
        player = PTDEPlayerReader(layout.specialEffect),
        characters = PTDECharacterReader(layout.specialEffect),
        placedParts = PlacedPartsReader(),
        collision = CollisionReader()
    )

    GameEdition.Remastered -> EditionReaders(
        place = DSRPlaceReader(),
        player = DSRPlayerReader(),
        characters = null,
        placedParts = null,
        collision = null
    )
}
