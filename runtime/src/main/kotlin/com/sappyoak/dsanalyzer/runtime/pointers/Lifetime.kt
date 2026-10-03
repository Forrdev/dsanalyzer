package com.sappyoak.dsanalyzer.runtime.pointers

/** How long an address stays valid once it has been resolved */
public enum class Lifetime {
    /** Fixed until the process exits */
    Process,
    /** Allocated once at startup and reachable until the game returns to its menu */
    Session,
    /** Reallocated by anything that reloads the world */
    World,
    /** Never cached */
    Volatile;
}