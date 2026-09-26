package com.sappyoak.dsanalyzer.game.world.scripts

import com.sappyoak.dsanalyzer.game.files.GamePath
import com.sappyoak.dsanalyzer.game.world.maps.MapId

/** The script shared by every map */

public val COMMON_EVENT_SCRIPT_PATH: GamePath = GamePath.of("/event/common.emevd.dcx")

/** The map's own event script */
public val MapId.eventScriptPath: GamePath get() = GamePath.of("/event/$name.emevd.dcx")

