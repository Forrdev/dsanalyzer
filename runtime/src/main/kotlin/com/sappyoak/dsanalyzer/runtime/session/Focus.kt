package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.runtime.session.animation.AnimationSnapshot

/**
 * Whether to follow the player's target, and how far away it may be before it is let go
 */
public data class FocusRequest(
    public val enabled: Boolean,
    public val range: Float
) {
    public companion object {
        public val Off: FocusRequest = FocusRequest(enabled = false, range = 0f)
    }
}

/**
 * The character the player is locked onto, or the one last locked onto while it lives and stays within
 * range. [character] carries its AI whatever the AI tracking scope
 */
public data class FocusSnapshot(
    public val character: CharacterSnapshot,
    /** Locked onto now, rather than kept from an earlier lock */
    public val locked: Boolean,
    public val animation: AnimationSnapshot?
)
