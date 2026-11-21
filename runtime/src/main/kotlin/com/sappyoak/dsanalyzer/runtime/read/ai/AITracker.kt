package com.sappyoak.dsanalyzer.runtime.read.ai

import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterReading
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.ai.AIScope
import com.sappyoak.dsanalyzer.runtime.session.ai.AITarget


internal class AITracker(gracePeriod: Int, layout: GameLayout) {
    private val reader = AIReader(layout)
    private val selection = AISelection(gracePeriod)

    fun read(
        memory: ProcessMemory,
        sources: List<AISource>,
        characters: List<CharacterSnapshot>
    ): List<CharacterSnapshot> {
        if (sources.isEmpty()) return characters

        val read = sources.mapNotNull { source ->
            reader.read(memory, source)?.let { source.handle to it }
        }.toMap()
        return characters.map { character ->
            read[character.handle]?.let { character.copy(ai = it) } ?: character
        }
    }

    /** [focus] is read whatever the [scope], so whoever follows it always has its AI */
    fun select(memory: ProcessMemory, scope: AIScope, reading: CharacterReading, focus: Int?): List<AISource> {
        val wanted = if (scope == AIScope.Off) reading.others.filter { it.handle == focus } else reading.others
        val found = wanted.mapNotNull { reader.locate(memory, it) }
        val targets = found.associate { source ->
            source.handle to if (scope == AIScope.TargetingPlayer) reader.targetOf(memory, source).handle else null
        }
        val selected = selection.select(scope, targets, reading.playerHandle) + listOfNotNull(focus)
        return found.filter { it.handle in selected }
    }

    /* Drops what is kept between reads, for a load that rebuilds every AI */
    fun forget() {
        reader.forget()
    }
}

private val AITarget.handle: Int? get() = (this as? AITarget.Character)?.handle
