package com.sappyoak.dsanalyzer.game.world.scripts

import com.sappyoak.dsanalyzer.game.files.GamePath

public val ScriptId.scriptPath: GamePath get() = GamePath.of("/event/$label.emevd.dcx")
public val ScriptId.namesPath: GamePath get() = GamePath.of("/event/$label.emeld.dcx")