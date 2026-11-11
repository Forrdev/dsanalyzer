package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.session.EditionTables

public val PTDEPointers: List<GamePointer> = listOf(
    FollowCam.Pointer,
    ChrIns.Pointer,
    ChrCtrl.Pointer,
    ChrPosData.Pointer,
    AnimData.Pointer,
    AnimRequestMgr.Pointer,
    SpecialEffects.Pointer,
    HitIns.Pointer,
    WorldChrMan.Pointer,
    WorldRes.Pointer,
    EmevdMan.Pointer,
    PlayerGameData.Pointer,
    GameMan.Pointer,
    WorldArea.Pointer,
    GameDataMan.Pointer,
    EventFlags.Pointer,
    EventFlags.Manager,
    MenuMan.Pointer,
    Havok.Manager,
    AttackMan.Pointer,
    BulletMan.Pointer
)

/**
 * Every collection the tables name, each paired with the structure it is read out of
 */
public val PTDETables: List<Pair<GamePointer, GameTable>> = listOf(
    SpecialEffects.Pointer to SpecialEffects.Active,
    WorldChrMan.Pointer to WorldChrMan.Blocks,
    WorldRes.Pointer to WorldRes.Blocks,
    WorldRes.Pointer to WorldRes.BlockInfos,
    EmevdMan.Pointer to EmevdMan.Scripts
)

public val PTDEEdition: EditionTables = EditionTables(
    pointers = PTDEPointers,
    layout = PTDELayout,
    buildOf = { memory, module -> memory.ptdeVersion(module)?.name }
)
