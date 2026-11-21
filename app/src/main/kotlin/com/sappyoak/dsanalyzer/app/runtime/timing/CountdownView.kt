package com.sappyoak.dsanalyzer.app.runtime.timing

import com.sappyoak.dsanalyzer.runtime.snapshot.CharacterSnapshot

/** [character]'s next window in its current action, when its AI is tracked and its model's are read */
public fun TimingLibrary.countdownOf(character: CharacterSnapshot): Countdown? {
    val ai = character.ai ?: return null
    val action = ai.playingAction ?: return null
    val windows = this[character.modelId]?.windowsOf(action) ?: return null
    return countdownAt(windows, ai.playingTime)
}

/** 'hit in 0.42s', or 'HIT 0.08s' while it is open */
public fun Countdown.describe(): String = when (this) {
    is Countdown.Coming -> "${window.kind.word} in ${"%.2f".format(seconds)}s"
    is Countdown.Live -> "${window.kind.word.uppercase()} ${"%.2f".format(seconds)}s"
}

internal val WindowKind.word: String
    get() = when (this) {
        WindowKind.Hit -> "hit"
        WindowKind.Bullet -> "shot"
        WindowKind.Grab -> "grab"
    }
