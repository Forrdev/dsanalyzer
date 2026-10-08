package com.sappyoak.dsanalyzer.game.world.animations

import com.sappyoak.dsanalyzer.formats.tae.template.EventBank

/** The player, whose animations are the ones split across two binders */
private const val PLAYER_MODEL = 0

private const val MODEL_DIGITS = 4

public sealed interface AnimationSetId {
    public val label: String
    public val bank: EventBank

    public data class Character(public val model: Int) : AnimationSetId {
        override val label: String = "c" + model.toString().padStart(MODEL_DIGITS, '0')
        override val bank: EventBank = EventBank.Character
        override fun toString(): String = label
    }

    public data class Object(public val model: Int) : AnimationSetId {
        override val label: String = "o" + model.toString().padStart(MODEL_DIGITS, '0')
        override val bank: EventBank = EventBank.Object
        override fun toString(): String = label
    }
}

internal val AnimationSetId.isPlayer: Boolean
    get() = this is AnimationSetId.Character && model == PLAYER_MODEL