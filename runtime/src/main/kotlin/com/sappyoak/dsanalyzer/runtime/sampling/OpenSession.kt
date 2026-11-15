package com.sappyoak.dsanalyzer.runtime.sampling

import com.sappyoak.dsanalyzer.runtime.EditionTables
import com.sappyoak.dsanalyzer.runtime.GameConnection
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.resolvePointers
import com.sappyoak.dsanalyzer.runtime.write.GameWriter

public fun GameConnection.openSession(
    edition: EditionTables,
    extensions: List<SessionExtension> = emptyList()
): GameSession {
    val resolved = memory.resolvePointers(edition.pointers, mainModule)
    val writer = GameWriter(memory, code)
    return GameSession(game, resolved, GameMemory(memory, resolved), edition.layoutFor(mainModule), mainModule, writer, extensions)
}
