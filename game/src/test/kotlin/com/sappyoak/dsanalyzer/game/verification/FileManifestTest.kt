package com.sappyoak.dsanalyzer.game.verification

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.files.GamePath

class FileManifestTest : FunSpec({
    test("each edition's manifest names the files that edition ships") {
        val ptde = loadFileManifest(GameEdition.PrepareToDie)

        assertSoftly {
            ptde.edition shouldBe GameEdition.PrepareToDie
            ptde.paths.size shouldBeGreaterThan 5_000
            ptde.paths shouldContain GamePath.of("/map/mapstudio/m10_02_00_00.msb")
            loadFileManifest(GameEdition.Remastered).paths shouldNotBe ptde.paths
        }
    }

    /**
     * Three features ask for the same manifest when an installation opens, and the files are
     * bundled, so a second ask hands back what the first one read rather than parsing it again
     */
    test("a manifest is read once per edition") {
        assertSoftly {
            GameEdition.entries.forEach { edition ->
                val first = loadFileManifest(edition)
                (loadFileManifest(edition) === first) shouldBe true
            }
        }
    }
})