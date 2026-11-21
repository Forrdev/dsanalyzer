package com.sappyoak.dsanalyzer.runtime.read.target

import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.read.animation.AnimationTracker
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterRef
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.FocusRequest
import com.sappyoak.dsanalyzer.runtime.session.FocusSnapshot
import com.sappyoak.dsanalyzer.runtime.session.PlayerSnapshot
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimationSnapshot
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimationTarget

/**
 * Picks the character to follow from the lock-on state, then reads it and its animation.
 * [select] runs before the characters are read, so their AI can include the focus
 */
internal class FocusReader(layout: GameLayout) {
    private val lockOn = LockOnReader(layout.lockOn)
    private val tracker = FocusTracker()
    private val animations = AnimationTracker(layout)

    fun select(
        game: GameMemory,
        request: FocusRequest,
        player: PlayerSnapshot?,
        characters: List<CharacterSnapshot>
    ): Focus? {
        val locked = if (request.enabled) lockOn.read(game, player) else null
        return tracker.resolve(request, locked, characters, player?.position)
    }

    /** [shared] is reused when the animation view already follows the same character */
    fun snapshot(
        game: GameMemory,
        focus: Focus?,
        characters: List<CharacterSnapshot>,
        others: List<CharacterRef>,
        shared: AnimationSnapshot?
    ): FocusSnapshot? {
        val focus = focus ?: return null
        val character = characters.firstOrNull { it.handle == focus.handle } ?: return null
        val target = AnimationTarget.Character(focus.handle)

        return FocusSnapshot(
            character = character,
            locked = focus.locked,
            animation = shared?.takeIf { it.target == target } ?: animations.read(game, target, others)
        )
    }

    fun forget() {
        tracker.forget()
    }
}
