package com.sappyoak.dsanalyzer.formats.fmg

/**
 * One file's worth of the game's own text by the id the rest of the game refers to it by
 */
public data class Fmg(public val strings: Map<Int, String>) {
    public operator fun get(id: Int): String? = strings[id]
    public operator fun plus(overrides: Fmg): Fmg = Fmg(strings + overrides.strings)
}