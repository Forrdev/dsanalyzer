package com.sappyoak.dsanalyzer.runtime.session.ai

import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.read.CharacterReading
import com.sappyoak.dsanalyzer.runtime.read.ai.AIReader
import com.sappyoak.dsanalyzer.runtime.read.ai.AISource
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot


internal class AITracker(gracePeriod: Int) {
    private val reader = AIReader()
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

    fun select(memory: ProcessMemory, scope: AIScope, reading: CharacterReading): List<AISource> {
        if (scope == AIScope.Off) {
            selection.select(scope, emptyMap(), null)
            return emptyList()
        }

        val found = reading.others.mapNotNull { reader.locate(memory, it) }
        val targets = found.associate { source ->
            source.handle to if (scope == AIScope.TargetingPlayer) reader.targetOf(memory, source).handle else null
        }
        val selected = selection.select(scope, targets, reading.playerHandle)
        return found.filter { it.handle in selected }
    }
}

private val AITarget.handle: Int? get() = (this as? AITarget.Character)?.handle
