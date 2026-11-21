package com.sappyoak.dsanalyzer.runtime.sampling

import com.sappyoak.dsanalyzer.runtime.edition.Edition
import com.sappyoak.dsanalyzer.runtime.GameConnection
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.resolvePointers
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.runtime.write.SessionExtension

public fun GameConnection.openSession(
    edition: Edition,
    extensions: List<SessionExtension> = emptyList()
): GameSession {
    val resolved = memory.resolvePointers(edition.pointers, mainModule)
    val writer = GameWriter(memory, code)
    val layout = edition.layoutFor(mainModule)
    return GameSession(game, resolved, GameMemory(memory, resolved), layout, mainModule, writer, extensions, edition)
}
