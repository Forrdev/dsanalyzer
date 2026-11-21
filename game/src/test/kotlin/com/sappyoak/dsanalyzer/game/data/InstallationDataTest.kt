package com.sappyoak.dsanalyzer.game.data

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import kotlinx.coroutines.test.runTest

class InstallationDataTest : FunSpec({
    test("the same installation shares its data") {
        runTest {
            val data = InstallationData(noFiles(backgroundScope), backgroundScope)
            data.of(installation()) shouldBeSameInstanceAs data.of(installation())
        }
    }

    test("an installation that moved gets new data") {
        runTest {
            val data = InstallationData(noFiles(backgroundScope), backgroundScope)
            val before = data.of(installation())
            data.of(installation(root = "moved")) shouldNotBeSameInstanceAs before
        }
    }

    test("a retired installation gets new data") {
        runTest {
            val data = InstallationData(noFiles(backgroundScope), backgroundScope)
            val before = data.of(installation())
            data.retire(before.installation.id)
            data.of(installation()) shouldNotBeSameInstanceAs before
        }
    }
})
