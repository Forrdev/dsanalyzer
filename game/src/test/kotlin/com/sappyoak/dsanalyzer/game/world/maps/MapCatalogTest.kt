package com.sappyoak.dsanalyzer.game.world.maps

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.game.files.GamePath
import io.kotest.matchers.should

private val DARKROOT = MapId.of(12, 0)
private val DARKROOT_LAYOUT = checkNotNull(MapId.parse("m12_00_00_01"))
private val PARISH = MapId.of(10, 1)

class MapCatalogTest : FunSpec({
    test("every map the game ships is named") {
        namedMaps.size shouldBe 17
    }

    test("an area covering two places is named for both") {
        assertSoftly {
            PARISH.label shouldBe "Undead Burg / Parish"
            MapId.of(13, 2).label shouldBe "Great Hollow / Ash Lake"
            MapId.of(10, 2).label shouldBe "Firelink Shrine"
        }
    }

    test("a map the table does not name falls back to its id") {
        checkNotNull(MapId.parse("m99_00_00_00")).label shouldBe "m99_00_00_00"
    }

    test("an id the game does not ship is not a place") {
        assertSoftly {
            MapId.of(255, 255).exists shouldBe false
            MapId.of(99, 0).exists shouldBe false
            PARISH.exists shouldBe true
            DARKROOT_LAYOUT.exists shouldBe true
        }
    }

    test("Darkroot Garden's layout is not the file its id names") {
        assertSoftly {
            DARKROOT.msbName shouldBe "m12_00_00_01"
            DARKROOT.msbPath shouldBe GamePath.of("/map/mapstudio/m12_00_00_01.msb")
            PARISH.msbPath shouldBe GamePath.of("/map/mapstudio/m10_01_00_00.msb")
        }
    }

    test("both of Darkroot's ids are the one area, so a listing offers it once") {
        assertSoftly {
            DARKROOT_LAYOUT.canonical shouldBe DARKROOT
            DARKROOT.canonical shouldBe DARKROOT
            listOf(DARKROOT, DARKROOT_LAYOUT).map { it.canonical }.distinct() shouldBe listOf(DARKROOT)
        }
    }

    test("an id nothing claims as an alternate layout is already canonical") {
        val unknown = checkNotNull(MapId.parse("m99_00_00_00"))
        assertSoftly {
            PARISH.canonical shouldBe PARISH
            unknown.canonical shouldBe unknown
        }
    }

    test("everything else is still addressed by its own id") {
        assertSoftly {
            DARKROOT.navmeshBinderPath shouldBe GamePath.of("/map/m12_00_00_00/m12_00_00_00.nvmbnd.dcx")
            DARKROOT.collisionPaths(CollisionDetail.High).header shouldBe
                    GamePath.of("/map/m12_00_00_00/h12_00_00_00.hkxbhd")
        }
    }
})