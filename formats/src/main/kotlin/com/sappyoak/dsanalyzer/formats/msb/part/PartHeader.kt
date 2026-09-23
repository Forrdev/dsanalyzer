package com.sappyoak.dsanalyzer.formats.msb.part

import com.sappyoak.dsanalyzer.formats.msb.GroupMask
import com.sappyoak.dsanalyzer.formats.msb.ModelIndex
import com.sappyoak.dsanalyzer.formats.msb.asReference
import com.sappyoak.dsanalyzer.formats.msb.assertZeroByte
import com.sappyoak.dsanalyzer.formats.msb.assertZeroBytes
import com.sappyoak.dsanalyzer.formats.msb.assertZeros
import com.sappyoak.dsanalyzer.formats.msb.readEntityId
import com.sappyoak.dsanalyzer.formats.msb.readGroupMask
import com.sappyoak.dsanalyzer.formats.msb.readRequiredOffset
import com.sappyoak.dsanalyzer.formats.msb.readStringAt
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue
import com.sappyoak.dsanalyzer.shared.math.Vec3
import com.sappyoak.dsanalyzer.shared.math.readVec3

public data class PartHeader(
    public val name: String,
    public val model: ModelIndex?,
    public val sibPath: String,
    public val position: Vec3,
    public val rotation: Vec3,
    public val scale: Vec3,
    public val drawGroups: GroupMask,
    public val displayGroups: GroupMask,
    public val entityId: Int?,
    public val rendering: PartRendering
)

public data class PartRendering(
    public val lightId: Int,
    public val fogId: Int,
    public val scatterId: Int,
    public val lensFlareId: Int,
    public val shadowId: Int,
    public val depthOfFieldId: Int,
    public val toneMapId: Int,
    public val toneCorrectId: Int,
    public val lanternId: Int,
    public val lodParamId: Int,
    public val isShadowSource: Boolean,
    public val isShadowDestination: Boolean,
    public val isShadowOnly: Boolean,
    public val drawByReflectCam: Boolean,
    public val drawOnlyReflectCam: Boolean,
    public val useDepthBiasFloat: Boolean,
    public val disablePointLightEffect: Boolean
)

internal fun BinaryReader.readPartHeader(start: Long): PartHeader {
    position = start
    val nameOffset = readRequiredOffset()
    skip(8) // type code, then index within its type
    val model = readInt().asReference(::ModelIndex)
    val sibOffset = readRequiredOffset()
    val location = readVec3()
    val rotation = readVec3()
    val scale = readVec3()
    val drawGroups = readGroupMask()
    val displayGroups = readGroupMask()
    val entityOffset = readRequiredOffset()
    val typeDataOffset = readRequiredOffset()
    assertZeros(1)

    position = start + entityOffset
    val entityId = readEntityId()
    val rendering = readRendering()
    position = start + typeDataOffset

    return PartHeader(
        name = readStringAt(start, nameOffset),
        model = model,
        sibPath = readStringAt(start, sibOffset),
        position = location,
        rotation = rotation,
        scale = scale,
        drawGroups = drawGroups,
        displayGroups = displayGroups,
        entityId = entityId,
        rendering = rendering
    )
}

private fun BinaryReader.readRendering(): PartRendering {
    val ids = List(10) { readByte().toInt() }
    assertZeroByte()
    val flags = List(7) { readBoolean() }
    assertZeroBytes(2)

    return PartRendering(
        lightId = ids[0],
        fogId = ids[1],
        scatterId = ids[2],
        lensFlareId = ids[3],
        shadowId = ids[4],
        depthOfFieldId = ids[5],
        toneMapId = ids[6],
        toneCorrectId = ids[7],
        lanternId = ids[8],
        lodParamId = ids[9],
        isShadowSource = flags[0],
        isShadowDestination = flags[1],
        isShadowOnly = flags[2],
        drawByReflectCam = flags[3],
        drawOnlyReflectCam = flags[4],
        useDepthBiasFloat = flags[5],
        disablePointLightEffect = flags[6]
    )
}