package com.sappyoak.dsanalyzer.app.data.maps.inspect

import com.sappyoak.dsanalyzer.app.data.maps.MapContents
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.nav.toDataAddress
import com.sappyoak.dsanalyzer.game.ref.Address
import com.sappyoak.dsanalyzer.game.ref.DataAddress
import com.sappyoak.dsanalyzer.game.ref.DataSpace
import com.sappyoak.dsanalyzer.game.world.maps.EntryKind

public data class InspectorRow(
    public val label: String,
    public val value: String,
    public val link: Address? = null
)

public data class InspectorSection(
    public val title: String,
    public val rows: List<InspectorRow>
)

public fun MapContents.inspect(ref: View.Data.MapEntry): List<InspectorSection> = when (ref.kind) {
    EntryKind.Model -> modelSections(msb.models[ref.index])
    EntryKind.Event -> eventSections(this, msb.events[ref.index])
    EntryKind.Part -> partSections(this, msb.parts[ref.index])
    EntryKind.Region -> regionSections(msb.regions[ref.index])
}

internal fun rows(build: MutableList<InspectorRow>.() -> Unit): List<InspectorRow> = buildList(build)

internal fun MutableList<InspectorRow>.row(label: String, value: Any?, link: Address? = null) {
    add(InspectorRow(label, value?.toString() ?: "none", link))
}

internal fun MutableList<InspectorRow>.reference(label: String, contents: MapContents, ref: View.Data.MapEntry?) {
    val entry = ref?.let(contents::entryAt)
    val shown = entry?.let { it.description?.let { desc -> "${it.name} - $desc" } ?: it.name }
    add(InspectorRow(label, shown ?: "none", ref?.toDataAddress()))
}

internal fun MutableList<InspectorRow>.entityRow(label: String, entityId: Int?) {
    add(InspectorRow(
        label = label,
        value = entityId?.toString() ?: "none",
        link = entityId?.let { DataAddress(DataSpace.Entity.token, listOf(it.toString())) }
    ))
}
