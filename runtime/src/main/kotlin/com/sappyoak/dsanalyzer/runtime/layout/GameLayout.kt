package com.sappyoak.dsanalyzer.runtime.layout

public data class GameLayout(
    public val camera: CameraLayout,
    public val clock: ClockLayout,
    public val place: PlaceLayout,
    public val specialEffect: SpecialEffectLayout,
    public val characters: CharacterLayout,
    public val ai: AILayout,
    public val action: ActionLayout,
    public val animation: AnimationLayout,
    public val animRequest: AnimRequestLayout,
    public val eventFlags: EventFlagLayout,
    public val menu: MenuLayout,
    public val placedParts: PlacedPartsLayout,
    public val playerGameData: PlayerGameDataLayout,
    public val loadQueue: LoadQueueLayout,
    public val debugDraw: DebugDrawLayout,
    public val lockOn: LockOnLayout,
    public val writes: WriteLayout
)
