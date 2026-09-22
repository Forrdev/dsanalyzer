package com.sappyoak.dsanalyzer.game.files

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.writeBytes
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private fun installation(): Path = Files.createTempDirectory("loose").also { root ->
    root.resolve("DarkSoulsRemastered.exe").writeBytes(byteArrayOf(1))
    root.resolve("chr").createDirectories().resolve("c0000.esc").writeBytes(byteArrayOf(1, 2, 3))
}

class LooseGameFilesTest : FunSpec({
    test("lists every file under its game path") {
        LooseGameFiles(installation()).listing() shouldBe FileListing.Named(
            setOf("/DarkSoulsRemastered.exe", "/chr/c0000.esd")
        )
    }

    test("reads a file by its game path") {
        LooseGameFiles(installation()).read("/chr/c0000.esd") shouldBe byteArrayOf(1, 2, 3)
    }

    test("a missing file does not exist and reads as null") {
        val files = LooseGameFiles(installation())

        assertSoftly {
            files.exists("/chr/c9999.esd") shouldBe false
            files.read("/chr/c9999.esd") shouldBe null
        }
    }

    test("a path cannot reach outside the installation") {
        val root = installation()
        root.parent.resolve("outside.txt").writeBytes(byteArrayOf(9))
        LooseGameFiles(root).exists("/../outside.txt") shouldBe false
    }
})