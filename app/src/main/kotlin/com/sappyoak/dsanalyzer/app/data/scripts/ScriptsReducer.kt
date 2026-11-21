package com.sappyoak.dsanalyzer.app.data.scripts

import com.sappyoak.dsanalyzer.app.data.flags.FlagCatalog
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
                .with(ScriptsEffect.IndexScripts(installation))
        }
    }


    is ScriptsMessage.ScriptLoaded -> state.accept(message.contents)
    is ScriptsMessage.ScriptsIndexed -> state.copy(
        flagIndex = message.flags,
        callIndex = message.calls,
        flagCatalog = FlagCatalog.of(state.flagNames, state.flagSites, message.flags),
        indexing = false,
        unreadable = message.unreadable,
        problem = null
    ).let { next ->
        next.installation?.let { next.copy(naming = true).with(ScriptsEffect.ReadFlags(it)) }
            ?: next.with()
    }

    is ScriptsMessage.FlagsRead -> state.copy(
        flagNames = message.reading.names,
        flagSites = message.reading.sites,
        flagCatalog = FlagCatalog.of(message.reading.names, message.reading.sites, state.flagIndex),
        naming = false,
        namingProblem = null
    ).with()
    is ScriptsMessage.NamingFailed -> state.copy(
        naming = false,
        namingProblem = "Flags could not be named: ${message.reason}"
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
        copy(findings = FlagFindings(flagId, references), problem = null).with()
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

/**
 * What the script search could not see, so finding nothing is not read as certain: references whose flag
 * comes from a caller none of the scripts start, and scripts that did not parse. Null when it saw everything
 */
internal fun ScriptsState.searchGaps(): String? {
    val gaps = listOfNotNull(
        flagIndex.parameterized.takeIf { it > 0 }?.let {
            "$it flag reference${plural(it)} from callers that could not be followed"
        },
        unreadable.takeIf { it.isNotEmpty() }?.let { scripts -> "could not read ${scripts.joinToString { it.title }}" }
    )
    return if (gaps.isEmpty()) null else "Searched ${flagIndex.scripts} scripts; ${gaps.joinToString("; ")}"
}

private fun plural(count: Int): String = if (count == 1) "" else "s"
