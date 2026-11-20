package com.sappyoak.dsanalyzer.app.data.animations

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId
import com.sappyoak.dsanalyzer.game.world.maps.CharacterModels

public data class AnimationsState(
    public val installation: Installation? = null,
    /** Characters first, then objects, as the navigator lists them */
    public val sets: List<AnimationSetId> = emptyList(),
    public val models: CharacterModels = CharacterModels(emptyMap()),
    public val shown: AnimationSetId? = null,
    public val animations: SetAnimations? = null,
    public val loading: Boolean = false,
    public val problem: String? = null
) {
    /** A set's model id with the character's name where the model list knows it */
    public fun title(set: AnimationSetId): String = when (set) {
        is AnimationSetId.Character -> models[set.model]?.let { "${set.label} $it" } ?: set.label
        is AnimationSetId.Object -> set.label
    }
}
