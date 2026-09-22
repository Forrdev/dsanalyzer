package com.sappyoak.dsanalyzer.game.verification

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.archive.archivePathHash
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.files.FileListing
import com.sappyoak.dsanalyzer.game.files.GamePath

private fun paths(vararg paths: String): Set<GamePath> = paths.mapTo(mutableSetOf(), GamePath::of)

private val MANIFEST = FileManifest(GameEdition.Remastered, paths("DarkSoulsRemastered.exe", "/chr/c0000.esd"))


class VerificationTest : FunSpec({
    test("named listings compare regardless of case, slashes or a leading slash") {
        verify(MANIFEST, FileListing.Named(paths("/darksoulsremastered.exe", "\\CHR\\c0000.esd"))) shouldBe
                VerificationResult(missing = emptyList(), unidentified = emptyList())
    }

    test("named listings report what is missing and what is extra") {
        verify(MANIFEST, FileListing.Named(paths("/DarkSoulsRemastered.exe", "/steam_appid.txt"))) shouldBe
                VerificationResult(missing = listOf("/chr/c0000.esd"), unidentified = listOf("/steam_appid.txt"))
    }

    test("hashed listings report unmatched hashes as unidentified") {
        val listing = FileListing.Hashed(setOf(archivePathHash("/chr/c0000.esd"), 0x1u))

        verify(MANIFEST, listing) shouldBe VerificationResult(
            missing = listOf("DarkSoulsRemastered.exe"),
            unidentified = listOf("0x00000001")
        )
    }
})