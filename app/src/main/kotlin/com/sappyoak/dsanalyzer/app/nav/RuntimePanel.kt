package com.sappyoak.dsanalyzer.app.nav

import com.sappyoak.dsanalyzer.runtime.session.RuntimeFeature

public enum class RuntimePanel(
    public val label: String,
    public val feature: RuntimeFeature? = null
) {
    Attachment("Attachment"),
    Player("Player", RuntimeFeature.PlayerState),
    Characters("Characters", RuntimeFeature.Characters),
    Animation("Animation", RuntimeFeature.Animation),
    LoadQueue("Load queue", RuntimeFeature.LoadQueue),
    Collision("Collision", RuntimeFeature.Collision),
    Camera("Camera"),
    Flags("Flags");
}
