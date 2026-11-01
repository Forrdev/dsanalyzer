package com.sappyoak.dsanalyzer.runtime.read.character

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot

internal data class CharacterRef(
    val handle: Int,
    val comManipulator: Address,
    val controller: Address
)

internal class CharacterReading(
    val characters: List<CharacterSnapshot>,
    val others: List<CharacterRef>
) {
    val playerHandle: Int? get() = characters.firstOrNull { it.isPlayer }?.handle

    companion object {
        val Empty = CharacterReading(emptyList(), emptyList())
    }
}

internal interface CharacterSource {
    fun read(game: GameMemory, allEffects: Boolean = false): CharacterReading
}
