package com.sappyoak.dsanalyzer.runtime.session.animation

public sealed interface AnimationTarget {
    public data object Off : AnimationTarget
    public data object Player : AnimationTarget
    public data class Character(public val handle: Int) : AnimationTarget
}
