package com.sappyoak.dsanalyzer.runtime.sampling

import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.read.LoadQueueReader
import com.sappyoak.dsanalyzer.runtime.read.ai.AIReader
import com.sappyoak.dsanalyzer.runtime.read.ai.AISource
import com.sappyoak.dsanalyzer.runtime.read.ai.AITracker
import com.sappyoak.dsanalyzer.runtime.read.animation.AnimationReader
import com.sappyoak.dsanalyzer.runtime.read.animation.AnimationTracker
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterRef
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterSource
import com.sappyoak.dsanalyzer.runtime.read.target.Focus
import com.sappyoak.dsanalyzer.runtime.read.target.FocusReader
import com.sappyoak.dsanalyzer.runtime.snapshot.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.FocusSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.LoadQueueSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.PlayerSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.ai.AIScope
import com.sappyoak.dsanalyzer.runtime.snapshot.animation.AnimationSnapshot

/** Ticks between slow-tier reads */
private const val WORLD_TIER_PERIOD = 6
private const val AI_GRACE_PERIOD = 15

internal data class WorldReading(
    val characters: List<CharacterSnapshot>,
    val loadQueue: LoadQueueSnapshot?,
    val animation: AnimationSnapshot?,
    val focus: FocusSnapshot?
)

internal class WorldReads(
    layout: GameLayout,
    private val characterSource: CharacterSource,
    private val loadQueueReader: LoadQueueReader
) {
    private val ai = AITracker(AI_GRACE_PERIOD, AIReader(layout.ai, layout.action))
    private val animations = AnimationTracker(AnimationReader(layout.animation, layout.animRequest))
    private val focusReader = FocusReader(layout.lockOn, animations)

    private var tier = Tier()
    private var untilTier = 0

    fun read(
        memory: GameMemory,
        loaded: Boolean,
        player: PlayerSnapshot?,
        options: SampleOptions
    ): WorldReading {
        val focus = if (loaded) focusReader.select(memory, options.focus, player, tier.characters) else null
        refreshTier(memory, options.aiScope, focus)

        val characters = ai.read(memory.memory, tier.ai, tier.characters)
        val animated = if (loaded) animations.read(memory, options.animation, tier.others) else null
        return WorldReading(
            characters = characters,
            loadQueue = tier.loadQueue,
            animation = animated,
            focus = focusReader.snapshot(memory, focus, characters, tier.others, animated)
        )
    }

    private fun refreshTier(
        memory: GameMemory,
        scope: AIScope,
        focus: Focus?
    ) {
        if (untilTier > 0) {
            untilTier--
            return
        }
        untilTier = WORLD_TIER_PERIOD - 1

        val reading = characterSource.read(memory)
        tier = Tier(
            loadQueue = loadQueueReader.read(memory),
            characters = reading.characters,
            others = reading.others,
            ai = ai.select(memory.memory, scope, reading, focus?.handle)
        )
    }
}

/** The slow tier, read every [WORLD_TIER_PERIOD] ticks and shown in between */
private data class Tier(
    val loadQueue: LoadQueueSnapshot? = null,
    val characters: List<CharacterSnapshot> = emptyList(),
    val others: List<CharacterRef> = emptyList(),
    val ai: List<AISource> = emptyList()
)
