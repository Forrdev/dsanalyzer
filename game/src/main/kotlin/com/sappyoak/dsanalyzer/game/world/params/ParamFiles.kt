package com.sappyoak.dsanalyzer.game.world.params

import com.sappyoak.dsanalyzer.formats.binder.OpenBinder
import com.sappyoak.dsanalyzer.formats.param.Param
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamDef
import com.sappyoak.dsanalyzer.formats.param.paramdef.readParamDef
import com.sappyoak.dsanalyzer.formats.param.readParam
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.GamePath
import com.sappyoak.dsanalyzer.game.files.openBinder
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

public val GAME_PARAM_PATH: GamePath = GamePath.of("/param/GameParam/GameParam.parambnd.dcx")
public val PARAMDEF_PATH: GamePath = GamePath.of("/paramdef/paramdef.paramdefbnd.dcx")

/** The game's own paramdefs by file name without its extension, 'NpcParam' */
public fun GameFiles.loadParamDefs(): Map<String, ParamDef>? =
    openBinder(PARAMDEF_PATH)?.byStem { readParamDef(it) }

/** Every GameParam file, undecoded, by file name without its extension, 'AtkParam_Npc' */
public fun GameFiles.loadParamFiles(): Map<String, Param>? =
    openBinder(GAME_PARAM_PATH)?.byStem { readParam(it) }

private fun <T> OpenBinder.byStem(read: (BinaryReader) -> T): Map<String, T> =
    binder.entries.associate { entry ->
        entry.name.orEmpty().substringAfterLast('\\').substringBefore('.') to read(open(entry))
    }
