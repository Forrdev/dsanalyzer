package com.sappyoak.dsanalyzer.game.world.flags

import com.sappyoak.dsanalyzer.game.world.maps.CharacterModels
import com.sappyoak.dsanalyzer.game.world.params.GameParams
import com.sappyoak.dsanalyzer.game.world.params.ParamNames
import com.sappyoak.dsanalyzer.game.world.params.RowNameTranslations

/** A naming context with no game data: every name falls back to its id */
internal val EMPTY_CONTEXT = NamingContext(
    GameParams(emptyMap()), ParamNames.None, emptyMap(), emptyMap(), CharacterModels(emptyMap()),
    RowNameTranslations.None
)
