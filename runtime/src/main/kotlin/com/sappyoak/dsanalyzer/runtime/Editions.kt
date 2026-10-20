package com.sappyoak.dsanalyzer.runtime

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.runtime.dsr.DSREdition
import com.sappyoak.dsanalyzer.runtime.ptde.PTDEEdition
import com.sappyoak.dsanalyzer.runtime.session.EditionTables

public fun tablesFor(edition: GameEdition): EditionTables? = when (edition) {
    GameEdition.PrepareToDie -> PTDEEdition
    GameEdition.Remastered -> DSREdition
}