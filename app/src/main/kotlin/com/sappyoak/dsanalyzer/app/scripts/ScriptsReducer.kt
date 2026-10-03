package com.sappyoak.dsanalyzer.app.scripts

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.title

public fun reduceScripts(
    state: ScriptsState,
    message: ScriptsMessage
): Transition<ScriptsState, ScriptsEffect> = when (message) {
    is ScriptsMessage.Opened -> ScriptsState(installation = message.installation, loading = true)
        .with(ScriptsEffect.LoadCatalog(message.installation))

    is ScriptsMessage.CatalogLoaded -> {
        val installation = state.installation
        val first = message.scripts.firstOrNull()
        val next = state.copy(scripts = message.scripts, selected = first, loading = first != null)

        if (installation == null || first == null) {
            next.copy(loading = false).with()
        } else {
            next.with(ScriptsEffect.LoadScript(installation, first))
        }
    }

    is ScriptsMessage.ScriptLoaded -> state.loaded(message.contents)

    is ScriptsMessage.ScriptSelected -> state.show(message.script, focus = null)
    is ScriptsMessage.Navigated -> state.show(message.ref.script, focus = message.ref.eventId)
    is ScriptsMessage.FlagRequested -> state.findFlag(message.flagId, message.seenIn)
    is ScriptsMessage.QueryChanged -> state.copy(query = message.query).with()
    is ScriptsMessage.Failed -> state.copy(loading = false, pendingFlag = null, problem = message.reason).with()
}

private fun ScriptsState.loaded(loadedContents: ScriptContents): Transition<ScriptsState, ScriptsEffect> {
    val next = copy(
        contents = loadedContents,
        selected = loadedContents.script,
        loading = false,
        focused = pending ?: focused?.takeIf { focus -> loadedContents.events.any { it.id == focus } },
        pending = null
    )

    val search = pendingFlag ?: return next.with()
    return next.searchFor(search)
}

private fun ScriptsState.show(script: ScriptId, focus: Long?): Transition<ScriptsState, ScriptsEffect> = when {
    script == selected && contents != null -> copy(focused = focus ?: focused, problem = null).with()
    installation == null -> copy(problem = "No installation is open").with()
    else -> copy(
        selected = script,
        contents = null,
        loading = true,
        focused = null,
        pending = focus,
        problem = null
    ).with(ScriptsEffect.LoadScript(installation, script))
}

private fun ScriptsState.findFlag(flagId: Int, seenIn: MapId?): Transition<ScriptsState, ScriptsEffect> {
    val places = placesFor(seenIn)
    if (places.isEmpty()) return copy(problem = "No script is open").with()

    return searchFor(FlagSearch(flagId, places))
}

private fun ScriptsState.placesFor(seenIn: MapId?): List<ScriptId> {
    if (seenIn == null) return listOfNotNull(selected)

    val wanted = listOf(ScriptId.Of(seenIn), ScriptId.Common)
    return if (scripts.isEmpty()) wanted else wanted.filter { it in scripts }
}

/**
 * Looks in the next place the search has left, loading that script first if it was not
 * the one already open
 */
private fun ScriptsState.searchFor(search: FlagSearch): Transition<ScriptsState, ScriptsEffect> {
    val next = search.next ?: return copy(pendingFlag = null, problem = search.missed()).with()

    if (next == selected && contents != null) {
        val event = contents.eventUsing(search.flagId)
        return if (event == null) {
            searchFor(search.advanced)
        } else {
            copy(focused = event, pendingFlag = null, problem = null).with()
        }
    }

    if (installation == null) {
        return copy(pendingFlag = null, problem = "No installation is open").with()
    }

    return copy(
        selected = next,
        contents = null,
        loading = true,
        focused = null,
        pending = null,
        pendingFlag = search,
        problem = null
    ).with(ScriptsEffect.LoadScript(installation, next))
}

private fun FlagSearch.missed(): String =
    "Flag $flagId is not named by ${places.joinToString(" or ") { it.title }}"