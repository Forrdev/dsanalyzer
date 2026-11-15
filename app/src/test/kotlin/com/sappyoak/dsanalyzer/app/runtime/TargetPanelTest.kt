package com.sappyoak.dsanalyzer.app.runtime

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.milliseconds

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.overlay.PanelHealth
import com.sappyoak.dsanalyzer.runtime.session.CameraSnapshot
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.FocusRequest
import com.sappyoak.dsanalyzer.runtime.session.FocusSnapshot
import com.sappyoak.dsanalyzer.runtime.session.RuntimeSnapshot
import com.sappyoak.dsanalyzer.runtime.session.SampleCost
import com.sappyoak.dsanalyzer.runtime.session.WorldPlace
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

private fun knight(ai: AISnapshot?, health: Int = 410) = CharacterSnapshot(
    handle = 2,
    modelId = 2410,
    npcParamId = 241000,
    position = Vec3(0f, 0f, 5f),
    health = health,
    healthMax = 1000,
    stamina = 100,
    characterType = 0,
    teamType = 0,
    targetHandle = null,
    effects = null,
    ai = ai
)

private fun seeing(focus: FocusSnapshot?) = RuntimeState(
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
        characters = listOfNotNull(focus?.character),
        flagChanges = emptyList(),
        menu = null,
        cost = SampleCost(1.milliseconds, 10, 10),
        focus = focus
    )
)

private fun focusOn(character: CharacterSnapshot, boss: Boolean = false) =
    FocusSnapshot(character, locked = true, boss = boss, animation = null)

class TargetPanelTest : FunSpec({
    test("the panel sits above the target, with its name, health and AI") {
        val panel = seeing(focusOn(knight(ATTACKING))).overlayScene(OverlaySettings()).panel.shouldNotBeNull()

        assertSoftly {
            panel.anchor shouldBe Vec3(0f, 2.2f, 5f)
            panel.title shouldBe "Silver Knight  c2410"
            panel.health shouldBe PanelHealth(410, 1000)
            panel.lines shouldBe listOf("think 241000")
        }
    }

    test("a boss keeps its name and health in the boss bar, so the panel leaves them out") {
        val panel = seeing(focusOn(knight(null), boss = true)).overlayScene(OverlaySettings()).panel.shouldNotBeNull()

        assertSoftly {
            panel.title shouldBe "c2410"
            panel.health.shouldBeNull()
        }
    }

    test("a dead target, or the panel switched off, draws nothing") {
        assertSoftly {
            seeing(focusOn(knight(null, health = 0))).overlayScene(OverlaySettings()).panel.shouldBeNull()
            seeing(focusOn(knight(ATTACKING)))
                .overlayScene(OverlaySettings(target = TargetPanelSettings(enabled = false)))
                .panel.shouldBeNull()
        }
    }

    test("the panel follows the lock only while the overlay and the panel are both on") {
        assertSoftly {
            Settings().focusRequest shouldBe FocusRequest(enabled = true, range = TargetPanelSettings.DEFAULT_TARGET_RANGE)
            ShowTargetPanel(false).applyTo(Settings()).focusRequest shouldBe FocusRequest.Off
            OverlayEnabled(false).applyTo(Settings()).focusRequest shouldBe FocusRequest.Off
        }
    }
})
