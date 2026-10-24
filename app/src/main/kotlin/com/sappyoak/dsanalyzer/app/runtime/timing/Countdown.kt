package com.sappyoak.dsanalyzer.app.runtime.timing

public sealed interface Countdown {
    public val window: AttackWindow

    public data class Coming(
        override val window: AttackWindow,
        public val seconds: Float,
        public val windup: Float
    ) : Countdown

    public data class Live(
        override val window: AttackWindow,
        public val seconds: Float
    ) : Countdown
}

public fun countdownAt(windows: List<AttackWindow>, clock: Float): Countdown? {
    windows.firstOrNull { clock >= it.start && clock < it.end }?.let { return Countdown.Live(it, it.end - clock) }

    val index = windows.indexOfFirst { it.start > clock }
    if (index < 0) return null
    val next = windows[index]
    val from = windows.getOrNull(index - 1)?.end?.coerceAtMost(clock) ?: 0f
    val lead = next.start - from
    val windup = if (lead <= 0f) 1f else ((clock - from) / lead).coerceIn(0f, 1f)
    return Countdown.Coming(next, next.start - clock, windup)
}
