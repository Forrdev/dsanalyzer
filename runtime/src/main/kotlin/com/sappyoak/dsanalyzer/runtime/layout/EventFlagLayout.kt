package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

/** The flag block, which is laid out the same in both editions */
public data class EventFlagLayout(public val pointer: GamePointer)

/**
 * The game's own calls that set and read one event flag. [set] takes the flag and the value, [get] the
 * flag. [owner] is the flag manager, in [ownerRegister] or, where that is null, first; [kindRegister] holds 0
 */
public data class FlagCallLayout(
    public val set: GamePointer,
    public val get: GamePointer,
    public val owner: GamePointer,
    public val ownerRegister: CallRegister?,
    public val kindRegister: CallRegister
)
