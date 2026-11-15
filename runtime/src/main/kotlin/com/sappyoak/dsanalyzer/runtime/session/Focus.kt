package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.runtime.session.animation.AnimationSnapshot

public data class FocusRequest(
    public val enabled: Boolean,
    public val range: Float
) {
    public companion object {
        public val Off: FocusRequest = FocusRequest(enabled = false, range = 0f)
    }
}

public data class FocusSnapshot(
    public val character: CharacterSnapshot,
    public val locked: Boolean,
    public val boss: Boolean,
    public val animation: AnimationSnapshot?
)
