package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.sappyoak.dsanalyzer.runtime.session.action.ActionSnapshot

private const val EMPTY_SLOT = -1

/** A character's ESD state and event channel, the same for the player and anyone else */
@Composable
internal fun ActionFields(action: ActionSnapshot, modifier: Modifier = ROW_PADDING) {
    Column(modifier = modifier) {
        Field("ESD state", action.esdStates.withIndex().joinToString("   ") { (group, state) ->
            "$group:${state ?: "-"}"
        })
        Field("Playing action", action.playingAction?.toString() ?: "none")
        action.requestedAction?.let { Field("AI requested", it.toString()) }
        Field("ESD flags", "%08X".format(action.esdFlags))
        Field("Ladder state", action.ladderState?.toString() ?: "off a ladder")
        action.damageAnimation?.let { Field("Last hit", "anim $it, level ${action.damageLevel}") }
        action.eventMessage?.let { message ->
            Field("Event commands", action.eventCommands.joinToString { if (it == EMPTY_SLOT) "-" else it.toString() })
            Field("Event message", if (message == EMPTY_SLOT) "-" else message.toString())
            Field("Event action", action.eventAction?.let { "$it" } ?: "no record")
        }
    }
}
