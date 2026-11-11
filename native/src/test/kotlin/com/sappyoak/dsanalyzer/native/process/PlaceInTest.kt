package com.sappyoak.dsanalyzer.native.process

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val SIZE = 0x1000L

class PlaceInTest : FunSpec({
    test("a block above goes at the first step of the free range") {
        placeIn(0x1_4300_1000, 0x1_5000_0000, SIZE, 0xD000_0000, 0x1_B000_0000, fromTop = false) shouldBe 0x1_4301_0000
    }

    test("a block below goes at the last step that still holds it") {
        placeIn(0x1_2000_0000, 0x1_3FFF_8000, SIZE, 0xD000_0000, 0x1_B000_0000, fromTop = true) shouldBe 0x1_3FFF_0000
    }

    test("a free range is clipped to what can be reached") {
        placeIn(0x1000_0000, 0x1_0000_0000, SIZE, 0xD000_0000, 0x1_B000_0000, fromTop = false) shouldBe 0xD000_0000
        placeIn(0x1_A000_0000, 0x2_0000_0000, SIZE, 0xD000_0000, 0x1_B000_0000, fromTop = true) shouldBe 0x1_AFFF_0000
    }

    test("a range with no whole step inside it is passed over") {
        placeIn(0x1_4300_1000, 0x1_4300_F000, SIZE, 0xD000_0000, 0x1_B000_0000, fromTop = false) shouldBe null
        placeIn(0x1000_0000, 0x2000_0000, SIZE, 0xD000_0000, 0x1_B000_0000, fromTop = true) shouldBe null
    }
})
