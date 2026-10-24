package com.sappyoak.dsanalyzer.app.runtime.timing

import com.sappyoak.dsanalyzer.overlay.HitRing
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot

/** Game frames a flashing ring stays on, then off: five flashes a second at 30 frames */
private const val FLASH_FRAMES = 3L

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

/** The ring for [frame]: filling through the wind-up, then flashing while the window is open */
public fun Countdown.ring(frame: Long): HitRing = when (this) {
    is Countdown.Coming -> HitRing(fill = windup, live = false)
    is Countdown.Live -> HitRing(fill = 1f, live = true, lit = (frame / FLASH_FRAMES) % 2 == 0L)
}

internal val WindowKind.word: String
    get() = when (this) {
        WindowKind.Hit -> "hit"
        WindowKind.Bullet -> "shot"
        WindowKind.Grab -> "grab"
    }
