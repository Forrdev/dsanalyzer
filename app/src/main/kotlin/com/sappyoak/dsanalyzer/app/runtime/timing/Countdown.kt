package com.sappyoak.dsanalyzer.app.runtime.timing

public sealed interface Countdown {
    public val window: AttackWindow

    public data class Coming(
        override val window: AttackWindow,
        public val seconds: Float
    ) : Countdown

    public data class Live(
        override val window: AttackWindow,
        public val seconds: Float
    ) : Countdown
}

public fun countdownAt(windows: List<AttackWindow>, clock: Float): Countdown? {
    windows.firstOrNull { clock >= it.start && clock < it.end }?.let { return Countdown.Live(it, it.end - clock) }

    val next = windows.firstOrNull { it.start > clock } ?: return null
    return Countdown.Coming(next, next.start - clock)
}
