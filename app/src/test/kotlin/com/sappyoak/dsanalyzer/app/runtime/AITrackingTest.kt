package com.sappyoak.dsanalyzer.app.runtime

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.runtime.session.ai.AIScope

class AiTrackingTest : FunSpec({
    test("enemies targeting you is the default, and each choice asks the sampler for its scope") {
        assertSoftly {
            Settings().runtime.ai.tracking shouldBe AITracking.TargetingPlayer
            AITracking.entries.map { it.scope } shouldBe listOf(AIScope.Off, AIScope.TargetingPlayer, AIScope.All)
            TrackAI(AITracking.All).applyTo(Settings()).runtime.ai.tracking shouldBe AITracking.All
        }
    }
})
