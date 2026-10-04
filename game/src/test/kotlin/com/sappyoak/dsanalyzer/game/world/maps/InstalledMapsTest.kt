package com.sappyoak.dsanalyzer.game.world.maps

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotBeEmpty
import java.nio.file.Path

import com.sappyoak.dsanalyzer.formats.msb.MSB
import com.sappyoak.dsanalyzer.formats.msb.part.Part
import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.openBinder
import com.sappyoak.dsanalyzer.game.files.openGameFiles
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest

private const val PTDE_MAP_COUNT = 17

/** Runs against a real PTDE installation, and only when DS1_PTDE_PATH points at one */
private val ROOT: Path? = System.getenv("DS1_PTDE_PATH")?.let(Path::of)

private fun <T> withInstalledMaps(block: (GameFiles, List<MapId>) -> T): T {
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

/** Every reference in the map that does not land inside the list it counts through */
private fun MSB.danglingReferences(): List<String> = parts.flatMap { part ->
    val enemy = (part as? Part.Enemy)?.data ?: (part as? Part.DummyEnemy)?.data
    val drawParent = (part as? Part.Object)?.data?.drawParent
        ?: (part as? Part.DummyObject)?.data?.drawParent
        ?: enemy?.drawParent

    val references = listOfNotNull(
        part.header.model?.let { "model" to (it.value in models.indices) },
        drawParent?.let { "draw parent" to (it.value in parts.indices) },
        (part as? Part.ConnectCollision)?.collision?.let { "collision" to (it.value in collisions.indices) }
    ) + enemy?.movePoints.orEmpty().filterNotNull().map { "move point" to (it.value in regions.indices) }

    references.filterNot { it.second }.map { "${part.header.name}: ${it.first}" }
}

class InstalledMapsTest : FunSpec({
    test("every map's MSB parses and every reference lands inside its list").config(enabled = ROOT != null) {
        withInstalledMaps { files, maps ->
            maps shouldHaveSize PTDE_MAP_COUNT
            assertSoftly {
                maps.forEach { map ->
                    withClue(map) { checkNotNull(files.loadMSB(map)).danglingReferences().shouldBeEmpty() }
                }
            }
        }
    }

    test("every map's collision binders open at both levels of detail").config(enabled = ROOT != null) {
        withInstalledMaps { files, maps ->
            assertSoftly {
                maps.forEach { map ->
                    CollisionDetail.entries.forEach { detail ->
                        withClue("$map $detail") {
                            checkNotNull(files.openBinder(map.collisionPaths(detail))).binder.entries.shouldNotBeEmpty()
                        }
                    }
                }
            }
        }
    }
})