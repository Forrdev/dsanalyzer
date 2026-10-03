package com.sappyoak.dsanalyzer.formats.emevd.emedf

/**
 * What an argument's value refers to elsewhere in the game
 */
public enum class ArgReference(private val suffix: String?) {
    /** An entity id, which is how a script names a part or region placed in a map */
    Entity("Entity ID"),
    EventFlag("Event Flag ID"),
    Character(null),
    Object(null),
    Region(null),
    Collision(null),
    Text(null),
    Item(null);

    /** Whether what this names is placed in a map */
    public val placed: Boolean
        get() = this == Entity || this == Character || this == Object || this == Region || this == Collision

    public companion object {
        public fun of(argName: String): ArgReference? = entries.firstOrNull { it.suffix != null && argName.endsWith(it.suffix) }
        public fun named(name: String): ArgReference? = entries.firstOrNull { it.name == name }
    }
}