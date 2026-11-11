package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.session.RuntimeFeature

public data class GameLayout(
    public val camera: CameraLayout,
    public val specialEffect: SpecialEffectLayout,
    public val characters: CharacterLayout,
    public val ai: AILayout,
    public val action: ActionLayout,
    public val animation: AnimationLayout,
    public val animRequest: AnimRequestLayout,
    public val menu: MenuLayout,
    public val playerGameData: PlayerGameDataLayout,
    public val loadQueue: LoadQueueLayout,
    public val debugDraw: DebugDrawLayout?,
    public val writes: WriteLayout
) {
    val supported: Set<RuntimeFeature> = buildSet {
        add(RuntimeFeature.Animation)
        add(RuntimeFeature.LoadQueue)
        if (debugDraw != null) {
            add(RuntimeFeature.Collision)
        }
    }
}
