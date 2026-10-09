package com.sappyoak.dsanalyzer.formats.msb.part

import com.sappyoak.dsanalyzer.formats.msb.CollisionIndex
import com.sappyoak.dsanalyzer.formats.msb.EnvironmentIndex
import com.sappyoak.dsanalyzer.formats.msb.RegionIndex
import com.sappyoak.dsanalyzer.formats.msb.asReference
import com.sappyoak.dsanalyzer.formats.msb.readEntityId
import com.sappyoak.dsanalyzer.formats.msb.readGroupMask
import com.sappyoak.dsanalyzer.formats.msb.readPartIndex
import com.sappyoak.dsanalyzer.shared.binary.*

private const val TYPE_CODE_OFFSET = 4

internal fun BinaryReader.readPart(): Part {
    val start = position
    val type = at(start + TYPE_CODE_OFFSET) { readCoded<PartType>() }
    val header = readPartHeader(start)

    return when (type) {
        PartType.MapPiece -> Part.MapPiece(header).also { assertZeros(2) }
        PartType.Object -> Part.Object(header, readObjectData())
        PartType.DummyObject -> Part.DummyObject(header, readObjectData())
        PartType.Enemy -> Part.Enemy(header, readEnemyData())
        PartType.DummyEnemy -> Part.DummyEnemy(header, readEnemyData())
        PartType.Player -> Part.Player(header).also { assertZeros(4) }
        PartType.Collision -> readCollision(header)
        PartType.Navmesh -> Part.Navmesh(header, readGroupMask()).also { assertZeros(4) }
        PartType.ConnectCollision -> Part.ConnectCollision(
            header = header,
            collision = readInt().asReference(::CollisionIndex),
            connectedMap = List(4) { readByte().toInt() }
        ).also { assertZeros(2) }
    }
}

private fun BinaryReader.readObjectData(): ObjectData {
    assertZeros(1)
    val drawParent = readPartIndex()
    val breakTerm = readByte().toInt()
    val netSyncType = readByte().toInt()
    assertValue(0) { readShort().toInt() }

    val data = ObjectData(
        drawParent = drawParent,
        breakTerm = breakTerm,
        netSyncType = netSyncType,
        initialAnimation = readShort().toInt(),
    )

    // skip unknown fields
    skip(6)
    assertZeros(1)
    return data
}

private fun BinaryReader.readEnemyData(): EnemyData {
    assertZeros(2)

    val thinkParamId = readInt()
    val npcParamId = readInt()
    val talkId = readInt()
    val pointMoveType = readUByte().toInt()
    assertZeroByte()
    val platoonId = readUShort().toInt()
    val charaInitId = readInt()
    val drawParent = readPartIndex()
    assertZeros(2)

    return EnemyData(
        thinkParamId = thinkParamId,
        npcParamId = npcParamId,
        talkId = talkId,
        pointMoveType = pointMoveType,
        platoonId = platoonId,
        charaInitId = charaInitId,
        drawParent = drawParent,
        movePoints = List(8) { readShort().toInt().asReference(::RegionIndex) },
        initialAnimation = readInt(),
        damageAnimation = readInt()
    )
}

private fun BinaryReader.readCollision(header: PartHeader): Part.Collision {
    val hitFilterId = readUByte().toInt()
    val soundSpaceType = readUByte().toInt()
    val environment = readShort().toInt().asReference(::EnvironmentIndex)
    val reflectPlaneHeight = readFloat()
    val navmeshGroups = readGroupMask()
    val vagrantEntityIds = List(3) { readInt() }
    val rawPlaceName = readShort().toInt()
    val startsDisabled = readBoolean()
    skip(1)
    val bonfireDisableId = readEntityId()
    repeat(3) { assertValue(-1) { readInt() } }

    val collision = Part.Collision(
        header = header,
        hitFilterId = hitFilterId,
        soundSpaceType = soundSpaceType,
        environment = environment,
        reflectPlaneHeight = reflectPlaneHeight,
        navmeshGroups = navmeshGroups,
        vagrantEntityIds = vagrantEntityIds,
        rawPlaceName = rawPlaceName,
        startsDisabled = startsDisabled,
        bonfireDisableId = bonfireDisableId,
        rawPlayRegion = readInt(),
        lockCamParamId1 = readShort().toInt(),
        lockCamParamId2 = readShort().toInt()
    )

    assertZeros(4)
    return collision
}