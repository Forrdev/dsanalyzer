package com.sappyoak.dsanalyzer.app.data.animations

import kotlin.math.roundToInt

import com.sappyoak.dsanalyzer.formats.tae.Tae
import com.sappyoak.dsanalyzer.formats.tae.template.DecodedEvent
import com.sappyoak.dsanalyzer.formats.tae.template.TaeTemplate
import com.sappyoak.dsanalyzer.formats.tae.template.decode
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId

/** Animation ids pack a category above the last four digits, written aCC_NNNN by the community tools */
private const val CATEGORY_SPAN = 10_000L

/** One character's or object's animations, decoded against its event bank's template */
public class SetAnimations(
    public val set: AnimationSetId,
    private val taes: List<Tae>,
    private val template: TaeTemplate,
    /** The edition's logic tick, which event windows are shown in */
    public val tickHz: Int
) {
    /** Every animation id across the set's TAE files, in order */
    public val ids: List<Long> = taes.flatMap { tae -> tae.animations.map { it.id } }.distinct().sorted()

    private val known: Set<Long> = ids.toSet()

    public operator fun contains(animationId: Long): Boolean = animationId in known

    /** The events of [animationId], following an imported animation to its source; null for an unknown id */
    public fun events(animationId: Long): List<DecodedEvent>? =
        taes.firstNotNullOfOrNull { it.decode(animationId, template) }
}

/** The logic tick [seconds] into an animation falls on, at [tickHz] ticks a second */
public fun ticksAt(seconds: Float, tickHz: Int): Int = (seconds * tickHz).roundToInt()

public fun animationLabel(animationId: Long): String =
    "a%02d_%04d".format(animationId / CATEGORY_SPAN, animationId % CATEGORY_SPAN)
