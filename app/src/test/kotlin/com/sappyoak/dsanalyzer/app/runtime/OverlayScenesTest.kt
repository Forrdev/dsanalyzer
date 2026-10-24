package com.sappyoak.dsanalyzer.app.runtime

import kotlin.time.Duration.Companion.milliseconds
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.overlay.MarkerKind
import com.sappyoak.dsanalyzer.overlay.OverlayScene
import com.sappyoak.dsanalyzer.runtime.ptde.MenuOpenMask
import com.sappyoak.dsanalyzer.runtime.session.BlockRoster
import com.sappyoak.dsanalyzer.runtime.session.CameraSnapshot
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionBody
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionGeometry
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionSnapshot
import com.sappyoak.dsanalyzer.runtime.session.collision.ShapeKind
import com.sappyoak.dsanalyzer.runtime.session.MenuSnapshot
import com.sappyoak.dsanalyzer.runtime.session.PlacedEnemy
import com.sappyoak.dsanalyzer.runtime.session.RuntimeSnapshot
import com.sappyoak.dsanalyzer.runtime.session.SampleCost
import com.sappyoak.dsanalyzer.runtime.session.WorldPlace
import com.sappyoak.dsanalyzer.shared.math.Aabb
import com.sappyoak.dsanalyzer.shared.math.Frustum
import com.sappyoak.dsanalyzer.shared.math.Mat4
import com.sappyoak.dsanalyzer.shared.math.Vec3

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
    isPlayer = player,
    effects = null
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

/** Everything on, which is what the pre-settings behaviour was */
private val DRAW_EVERYTHING = OverlaySettings()

private fun state(
    characters: List<CharacterSnapshot> = emptyList(),
    rosters: List<BlockRoster>? = null,
    camera: CameraSnapshot? = CAMERA,
    menu: MenuSnapshot? = null,
    collision: CollisionSnapshot? = null
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
    placedEnemies = rosters,
    collision = collision
)
private fun menu(
    mask: Int = 0,
    boxKind: Int = 0,
    screen: Int = 0,
    defaultQuantity: Int = 0,
    blocked: Boolean = false,
): MenuSnapshot = MenuSnapshot(mask, boxKind, screen, defaultQuantity, blocked)

class OverlayScenesTest : FunSpec({
    test("no world means nothing to draw") {
        assertSoftly {
            RuntimeState().overlayScene(DRAW_EVERYTHING) shouldBe OverlayScene.Empty
            state(camera = null).overlayScene(DRAW_EVERYTHING).markers.markers.shouldBeEmpty()
        }
    }

    test("the player and the characters around them are drawn") {
        val scene = state(characters = listOf(character(0f, player = true), character(5f))).overlayScene(DRAW_EVERYTHING)

        assertSoftly {
            scene.camera shouldBe CAMERA
            scene.markers.markers.map { it.kind } shouldBe listOf(MarkerKind.Player, MarkerKind.Character)
            scene.markers.markers.first().label shouldBe "you"
        }
    }

    test("a roster in the player's space contributes only the enemies the game has not built") {
        val characters = listOf(character(0f, player = true), character(10f), character(20f), character(30f))
        val block = roster(0, placed(10f), placed(20f), placed(30f), placed(40f, entityId = 1010964))

        val scene = state(characters, listOf(block)).overlayScene(DRAW_EVERYTHING)
        val placedMarkers = scene.markers.markers.filter { it.kind == MarkerKind.Placed }

        assertSoftly {
            // Three of the four are already on screen as live characters, at better positions
            placedMarkers.map { it.label } shouldBe listOf("1010964")
            scene.markers.markers.count { it.kind == MarkerKind.Character } shouldBe 3
        }
    }

    test("a roster whose coordinates do not agree is left out entirely") {
        val characters = listOf(character(0f, player = true), character(10f), character(20f), character(30f))
        // Same shape, different space — every position off by a block's origin
        val elsewhere = roster(1, placed(910f), placed(920f), placed(930f), placed(940f))

        val scene = state(characters, listOf(elsewhere)).overlayScene(DRAW_EVERYTHING)

        // The whole point: wrong-space markers are not drawn at all rather than drawn wrongly
        scene.markers.markers.none { it.kind == MarkerKind.Placed } shouldBe true
    }

    test("when two blocks are resident the one that agrees is the one used") {
        val characters = listOf(character(0f, player = true), character(10f), character(20f), character(30f))
        val mine = roster(0, placed(10f), placed(20f), placed(30f), placed(40f, entityId = 777))
        val theirs = roster(1, placed(910f), placed(920f), placed(930f), placed(940f, entityId = 999))

        val scene = state(characters, listOf(theirs, mine)).overlayScene(DRAW_EVERYTHING)

        scene.markers.markers.filter { it.kind == MarkerKind.Placed }.map { it.label } shouldBe listOf("777")
    }

    test("one or two coincidental matches are not enough to claim a block") {
        val characters = listOf(character(0f, player = true), character(10f), character(20f))
        // Two positions agree by chance; the rest of the block is somewhere else entirely
        val coincidence = roster(1, placed(10f), placed(20f), placed(930f, entityId = 1), placed(940f, entityId = 2))

        val scene = state(characters, listOf(coincidence)).overlayScene(DRAW_EVERYTHING)

        scene.markers.markers.none { it.kind == MarkerKind.Placed } shouldBe true
    }

    test("an enemy that has walked off its spawn point still counts as built") {
        val characters = listOf(character(0f, player = true), character(10.2f), character(20.3f), character(30.1f))
        val block = roster(0, placed(10f), placed(20f), placed(30f))

        val scene = state(characters, listOf(block)).overlayScene(DRAW_EVERYTHING)

        // Idling drift is not a second enemy, so none of these three is drawn twice
        scene.markers.markers.none { it.kind == MarkerKind.Placed } shouldBe true
    }

    test("an open menu suppresses the whole overlay, markers and frame alike") {
        val characters = listOf(character(0f, player = true), character(10f))
        val menu = menu(mask = MenuOpenMask.Talk, screen = 11)
        val scene = state(characters, menu = menu).overlayScene(DRAW_EVERYTHING)

        assertSoftly {
            scene.suppressWorldDrawing shouldBe true
            // Not assembled at all: projecting a scene nobody will see is work for nothing
            scene.markers.markers.shouldBeEmpty()
            // The camera is still carried, so nothing downstream has to treat this as "no world"
            scene.camera shouldBe CAMERA
        }
    }

    test("the menus that leave the pause screen at zero still suppress") {
        val characters = listOf(character(0f, player = true))

        fun suppressedWith(mask: Int, boxKind: Int = 0) =
            state(characters, menu = menu(mask, boxKind)).overlayScene(DRAW_EVERYTHING).suppressWorldDrawing

        assertSoftly {
            // Every one of these leaves `screen` at 0 while a menu is very much open, and every one
            // of them was drawn over by the `IsFullscreenMenu` check this replaced
            suppressedWith(MenuOpenMask.Shop) shouldBe true
            suppressedWith(MenuOpenMask.Repair) shouldBe true
            suppressedWith(MenuOpenMask.Talk) shouldBe true
            suppressedWith(MenuOpenMask.Box, boxKind = 8) shouldBe true
        }
    }

    test("nothing open, a non-blocking menu, and an unreadable menu all leave the overlay drawing") {
        val characters = listOf(character(0f, player = true))

        fun suppressedWith(menu: MenuSnapshot?) =
            state(characters, menu = menu).overlayScene(DRAW_EVERYTHING).suppressWorldDrawing

        assertSoftly {
            suppressedWith(menu()) shouldBe false
            // The game itself lets the player act through these two, so the world is still live
            suppressedWith(menu(MenuOpenMask.AmbientTalk)) shouldBe false
            suppressedWith(menu(MenuOpenMask.Box, boxKind = 9)) shouldBe false
            // Null is "the menu system could not be read", which must not blank the overlay
            suppressedWith(null) shouldBe false
        }
    }

    test("a placed enemy with no entity id falls back to naming its npc param") {
        val characters = listOf(character(0f, player = true), character(10f), character(20f), character(30f))
        val block = roster(0, placed(10f), placed(20f), placed(30f), placed(40f, entityId = -1))

        val scene = state(characters, listOf(block)).overlayScene(DRAW_EVERYTHING)

        // -1 is "no script references this", which is itself worth seeing
        scene.markers.markers.last().label shouldBe "npc 10"
    }

    test("the overlay switched off assembles nothing at all") {
        val characters = listOf(character(0f, player = true), character(5f))
        val off = OverlaySettings(enabled = false)

        // Empty rather than a scene with no markers: the window is gated on this too, so there is
        // nothing to project onto and no reason to have walked the characters
        state(characters).overlayScene(off) shouldBe OverlayScene.Empty
    }

    test("the player's own marker has its own switch") {
        val characters = listOf(character(0f, player = true), character(5f))
        val hidden = OverlaySettings(characters = CharacterOverlaySettings(drawPlayer = false))

        val kinds = state(characters).overlayScene(hidden).markers.markers.map { it.kind }

        // The crowd stays; only the player goes
        kinds shouldBe listOf(MarkerKind.Character)
    }

    test("None draws no characters, and Instantiated draws no placed enemies") {
        val characters = listOf(character(0f, player = true), character(10f), character(20f), character(30f))
        val block = roster(0, placed(10f), placed(20f), placed(30f), placed(40f, entityId = 777))

        fun markersWith(draw: CharacterDrawing) = state(characters, listOf(block))
            .overlayScene(OverlaySettings(characters = CharacterOverlaySettings(draw = draw)))
            .markers.markers

        assertSoftly {
            // None leaves the player, which is the other switch's business
            markersWith(CharacterDrawing.None).map { it.kind } shouldBe listOf(MarkerKind.Player)

            val instantiated = markersWith(CharacterDrawing.Instantiated)
            instantiated.count { it.kind == MarkerKind.Character } shouldBe 3
            instantiated.none { it.kind == MarkerKind.Placed } shouldBe true

            // The unbuilt part is the interesting one, and only All asks for it
            markersWith(CharacterDrawing.All).count { it.kind == MarkerKind.Placed } shouldBe 1
        }
    }

    test("hiding the player does not change which block is believed to be the player's") {
        // The guard for a real trap: the agreement test runs on the whole character list, so a
        // display switch cannot change which roster is identified. Filtering the input first would
        // make placed enemies vanish when you hid the crowd, which looks like anything but a
        // settings bug
        val characters = listOf(character(0f, player = true), character(10f), character(20f), character(30f))
        val block = roster(0, placed(10f), placed(20f), placed(30f), placed(40f, entityId = 777))

        val hidden = OverlaySettings(characters = CharacterOverlaySettings(drawPlayer = false))
        val scene = state(characters, listOf(block)).overlayScene(hidden)

        scene.markers.markers.filter { it.kind == MarkerKind.Placed }.map { it.label } shouldBe listOf("777")
    }

    test("the draw distance is carried, not applied") {
        val characters = listOf(character(0f, player = true), character(5f))
        val close = OverlaySettings(characters = CharacterOverlaySettings(drawDistance = 1f))

        val scene = state(characters).overlayScene(close)

        assertSoftly {
            scene.markers.drawDistance shouldBe 1f
            // Still both of them: culling by distance is the overlay's job, and doing it here as
            // well would mean two places deciding and one of them going stale
            scene.markers.markers.size shouldBe 2
        }
    }

    test("collision needs both a read and the switch") {
        val characters = listOf(character(0f, player = true))
        val read = CollisionSnapshot(
            bodies = listOf(
                CollisionBody(
                    name = "1000B1",
                    kind = ShapeKind.Mopp,
                    filterInfo = 0x1C,
                    allowedPenetration = Float.MAX_VALUE,
                    bounds = Aabb.Empty,
                    geometry = CollisionGeometry.Unread
                )
            ),
            failed = 0
        )
        val on = OverlaySettings(collision = CollisionOverlaySettings(enabled = true))

        assertSoftly {
            // Switched on but never read
            state(characters).overlayScene(on).collision shouldBe null
            // Read but switched off
            state(characters, collision = read).overlayScene(DRAW_EVERYTHING).collision shouldBe null
            // Both
            state(characters, collision = read).overlayScene(on).collision?.bodies shouldBe read.bodies
        }
    }

    test("a hand-edited negative budget is coerced rather than reaching an array allocation") {
        val characters = listOf(character(0f, player = true))
        val read = CollisionSnapshot(bodies = emptyList(), failed = 0)
        val nonsense = OverlaySettings(
            collision = CollisionOverlaySettings(enabled = true, radius = -5f, triangleBudget = -100)
        )

        val view = state(characters, collision = read).overlayScene(nonsense).collision

        assertSoftly {
            // TriangleProjector allocates FloatArray(capacity * 3), so a negative budget throws
            view?.triangleBudget shouldBe 0
            view?.radius shouldBe 0f
        }
    }
})
