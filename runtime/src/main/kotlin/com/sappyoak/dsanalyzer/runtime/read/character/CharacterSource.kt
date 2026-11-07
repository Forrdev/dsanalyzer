package com.sappyoak.dsanalyzer.runtime.read.character

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.PlayerSnapshot
import com.sappyoak.dsanalyzer.runtime.session.WorldSnapshot

internal data class CharacterRef(
    val handle: Int,
    val comManipulator: Address,
    val controller: Address
)

internal class CharacterReading(
    val characters: List<CharacterSnapshot>,
    val others: List<CharacterRef>,
    val playerHandle: Int?
) {
    companion object {
        val Empty = CharacterReading(emptyList(), emptyList(), null)
    }
}

internal class PlayerReading(
    val player: PlayerSnapshot?,
    val world: WorldSnapshot?,
    val present: Int
) {
    companion object {
        val Empty = PlayerReading(null, null, 0)
    }
}

internal interface CharacterSource {
    val playerStructures: Int

    fun player(game: GameMemory): PlayerReading
    fun read(game: GameMemory, allEffects: Boolean = false): CharacterReading
}
