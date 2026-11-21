package com.sappyoak.dsanalyzer.runtime.snapshot

public data class FlagChange(public val flagId: Int, public val set: Boolean)

public data class WatchedFlag(public val flagId: Int, public val set: Boolean?)
