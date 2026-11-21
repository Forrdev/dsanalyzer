package com.sappyoak.dsanalyzer.runtime.edition

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.snapshot.RuntimeFeature
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.DrawBackend

public class Edition internal constructor(
    public val game: GameEdition,
    internal val pointers: List<GamePointer>,
    internal val layoutFor: (AddressRange) -> GameLayout,
    internal val build: (ProcessMemory, AddressRange) -> String?,
    internal val draw: DrawBackend,
    public val supports: Set<RuntimeFeature>
)

public fun editionFor(game: GameEdition): Edition = when (game) {
    GameEdition.PrepareToDie -> PTDEEdition
    GameEdition.Remastered -> DSREdition
}
