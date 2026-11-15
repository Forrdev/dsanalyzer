package com.sappyoak.dsanalyzer.app.runtime

import com.sappyoak.dsanalyzer.app.runtime.timing.countdownOf
import com.sappyoak.dsanalyzer.app.runtime.timing.ring
import com.sappyoak.dsanalyzer.game.world.maps.loadCharacterModels
import com.sappyoak.dsanalyzer.overlay.OverlayScene
import com.sappyoak.dsanalyzer.overlay.PanelHealth
import com.sappyoak.dsanalyzer.overlay.TargetPanel
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.FocusSnapshot
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimationSnapshot
import com.sappyoak.dsanalyzer.shared.math.Vec3

/**
 * Where the game draws its floating HP bar, above the character's position. Measured in DSR on a
 * Hollow Warrior (2.17) and a Giant Skeleton (2.20) from the bar's own screen position, so it does not
 * follow the character's size
 */
private const val HP_BAR_HEIGHT = 2.2f

internal fun RuntimeState.overlayScene(settings: OverlaySettings): OverlayScene {
    if (!settings.enabled) return OverlayScene.Empty

    val camera = snapshot?.camera ?: return OverlayScene.Empty

    if (snapshot.menu?.isBlocking == true) return OverlayScene(camera = camera, suppressWorldDrawing = true)

    val focus = snapshot.focus?.takeIf { settings.target.enabled && it.character.alive }
    return OverlayScene(camera = camera, panel = focus?.let { panelOf(it, settings.target.hitWindows) })
}

private fun RuntimeState.panelOf(focus: FocusSnapshot, windows: Boolean): TargetPanel {
    val character = focus.character
    val countdown = if (windows) timings.countdownOf(character) else null

    return TargetPanel(
        anchor = character.position.raisedBy(HP_BAR_HEIGHT),
        title = if (focus.boss) character.modelName else character.title(),
        health = if (focus.boss) null else PanelHealth(character.health, character.healthMax),
        lines = listOfNotNull(
            character.ai?.let { "think ${it.thinkId}" },
            character.ai?.goals?.let { goals -> goals.planLine { timings[character.modelId]?.windowsOf(it).orEmpty() } },
            focus.animation?.describe()
        ),
        ring = countdown?.ring(snapshot?.frame ?: 0L)
    )
}

/** The model's name where one is known, then its id */
private fun CharacterSnapshot.title(): String =
    loadCharacterModels()[modelId]?.let { "$it  $modelName" } ?: modelName

/** The animation carrying the most weight, and how far into it the character is */
private fun AnimationSnapshot.describe(): String? {
    val playing = occupied
        .filter { it.animationId != null && it.control != null }
        .maxByOrNull { it.control?.masterWeight ?: 0f } ?: return null
    return "anim ${playing.animationId}  ${"%.2f".format(playing.control?.localTime ?: 0f)}s"
}

private fun Vec3.raisedBy(height: Float): Vec3 = Vec3(x, y + height, z)
