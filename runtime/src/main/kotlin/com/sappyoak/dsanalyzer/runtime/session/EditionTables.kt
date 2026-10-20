package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

/**
 * What one edition of the game needs before it can be read
 */
public class EditionTables(
    public val pointers: List<GamePointer>,
    public val layout: GameLayout,
    public val buildOf: (ProcessMemory, AddressRange) -> String?
)