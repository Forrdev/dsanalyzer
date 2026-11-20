package com.sappyoak.dsanalyzer.app.data.animations

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId

public fun reduceAnimations(
    state: AnimationsState,
    message: AnimationsMessage
): Transition<AnimationsState, AnimationsEffect> = when (message) {
    is AnimationsMessage.Opened -> AnimationsState(installation = message.installation)
        .with(AnimationsEffect.LoadCatalog(message.installation))

    is AnimationsMessage.CatalogLoaded -> state.copy(
        sets = message.sets.sortedBy { it is AnimationSetId.Object },
        models = message.models
    ).with()

    is AnimationsMessage.SetSelected -> {
        val installation = state.installation
        when {
            message.set == state.shown -> state.with()
            installation == null -> state.copy(problem = "No installation is open").with()
            else -> state.copy(shown = message.set, animations = null, loading = true, problem = null)
                .with(AnimationsEffect.Load(installation, message.set))
        }
    }

    // A load that finishes after another set was chosen is dropped rather than shown under the wrong name
    is AnimationsMessage.Loaded ->
        if (message.animations.set == state.shown) state.copy(animations = message.animations, loading = false).with()
        else state.with()

    is AnimationsMessage.Failed -> state.copy(loading = false, problem = message.reason).with()
}
