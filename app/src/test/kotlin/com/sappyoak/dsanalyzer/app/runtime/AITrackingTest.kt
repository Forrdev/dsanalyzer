package com.sappyoak.dsanalyzer.app.runtime

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.milliseconds

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.runtime.session.CameraSnapshot
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.RuntimeSnapshot
import com.sappyoak.dsanalyzer.runtime.session.SampleCost
import com.sappyoak.dsanalyzer.runtime.session.WorldPlace
import com.sappyoak.dsanalyzer.runtime.session.ai.AIScope
import com.sappyoak.dsanalyzer.runtime.session.ai.AISnapshot
import com.sappyoak.dsanalyzer.runtime.session.ai.AITarget
import com.sappyoak.dsanalyzer.shared.math.Frustum
import com.sappyoak.dsanalyzer.shared.math.Mat4
import com.sappyoak.dsanalyzer.shared.math.Vec3

private val ATTACKING = AISnapshot(
    thinkId = 241000,
    target = AITarget.Character(1),
    requestedAction = 3002,
    playingAction = 3002,
    timers = List(4) { 0f },
    numbers = List(8) { 0 }
)

private fun knight(ai: AISnapshot?) = CharacterSnapshot(
    handle = 2,
    modelId = 2410,
    npcParamId = 241000,
    position = Vec3(0f, 0f, 5f),
    health = 410,
    healthMax = 1000,
    stamina = 100,
    characterType = 0,
    teamType = 0,
    targetHandle = null,
    isPlayer = false,
    effects = null,
    ai = ai
)

private fun seeing(vararg characters: CharacterSnapshot) = RuntimeState(
    snapshot = RuntimeSnapshot(
        inGameTimeMillis = 0,
        frame = 1,
        place = WorldPlace.InWorld(MapId.of(area = 15, block = 1)),
        loaded = true,
        reloaded = false,
        player = null,
        world = null,
        camera = CameraSnapshot(Vec3.Zero, null, null, Mat4.Identity, Frustum(0.75f, 16f / 9f, 0.1f, 1000f)),
        loadQueue = null,
        characters = characters.toList(),
        flagChanges = emptyList(),
        menu = null,
        cost = SampleCost(1.milliseconds, 10, 10)
    )
)

class AiTrackingTest : FunSpec({
    test("enemies targeting you is the default, and each choice asks the sampler for its scope") {
        assertSoftly {
            Settings().ai.tracking shouldBe AITracking.TargetingPlayer
            AITracking.entries.map { it.scope } shouldBe listOf(AIScope.Off, AIScope.TargetingPlayer, AIScope.All)
            TrackAI(AITracking.All).applyTo(Settings()).ai.tracking shouldBe AITracking.All
        }
    }

    test("a tracked enemy's marker names the action its AI asked for, and an untracked one does not") {
        val labels = seeing(knight(ATTACKING), knight(null))
            .overlayScene(OverlaySettings())
            .markers.markers.map { it.label }

        labels shouldBe listOf("2410  410hp  a3002", "2410  410hp")
    }
})
