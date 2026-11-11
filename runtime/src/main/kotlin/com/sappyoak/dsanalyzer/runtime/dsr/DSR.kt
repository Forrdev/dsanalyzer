package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.session.EditionTables

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
    MenuMan.Pointer
)

public val DSREdition: EditionTables = EditionTables(
    pointers = DSRPointers,
    layout = DSRLayout,
    buildOf = { memory, module -> DSRVersion.from(module.size.toInt()).name }
)
