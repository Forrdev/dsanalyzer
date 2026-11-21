package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

public val DSRPointers: List<GamePointer> = listOf(
    FieldArea.Pointer,
    ChrCam.Pointer,
    WorldInfoOwner.Pointer,
    WorldChrMan.Pointer,
    Player.Pointer,
    ChrCtrl.Pointer,
    AnimData.Pointer,
    AnimRequestMgr.Pointer,
    PlayerGameData.Pointer,
    GameDataMan.Pointer,
    GameMan.Pointer,
    MenuMan.Pointer,
    EventFlags.Pointer,
    WorldHitMan.Pointer,
    AttackMan.Pointer,
    Graphics.Pointer,
    DSRFunctions.LevelUp,
    DSRFunctions.Warp,
    DSRFunctions.EventMan,
    DSRFunctions.TalkDispatch,
    DSRFunctions.TalkOwnerLookup,
    DSRFunctions.TalkEventVTable,
    DSRFunctions.ItemAdd,
    DSRFunctions.ItemPopup,
    DSRFunctions.ItemGetMenuMan,
    DSRFunctions.SetEvent,
    DSRFunctions.GetEvent,
    DSRFunctions.EventFlagMan
)
