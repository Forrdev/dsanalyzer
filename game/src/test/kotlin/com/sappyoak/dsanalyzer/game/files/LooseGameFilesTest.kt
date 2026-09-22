package com.sappyoak.dsanalyzer.game.files

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.writeBytes
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val DSR_EXE = "DarkSoulsRemastered.exe"
private const val TEST_CHR = "c0000.esd"
private const val TEST_MAP = "m10_02_00_00.msb"


private fun installation(): Path = Files.createTempDirectory("loose").also { root ->
    root.resolve(DSR_EXE).writeBytes(byteArrayOf(1))
    root.resolve("chr").createDirectories().resolve(TEST_CHR).writeBytes(byteArrayOf(1, 2, 3))
    root.resolve("map").resolve("MapStudio").createDirectories()
        .resolve(TEST_MAP).writeBytes(byteArrayOf(4))
}

private fun GameFiles.readAll(path: String): ByteArray? = open(path)?.run { readBytes(size.toInt()) }

class LooseGameFilesTest : FunSpec({
    test("lists every file under its game path") {
        LooseGameFiles(installation()).listing() shouldBe FileListing.Named(
            setOf("/$DSR_EXE", "/chr/$TEST_CHR", "/map/MapStudio/$TEST_MAP")
                .mapTo(mutableSetOf(), GamePath::of)
        )
    }

    test("reads a file by its game path") {
        LooseGameFiles(installation()).readAll("/chr/$TEST_CHR") shouldBe byteArrayOf(1, 2, 3)
    }

    test("finds a file regardless of its case on disk") {
        LooseGameFiles(installation()).readAll("/map/mapstudio/$TEST_MAP") shouldBe byteArrayOf(4)
    }

    test("a missing file does not exist and opens as null") {
        val files = LooseGameFiles(installation())

        assertSoftly {
            files.exists("/chr/c9999.esd") shouldBe false
            files.open("/chr/c9999.esd") shouldBe null
        }
    }

    test("a path cannot reach outside the installation") {
        val root = installation()
        root.parent.resolve("outside.txt").writeBytes(byteArrayOf(9))
        LooseGameFiles(root).exists("/../outside.txt") shouldBe false
    }
})