package com.sappyoak.dsanalyzer.app.maps.inspect

import com.sappyoak.dsanalyzer.app.maps.MapContents
import com.sappyoak.dsanalyzer.game.world.EntryKind
import com.sappyoak.dsanalyzer.game.world.WorldRef

public data class InspectorRow(
    public val label: String,
    public val value: String,
    public val link: WorldRef? = null
)

public data class InspectorSection(
    public val title: String,
    public val rows: List<InspectorRow>
)

public fun MapContents.inspect(ref: WorldRef.Entry): List<InspectorSection> = when (ref.kind) {
    EntryKind.Model -> modelSections(msb.models[ref.index])
    EntryKind.Event -> eventSections(this, msb.events[ref.index])
    EntryKind.Part -> partSections(this, msb.parts[ref.index])
    EntryKind.Region -> regionSections(msb.regions[ref.index])
}

internal fun rows(build: MutableList<InspectorRow>.() -> Unit): List<InspectorRow> = buildList(build)

internal fun MutableList<InspectorRow>.row(label: String, value: Any?, link: WorldRef? = null) {
    add(InspectorRow(label, value?.toString() ?: "none", link))
}

internal fun MutableList<InspectorRow>.reference(label: String, contents: MapContents, ref: WorldRef.Entry?) {
    val entry = ref?.let(contents::entryAt)
    val shown = entry?.let { it.description?.let { desc -> "${it.name} - $desc" } ?: it.name }
    add(InspectorRow(label, shown ?: "none", ref))
}

internal fun MutableList<InspectorRow>.entityRow(label: String, entityId: Int?) {
    add(InspectorRow(label, entityId?.toString() ?: "none", entityId?.let(WorldRef::Entity)))
}