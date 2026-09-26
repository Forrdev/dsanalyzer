package com.sappyoak.dsanalyzer.game.world.scripts

import com.sappyoak.dsanalyzer.formats.emevd.Emevd
import com.sappyoak.dsanalyzer.formats.emevd.EventNames
import com.sappyoak.dsanalyzer.formats.emevd.readEmeld
import com.sappyoak.dsanalyzer.formats.emevd.readEmevd
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.world.maps.MapId

/** The compiled events of one script */
public fun GameFiles.loadScript(script: ScriptId): Emevd? = open(script.scriptPath)?.let(::readEmevd)

/** The names of that script's events */
public fun GameFiles.loadScriptNames(script: ScriptId): EventNames? = open(script.namesPath)?.let(::readEmeld)

/** Every script this installation has */
public fun GameFiles.availableScripts(maps: List<MapId>): List<ScriptId> =
    (listOf(ScriptId.Common) + maps.map(ScriptId::Of)).filter { exists(it.scriptPath) }