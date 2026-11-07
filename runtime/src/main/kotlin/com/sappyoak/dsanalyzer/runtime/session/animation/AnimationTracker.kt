package com.sappyoak.dsanalyzer.runtime.session.animation

import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.read.animation.AnimationReader
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterRef

internal class AnimationTracker(layout: GameLayout) {
    private val reader = AnimationReader(layout.animation, layout.animRequest)

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
