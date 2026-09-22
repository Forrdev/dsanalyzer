package com.sappyoak.dsanalyzer.formats.msb.event

import com.sappyoak.dsanalyzer.formats.msb.assertZeroBytes
import com.sappyoak.dsanalyzer.formats.msb.assertZeros
import com.sappyoak.dsanalyzer.formats.msb.readCoded
import com.sappyoak.dsanalyzer.formats.msb.readEntityId
import com.sappyoak.dsanalyzer.formats.msb.readPartIndex
import com.sappyoak.dsanalyzer.formats.msb.readRegionIndex
import com.sappyoak.dsanalyzer.formats.msb.readRequiredOffset
import com.sappyoak.dsanalyzer.formats.msb.readStringAt
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue
import com.sappyoak.dsanalyzer.shared.binary.readUByte
import com.sappyoak.dsanalyzer.shared.binary.skip
import com.sappyoak.dsanalyzer.shared.math.readVec3

private const val ENVIRONMENT_UNKNOWN_FIELD_PADDING = 0x18
private const val GENERATOR_COUNTS_PADDING = 0x1F
private const val GENERATOR_TAIL_PADDING = 0x40

internal fun BinaryReader.readEvent(): Event {
    val start = position
    val nameOffset = readRequiredOffset()
    skip(4) // index across all events
    val type = readCoded<EventType>()
    skip(4) // index within its type
    val baseOffset = readRequiredOffset()
    val typeOffset = readRequiredOffset()
    assertZeros(1)

    position = start + baseOffset

    val header = EventHeader(
        name = readStringAt(start, nameOffset),
        part = readPartIndex(),
        region = readRegionIndex(),
        entityId = readEntityId()
    )
    assertZeros(1)

    position = start + typeOffset
    return when (type) {
        EventType.Light -> Event.Light(header, readInt())
        EventType.Sound -> Event.Sound(header, readInt(), readInt())
        EventType.Sfx -> Event.Sfx(header, readInt())
        EventType.Wind -> readWind(header)
        EventType.Treasure -> readTreasure(header)
        EventType.Generator -> readGenerator(header)
        EventType.Message -> readMessage(header)
        EventType.ObjAct -> readObjAct(header)
        EventType.SpawnPoint -> Event.SpawnPoint(header, readRegionIndex()).also { assertZeros(3) }
        EventType.MapOffset -> Event.MapOffset(header, readVec3(), readFloat())
        EventType.Navmesh -> Event.Navmesh(header, readRegionIndex()).also { assertZeros(3) }
        EventType.Environment -> Event.Environment(header).also {
            skip(ENVIRONMENT_UNKNOWN_FIELD_PADDING)
            assertZeros(2)
        }
        EventType.PseudoMultiplayer -> Event.PseudoMultiplayer(
            header = header,
            hostEntityId = readEntityId(),
            eventFlagId = readInt(),
            activateGoodsId = readInt()
        ).also { assertZeros(1) }
    }
}

private fun BinaryReader.readWind(header: EventHeader): Event.Wind {
    val vectorMin = readVec3()
    skip(4) // unknown value
    val vectorMax = readVec3()
    skip(4) // unknown value

    return Event.Wind(
        header = header,
        vectorMin = vectorMin,
        vectorMax = vectorMax,
        swingCycles = List(4) { readFloat() },
        swingPowers = List(4) { readFloat() }
    )
}

private fun BinaryReader.readMessage(header: EventHeader): Event.Message {
    val messageId = readShort().toInt()
    skip(2)

    val message = Event.Message(
        header = header,
        messageId = messageId,
        hidden = readBoolean()
    )

    assertZeroBytes(3)
    return message
}

private fun BinaryReader.readTreasure(header: EventHeader): Event.Treasure {
    assertZeros(1)
    val part = readPartIndex()
    val itemLots = List(5) { readInt().also { assertValue(-1) { readInt() } } }
    val treasure = Event.Treasure(
        header = header,
        part = part,
        itemLots = itemLots,
        inChest = readBoolean(),
        startsDisabled = readBoolean()
    )
    assertValue(0) { readShort().toInt() }
    return treasure
}

private fun BinaryReader.readObjAct(header: EventHeader): Event.ObjAct {
    val objActEntityId = readEntityId()
    val part = readPartIndex()
    val paramId = readShort().toInt()
    val state = readUByte().toInt()
    assertZeroBytes(1)
    return Event.ObjAct(
        header = header,
        objActEntityId = objActEntityId,
        part = part,
        paramId = paramId,
        state = state,
        eventFlagId = readInt()
    )
}

private fun BinaryReader.readGenerator(header: EventHeader): Event.Generator {
    val maxNum = readUByte().toInt()
    val genType = readByte().toInt()
    val limitNum = readShort().toInt()
    val minGenNum = readShort().toInt()
    val maxGenNum = readShort().toInt()
    val minInterval = readFloat()
    val maxInterval = readFloat()
    val initialSpawnCount = readUByte().toInt()
    assertZeroBytes(GENERATOR_COUNTS_PADDING)

    val generator = Event.Generator(
        header = header,
        maxNum = maxNum,
        genType = genType,
        limitNum = limitNum,
        minGenNum = minGenNum,
        maxGenNum = maxGenNum,
        minInterval = minInterval,
        maxInterval = maxInterval,
        initialSpawnCount = initialSpawnCount,
        spawnPoints = List(4) { readRegionIndex() },
        spawnParts = List(32) { readPartIndex() }
    )

    assertZeroBytes(GENERATOR_TAIL_PADDING)
    return generator
}