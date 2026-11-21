package com.sappyoak.dsanalyzer.game.world.ai

import com.sappyoak.dsanalyzer.formats.msb.MSB
import com.sappyoak.dsanalyzer.formats.msb.part.Part
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId

/**
 * The characters the enemies in [layouts] place, by the NpcThinkParam row each thinks with: the way from an AI
 * script to the animations its EzState ids play. Cutscene dummies do not think and are left out
 */
public fun charactersByThink(layouts: List<MSB>): Map<Int, Set<AnimationSetId>> = layouts
    .flatMap { msb ->
        msb.parts.filterIsInstance<Part.Enemy>().mapNotNull { enemy ->
            val set = enemy.header.model?.let { AnimationSetId.parse(msb[it].name) }
            set?.let { enemy.data.thinkParamId to it }
        }
    }
    .groupBy({ it.first }, { it.second })
    .mapValues { (_, sets) -> sets.toSortedSet(compareBy(AnimationSetId::label)) }
