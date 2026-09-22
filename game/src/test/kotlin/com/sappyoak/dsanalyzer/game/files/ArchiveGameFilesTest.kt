package com.sappyoak.dsanalyzer.game.files

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.DeflaterOutputStream
import kotlin.io.path.writeBytes

import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.archiveStems

private const val HEADER_SIZE = 0x18
private const val BUCKET_SIZE = 8
private const val RECORD_SIZE = 16
private const val ALIGNMENT = 16

private val PLAIN = byteArrayOf(1, 2, 3)
private val PACKED = ByteArray(40) { it.toByte() }

/** A BHD5/BDT pair holding [files] in one bucket, each padded to [ALIGNMENT] */
private fun Path.writeArchive(stem: String, files: Map<String, ByteArray>) {
    val data = ByteArrayOutputStream()
    val header = ByteBuffer.allocate(HEADER_SIZE + BUCKET_SIZE + RECORD_SIZE * files.size)
        .order(ByteOrder.LITTLE_ENDIAN)
        .put("BHD5".toByteArray()).put(1).put(0).put(0).put(0)
        .putInt(1).putInt(0).putInt(1).putInt(HEADER_SIZE)
        .putInt(files.size).putInt(HEADER_SIZE + BUCKET_SIZE)

    files.forEach { (path, bytes) ->
        val padded = bytes.copyOf((bytes.size + ALIGNMENT - 1) / ALIGNMENT * ALIGNMENT)
        header.putInt(GamePath.of(path).hash.toInt()).putInt(padded.size).putLong(data.size().toLong())
        data.write(padded)
    }

    resolve("$stem.bhd5").writeBytes(header.array())
    resolve("$stem.bdt").writeBytes(data.toByteArray())
}

/** [bytes] wrapped in a DCX/DFLT container */
private fun dcx(bytes: ByteArray): ByteArray {
    val deflated = ByteArrayOutputStream().also { out -> DeflaterOutputStream(out).use { it.write(bytes) } }
    return ByteBuffer.allocate(0x4C + deflated.size())
        .put("DCX\u0000".toByteArray())
        .position(0x18).put("DCS\u0000".toByteArray()).putInt(bytes.size).putInt(deflated.size())
        .position(0x28).put("DFLT".toByteArray())
        .position(0x44).put("DCA\u0000".toByteArray()).putInt(8)
        .put(deflated.toByteArray())
        .array()
}


private fun archiveInstallation(): Installation {
    val root = Files.createTempDirectory("archived")
    val stems = archiveStems(GameEdition.PrepareToDie)
    root.writeArchive(stems.first(), mapOf("/chr/c0000.esd" to PLAIN, "/event/common.emevd.dcx" to dcx(PACKED)))
    stems.drop(1).forEach { root.writeArchive(it, mapOf("/$it.txt" to PLAIN)) }

    return Installation(
        id = InstallationId("ptde"),
        root = root,
        executable = root.resolve("DARKSOULS.exe"),
        build = GameBuild(GameEdition.PrepareToDie)
    )
}

class ArchiveGameFilesTest : FunSpec({
    test("a plain file opens in place, padding included") {
        ArchiveGameFiles.open(archiveInstallation()).use { files ->
            val reader = files.open(GamePath.of("/CHR/c0000.esd"))!!
            assertSoftly {
                reader.size shouldBe ALIGNMENT.toLong()
                reader.readBytes(PLAIN.size) shouldBe PLAIN
            }
        }
    }

    test("a compressed file opens decompressed") {
        ArchiveGameFiles.open(archiveInstallation()).use { files ->
            files.open(GamePath.of("/event/common.emevd.dcx"))!!.readBytes(PACKED.size) shouldBe PACKED
        }
    }

    test("files are found in every archive, and missing ones are not") {
        ArchiveGameFiles.open(archiveInstallation()).use { files ->
            assertSoftly {
                files.exists(GamePath.of("/dvdbnd3.txt")) shouldBe true
                files.exists(GamePath.of("/chr/c9999.esd")) shouldBe false
                files.open(GamePath.of("/chr/c9999.esd")) shouldBe null
            }
        }
    }
})