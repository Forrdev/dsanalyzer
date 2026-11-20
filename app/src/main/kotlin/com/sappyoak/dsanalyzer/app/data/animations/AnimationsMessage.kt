package com.sappyoak.dsanalyzer.app.data.animations

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId
import com.sappyoak.dsanalyzer.game.world.maps.CharacterModels

public sealed interface AnimationsMessage {
    public data class Opened(public val installation: Installation) : AnimationsMessage
    public data class CatalogLoaded(
        public val sets: List<AnimationSetId>,
        public val models: CharacterModels
    ) : AnimationsMessage
    public data class SetSelected(public val set: AnimationSetId) : AnimationsMessage
    public data class Loaded(public val animations: SetAnimations) : AnimationsMessage
    public data class Failed(public val reason: String) : AnimationsMessage
}
