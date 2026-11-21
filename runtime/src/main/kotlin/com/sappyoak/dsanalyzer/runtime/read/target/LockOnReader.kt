package com.sappyoak.dsanalyzer.runtime.read.target

import com.sappyoak.dsanalyzer.runtime.layout.LockOnLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.snapshot.PlayerSnapshot

/** A list longer than this is taken for a loop read mid-update */
private const val MAX_CANDIDATES = 64

/** Reads the handle of the character the player is locked onto, or null while nothing is locked */
internal class LockOnReader(private val layout: LockOnLayout) {
    private val manager = (layout as? LockOnLayout.Manager)?.let { StructView(it.pointer) }
    private val node = (layout as? LockOnLayout.Manager)?.let { StructView(it.node.size) }
    private val lockable = (layout as? LockOnLayout.Manager)?.let { StructView(it.node.handle + Int.SIZE_BYTES) }

    fun read(game: GameMemory, player: PlayerSnapshot?): Int? = when (layout) {
        LockOnLayout.PlayerTarget -> player?.character?.targetHandle
        is LockOnLayout.Manager -> chosen(game, layout)
    }

    private fun chosen(game: GameMemory, layout: LockOnLayout.Manager): Int? {
        val manager = checkNotNull(manager)
        val node = checkNotNull(node)
        val lockable = checkNotNull(lockable)
        if (!manager.refresh(game) || !manager.boolean(layout.locked)) return null

        var at = manager.pointer(layout.candidates)
        repeat(MAX_CANDIDATES) {
            if (!node.refresh(game.memory, at)) return null
            if (node.unsigned(layout.node.flags) and layout.node.chosen != 0) {
                return if (lockable.refresh(game.memory, node.pointer(layout.node.lockable))) {
                    lockable.int(layout.node.handle)
                } else null
            }
            at = node.pointer(layout.node.next)
        }
        return null
    }
}
