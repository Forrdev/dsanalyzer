package com.sappyoak.dsanalyzer.runtime.layout

public data class GameLayout(
    public val camera: CameraLayout,
    public val specialEffect: SpecialEffectLayout,
    public val animation: AnimationLayout,
    public val animRequest: AnimRequestLayout,
    public val playerGameData: PlayerGameDataLayout,
    public val loadQueue: LoadQueueLayout
)
