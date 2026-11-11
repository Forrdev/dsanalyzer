package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.read.action.ActionReader
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterSource
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterReader
import com.sappyoak.dsanalyzer.runtime.read.place.DSRPlaceReader
import com.sappyoak.dsanalyzer.runtime.read.place.PlaceSource
import com.sappyoak.dsanalyzer.runtime.read.place.PTDEPlaceReader
import com.sappyoak.dsanalyzer.runtime.session.RuntimeFeature

internal class EditionReaders(
    val place: PlaceSource,
    val clock: ClockSource,
    val characters: CharacterSource,
) {
    val supported: Set<RuntimeFeature> = buildSet {
        add(RuntimeFeature.Clock)
        add(RuntimeFeature.Animation)
        add(RuntimeFeature.PlayerState)
        add(RuntimeFeature.Characters)
        add(RuntimeFeature.Place)
    }
}

internal fun readersFor(edition: GameEdition, layout: GameLayout): EditionReaders = when (edition) {
    GameEdition.PrepareToDie -> EditionReaders(
        place = PTDEPlaceReader(),
        clock = ClockSource.of(edition),
        characters = CharacterReader(layout, ActionReader(layout))
    )

    GameEdition.Remastered -> EditionReaders(
        place = DSRPlaceReader(),
        clock = ClockSource.of(edition),
        characters = CharacterReader(layout, ActionReader(layout))
    )
}
