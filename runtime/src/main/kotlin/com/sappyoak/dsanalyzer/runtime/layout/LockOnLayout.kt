package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

/** Where the game keeps the character the player is locked onto */
public sealed interface LockOnLayout {
    /** The player's own target field is the lock target, and reads -1 while nothing is locked */
    public data object PlayerTarget : LockOnLayout

    /** The lock-on manager's candidate list, whose chosen node is the target while the locked byte is set */
    public data class Manager(
        public val pointer: GamePointer,
        public val locked: Int,
        public val candidates: Int,
        public val node: LockOnNodeLayout
    ) : LockOnLayout
}

public data class LockOnNodeLayout(
    public val next: Int,
    public val lockable: Int,
    public val flags: Int,
    public val chosen: Int,
    /** In the lockable object [lockable] points at */
    public val handle: Int,
    public val size: Int
)
