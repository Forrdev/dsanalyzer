package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.layout.CameraLayout
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.layout.SpecialEffectLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.session.EditionTables

public val DSRPointers: List<GamePointer> = listOf(
    FieldArea.Pointer,
    ChrCam.Pointer,
    WorldInfoOwner.Pointer,
    WorldChrMan.Pointer,
    Player.Pointer,
    GameDataMan.Pointer,
    MenuMan.Pointer
)

internal val DSRLayout: GameLayout = GameLayout(
    camera = CameraLayout(
        pointer = ChrCam.Pointer,
        transform = ChrCam.Transform,
        fieldOfView = ChrCam.FieldOfView,
        aspect = ChrCam.Aspect,
        near = ChrCam.Near,
        far = ChrCam.Far,
        smoothedPosition = null,
        orientation = null,
        targetPitch = null
    ),
    specialEffect = SpecialEffectLayout(
        holderSize = SPECIAL_EFFECT_SIZE,
        head = SpecialEffects.HeadPointer,
        owner = SpecialEffects.OwnerPointer,
        stateInfoCounts = SpecialEffects.StateInfoCounts,
        chain = SpecialEffects.Active,
        nodeSize = EFFECT_NODE_SIZE,
        remaining = EffectNode.Remaining,
        total = EffectNode.Total,
        magnitude = EffectNode.Magnitude,
        duration = EffectNode.Duration,
        flags = EffectNode.Flags,
        effectId = EffectNode.EffectId,
        paramRow = EffectNode.ParamRow,
        excludedMask = EffectNodeFlags.Excluded,
        liveMask = EffectNodeFlags.Live
    )
)

public val DSREdition: EditionTables = EditionTables(
    pointers = DSRPointers,
    layout = DSRLayout,
    buildOf = { memory, module -> DSRVersion.from(module.size.toInt()).name }
)