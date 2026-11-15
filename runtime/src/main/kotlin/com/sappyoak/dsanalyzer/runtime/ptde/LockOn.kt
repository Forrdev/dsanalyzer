package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/** Through [Locked], with a word to spare */
private const val LOCK_ON_MAN_SIZE: Int = 0x1030

public object LockOnMan {
    public val Pointer: GamePointer = GamePointer(
        name = "LockOnMan",
        base = PTDESignatures.LockOnMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = LOCK_ON_MAN_SIZE
    )

    /** First node of the candidate list */
    public const val Candidates: Int = 0x10

    /** A byte, set while the player is locked on */
    public const val Locked: Int = 0x1020
}

public object LockOnNode {
    public const val Next: Int = 0x2C
    public const val Lockable: Int = 0x30
    /** A byte; [Chosen] marks the target */
    public const val Flags: Int = 0x34
    public const val Chosen: Int = 0x20
    public const val SIZE: Int = 0x38

    /** In the lockable object */
    public const val Handle: Int = 0x14
}
