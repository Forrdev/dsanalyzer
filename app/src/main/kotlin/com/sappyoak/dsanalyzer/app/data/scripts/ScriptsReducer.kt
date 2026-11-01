package com.sappyoak.dsanalyzer.app.data.scripts

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.title

public fun reduceScripts(
    state: ScriptsState,
    message: ScriptsMessage
): Transition<ScriptsState, ScriptsEffect> = when (message) {
    is ScriptsMessage.Opened -> ScriptsState(installation = message.installation)
        .with(ScriptsEffect.LoadCatalog(message.installation))

    is ScriptsMessage.CatalogLoaded -> {
        val installation = state.installation
        val next = state.copy(scripts = message.scripts)

        if (installation == null || message.scripts.isEmpty()) {
            next.with()
        } else {
            next.copy(indexing = true)
                .with(ScriptsEffect.IndexFlags(installation, message.scripts))
        }
    }


    is ScriptsMessage.ScriptLoaded -> state.accept(message.contents)
    is ScriptsMessage.FlagsIndexed -> state.copy(
        flagIndex = message.index,
        indexing = false,
        unreadable = message.unreadable,
        problem = null
    ).with()

    is ScriptsMessage.ScriptSelected -> state.read(message.script)
    is ScriptsMessage.FlagRequested -> state.findFlag(message.flagId, message.seenIn)
    is ScriptsMessage.QueryChanged -> state.copy(query = message.query).with()
    is ScriptsMessage.Failed -> state.copy(loadingScript = null, problem = message.reason).with()
    is ScriptsMessage.IndexFailed -> state.copy(
        indexing = false,
        problem = "Scripts could not be searched for flags: ${message.reason}"
    ).with()
}

private fun ScriptsState.read(script: ScriptId): Transition<ScriptsState, ScriptsEffect> {
    val installation = installation

    return when {
        script == contents?.script || script == loadingScript -> with()
        installation == null -> copy(problem = "No installation is open").with()
        else -> copy(contents = null, loadingScript = script, problem = null)
            .with(ScriptsEffect.LoadScript(installation, script))
    }
}

private fun ScriptsState.accept(contents: ScriptContents): Transition<ScriptsState, ScriptsEffect> =
    if (contents.script == loadingScript) {
        copy(contents = contents, loadingScript = null).with()
    } else {
        with()
    }

private fun ScriptsState.findFlag(
    flagId: Int,
    seenIn: MapId?
): Transition<ScriptsState, ScriptsEffect> = when {
    flagIndex.built -> {
        val references = flagIndex.explain(flagId, seenIn)
        copy(
            findings = FlagFindings(flagId, references),
            problem = if (references.isEmpty()) nothingNames(flagId) else null
        ).with()
    }

    indexing -> copy(
        findings = null,
        problem = "Reading every script to find flag $flagId"
    ).with()

    installation == null -> copy(problem = "No installation is open").with()

    else -> copy(
        problem = "Scripts could not be searched, so flag $flagId could not be found"
    ).with()
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
