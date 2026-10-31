package com.sappyoak.dsanalyzer.tools.params

import java.nio.file.Files
import java.nio.file.Path

import com.sappyoak.dsanalyzer.formats.param.Param
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamDef
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.files.openGameFiles
import com.sappyoak.dsanalyzer.game.world.params.loadParamDefs
import com.sappyoak.dsanalyzer.game.world.params.loadParamFiles
import com.sappyoak.dsanalyzer.tools.installationAt

private const val GAME_PARAM_FILE = "GameParam.kt"

/** What one installation holds, both keyed by file name without its extension */
internal class EditionParams(val defs: Map<String, ParamDef>, val params: Map<String, Param>)

/** Generated source by file name, plus the cross-edition comparison as console lines */
internal class GeneratedParams(
    val rowClasses: Map<String, String>,
    val gameParams: String,
    val report: String
)

/** Everything the caller chose. Gradle passes all four, so nothing is defaulted here */
private class Options(val roots: Map<GameEdition, Path>, val defsOut: Path, val gameOut: Path)

/**
 * Generates the row classes and the registry from **both** installations in one run
 *
 * One run rather than one per edition, because whether a param can share a row class is only knowable
 * by comparing the two. Running separately and merging afterwards would mean each run guessing at
 * what the other found
 */
public fun main(args: Array<String>) {
    val options = parse(args)
    val editions = EditionPair(GameEdition.PrepareToDie, GameEdition.Remastered)

    val loaded = options.roots.mapValues { (edition, root) -> read(root, edition) }
    val generated = generateParams(loaded, editions)

    Files.createDirectories(options.defsOut)
    generated.rowClasses.forEach { (file, text) -> Files.writeString(options.defsOut.resolve(file), text) }
    Files.createDirectories(options.gameOut)
    Files.writeString(options.gameOut.resolve(GAME_PARAM_FILE), generated.gameParams)

    print(generated.report)
    println(
        "${generated.rowClasses.size} row classes into ${options.defsOut}, " +
                "$GAME_PARAM_FILE into ${options.gameOut}"
    )
}

private fun read(root: Path, edition: GameEdition): EditionParams =
    openGameFiles(installationAt(root, edition)).use { files ->
        EditionParams(
            defs = checkNotNull(files.loadParamDefs()) { "No paramdef.paramdefbnd in $root" },
            params = checkNotNull(files.loadParamFiles()) { "No GameParam.parambnd in $root" }
        )
    }

private fun parse(args: Array<String>): Options {
    val values = args.associate { argument ->
        val separator = argument.indexOf('=')
        require(argument.startsWith("--") && separator > 2) {
            "Expected --key=value arguments, got '$argument'"
        }
        argument.substring(2, separator) to argument.substring(separator + 1)
    }

    fun required(key: String): String = values[key] ?: error("Missing --$key")

    return Options(
        roots = mapOf(
            GameEdition.PrepareToDie to Path.of(required("ptde")),
            GameEdition.Remastered to Path.of(required("dsr"))
        ),
        defsOut = Path.of(required("defs")),
        gameOut = Path.of(required("game"))
    )
}

internal fun generateParams(
    loaded: Map<GameEdition, EditionParams>,
    editions: EditionPair
): GeneratedParams {
    val defStems = loaded.mapValues { (edition, held) -> defStems(edition, held) }
    val used = defStems.values.flatMap { it.values }.distinct().sorted()

    val defsByStem = used.associateWith { stem ->
        loaded.mapNotNull { (edition, held) -> held.defs[stem]?.let { edition to it } }.toMap()
    }
    val comparisons = compareParamDefs(
        byEdition = loaded.mapValues { (_, held) -> held.defs.filterKeys { it in used } },
        editions = editions
    )
    val varies = comparisons.associate { it.stem to it.variance.varies }

    return GeneratedParams(
        rowClasses = used.associate { stem ->
            val className = rowClassName(stem)
            "$className.kt" to emitRowClass(
                className = className,
                defFile = "$stem.paramdef",
                defs = defsByStem.getValue(stem),
                editions = editions,
                varies = varies.getValue(stem)
            )
        },
        gameParams = emitGameParams(registryFiles(defStems, varies), editions),
        report = report(comparisons)
    )
}

/**
 * Every file either installation holds, with the row type each edition reads it through
 *
 * A file present in one edition only contributes one slot, and the other edition's stays null
 */
private fun registryFiles(
    defStems: Map<GameEdition, Map<String, String>>,
    varies: Map<String, Boolean>
): List<GameParamFile> =
    defStems.values.flatMap { it.keys }.distinct().sorted().map { fileStem ->
        val stems = defStems.mapNotNull { (edition, byFile) -> byFile[fileStem]?.let { edition to it } }.toMap()
        val defStem = stems.values.distinct().singleOrNull()
            ?: error("$fileStem reads as ${stems.values.joinToString(" in one edition and ")} in the other")
        val className = rowClassName(defStem)

        GameParamFile(
            stem = fileStem,
            rowClass = className,
            types = stems.keys.associateWith { edition ->
                if (varies.getValue(defStem)) "$className.$edition" else className
            }
        )
    }

/**
 * Each GameParam file paired with the def its type names, refusing one written against another
 * version of that def
 *
 * The row size is only checked where the file can establish it. A param stores an offset per row
 * rather than a size, so with a single row the size has to come from the end of the data and takes
 * trailing padding with it — which is a property of the file, not a disagreement with the def
 */
private fun defStems(edition: GameEdition, held: EditionParams): Map<String, String> {
    val byType = held.defs.entries.associateBy { it.value.paramType }
    return held.params.mapValues { (stem, param) ->
        val (defStem, def) = byType[param.paramType]
            ?: error("$edition: $stem is a ${param.paramType}, and no paramdef declares that type")

        if (param.rows.size > 1) {
            check(def.layout.rowSize == param.rowSize) {
                "$edition: $stem does not match its def: " +
                        "rows of ${param.rowSize} bytes against ${def.layout.rowSize}"
            }
        }

        if (def.dataVersion != param.dataVersion) {
            println(
                " $edition $stem is data version ${param.dataVersion} against its def's " +
                        "${def.dataVersion}; row size agrees at ${param.rowSize}, so the layout is taken as unchanged"
            )
        }

        defStem
    }
}

private fun rowClassName(defStem: String): String = "${defStem}Row"
