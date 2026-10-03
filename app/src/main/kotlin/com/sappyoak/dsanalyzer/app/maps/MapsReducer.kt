package com.sappyoak.dsanalyzer.app.maps

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with
import com.sappyoak.dsanalyzer.game.world.WorldRef
import com.sappyoak.dsanalyzer.game.world.maps.MapId

public fun reduceMaps(
    state: MapsState,
    message: MapsMessage
) : Transition<MapsState, MapsEffect> = when (message) {
    is MapsMessage.Opened -> MapsState(installation = message.installation, loading = true)
        .with(MapsEffect.LoadCatalog(message.installation))

    is MapsMessage.CatalogLoaded -> {
        val installation = state.installation
        val first = message.maps.firstOrNull()
        val next = state.copy(maps = message.maps, selected = first, loading = first != null)

        if (installation == null || first == null) {
            next.copy(loading = false).with()
        } else {
            next.with(MapsEffect.LoadMap(installation, first), MapsEffect.IndexEntities(installation, message.maps))
        }
    }

    is MapsMessage.MapLoaded -> state.copy(
        contents = message.contents,
        selected = message.contents.map,
        loading = false,
        focused = state.pending?.takeIf { it.map == message.contents.map } ?: state.focused,
        pending = null
    ).with()

    is MapsMessage.EntitiesIndexed -> state.copy(
        entities = message.index,
        problem = message.skipped.takeIf { it.isNotEmpty() }?.let(::describeSkipped) ?: state.problem
    ).with()

    is MapsMessage.Navigated -> state.navigate(message.ref)

    MapsMessage.BackRequested -> state.step(from = state.back, onto = state.forward) { back, forward ->
        copy(back = back, forward = forward)
    }

    MapsMessage.ForwardRequested -> state.step(from = state.forward, onto = state.back) { forward, back ->
        copy(back = back, forward = forward)
    }

    is MapsMessage.QueryChanged -> state.copy(query = message.query).with()
    is MapsMessage.KindToggled -> state.copy(
        kinds = if (message.kind in state.kinds) state.kinds - message.kind else state.kinds + message.kind
    ).with()

    is MapsMessage.Failed -> state.copy(loading = false, problem = message.reason).with()
}

/** Moves to [ref], remembering where it came from so back and forward work */
private fun MapsState.navigate(ref: WorldRef): Transition<MapsState, MapsEffect> = when (ref) {
    is WorldRef.Map -> show(ref.map, focus = null)
    is WorldRef.Entry -> show(ref.map, focus = ref)
    is WorldRef.Entity -> entities[ref.entityId].firstOrNull()
        ?.let { show(it.map, focus = it) }
        ?: copy(problem = "No entry has entity id ${ref.entityId}").with()
    is WorldRef.ScriptEvent -> with()
    is WorldRef.EventFlag -> with()
}

private fun MapsState.show(
    map: MapId,
    focus: WorldRef.Entry?
): Transition<MapsState, MapsEffect> {
    val history = focused?.let { copy(back = back + it, forward = emptyList()) } ?: copy(forward = emptyList())
    val installation = installation

    return when {
        map == selected && contents != null -> history.copy(focused = focus, problem = null).with()
        installation == null -> history.copy(problem = "No installation is open").with()
        else -> history.copy(
            selected = map,
            contents = null,
            loading = true,
            focused = null,
            pending = focus,
            problem = null
        ).with(MapsEffect.LoadMap(installation, map))
    }
}

/** Pops [from] and pushes what is focused now onto [onto] */
private inline fun MapsState.step(
    from: List<WorldRef.Entry>,
    onto: List<WorldRef.Entry>,
    stacks: MapsState.(List<WorldRef.Entry>, List<WorldRef.Entry>) -> MapsState
): Transition<MapsState, MapsEffect> {
    val target = from.lastOrNull() ?: return with()
    val moved = stacks(from.dropLast(1), focused?.let { onto + it } ?: onto)
    val installation = moved.installation

    return when {
        target.map == moved.selected && moved.contents != null -> moved.copy(focused = target).with()
        installation == null -> moved.with()
        else -> moved.copy(
            selected = target.map,
            contents = null,
            loading = true,
            focused = null,
            pending = target
        ).with(MapsEffect.LoadMap(installation, target.map))
    }
}

private fun describeSkipped(maps: List<MapId>): String =
    "No layout file for ${maps.joinToString()}, so entity search will not cover " + if (maps.size == 1) "it" else "them"