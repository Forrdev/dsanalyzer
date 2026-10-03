package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.game.world.maps.MapId

public sealed interface WorldPlace {
    /** The map the area bytes name when it is a shippable map */
    public data class InWorld(public val map: MapId) : WorldPlace

    /**
     * No world, the title screen, or a load across areas
     *
     * The id is kept rather than thrown away, because flags do change here. A quitout writes
     * several and a change is worth recording against where it happened
     */
    public data class Loading(public val map: MapId) : WorldPlace

    /** The area structure could not be read */
    public data object Unreadable : WorldPlace
}

/* THe map a world is loaded in, or null on a load screen */
public val WorldPlace.loadedMap: MapId? get() = (this as? WorldPlace.InWorld)?.map