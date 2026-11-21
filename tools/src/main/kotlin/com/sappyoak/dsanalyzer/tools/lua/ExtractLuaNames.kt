package com.sappyoak.dsanalyzer.tools.lua

import java.nio.file.Files
import java.nio.file.Path
import kotlinx.serialization.json.Json

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.files.openGameFiles
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest
import com.sappyoak.dsanalyzer.game.world.ai.LuaNamesFile
import com.sappyoak.dsanalyzer.game.world.ai.availableLua
import com.sappyoak.dsanalyzer.game.world.ai.loadLuaBinder
import com.sappyoak.dsanalyzer.game.world.maps.availableMaps
import com.sappyoak.dsanalyzer.lua.names.collectNames
import com.sappyoak.dsanalyzer.tools.installationAt

/**
 * Writes the parameter and local names Remastered's Lua scripts keep, which Prepare to Die's were stripped
 * of, as the bundled `ds1-lua-names.json`. Run again only when the decompiler reads debug info differently
 *
 *    ./gradlew :tools:extractLuaNames -Pdsr=<installation> [-Pout=<file>]
 */
public fun main(args: Array<String>) {
    val (root, out) = args.map(Path::of)
    val edition = GameEdition.Remastered
    val chunks = openGameFiles(installationAt(root, edition)).use { files ->
        val maps = files.availableMaps(loadFileManifest(edition))
        files.availableLua(maps).keys.flatMap { binder -> files.loadLuaBinder(binder).toSortedMap().values }
    }
    val names = collectNames(chunks)

    Files.createDirectories(out.parent)
    Files.writeString(out, Json.encodeToString(LuaNamesFile.of(names)))
    println("${names.locals.size} functions and ${names.parameters.size} global parameter lists into $out")
}
