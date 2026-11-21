package com.sappyoak.dsanalyzer.game.world.animations

import com.sappyoak.dsanalyzer.formats.tae.template.EventBank

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

    public companion object {
        /** The set [label] names, written as its own [label] is: `c2690`, `o1234` */
        public fun parse(label: String): AnimationSetId? {
            val model = label.drop(1).takeIf { it.isNotEmpty() && it.all(Char::isDigit) }?.toIntOrNull() ?: return null
            val set = when (label.first()) {
                'c' -> Character(model)
                'o' -> Object(model)
                else -> return null
            }
            return set.takeIf { it.label == label }
        }
    }
}
