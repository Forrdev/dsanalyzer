package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

/** The flag block, which is laid out the same in both editions */
public data class EventFlagLayout(public val pointer: GamePointer)
