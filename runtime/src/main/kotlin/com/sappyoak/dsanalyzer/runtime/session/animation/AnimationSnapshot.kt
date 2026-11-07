package com.sappyoak.dsanalyzer.runtime.session.animation

import kotlin.math.roundToInt

public data class AnimationSnapshot(
    public val target: AnimationTarget,
    public val playSpeed: Float,
    public val slots: List<AnimSlotSnapshot>,
    public val requests: List<AnimRequestSnapshot>,
    public val channels: List<AnimChannelSnapshot>
) {
    public val occupied: List<AnimSlotSnapshot> get() = slots.filter { it.state != SlotState.Free }
    public val weightSum: Float get() = slots.sumOf { (it.control?.masterWeight ?: 0f).toDouble() }.toFloat()

    public val orphans: List<AnimSlotSnapshot>
        get() {
            if (channels.isEmpty()) return emptyList()
            val fadedFrom = occupied.mapNotNullTo(HashSet()) { slot -> slot.fadeFrom.takeUnless { it.isNone } }
            return slots.filter { it.isStuck && it.channel == null && it.handle !in fadedFrom }
        }
}

private val AnimSlotSnapshot.isStuck: Boolean
    get() {
        val played = control ?: return false
        return state == SlotState.Playing && !played.looping &&
                (played.playbackSpeed == 0f || played.cycles >= played.maxCycles)
    }


public data class AnimSlotSnapshot(
    public val index: Int,
    public val handle: AnimHandle,
    public val state: SlotState,
    /** The eviction selector's first key */
    public val priority: Int,
    /** Seconds since the slot started */
    public val elapsed: Float,
    public val fadeFrom: AnimHandle,
    public val fadeDuration: Float,
    public val fadeRemaining: Float,
    public val control: AnimControlSnapshot?,
    public val channel: Int? = null,
    public val animationId: Int? = null
)

public data class AnimControlSnapshot(
    public val localTime: Float,
    public val masterWeight: Float,
    public val weightScale: Float,
    public val playbackSpeed: Float,
    public val cycles: Int,
    public val maxCycles: Int
) {
    public val looping: Boolean get() = maxCycles == -1
}

public data class AnimRequestSnapshot(
    public val entry: Int,
    public val animationId: Int,
    public val enabled: Boolean,
    public val band: Int?,
    public val channel: AnimHandle
) {
    public val live: Boolean get() = enabled && animationId >= 0
}

public data class AnimChannelSnapshot(
    public val index: Int,
    public val handle: AnimHandle,
    public val slot: AnimHandle,
    public val fadeWeight: Float
)

@JvmInline
public value class AnimHandle(public val raw: Int) {
    public val isNone: Boolean get() = raw == -1

    public val index: Int get() = raw and 0xFFFF
    public val serial: Int get() = raw ushr 16

    public companion object {
        public val None: AnimHandle = AnimHandle(-1)
    }
}
public enum class SlotState(public val code: Int) {
    Free(0),
    Playing(1),
    FadingIn(2),
    FadingOut(3),
    Finished(4),
    Unknown(-1);

    public companion object {
        public fun of(code: Int): SlotState = entries.firstOrNull { it.code == code } ?: Unknown
    }
}
