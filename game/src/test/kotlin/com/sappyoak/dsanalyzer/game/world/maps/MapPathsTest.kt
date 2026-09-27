package com.sappyoak.dsanalyzer.game.world.maps

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.game.files.GamePath

private val PARISH = checkNotNull(MapId.parse("m10_02_00_00"))

private fun path(value: String) = GamePath.of(value)

class MapPathsTest : FunSpec({
    test("a map's layout is a named file under mapstudio") {
        PARISH.msbPath shouldBe path("/map/mapstudio/m10_02_00_00.msb")
    }

    test("a map's navmeshes are a binder beside it") {
        PARISH.navmeshBinderPath shouldBe path("/map/m10_02_00_00/m10_02_00_00.nvmbnd.dcx")
    }

    test("collision is a split binder named for its detail rather than for the map") {
        assertSoftly {
            PARISH.collisionPaths(CollisionDetail.High).header shouldBe
                    path("/map/m10_02_00_00/h10_02_00_00.hkxbhd")
            PARISH.collisionPaths(CollisionDetail.High).data shouldBe
                    path("/map/m10_02_00_00/h10_02_00_00.hkxbdt")
            PARISH.collisionPaths(CollisionDetail.Low).header shouldBe
                    path("/map/m10_02_00_00/l10_02_00_00.hkxbhd")
        }
    }

    test("a map id survives being written out and read back") {
        assertSoftly {
            MapId.parse("m10_02_00_00") shouldBe PARISH
            PARISH.name shouldBe "m10_02_00_00"
            MapId.parse("m10_02") shouldBe null
        }
    }
})