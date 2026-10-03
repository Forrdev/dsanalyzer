package com.sappyoak.dsanalyzer.formats.emevd.emedf

/**
 * What an argument's value refers to elsewhere in the game
 */
public enum class ArgReference(private val suffix: String?) {
    /** An entity id, which is how a script names a part or region placed in a map */
    Entity("Entity ID"),
    EventFlag("Event Flag ID"),
    /** The first and last of a span of flags the instruction treats as one */
    EventFlagRangeStart(null),
    EventFlagRangeEnd(null),

    /**
     * How a nearby flag argument is addressed, as a flag id of its own, or as an offset from the
     * event's id or its initialization slot
     */
    EventFlagType(null),
    Character(null),
    Object(null),
    Region(null),
    Collision(null),
    Text(null),
    Item(null);

    /** Whether what this names is placed in a map */
    public val placed: Boolean
        get() = this == Entity || this == Character || this == Object || this == Region || this == Collision

    /** Whether this argument holds a flag id, whichever part of a reference it plays */
    public val namesFlag: Boolean
        get() = this == EventFlag || this == EventFlagRangeStart || this == EventFlagRangeEnd

    public companion object {
        public fun of(argName: String): ArgReference? = entries.firstOrNull { it.suffix != null && argName.endsWith(it.suffix) }
        public fun named(name: String): ArgReference? = entries.firstOrNull { it.name == name }
    }
}