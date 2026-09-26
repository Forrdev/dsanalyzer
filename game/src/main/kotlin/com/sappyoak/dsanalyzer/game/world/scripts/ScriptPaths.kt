package com.sappyoak.dsanalyzer.game.world.scripts

import com.sappyoak.dsanalyzer.game.files.GamePath
import com.sappyoak.dsanalyzer.game.world.maps.MapId

/** The script shared by every map */
public val COMMON_EVENT_SCRIPT_PATH: GamePath = GamePath.of("/event/common.emevd.dcx")

/** The names of the common script's events */
public val COMMON_EVENT_NAMES_PATH: GamePath = GamePath.of("/event/common.emeld.dxc")

/** The map's own event script */
public val MapId.eventScriptPath: GamePath get() = GamePath.of("/event/$name.emevd.dcx")
/** The names of the map script's events */
public val MapId.eventNamesPath: GamePath get() = GamePath.of("/event/$name.emeld.dcx")
