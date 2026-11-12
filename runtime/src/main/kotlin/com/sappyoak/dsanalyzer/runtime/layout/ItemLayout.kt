package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.write.mailbox.CallRegister

public data class ItemLayout(
    /** Takes the inventory, the category, the id and the count, then [addFlags] */
    public val add: GamePointer,
    public val inventory: FieldLayout,
    /** What the game's own callers pass after the count */
    public val addFlags: List<Long>,
    /** Takes the category, the id and the count, with [popupOwner] in [popupOwnerRegister] or, where that is null, first */
    public val popup: GamePointer,
    public val popupOwner: GamePointer,
    public val popupOwnerRegister: CallRegister?
)
