package com.sappyoak.dsanalyzer.app.nav

import com.sappyoak.dsanalyzer.analysis.Citation
import com.sappyoak.dsanalyzer.game.ref.Address
import com.sappyoak.dsanalyzer.game.ref.DataAddress
import com.sappyoak.dsanalyzer.game.ref.DataSpace
import com.sappyoak.dsanalyzer.game.ref.knownSpace
import com.sappyoak.dsanalyzer.game.ref.parseAddress
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

public interface ResolveContext {
    public fun hasMap(map: MapId): Boolean
    public fun hasScript(script: ScriptId): Boolean
    public fun hasParam(file: String): Boolean
    public fun hasEsd(source: EsdSource): Boolean

    public fun entryFor(entityId: Int): View.Data.MapEntry?

    public companion object {
        public val Empty: ResolveContext = object : ResolveContext {
            override fun hasMap(map: MapId): Boolean = false
            override fun hasScript(script: ScriptId): Boolean = false
            override fun hasParam(file: String): Boolean = false
            override fun hasEsd(source: EsdSource): Boolean = false
            override fun entryFor(entityId: Int): View.Data.MapEntry? = null
        }
    }
}

public fun ResolveContext.resolve(citation: Citation): Resolution =
    parseAddress(citation.text)?.let { resolve(it) } ?: Resolution.Malformed(citation.text)

public fun ResolveContext.resolve(address: Address): Resolution =
    if (address is DataAddress) resolveData(address) else Resolution.NoView(address)

private fun ResolveContext.resolveData(address: DataAddress): Resolution =
    when (address.knownSpace) {
        DataSpace.Map -> mapView(address)
        DataSpace.Entity -> entityView(address)
        DataSpace.Flag -> flagView(address)
        DataSpace.Script -> scriptView(address)
        DataSpace.Param -> paramView(address)
        DataSpace.Esd -> esdView(address)
        else -> Resolution.NoView(address)
    }

private fun ResolveContext.mapView(address: DataAddress): Resolution {
    val segments = address.segments
    val map = segments.firstOrNull()?.let(MapId::parse) ?: return Resolution.NoView(address)

    val view: View.Data = when (segments.size) {
        1 -> View.Data.Map(map)
        ENTRY_SEGMENTS -> {
            val kind = entryKindOf(segments[1]) ?: return Resolution.NoView(address)
            val index = segments[2].toIndexOrNull() ?: return Resolution.NoView(address)
            View.Data.MapEntry(map, kind, index)
        }
        else -> return Resolution.NoView(address)
    }

    return if (hasMap(map)) Resolution.Resolved(view) else Resolution.Absent(address, map.toString())
}

private fun ResolveContext.entityView(address: DataAddress): Resolution {
    val id = address.segments.singleOrNull()?.toIndexOrNull() ?: return Resolution.NoView(address)
    return entryFor(id)?.let { Resolution.Resolved(it) } ?: Resolution.Absent(address, "entity $id")
}

private fun ResolveContext.scriptView(address: DataAddress): Resolution {
    val segments = address.segments
    val script = segments.firstOrNull()?.let(::scriptIdOf) ?: return Resolution.NoView(address)

    val view: View.Data = when {
        segments.size == 1 -> View.Data.Script(script)
        segments.size == EVENT_SEGMENTS && segments[1] == EVENT -> {
            val id = segments[2].toEventIdOrNull() ?: return Resolution.NoView(address)
            View.Data.ScriptEvent(script, id)
        }
        else -> return Resolution.NoView(address)
    }

    return if (hasScript(script)) {
        Resolution.Resolved(view)
    } else {
        Resolution.Absent(address, script.token)
    }
}

private fun ResolveContext.paramView(address: DataAddress): Resolution {
    val segments = address.segments
    val file = segments.firstOrNull() ?: return Resolution.Resolved(View.Data.ParamSearch)

    val view: View.Data = when (segments.size) {
        1 -> View.Data.Param(file)
        2 -> View.Data.ParamRow(file, segments[1].toIntOrNull() ?: return Resolution.NoView(address))
        else -> return Resolution.NoView(address)
    }

    return if (hasParam(file)) Resolution.Resolved(view) else Resolution.Absent(address, file)
}

private fun ResolveContext.esdView(address: DataAddress): Resolution {
    val segments = address.segments
    val player = segments.firstOrNull() == PLAYER_ESD
    val source = if (player) {
        EsdSource.Player
    } else {
        val map = segments.firstOrNull()?.let(MapId::parse) ?: return Resolution.NoView(address)
        val talk = segments.getOrNull(1)?.toIndexOrNull() ?: return Resolution.NoView(address)
        EsdSource.Talk(map, talk)
    }

    val rest = segments.drop(if (player) 1 else 2)
    val view: View.Data = when (rest.size) {
        0 -> View.Data.Esd(source)
        2 -> {
            val group = rest[0].toLongOrNull() ?: return Resolution.NoView(address)
            val state = rest[1].toLongOrNull() ?: return Resolution.NoView(address)
            View.Data.EsdState(source, group, state)
        }
        else -> return Resolution.NoView(address)
    }
    val missing = segments.take(if (player) 1 else 2).joinToString("/")
    return if (hasEsd(source)) Resolution.Resolved(view) else Resolution.Absent(address, missing)
}

private fun flagView(address: DataAddress): Resolution {
    val token = address.segments.singleOrNull() ?: return Resolution.NoView(address)
    val at = token.indexOf(SEEN_IN)

    if (at < 0) {
        val id = token.toIndexOrNull() ?: return Resolution.NoView(address)
        return Resolution.Resolved(View.Data.EventFlag(id))
    }

    val id = token.take(at).toIndexOrNull() ?: return Resolution.NoView(address)
    val seenIn = MapId.parse(token.substring(at + 1)) ?: return Resolution.NoView(address)
    return Resolution.Resolved(View.Data.EventFlag(id, seenIn))
}

private const val ENTRY_SEGMENTS = 3
private const val EVENT_SEGMENTS = 3

private fun String.toIndexOrNull(): Int? = toIntOrNull()?.takeIf { it >= 0 }
private fun String.toEventIdOrNull(): Long? = toLongOrNull()?.takeIf { it >= 0 }
