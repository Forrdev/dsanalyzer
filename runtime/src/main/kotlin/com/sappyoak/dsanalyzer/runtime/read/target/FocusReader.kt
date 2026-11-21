package com.sappyoak.dsanalyzer.runtime.read.target

import com.sappyoak.dsanalyzer.runtime.layout.LockOnLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.read.animation.AnimationTracker
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterRef
import com.sappyoak.dsanalyzer.runtime.snapshot.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.FocusRequest
import com.sappyoak.dsanalyzer.runtime.snapshot.FocusSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.PlayerSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.animation.AnimationSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.animation.AnimationTarget

/**
 * Picks the character to follow from the lock-on state, then reads it and its animation.
 * [select] runs before the characters are read, so their AI can include the focus
 */
internal class FocusReader(lockOn: LockOnLayout, private val animations: AnimationTracker) {
    private val lockOn = LockOnReader(lockOn)
    private val tracker = FocusTracker()

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
}
