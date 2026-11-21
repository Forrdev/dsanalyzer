package com.sappyoak.dsanalyzer.runtime.read.target

import com.sappyoak.dsanalyzer.runtime.snapshot.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.FocusRequest
import com.sappyoak.dsanalyzer.shared.math.Vec3

/** The character being followed, and whether the player is locked onto it right now */
internal data class Focus(val handle: Int, val locked: Boolean)

/**
 * Follows the lock target, and keeps the last one after the lock drops for as long as it lives, stays
 * loaded and stays within the requested range of the player
 */
internal class FocusTracker {
    private var kept: Int? = null

    fun resolve(
        request: FocusRequest,
        locked: Int?,
        characters: List<CharacterSnapshot>,
        player: Vec3?
    ): Focus? {
        if (!request.enabled) {
            kept = null
            return null
        }
        if (locked != null) {
            kept = locked
            return Focus(locked, locked = true)
        }

        val handle = kept ?: return null
        val character = characters.firstOrNull { it.handle == handle }
        val stays = character != null && character.alive &&
                player != null && player.distanceTo(character.position) <= request.range
        if (!stays) {
            kept = null
            return null
        }
        return Focus(handle, locked = false)
    }
}
