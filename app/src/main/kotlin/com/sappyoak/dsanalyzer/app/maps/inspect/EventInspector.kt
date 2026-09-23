package com.sappyoak.dsanalyzer.app.maps.inspect

import com.sappyoak.dsanalyzer.app.maps.MapContents
import com.sappyoak.dsanalyzer.formats.msb.event.Event

internal fun eventSections(contents: MapContents, event: Event): List<InspectorSection> = buildList {
    add(InspectorSection("Event", attachmentRows(contents, event)))
    typeRows(contents, event)?.let { add(InspectorSection(event::class.simpleName.orEmpty(), it)) }
}

private fun attachmentRows(contents: MapContents, event: Event) = rows {
    val header = event.header
    row("Name", header.name)
    row("Type", event::class.simpleName)
    entityRow("Entity id", header.entityId)
    reference("Attached part", contents, contents.part(header.part))
    reference("Attached region", contents, contents.region(header.region))
}

private fun typeRows(contents: MapContents, event: Event): List<InspectorRow>? = when (event) {
    is Event.Treasure -> rows {
        reference("Treasure part", contents, contents.part(event.part))
        event.itemLots.filter { it != -1 }.forEachIndexed { slot, lot -> row("Item lot $slot", lot) }
        row("In chest", event.inChest)
        row("Starts disabled", event.startsDisabled)
    }

    is Event.Generator -> rows {
        row("Max at once", event.maxNum)
        row("Limit", event.limitNum)
        row("Per spawn", "${event.minGenNum} to ${event.maxGenNum}")
        row("Interval", "${event.minInterval}s to ${event.maxInterval}s")
        row("Initial spawns", event.initialSpawnCount)
        event.spawnPoints.forEachIndexed { slot, point ->
            if (point != null) reference("Spawn point $slot", contents, contents.region(point))
        }
        event.spawnParts.forEachIndexed { slot, part ->
            if (part != null) reference("Spawn part $slot", contents, contents.part(part))
        }
    }

    is Event.ObjAct -> rows {
        entityRow("ObjAct entity", event.objActEntityId)
        reference("ObjAct part", contents, contents.part(event.part))
        row("ObjAct param", event.paramId)
        row("State", event.state)
        row("Event flag", event.eventFlagId)
    }

    is Event.SpawnPoint -> rows { reference("Spawn region", contents, contents.region(event.region)) }

    is Event.Navmesh -> rows { reference("Navmesh region", contents, contents.region(event.region)) }

    is Event.PseudoMultiplayer -> rows {
        entityRow("Host entity", event.hostEntityId)
        row("Event flag", event.eventFlagId)
        row("Activate goods", event.activateGoodsId)
    }

    is Event.Message -> rows {
        row("Message id", event.messageId)
        row("Hidden", event.hidden)
    }

    is Event.Light -> rows { row("Point light", event.pointLightId) }
    is Event.Sound -> rows {
        row("Sound type", event.soundType)
        row("Sound id", event.soundId)
    }
    is Event.Sfx -> rows { row("Effect id", event.effectId) }
    is Event.MapOffset -> rows {
        row("Position", event.position)
        row("Degree", event.degree)
    }
    is Event.Environment -> emptyList()

    is Event.Wind -> null
}