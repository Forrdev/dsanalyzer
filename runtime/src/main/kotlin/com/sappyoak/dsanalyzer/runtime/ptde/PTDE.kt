package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.EditionTables

public val PTDEPointers: List<GamePointer> = listOf(
    FollowCam.Pointer,
    ChrIns.Pointer,
    ChrCtrl.Pointer,
    ChrPosData.Pointer,
    AnimData.Pointer,
    AnimRequestMgr.Pointer,
    SpecialEffects.Pointer,
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
    AttackMan.Pointer,
    BulletMan.Pointer,
    WorldHitMan.Pointer,
    Graphics.Pointer,
    LockOnMan.Pointer,
    PTDEFunctions.LevelUp,
    PTDEFunctions.WarpDispatch,
    PTDEFunctions.WarpOwner,
    PTDEFunctions.TalkDispatch,
    PTDEFunctions.TalkOwnerLookup,
    PTDEFunctions.TalkEventVTable,
    PTDEFunctions.TalkHandlerVTable,
    PTDEFunctions.ItemAdd,
    PTDEFunctions.ItemPopup,
    PTDEFunctions.ItemGetMenuMan,
    PTDEFunctions.SetEventFlag,
    PTDEFunctions.GetEventFlag
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
    layoutFor = { ptdeLayout() }
)
