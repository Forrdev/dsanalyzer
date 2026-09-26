package com.sappyoak.dsanalyzer.game.world.scripts

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import java.nio.file.Path
import kotlin.time.measureTime

import com.sappyoak.dsanalyzer.formats.emevd.Emevd
import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.openGameFiles
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.maps.availableMaps

private val ROOT: Path? = System.getenv("DS1_PTDE_PATH")?.let(Path::of)

private fun <T> withInstalledScripts(block: (GameFiles, List<MapId>) -> T): T {
    val root = checkNotNull(ROOT)
    val installation = Installation(
        id = InstallationId.forRoot(root),
        root = root,
        executable = root.resolve(GameEdition.PrepareToDie.executableName),
        build = GameBuild(GameEdition.PrepareToDie)
    )
    return openGameFiles(installation).use { files ->
        block(files, files.availableMaps(loadFileManifest(GameEdition.PrepareToDie)))
    }
}

/** A parameter that copies into an instruction the event does not have */
private fun Emevd.danglingParameters(): List<String> = events.flatMap { event ->
    event.parameters
        .filter { it.instructionIndex !in event.instructions.indices }
        .map { "event ${event.id}: instruction ${it.instructionIndex}" }
}

class InstalledScriptsTest : FunSpec({
    test("the common script and every map's script parse").config(enabled = ROOT != null) {
        withInstalledScripts { files, maps ->
            val scripts: Map<String, Emevd>
            val elapsed = measureTime {
                scripts = buildMap {
                    put("common", checkNotNull(files.loadCommonEventScript()))
                    maps.forEach { map -> put(map.name, checkNotNull(files.loadEventScript(map))) }
                }
            }
            println(
                "parsed ${scripts.size} scripts in $elapsed, " +
                        "${scripts.values.sumOf { it.events.size }} events, " +
                        "${scripts.values.sumOf { script -> script.events.sumOf { it.instructions.size } }} instructions"
            )

            assertSoftly {
                scripts.forEach { (name, script) ->
                    withClue(name) {
                        script.events.shouldNotBeEmpty()
                        script.danglingParameters() shouldBe emptyList()
                    }
                }
            }
        }
    }

    test("a map script links the common script it draws instructions from").config(enabled = ROOT != null) {
        withInstalledScripts { files, maps ->
            val script = checkNotNull(files.loadEventScript(maps.first()))
            println("${maps.first()} links ${script.linkedFiles}")
            script.linkedFiles.shouldNotBeEmpty()
        }
    }
})