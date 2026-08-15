package com.sappyoak.dsanalyzer.game.identity

import kotlinx.serialization.Serializable

@JvmInline @Serializable value class GameBuildId(val value: String) {
    val isUnknown: Boolean get() = this == Unknown

    override fun toString() = value

    companion object {
        val Unknown = GameBuildId("unknown")

        fun of(value: String) = GameBuildId(value)
    }
}

@JvmInline @Serializable value class GameBuildLabel(val value: String) {
    override fun toString() = value

    companion object {
        fun of(value: String) = GameBuildLabel(value)
    }
}

