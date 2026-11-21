package com.sappyoak.dsanalyzer.game.world.ai

import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.writeText
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty

import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.files.openGameFiles
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest
import com.sappyoak.dsanalyzer.game.world.maps.availableMaps
import com.sappyoak.dsanalyzer.lua.chunk.LuaChunk
import com.sappyoak.dsanalyzer.lua.chunk.LuaFunction
import com.sappyoak.dsanalyzer.lua.names.functionKey

/** A Lua 5.0 luac with 4-byte instructions; the round trip only runs when LUA50_LUAC points at one */
private val LUAC: Luac? = System.getenv("LUA50_LUAC")?.let { Luac(Path.of(it)) }

/** Each edition runs when its variable points at an installation */
private val EDITIONS = mapOf(
    GameEdition.PrepareToDie to System.getenv("DS1_PTDE_PATH"),
    GameEdition.Remastered to System.getenv("DS1_DSR_PATH")
)

/** How many problems the failure message itself lists; the report file has them all */
private const val SHOWN = 40

/** What a round trip found, and how many stripped functions borrowed every name from the bundled ones */
private class RoundTrip(val problems: List<String>, val borrowed: Int, val stripped: Int)

/**
 * Every script of an installation, decompiled and compiled again with luac, must give back the same functions.
 * Problems are written to `build/reports/lua-round-trip-<edition>.txt`, one per line, after a line counting
 * the stripped functions that borrowed their names
 */
private fun roundTrip(edition: GameEdition, root: Path, luac: Luac): RoundTrip {
    val installation = Installation(
        id = InstallationId.forRoot(root),
        root = root,
        executable = root.resolve(edition.executableName),
        build = GameBuild(edition)
    )
    val chunks = openGameFiles(installation).use { files ->
        val maps = files.availableMaps(loadFileManifest(edition))
        files.availableLua(maps).keys.flatMap { binder ->
            files.loadLuaBinder(binder).map { (name, chunk) -> LuaScriptId(binder, name) to chunk }
        }
    }
    val stripped = chunks.flatMap { (_, chunk) -> chunk.main.all().filter { it.lines.isEmpty() }.map { chunk to it } }
    val borrowed = stripped.count { (chunk, function) -> functionKey(function, chunk.charset) in loadLuaNames().locals }
    return RoundTrip(chunks.flatMap { (script, chunk) -> problems(script, chunk, luac) }, borrowed, stripped.size)
}

private fun LuaFunction.all(): List<LuaFunction> = listOf(this) + functions.flatMap { it.all() }

private fun problems(script: LuaScriptId, chunk: LuaChunk, luac: Luac): List<String> {
    val decompiled = decompileScript(chunk)
    val failures = decompiled.failures.map { "$script: not decompiled: $it" }
    val compared = luac.compile(decompiled, strip = chunk.main.lines.isEmpty()).fold(
        onSuccess = { rebuilt -> differences(chunk.main, rebuilt.main).map { "$script $it" } },
        onFailure = { listOf("$script: does not compile: ${it.message}") }
    )
    return failures + compared
}

class InstalledLuaRoundTripTest : FunSpec({
    EDITIONS.forEach { (edition, path) ->
        test("every $edition script decompiles and compiles back to the same functions")
            .config(enabled = path != null && LUAC != null) {
                val result = roundTrip(edition, Path.of(checkNotNull(path)), checkNotNull(LUAC))
                val problems = result.problems
                val report = Path.of("build", "reports").createDirectories().resolve("lua-round-trip-$edition.txt")
                val borrowed = "${result.borrowed} of ${result.stripped} stripped functions borrowed every name"
                report.writeText((listOf(borrowed) + problems).joinToString("\n"))

                withClue("${problems.size} problems, all in $report:\n" + problems.take(SHOWN).joinToString("\n")) {
                    problems.shouldBeEmpty()
                }
            }
    }
})
