package com.sappyoak.dsanalyzer.runtime.sampling

import com.sappyoak.dsanalyzer.runtime.snapshot.FocusRequest
import com.sappyoak.dsanalyzer.runtime.snapshot.ai.AIScope
import com.sappyoak.dsanalyzer.runtime.snapshot.animation.AnimationTarget

public data class SampleOptions(
    public val aiScope: AIScope = AIScope.TargetingPlayer,
    public val animation: AnimationTarget = AnimationTarget.Player,
    public val focus: FocusRequest = FocusRequest.Off,
    public val watchedFlag: Int? = null
)
