package com.sappyoak.dsanalyzer.app.scripts

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

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

    is ScriptsMessage.ScriptLoaded -> state.copy(
        contents = message.contents,
        selected = message.contents.script,
        loading = false,
        focused = state.pending ?: state.focused?.takeIf { focused ->
            message.contents.events.any { it.id == focused }
        },
        pending = null
    ).with()

    is ScriptsMessage.ScriptSelected -> state.show(message.script, focus = null)
    is ScriptsMessage.Navigated -> state.show(message.ref.script, focus = message.ref.eventId)
    is ScriptsMessage.FlagRequested -> state.focusFlag(message.flagId)
    is ScriptsMessage.QueryChanged -> state.copy(query = message.query).with()
    is ScriptsMessage.Failed -> state.copy(loading = false, problem = message.reason).with()
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

/**
 * Focuses whichever event in the open script names [flagId]
 */
private fun ScriptsState.focusFlag(flagId: Int): Transition<ScriptsState, ScriptsEffect> {
    val open = contents ?: return copy(problem = "No script is open").with()
    val event = open.eventUsing(flagId)

    return if (event == null) {
        copy(problem = "Flag $flagId is not named by ${open.script.label}").with()
    } else {
        copy(focused = event, problem = null).with()
    }
}