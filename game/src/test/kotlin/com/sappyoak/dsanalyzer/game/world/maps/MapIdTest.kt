package com.sappyoak.dsanalyzer.game.world.maps

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.util.Locale

private val FIRELINK = MapId.of(10, 2)

class MapIdTest : FunSpec({
    test("an id names itself the way the files are named") {
        assertSoftly {
            FIRELINK.name shouldBe "m10_02_00_00"
            MapId.of(18, 1).name shouldBe "m18_01_00_00"
            MapId.of(12, 0, 0, 1).name shouldBe "m12_00_00_01"
        }
    }

    test("the components come back out of the packed id") {
        val id = MapId.of(12, 0, 0, 1)

        assertSoftly {
            id.area shouldBe 12
            id.block shouldBe 0
            id.region shouldBe 0
            id.index shouldBe 1
            id.withoutVariant shouldBe MapId.of(12, 0)
        }
    }

    test("a name parses back to the id that made it") {
        assertSoftly {
            MapId.parse("m10_02_00_00") shouldBe FIRELINK
            MapId.parse("  m10_02_00_00  ") shouldBe FIRELINK
            MapId.parse("m10_2_00_00") shouldBe null
            MapId.parse("m10_02_00_00.msb") shouldBe null
            MapId.parse("firelink") shouldBe null
        }
    }

    test("padding is a minimum rather than a width, so nothing is lost to it") {
        MapId.of(10, 2, 0, 100).name shouldBe "m10_02_00_100"
    }

    /**
     * Every path in the game is built from a map's name, and a format string takes its digits from
     * the default locale, so this is what a user whose locale numbers in anything but ASCII gets
     */
    test("a name is ASCII whatever the default locale numbers in") {
        val original = Locale.getDefault(Locale.Category.FORMAT)
        try {
            Locale.setDefault(Locale.Category.FORMAT, Locale.forLanguageTag("hi-IN-u-nu-deva"))

            assertSoftly {
                // The locale really does renumber, so the assertion below has something to catch
                "%02d".format(2) shouldNotBe "02"
                FIRELINK.name shouldBe "m10_02_00_00"
            }
        } finally {
            Locale.setDefault(Locale.Category.FORMAT, original)
        }
    }
})