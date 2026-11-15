package com.sappyoak.dsanalyzer.runtime.read.target

import com.sappyoak.dsanalyzer.runtime.layout.BossBarLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView

private const val FREE = -1

/** The handles the boss bars are showing, empty where the boss slots are not measured */
internal class BossBarReader(private val layout: BossBarLayout?) {
    private val view = layout?.let { StructView(it.pointer) }

    fun handles(game: GameMemory): Set<Int> {
        val layout = layout ?: return emptySet()
        val view = checkNotNull(view)
        if (!view.refresh(game)) return emptySet()

        return (0 until layout.count)
            .map { view.int(it * layout.size + layout.handle) }
            .filterTo(HashSet()) { it != FREE }
    }
}
