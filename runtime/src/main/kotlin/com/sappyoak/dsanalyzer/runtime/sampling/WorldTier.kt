package com.sappyoak.dsanalyzer.runtime.sampling

import com.sappyoak.dsanalyzer.runtime.read.ai.AISource
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterRef
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.LoadQueueSnapshot

internal data class WorldTier(
    val loadQueue: LoadQueueSnapshot? = null,
    val characters: List<CharacterSnapshot> = emptyList(),
    val others: List<CharacterRef> = emptyList(),
    val ai: List<AISource> = emptyList()
) {
    val tracked: Set<Int> = ai.mapTo(HashSet()) { it.handle }
}
