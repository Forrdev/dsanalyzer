package com.sappyoak.dsanalyzer.formats.emevd.emedf

/**
 * What an argument's value refers to elsewhere in the game
 */
public enum class ArgReference(private val suffix: String) {
    /** An entity id, which is how a script names a part or region placed in a map */
    Entity("Entity ID"),
    EventFlag("Event Flag ID");

    public companion object {
        public fun of(argName: String): ArgReference? = entries.firstOrNull { argName.endsWith(it.suffix) }
    }
}