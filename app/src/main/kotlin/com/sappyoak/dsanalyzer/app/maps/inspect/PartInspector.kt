package com.sappyoak.dsanalyzer.app.maps.inspect

import com.sappyoak.dsanalyzer.app.maps.MapContents
import com.sappyoak.dsanalyzer.formats.msb.PartIndex
import com.sappyoak.dsanalyzer.formats.msb.RegionIndex
import com.sappyoak.dsanalyzer.formats.msb.part.EnemyData
import com.sappyoak.dsanalyzer.formats.msb.part.ObjectData
import com.sappyoak.dsanalyzer.formats.msb.part.Part
import com.sappyoak.dsanalyzer.formats.msb.part.hitFilter
import com.sappyoak.dsanalyzer.formats.msb.part.placeNameId
import com.sappyoak.dsanalyzer.formats.msb.part.playRegionId
import com.sappyoak.dsanalyzer.formats.msb.part.stableFootingFlag
import com.sappyoak.dsanalyzer.game.world.EntryKind
import com.sappyoak.dsanalyzer.game.world.WorldRef

internal fun partSections(contents: MapContents, part: Part): List<InspectorSection> = buildList {
    add(InspectorSection("Placement", placementRows(contents, part)))

    when (part) {
        is Part.Object -> add(InspectorSection("Object", objectRows(contents, part.data)))
        is Part.DummyObject -> add(InspectorSection("Object", objectRows(contents, part.data)))
        is Part.Enemy -> add(InspectorSection("Enemy", enemyRows(contents, part.data)))
        is Part.DummyEnemy -> add(InspectorSection("Enemy", enemyRows(contents, part.data)))
        is Part.Collision -> add(InspectorSection("Collision", collisionRows(contents, part)))
        is Part.Navmesh -> add(InspectorSection("Navmesh", rows { row("Navmesh groups", part.navmeshGroups) }))
        is Part.ConnectCollision -> add(InspectorSection("Connection", connectRows(contents, part)))
        is Part.MapPiece, is Part.Player -> Unit
    }
}
private fun placementRows(contents: MapContents, part: Part) = rows {
    val header = part.header
    row("Name", header.name)
    row("Type", part::class.simpleName)
    reference("Model", contents, header.model?.let { contents.ref(EntryKind.Model, it.value) })
    entityRow("Entity id", header.entityId)
    row("Position", header.position)
    row("Rotation", header.rotation)
    row("Scale", header.scale)
    row("Draw groups", header.drawGroups)
    row("Display groups", header.displayGroups)
}

private fun objectRows(contents: MapContents, data: ObjectData) = rows {
    reference("Draw parent", contents, contents.part(data.drawParent))
    row("Break term", data.breakTerm)
    row("Net sync type", data.netSyncType)
    row("Initial animation", data.initialAnimation)
}

private fun enemyRows(contents: MapContents, data: EnemyData) = rows {
    reference("Draw parent", contents, contents.part(data.drawParent))
    row("Think param", data.thinkParamId)
    row("NPC param", data.npcParamId)
    row("Talk id", data.talkId)
    row("Chara init", data.charaInitId)
    row("Platoon", data.platoonId)
    row("Initial animation", data.initialAnimation)
    data.movePoints.forEachIndexed { slot, point ->
        if (point != null) reference("Move point $slot", contents, contents.region(point))
    }
}

private fun collisionRows(contents: MapContents, part: Part.Collision) = rows {
    row("Hit filter", part.hitFilter?.name ?: "unknown (${part.hitFilterId})")
    row("Play region", part.playRegionId)
    row("Stable footing flag", part.stableFootingFlag)
    row("Place name", part.placeNameId ?: "from map area")
    row("Starts disabled", part.startsDisabled)
    entityRow("Bonfire disable id", part.bonfireDisableId)
    entityRow("Disables bonfire", part.disabledBonfire)
    row("Navmesh groups", part.navmeshGroups)
    row("Reflect plane height", part.reflectPlaneHeight)
    val environment = part.environment?.let { contents.environmentEvent(it.value) }
    reference("Environment", contents, environment?.let { contents.ref(EntryKind.Event, it) })
}

private fun connectRows(contents: MapContents, part: Part.ConnectCollision) = rows {
    val collision = part.collision?.let { contents.collisionPart(it.value) }
    reference("Collision", contents, collision?.let { contents.ref(EntryKind.Part, it) })
    row("Connected map", part.connectedMap.joinToString("_") { "%02d".format(it) })
}

internal fun MapContents.ref(kind: EntryKind, index: Int): WorldRef.Entry =
    WorldRef.Entry(map, kind, index)

internal fun MapContents.part(index: PartIndex?): WorldRef.Entry? = index?.let {
    ref(EntryKind.Part, it.value)
}

internal fun MapContents.region(index: RegionIndex?): WorldRef.Entry? = index?.let {
    ref(EntryKind.Region, it.value)
}