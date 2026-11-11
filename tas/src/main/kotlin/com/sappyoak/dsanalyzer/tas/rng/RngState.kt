package com.sappyoak.dsanalyzer.tas.rng

/** Every gameplay stream a run pins: the AI generator and `GameMan`'s twister */
public data class RngState(public val game: GameRngState, public val twister: TwisterState)

/**
 * What a taser sees: the state now, how many draws the last finished frame made from each stream
 * (null when the log does not cover it), and the next floats the AI generator will hand out
 */
public data class RngView(
    public val game: GameRngState,
    public val twisterLeft: Int,
    public val gameDrawsLastFrame: Int?,
    public val twisterDrawsLastFrame: Int?,
    public val upcoming: List<Float>
)
