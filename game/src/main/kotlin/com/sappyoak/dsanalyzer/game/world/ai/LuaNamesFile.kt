package com.sappyoak.dsanalyzer.game.world.ai

import java.security.MessageDigest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

import com.sappyoak.dsanalyzer.game.bundledText
import com.sappyoak.dsanalyzer.lua.chunk.LocalVariable
import com.sappyoak.dsanalyzer.lua.names.LuaNames

private const val RESOURCE = "/definitions/ds1-lua-names.json"

/**
 * [LuaNames] as bundled: Remastered's scripts keep the debug info Prepare to Die's were stripped of. Each
 * local is written `<startPc> <endPc> <name>`, name last since loop internals like `(for limit)` hold a space
 */
@Serializable
public class LuaNamesFile(
    public val locals: Map<String, List<String>>,
    public val parameters: Map<String, List<String>>
) {
    public fun toNames(): LuaNames = LuaNames(locals.mapValues { (_, list) -> list.map(::local) }, parameters)

    public companion object {
        public fun of(names: LuaNames): LuaNamesFile = LuaNamesFile(
            names.locals.mapValues { (_, list) -> list.map { "${it.startPc} ${it.endPc} ${it.name}" } }.toSortedMap(),
            names.parameters.toSortedMap()
        )
    }
}

private fun local(written: String): LocalVariable {
    val (start, end, name) = written.split(' ', limit = 3)
    return LocalVariable(name, start.toInt(), end.toInt())
}

private val NAMES: LuaNames by lazy { Json.decodeFromString<LuaNamesFile>(bundledText(RESOURCE)).toNames() }

/** The bundled names for Prepare to Die's stripped scripts */
public fun loadLuaNames(): LuaNames = NAMES

private val DIGEST: String by lazy {
    MessageDigest.getInstance("SHA-256").digest(bundledText(RESOURCE).toByteArray()).toHexString()
}

/** A digest of the bundled names, which change what stripped scripts decompile to */
internal fun luaNamesDigest(): String = DIGEST
