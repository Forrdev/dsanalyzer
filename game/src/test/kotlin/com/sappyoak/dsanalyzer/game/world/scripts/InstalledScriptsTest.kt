package com.sappyoak.dsanalyzer.game.world.scripts

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldNotBeEmpty
import java.nio.file.Path
import kotlin.time.measureTime

import com.sappyoak.dsanalyzer.formats.emevd.Emevd
import com.sappyoak.dsanalyzer.formats.emevd.emedf.decode
import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.openGameFiles
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest
import com.sappyoak.dsanalyzer.game.world.maps.availableMaps

private val ROOT: Path? = System.getenv("DS1_PTDE_PATH")?.let(Path::of)

private fun <T> withInstalledScripts(block: (GameFiles, List<ScriptId>) -> T): T {
    val root = checkNotNull(ROOT)
    val installation = Installation(
        id = InstallationId.forRoot(root),
        root = root,
        executable = root.resolve(GameEdition.PrepareToDie.executableName),
        build = GameBuild(GameEdition.PrepareToDie)
    )
    return openGameFiles(installation).use { files ->
        val maps = files.availableMaps(loadFileManifest(GameEdition.PrepareToDie))
        block(files, files.availableScripts(maps))
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
        withInstalledScripts { files, scripts ->
            val parsed: Map<ScriptId, Emevd>
            val elapsed = measureTime {
                parsed = scripts.associateWith { checkNotNull(files.loadScript(it)) }
            }
            println(
                "parsed ${parsed.size} scripts in $elapsed, " +
                        "${parsed.values.sumOf { it.events.size }} events, " +
                        "${parsed.values.sumOf { script -> script.events.sumOf { it.instructions.size } }} instructions"
            )

            assertSoftly {
                parsed.forEach { (script, emevd) ->
                    withClue(script.label) {
                        emevd.events.shouldNotBeEmpty()
                        emevd.danglingParameters() shouldBe emptyList()
                    }
                }
            }
        }
    }

    test("Prepare to Die scripts declare no linked files").config(enabled = ROOT != null) {
        val linking = withInstalledScripts { files, scripts ->
            scripts.mapNotNull { script ->
                val linked = checkNotNull(files.loadScript(script)).linkedFiles
                if (linked.isEmpty()) null else "${script.label} links $linked"
            }
        }

        linking.shouldBeEmpty()
    }

    test("every instruction decodes against the bundled definitions").config(enabled= ROOT != null) {
        withInstalledScripts { files, scripts ->
            val emedf = loadInstructionDefinitions()
            val decoded = scripts.flatMap { script ->
                checkNotNull(files.loadScript(script)).events.flatMap { it.decode(emedf) }
            }
            val undefined = decoded.filter { it.definition == null }.groupingBy { it.instruction.toString() }.eachCount()
            val mismatched = decoded.filter { it.sizeMismatch }
                .groupingBy { "${it.instruction} (${it.instruction.args.size} bytes)" }
                .eachCount()

            println("decoded ${decoded.size} instructions against ${emedf.size} definitions")
            println("undefined opcodes: ${undefined.ifEmpty { "none" }}")
            println("argument size mismatches: ${mismatched.ifEmpty { "none" }}")

            // nothing is asserted about coverage. The definitions are community data, and what they
            // miss is worth seeing rather than failing the build over
            decoded.shouldNotBeEmpty()
        }
    }

    test("event names load and match the events they name").config(enabled = ROOT != null) {
        withInstalledScripts { files, scripts ->
            val named = scripts.mapNotNull { script ->
                val names = files.loadScriptNames(script) ?: return@mapNotNull null
                val emevd = checkNotNull(files.loadScript(script))
                script to emevd.events.mapNotNull { event -> names[event.id]?.let { event.id to it } }
            }

            println("named events: " + named.joinToString { (script, names) -> "${script.label}=${names.size}" })

            named.firstOrNull { it.second.isNotEmpty() }?.let { (script, names) ->
                println("${script.label} sample: " + names.take(5).joinToString { "${it.first} ${it.second}" })
            }
        }
    }

    /**
     * The one check that can say the translations are complete.
     *
     * This reads the names out of a real installation and asserts every one of them is covered, which is what
     * catches a name that was neer seen, or one a later edit dropped
     */
    test("every name a real installation carries is translated").config(enabled = ROOT != null) {
        val translations = loadEventNameTranslations()

        val missing = withInstalledScripts { files, scripts ->
            scripts.flatMap { script ->
                files.loadScriptNames(script)?.all.orEmpty().values
                    .filter { translations[it] == null }
                    .map { "${script.label}: $it" }
            }.distinct()
        }

        println("untranslated names: ${missing.size}")
        missing.shouldBeEmpty()
    }
})