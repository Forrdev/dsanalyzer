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
            next.copy(indexing = true).with(
                ScriptsEffect.LoadScript(installation, first),
                ScriptsEffect.IndexFlags(installation, message.scripts)
            )
        }
    }

    is ScriptsMessage.ScriptLoaded -> state.loaded(message.contents)
    is ScriptsMessage.FlagsIndexed -> state.indexed(message.index, message.unreadable)

    is ScriptsMessage.ScriptSelected -> state.show(message.script, focus = null)
    is ScriptsMessage.Navigated -> state.show(message.ref.script, focus = message.ref.eventId)
    is ScriptsMessage.FlagRequested -> state.findFlag(message.flagId, message.seenIn)
    is ScriptsMessage.QueryChanged -> state.copy(query = message.query).with()
    is ScriptsMessage.Failed -> state.copy(loading = false, pendingFlag = null, problem = message.reason).with()
}

private fun ScriptsState.loaded(loadedContents: ScriptContents): Transition<ScriptsState, ScriptsEffect> = copy(
    contents = loadedContents,
    selected = loadedContents.script,
    loading = false,
    focused = pending ?: focused?.takeIf { focus -> loadedContents.events.any { it.id == focus } },
    pending = null
).with()

private fun ScriptsState.indexed(
    index: FlagIndex,
    unreadableScripts: List<ScriptId>
): Transition<ScriptsState, ScriptsEffect> {
    val next = copy(flagIndex = index, indexing = false, unreadable = unreadableScripts)
    val waiting = pendingFlag ?: return next.with()

    return next.showFlag(waiting.flagId, waiting.seenIn)
}

private fun ScriptsState.show(script: ScriptId, focus: Long?): Transition<ScriptsState, ScriptsEffect> = when {
    script == selected && contents != null -> copy(focused = focus ?: focused, pendingFlag = null, problem = null).with()
    installation == null -> copy(problem = "No installation is open").with()
    else -> copy(
        selected = script,
        contents = null,
        loading = true,
        focused = null,
        pending = focus,
        pendingFlag = null,
        problem = null
    ).with(ScriptsEffect.LoadScript(installation, script))
}

private fun ScriptsState.findFlag(flagId: Int, seenIn: MapId?): Transition<ScriptsState, ScriptsEffect> = when {
    flagIndex.built -> showFlag(flagId, seenIn)
    indexing -> copy(
        pendingFlag = FlagRequest(flagId, seenIn),
        findings = null,
        problem = "Reading every script to find flag $flagId"
    ).with()
    else -> copy(problem = "No installation is open").with()
}

private fun ScriptsState.showFlag(flagId: Int, seenIn: MapId?): Transition<ScriptsState, ScriptsEffect> {
    val references = flagIndex.explain(flagId, seenIn)
    val chosen = references.firstOrNull()
    val found = FlagFindings(flagId, references, chosen)

    if (chosen == null) {
        return copy(
            pendingFlag = null,
            findings = found,
            problem = nothingNames(flagId)
        ).with()
    }

    return copy(
        pendingFlag = null,
        findings = found
    ).show(chosen.script, focus = chosen.eventId)
}

private fun ScriptsState.nothingNames(flagId: Int): String = buildString {
    append("No script names flag $flagId")
    append(" (searched ${flagIndex.scripts})")

    if (flagIndex.parameterized > 0) {
        append(", though ${flagIndex.parameterized} flag references are passed in by their")
        append(" callers and could not be read")
    }

    if (unreadable.isNotEmpty()) {
        append(". Could not read ${unreadable.joinToString { it.title }}")
    }
}