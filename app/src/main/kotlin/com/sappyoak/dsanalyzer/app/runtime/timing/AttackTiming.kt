package com.sappyoak.dsanalyzer.app.runtime.timing

import com.sappyoak.dsanalyzer.formats.tae.Tae

public enum class WindowKind(internal val eventTypes: Set<Int>) {
    /** 'AttackBehavior' and 'CommonBehavior' for hits off the body */
    Hit(setOf(1, 5)),
    /** 'BulletBehavior' */
    Bullet(setOf(2)),
    /** 'ThrowAttackBehavior' */
    Grab(setOf(304));

    internal companion object {
        fun of(eventType: Int): WindowKind? = entries.firstOrNull { eventType in it.eventTypes }
    }
}

public data class AttackWindow(
    public val kind: WindowKind,
    public val start: Float,
    public val end: Float
)

public data class ModelTimings(public val animations: Map<Long, List<AttackWindow>>) {
    public fun windowsOf(animation: Int): List<AttackWindow> = animations[animation.toLong()].orEmpty()

    public companion object {
        public val None: ModelTimings = ModelTimings(emptyMap())
    }
}

internal fun timingsOf(files: List<Tae>): ModelTimings = ModelTimings(
    files.flatMap { tae ->
        tae.animations.mapNotNull { animation ->
            val windows = tae.eventsFor(animation.id).orEmpty()
                .mapNotNull { event ->
                    WindowKind.of(event.type)?.let { AttackWindow(it, event.startTime, event.endTime) }
                }
                .sortedBy { it.start }
            if (windows.isEmpty()) null else animation.id to windows
        }
    }.toMap()
)
