package com.sappyoak.dsanalyzer.app.runtime

import com.sappyoak.dsanalyzer.overlay.MarkerKind
import com.sappyoak.dsanalyzer.overlay.OverlayScene
import com.sappyoak.dsanalyzer.overlay.WorldMarker
import com.sappyoak.dsanalyzer.runtime.session.BlockRoster
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.PlacedEnemy
import com.sappyoak.dsanalyzer.shared.math.Vec3
import kotlin.math.abs

/**
 * How close a placed position has to be to a live one to be the same enemy.
 *
 * Generous on purpose. A placed position and the live position of the character built from it were
 * measured equal **byte for byte** in the player's own block, so this could be exact — but a
 * character that has taken a step is no longer on its spawn point, and the question being asked is
 * "did this part produce that character", not "has it moved". Half a unit is far below the spacing
 * between any two placed enemies and far above the drift of one that is merely idling.
 */
private const val SAME_SPOT = 0.5f

/*
* At least this many matches before a block is believed to be the player's.
*
* One match is a coincidence waiting to happen — two blocks can easily each hold an enemy near the
* same coordinates in their own spaces. Three is enough that agreeing by chance stops being
* plausible.
*/
private const val CONFIDENT_MATCHES = 3

internal fun RuntimeState.overlayScene(): OverlayScene {
    val camera = snapshot?.camera ?: return OverlayScene.Empty

    if (snapshot.menu?.isFullScreenOpen == true) return OverlayScene(camera = camera, suppressWorldDrawing = true)

    val characters = snapshot.characters

    val live = characters.map { it.marker() }
    val block = placedEnemies?.playersBlock(characters)
    val unbuilt = block?.enemies.orEmpty()
        .filterNot { placed -> characters.any { it.position.near(placed.position) } }
        .map { it.marker() }

    return OverlayScene(camera = camera, markers = live + unbuilt)
}

private fun List<BlockRoster>.playersBlock(characters: List<CharacterSnapshot>): BlockRoster? {
    if (characters.isEmpty()) return null

    return map { roster -> roster to roster.agreementWith(characters) }
        .filter { (_, matches) -> matches >= CONFIDENT_MATCHES }
        .maxByOrNull { (_, matches) -> matches }
        ?.first
}

private fun BlockRoster.agreementWith(characters: List<CharacterSnapshot>): Int =
    enemies.count { placed -> characters.any { it.position.near(placed.position) } }

private fun Vec3.near(other: Vec3): Boolean =
    abs(x - other.x) < SAME_SPOT && abs(y - other.y) < SAME_SPOT && abs(z - other.z) < SAME_SPOT

private fun CharacterSnapshot.marker(): WorldMarker = WorldMarker(
    position = position,
    label = if (isPlayer) "you" else "$modelId  ${health}hp",
    kind = if (isPlayer) MarkerKind.Player else MarkerKind.Character
)

private fun PlacedEnemy.marker(): WorldMarker = WorldMarker(
    position = position,
    // The entity id rather than the name, because it is what an EMEVD script references and so
    // what a person reads this marker in order to go and look up
    label = if (entityId > 0) "$entityId" else "npc $npcParamId",
    kind = MarkerKind.Placed
)