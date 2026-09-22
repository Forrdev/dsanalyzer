package com.sappyoak.dsanalyzer.game

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

/** A folder holding [edition]'s executable plus [paths], created as folders when they have no extension */
private fun installation(edition: GameEdition, paths: List<String>): Path =
    Files.createTempDirectory("installation").also { root ->
        root.resolve(edition.executableName).createFile()
        paths.forEach { path ->
            val target = root.resolve(path)
            if ('.' in path) target.createFile() else target.createDirectories()
        }
    }

class InstallationInspectionTest : FunSpec({
    test("a folder without a game executable is rejected") {
        inspectInstallation(Files.createTempDirectory("empty")).shouldBe(
            InstallationCheck.Rejected(RejectionReason.NoExecutable)
        )
    }

    test("an archived installation with all of its archives is valid") {
        val root = installation(GameEdition.PrepareToDie, requiredPaths(GameEdition.PrepareToDie))

        inspectInstallation(root).shouldBeInstanceOf<InstallationCheck.Valid>()
            .installation.build.edition shouldBe GameEdition.PrepareToDie
    }

    test("an archived installation missing an archive names it") {
        val root = installation(GameEdition.PrepareToDie, requiredPaths(GameEdition.PrepareToDie) - "dvdbnd2.bdt")

        inspectInstallation(root) shouldBe InstallationCheck.Rejected(RejectionReason.MissingFiles(listOf("dvdbnd2.bdt")))
    }

    test("a loose installation with its data folders is valid") {
        val root = installation(GameEdition.Remastered, requiredPaths(GameEdition.Remastered))

        inspectInstallation(root).shouldBeInstanceOf<InstallationCheck.Valid>()
            .installation.build.edition shouldBe GameEdition.Remastered
    }

    test("an executable alone is not a loose installation") {
        val root = installation(GameEdition.Remastered, emptyList())

        inspectInstallation(root) shouldBe InstallationCheck.Rejected(
            RejectionReason.MissingFiles(requiredPaths(GameEdition.Remastered))
        )
    }
})