package com.sappyoak.dsanalyzer.overlay

public enum class FaceCull {
    /** Draw every triangle, whichever way it faces */
    Off,
    /** Drop triangles wound clockwise on screen */
    Clockwise,
    /** Drop triangles wound counter-clockwise on screen */
    CounterClockwise;

    internal fun rejects(twiceSignedArea: Float): Boolean = when (this) {
        Off -> false
        Clockwise -> twiceSignedArea >= 0f
        CounterClockwise -> twiceSignedArea <= 0f
    }
}