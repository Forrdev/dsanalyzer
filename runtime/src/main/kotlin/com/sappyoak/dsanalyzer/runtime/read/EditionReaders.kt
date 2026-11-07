package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterSource
import com.sappyoak.dsanalyzer.runtime.read.character.PTDECharacterReader
import com.sappyoak.dsanalyzer.runtime.read.collision.CollisionReader
import com.sappyoak.dsanalyzer.runtime.read.place.DSRPlaceReader
import com.sappyoak.dsanalyzer.runtime.read.place.PlaceSource
import com.sappyoak.dsanalyzer.runtime.read.place.PTDEPlaceReader
import com.sappyoak.dsanalyzer.runtime.session.RuntimeFeature

internal class EditionReaders(
    val place: PlaceSource?,
    val clock: ClockSource,
    val characters: CharacterSource?,
    val placedParts: PlacedPartsReader?,
    val collision: CollisionReader?
) {
    val supported: Set<RuntimeFeature> = buildSet {
        add(RuntimeFeature.Clock)
        add(RuntimeFeature.Animation)

        if (place != null) add(RuntimeFeature.Place)
        if (characters != null) {
            add(RuntimeFeature.PlayerState)
            add(RuntimeFeature.Characters)
        }
        if (characters != null) add(RuntimeFeature.Characters)
        if (placedParts != null) add(RuntimeFeature.PlacedParts)
        if (collision != null) add(RuntimeFeature.Collision)
    }
}

internal fun readersFor(edition: GameEdition, layout: GameLayout): EditionReaders = when (edition) {
    GameEdition.PrepareToDie -> EditionReaders(
        place = PTDEPlaceReader(),
        clock = ClockSource.of(edition),
        characters = PTDECharacterReader(layout),
        placedParts = PlacedPartsReader(),
        collision = CollisionReader()
    )

    GameEdition.Remastered -> EditionReaders(
        place = DSRPlaceReader(),
        clock = ClockSource.of(edition),
        characters = null,
        placedParts = null,
        collision = null
    )
}
