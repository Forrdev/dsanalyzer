package com.sappyoak.dsanalyzer.game.world.maps

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

private val MODELS = loadCharacterModels()

class CharacterModelsTest : FunSpec({
    test("the bundled models load") {
        MODELS.size shouldBeGreaterThan 150
    }

    test("a model number reads as what it is") {
        assertSoftly {
            MODELS[2230] shouldBe "Stray Demon"
            MODELS[2320] shouldBe "Iron Golem"
            MODELS[0] shouldBe "Player Character"
        }
    }

    test("a name taken out of a map describes itself") {
        assertSoftly {
            MODELS.describe("c2230") shouldBe "Stray Demon"
            MODELS.describe("C2230") shouldBe "Stray Demon"
            MODELS.describe(" c2320 ") shouldBe "Iron Golem"
        }
    }

    test("a name that is not a character model describes nothing") {
        assertSoftly {
            MODELS.describe("m1000B1") shouldBe null
            MODELS.describe("h0017B1") shouldBe null
            MODELS.describe("c9999") shouldBe null
            MODELS.describe("") shouldBe null
        }
    }
})