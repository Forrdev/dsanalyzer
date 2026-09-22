package com.sappyoak.dsanalyzer.game

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.FileTime
import kotlin.io.path.createDirectories
import kotlin.io.path.setLastModifiedTime
import kotlin.io.path.writeBytes

private val STAMP = FileTime.fromMillis(1_700_000_000_000)
private val LOOSE_FILES = listOf("chr/c0000.esd", "map/m10_00_00_00/h0000B0.hkx")

/** Writes [bytes] to [path] under [root] with a fixed modification time, so only real edits change stamps */
private fun write(root: Path, path: String, bytes: ByteArray = byteArrayOf(1)) {
    val file = root.resolve(path)
    file.parent.createDirectories()
    file.writeBytes(bytes)
    file.setLastModifiedTime(STAMP)
}

private fun installation(edition: GameEdition, files: List<String>): Installation {
    val root = Files.createTempDirectory("fingerprint")
    (listOf(edition.executableName) + files).forEach { write(root, it) }
    return Installation(InstallationId("test"), root, root.resolve(edition.executableName), GameBuild(edition))
}

class InstallationFingerprintTest : FunSpec({
    test("an untouched installation fingerprints the same every time") {
        val installation = installation(GameEdition.Remastered, LOOSE_FILES)

        installation.fingerprint() shouldBe installation.fingerprint()
    }

    test("a loose installation notices a file edited deep in its tree") {
        val installation = installation(GameEdition.Remastered, LOOSE_FILES)
        val before = installation.fingerprint()

        write(installation.root, "map/m10_00_00_00/h0000B0.hkx", byteArrayOf(1, 2))

        installation.fingerprint() shouldNotBe before
    }

    test("a loose installation notices a file added deep in its tree") {
        val installation = installation(GameEdition.Remastered, LOOSE_FILES)
        val before = installation.fingerprint()

        write(installation.root, "map/m10_00_00_00/h0001B0.hkx")

        installation.fingerprint().fileCount shouldBe before.fileCount + 1
    }

    test("an archived installation notices a rewritten header, even with its size and time unchanged") {
        val installation = installation(GameEdition.PrepareToDie, requiredPaths(GameEdition.PrepareToDie))
        val before = installation.fingerprint()

        write(installation.root, "dvdbnd0.bhd5", byteArrayOf(2))

        installation.fingerprint() shouldNotBe before
    }

    test("an archived installation notices a changed archive") {
        val installation = installation(GameEdition.PrepareToDie, requiredPaths(GameEdition.PrepareToDie))
        val before = installation.fingerprint()

        write(installation.root, "dvdbnd1.bdt", byteArrayOf(1, 2))

        installation.fingerprint() shouldNotBe before
    }
})