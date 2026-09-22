package com.sappyoak.dsanalyzer.formats.msb.part

import com.sappyoak.dsanalyzer.formats.msb.GroupMask
import com.sappyoak.dsanalyzer.formats.msb.ModelIndex
import com.sappyoak.dsanalyzer.shared.math.Vec3

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