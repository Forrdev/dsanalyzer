package com.sappyoak.dsanalyzer.game.world.ai

import com.sappyoak.dsanalyzer.formats.binder.BinderEntry
import com.sappyoak.dsanalyzer.formats.binder.OpenBinder
import com.sappyoak.dsanalyzer.formats.luainfo.LuaInfo
import com.sappyoak.dsanalyzer.formats.luainfo.readLuaInfo
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.openBinder
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.lua.chunk.LuaChunk
import com.sappyoak.dsanalyzer.lua.chunk.readLuaChunk
import com.sappyoak.dsanalyzer.lua.decompile.DecompiledChunk
import com.sappyoak.dsanalyzer.lua.decompile.decompile
import com.sappyoak.dsanalyzer.lua.names.LuaNames

private const val LUA_EXTENSION = ".lua"
private const val LUA_INFO_EXTENSION = ".luainfo"

/** The Lua binders this installation has, with the scripts in each, shared ones first */
public fun GameFiles.availableLua(maps: List<MapId>): Map<LuaBinder, List<String>> =
    (listOf(LuaBinder.Common, LuaBinder.Event) + maps.map { LuaBinder.Map(it) })
        .mapNotNull { binder -> openBinder(binder.path)?.let { binder to it.scriptNames() } }
        .filter { (_, names) -> names.isNotEmpty() }
        .toMap()

/** The goals [binder] registers with the engine, or null when it has no `.luainfo` */
public fun GameFiles.loadLuaInfo(binder: LuaBinder): LuaInfo? = openBinder(binder.path)?.let { open ->
    open.binder.entries.firstOrNull { it.name?.endsWith(LUA_INFO_EXTENSION, ignoreCase = true) == true }
        ?.let { entry -> readLuaInfo(open.open(entry)) }
}

public fun GameFiles.loadLua(script: LuaScriptId): LuaChunk? = openBinder(script.binder.path)?.let { binder ->
    binder.binder.entries.firstOrNull { scriptName(it.name) == script.name }?.let { binder.chunk(it) }
}

/** Every script in [binder] at once, which opens the binder only the once */
public fun GameFiles.loadLuaBinder(binder: LuaBinder): Map<String, LuaChunk> =
    openBinder(binder.path)?.let { open ->
        open.binder.entries.mapNotNull { entry -> scriptName(entry.name)?.let { it to open.chunk(entry) } }.toMap()
    }.orEmpty()

/** [chunk] rebuilt as source; a stripped one, as every Prepare to Die script is, borrows the bundled names */
public fun decompileScript(chunk: LuaChunk): DecompiledChunk =
    decompile(chunk, if (chunk.main.lines.isEmpty()) loadLuaNames() else LuaNames.None)

private fun OpenBinder.chunk(entry: BinderEntry): LuaChunk =
    open(entry).let { reader -> readLuaChunk(reader.at(0) { readBytes(size.toInt()) }) }

private fun OpenBinder.scriptNames(): List<String> = binder.entries.mapNotNull { scriptName(it.name) }.sorted()

/** `...\006540_battle.lua` is `006540_battle.lua`; the binders' `.luagnl` and `.luainfo` are not scripts */
private fun scriptName(path: String?): String? =
    path?.substringAfterLast('\\')?.takeIf { it.endsWith(LUA_EXTENSION, ignoreCase = true) }
