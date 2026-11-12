package com.sappyoak.dsanalyzer.runtime.inject

import com.sappyoak.dsanalyzer.native.memory.Signature

/**
 * A heap step object the game advances every frame, reading the
 * table pointer from the object each time, so pointing the object at another table
 * changes no code. [path] walks from the static [root] names to the object. [table] is
 * the field holding the table pointer, and [slot] the step than runs while the game is up
 */
public class StepTableSite(
    public val root: Signature,
    public val path: List<Long>,
    public val table: Int,
    public val slot: Int
)

/**
 * What the cave tests before it makes a call. [owner] is the character manager's static global and
 * [player] the field in it holding the player. A call waits for a later frame while either is nulll
 */
public class PlayerGate(
    public val owner: Signature,
    public val player: Int
)
