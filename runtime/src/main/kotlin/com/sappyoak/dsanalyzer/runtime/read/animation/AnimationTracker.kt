package com.sappyoak.dsanalyzer.runtime.read.animation

import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterRef
import com.sappyoak.dsanalyzer.runtime.snapshot.animation.AnimationSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.animation.AnimationTarget

internal class AnimationTracker(private val reader: AnimationReader) {

    fun read(
        game: GameMemory,
        target: AnimationTarget,
        others: List<CharacterRef>
    ): AnimationSnapshot? =
        when (target) {
            AnimationTarget.Off -> null
            AnimationTarget.Player -> reader.player(game)?.let { source ->
                reader.read(game.memory, source, target) ?: null.also { reader.forgetPlayer(game) }
            }
            is AnimationTarget.Character -> others.firstOrNull { it.handle == target.handle }
                ?.let { reader.locate(game.memory, it.controller) }
                ?.let { reader.read(game.memory, it, target) }
        }
}
