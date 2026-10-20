package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.layout.CameraLayout
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.layout.SpecialEffectLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.session.EditionTables

public val PTDEPointers: List<GamePointer> = listOf(
    FollowCam.Pointer,
    ChrIns.Pointer,
    ChrCtrl.Pointer,
    ChrPosData.Pointer,
    AnimData.Pointer,
    AnimRequest.ChannelA,
    AnimRequest.ChannelB,
    SpecialEffects.Pointer,
    WorldChrMan.Pointer,
    WorldRes.Pointer,
    EmevdMan.Pointer,
    PlayerStats.Pointer,
    WorldState.Pointer,
    WorldArea.Pointer,
    DeathCam.Pointer,
    GameDataMan.Pointer,
    EventFlags.Pointer,
    EventFlags.Manager,
    MenuMan.Pointer,
    Havok.Manager
)

/**
 * Every collection the tables name, each paired with the structure it is read out of
 */
public val PTDETables: List<Pair<GamePointer, GameTable>> = listOf(
    SpecialEffects.Pointer to SpecialEffects.Active,
    WorldChrMan.Pointer to WorldChrMan.Blocks,
    WorldRes.Pointer to WorldRes.Blocks,
    EmevdMan.Pointer to EmevdMan.Scripts
)

internal val PTDELayout: GameLayout = GameLayout(
    camera = CameraLayout(
        pointer = FollowCam.Pointer,
        transform = FrpgCam.Transform,
        fieldOfView = FrpgCam.FieldOfView,
        aspect = FrpgCam.Aspect,
        near = FrpgCam.Near,
        far = FrpgCam.Far,
        smoothedPosition = FollowCam.PosX,
        orientation = FollowCam.Orientation,
        targetPitch = FollowCam.TargetPitch
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

public val PTDEEdition: EditionTables = EditionTables(
    pointers = PTDEPointers,
    layout = PTDELayout,
    buildOf = { memory, module -> memory.ptdeVersion(module)?.name }
)