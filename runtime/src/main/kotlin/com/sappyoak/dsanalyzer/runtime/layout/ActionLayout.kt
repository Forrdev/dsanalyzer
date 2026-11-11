package com.sappyoak.dsanalyzer.runtime.layout

/** A character's 'ActionCtrl.' Its ESD machines, the action it plays, the hit it last took */
public data class ActionLayout(
    /** 'ChrCtrl' field that points at the 'ActionCtrl' */
    public val fromControl: Int,
    public val slots: Int,
    public val slotStride: Int,
    public val slotCount: Int,
    public val slotEnabled: Int,
    public val state: EsdStateLayout,
    public val playingAction: Int,
    public val playingTime: Int,
    public val requestedAction: Int,
    public val esdFlags: Int,
    public val ladderState: Int,
    public val damageAnimation: Int,
    public val damageLevel: Int,
    public val size: Int,
    /** 'null' where the event fields of the 'ChrIns' are not measured */
    public val events: EventFieldsLayout?
)

/** Where a machine slot keeps its current state id */
public sealed interface EsdStateLayout {
    /** In the slot itself */
    public data class Inline(public val offset: Int) : EsdStateLayout
    /** Machine field pointing at the current state, whose first int is the id */
    public data class ThroughMachine(public val offset: Int) : EsdStateLayout
}

/** 'ChrIns' fields the event system sets */
public data class EventFieldsLayout(
    public val commands: Int,
    public val commandCount: Int,
    public val message: Int,
    public val actionPointer: Int,
    public val actionType: Int,
    public val actionFirst: Int,
    public val actionSecond: Int,
    public val actionSize: Int
)
