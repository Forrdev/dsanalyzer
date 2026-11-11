package com.sappyoak.dsanalyzer.runtime

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.runtime.dsr.DSREdition
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.ptde.PTDEEdition

public class EditionTables(
    public val pointers: List<GamePointer>,
    public val layoutFor: (AddressRange) -> GameLayout
)

public fun tablesFor(edition: GameEdition): EditionTables? = when (edition) {
    GameEdition.PrepareToDie -> PTDEEdition
    GameEdition.Remastered -> DSREdition
}
