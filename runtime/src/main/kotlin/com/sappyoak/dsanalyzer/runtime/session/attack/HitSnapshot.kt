package com.sappyoak.dsanalyzer.runtime.session.attack

public data class HitSnapshot(
    public val id: Int,
    public val owner: Int,
    public val atkParamId: Int,
    public val sweep: List<HitShape>,
    public val live: Boolean
)
