package com.sappyoak.dsanalyzer.app.runtime

import kotlin.time.Duration.Companion.milliseconds

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.overlay.MarkerKind
import com.sappyoak.dsanalyzer.overlay.OverlayScene
import com.sappyoak.dsanalyzer.runtime.session.BlockRoster
import com.sappyoak.dsanalyzer.runtime.session.CameraSnapshot
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.MenuSnapshot
import com.sappyoak.dsanalyzer.runtime.session.PlacedEnemy
import com.sappyoak.dsanalyzer.runtime.session.RuntimeSnapshot
import com.sappyoak.dsanalyzer.runtime.session.SampleCost
import com.sappyoak.dsanalyzer.runtime.session.WorldPlace
import com.sappyoak.dsanalyzer.shared.math.Frustum
import com.sappyoak.dsanalyzer.shared.math.Mat4
import com.sappyoak.dsanalyzer.shared.math.Vec3
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

private val CAMERA = CameraSnapshot(
    position = Vec3(0f, 0f, 0f),
    orientation = Vec3(0f, 0f, 0f),
    targetPitch = 0f,
    transform = Mat4.Identity,
    frustum = Frustum(fovRadians = 0.75f, aspect = 16f / 9f, near = 0.1f, far = 1000f)
)

private fun character(x: Float, player: Boolean = false) = CharacterSnapshot(
    handle = x.toInt(),
    modelId = 1000,
    npcParamId = 10,
    position = Vec3(x, 0f, 0f),
    health = 100,
    healthMax = 100,
    stamina = 50,
    characterType = 0,
    teamType = 0,
    targetHandle = null,
    isPlayer = player
)

private fun placed(x: Float, entityId: Int = 0) = PlacedEnemy(
    index = x.toInt(),
    name = "c1000_000${x.toInt()}",
    entityId = entityId,
    position = Vec3(x, 0f, 0f),
    npcParamId = 10,
    thinkParamId = 20
)

private fun roster(index: Int, vararg enemies: PlacedEnemy) =
    BlockRoster(blockIndex = index, partCount = enemies.size, enemies = enemies.toList())

private fun state(
    characters: List<CharacterSnapshot> = emptyList(),
    rosters: List<BlockRoster>? = null,
    camera: CameraSnapshot? = CAMERA,
    menu: MenuSnapshot? = null
) = RuntimeState(
    snapshot = RuntimeSnapshot(
        inGameTimeMillis = 0,
        frame = 1,
        place = WorldPlace.InWorld(MapId.of(area = 10, block = 2)),
        loaded = true,
        reloaded = false,
        player = null,
        world = null,
        camera = camera,
        loadQueue = null,
        characters = characters,
        flagChanges = emptyList(),
        menu = menu,
        cost = SampleCost(1.milliseconds, 1, 1)
    ),
    placedEnemies = rosters
)

private fun menu(
    isFullScreenOpen: Boolean = false,
    screen: Int = -1,
    defaultQuantity: Int = -1
): MenuSnapshot = MenuSnapshot(isFullScreenOpen, screen, defaultQuantity)

class OverlayScenesTest : FunSpec({
    test("no world means nothing to draw") {
        assertSoftly {
            RuntimeState().overlayScene() shouldBe OverlayScene.Empty
            state(camera = null).overlayScene().markers.shouldBeEmpty()
        }
    }

    test("the player and the characters around them are drawn") {
        val scene = state(characters = listOf(character(0f, player = true), character(5f))).overlayScene()

        assertSoftly {
            scene.camera shouldBe CAMERA
            scene.markers.map { it.kind } shouldBe listOf(MarkerKind.Player, MarkerKind.Character)
            scene.markers.first().label shouldBe "you"
        }
    }

    test("a roster in the player's space contributes only the enemies the game has not built") {
        val characters = listOf(character(0f, player = true), character(10f), character(20f), character(30f))
        val block = roster(0, placed(10f), placed(20f), placed(30f), placed(40f, entityId = 1010964))

        val scene = state(characters, listOf(block)).overlayScene()
        val placedMarkers = scene.markers.filter { it.kind == MarkerKind.Placed }

        assertSoftly {
            // Three of the four are already on screen as live characters, at better positions
            placedMarkers.map { it.label } shouldBe listOf("1010964")
            scene.markers.count { it.kind == MarkerKind.Character } shouldBe 3
        }
    }

    test("a roster whose coordinates do not agree is left out entirely") {
        val characters = listOf(character(0f, player = true), character(10f), character(20f), character(30f))
        // Same shape, different space — every position off by a block's origin
        val elsewhere = roster(1, placed(910f), placed(920f), placed(930f), placed(940f))

        val scene = state(characters, listOf(elsewhere)).overlayScene()

        // The whole point: wrong-space markers are not drawn at all rather than drawn wrongly
        scene.markers.none { it.kind == MarkerKind.Placed } shouldBe true
    }

    test("when two blocks are resident the one that agrees is the one used") {
        val characters = listOf(character(0f, player = true), character(10f), character(20f), character(30f))
        val mine = roster(0, placed(10f), placed(20f), placed(30f), placed(40f, entityId = 777))
        val theirs = roster(1, placed(910f), placed(920f), placed(930f), placed(940f, entityId = 999))

        val scene = state(characters, listOf(theirs, mine)).overlayScene()

        scene.markers.filter { it.kind == MarkerKind.Placed }.map { it.label } shouldBe listOf("777")
    }

    test("one or two coincidental matches are not enough to claim a block") {
        val characters = listOf(character(0f, player = true), character(10f), character(20f))
        // Two positions agree by chance; the rest of the block is somewhere else entirely
        val coincidence = roster(1, placed(10f), placed(20f), placed(930f, entityId = 1), placed(940f, entityId = 2))

        val scene = state(characters, listOf(coincidence)).overlayScene()

        scene.markers.none { it.kind == MarkerKind.Placed } shouldBe true
    }

    test("an enemy that has walked off its spawn point still counts as built") {
        val characters = listOf(character(0f, player = true), character(10.2f), character(20.3f), character(30.1f))
        val block = roster(0, placed(10f), placed(20f), placed(30f))

        val scene = state(characters, listOf(block)).overlayScene()

        // Idling drift is not a second enemy, so none of these three is drawn twice
        scene.markers.none { it.kind == MarkerKind.Placed } shouldBe true
    }

    test("an open full-screen menu suppresses the whole overlay, markers and frame alike") {
        val characters = listOf(character(0f, player = true), character(10f))
        val scene = state(characters, menu = menu(true)).overlayScene()

        assertSoftly {
            scene.suppressWorldDrawing shouldBe true
            // Not assembled at all: projecting a scene nobody will see is work for nothing
            scene.markers.shouldBeEmpty()
            // The camera is still carried, so nothing downstream has to treat this as "no world"
            scene.camera shouldBe CAMERA
        }
    }

    test("a placed enemy with no entity id falls back to naming its npc param") {
        val characters = listOf(character(0f, player = true), character(10f), character(20f), character(30f))
        val block = roster(0, placed(10f), placed(20f), placed(30f), placed(40f, entityId = -1))

        val scene = state(characters, listOf(block)).overlayScene()

        // -1 is "no script references this", which is itself worth seeing
        scene.markers.last().label shouldBe "npc 10"
    }
})