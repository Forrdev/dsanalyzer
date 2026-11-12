package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.session.CheatBit
import com.sappyoak.dsanalyzer.runtime.session.CheatFlag

/** One field of a structure the tool reaches by pointer */
public data class FieldLayout(
    public val pointer: GamePointer,
    public val offset: Int
)

public data class WriteLayout(
    public val chrFlags1: FieldLayout,
    public val chrFlags2: FieldLayout,
    public val mapFlags: FieldLayout,
    public val hitboxDraw: FieldLayout?,
    public val cheatBits: Map<CheatFlag, CheatBit>,
    public val mailbox: MailboxLayout,
    public val position: LivePositionLayout,
    public val stats: StatWriteLayout,
    public val warp: WarpLayout,
    public val talk: TalkLayout
)
