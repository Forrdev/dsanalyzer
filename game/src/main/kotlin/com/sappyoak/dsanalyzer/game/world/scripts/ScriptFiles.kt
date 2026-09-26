package com.sappyoak.dsanalyzer.game.world.scripts

import com.sappyoak.dsanalyzer.formats.emevd.Emevd
import com.sappyoak.dsanalyzer.formats.emevd.readEmevd
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.world.maps.MapId

/** Read a maps event script */
public fun GameFiles.loadEventScript(map: MapId): Emevd? = open(map.eventScriptPath)?.let(::readEmevd)

/** Read the common event script */
public fun GameFiles.loadCommonEventScript(): Emevd? = open(COMMON_EVENT_SCRIPT_PATH)?.let(::readEmevd)