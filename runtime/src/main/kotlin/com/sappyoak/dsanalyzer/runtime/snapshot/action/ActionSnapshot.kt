package com.sappyoak.dsanalyzer.runtime.snapshot.action

public data class ActionSnapshot(
    public val esdStates: List<Int?>,
    public val playingAction: Int?,
    public val requestedAction: Int?,
    public val esdFlags: Int,
    public val ladderState: Int?,
    public val damageAnimation: Int?,
    public val damageLevel: Int,
    public val eventCommands: List<Int>,
    /** 'null' where the edition's event fields are not measured, as with [eventCommands] left empty */
    public val eventMessage: Int?,
    public val eventAction: EventActionSnapshot?
)

public data class EventActionSnapshot(
    public val type: Int,
    public val first: Int,
    public val second: Int
) {
    public val active: Boolean get() = type != 0
}
