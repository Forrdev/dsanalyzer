package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object PTDEFunctions {
    public val LevelUp: GamePointer = GamePointer(
        name = "LevelUp",
        base = PTDESignatures.LevelUp,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val WarpDispatch: GamePointer = GamePointer(
        name = "WarpDispatch",
        base = PTDESignatures.WarpDispatch,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val WarpOwner: GamePointer = GamePointer(
        name = "WarpOwner",
        base = PTDESignatures.WarpOwner,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 8
    )

    public val TalkDispatch: GamePointer = GamePointer(
        name = "TalkDispatch",
        base = PTDESignatures.TalkDispatch,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val TalkOwnerLookup: GamePointer = GamePointer(
        name = "TalkOwnerLookup",
        base = PTDESignatures.TalkOwnerLookup,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val TalkEventVTable: GamePointer = GamePointer(
        name = "TalkEventVTable",
        base = PTDESignatures.TalkEventVTable,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val TalkHandlerVTable: GamePointer = GamePointer(
        name = "TalkHandlerVTable",
        base = PTDESignatures.TalkHandlerVTable,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val ItemAdd: GamePointer = GamePointer(
        name = "ItemAdd",
        base = PTDESignatures.ItemAdd,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val ItemPopup: GamePointer = GamePointer(
        name = "ItemPopup",
        base = PTDESignatures.ItemPopup,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val ItemGetMenuMan: GamePointer = GamePointer(
        name = "ItemGetMenuMan",
        base = PTDESignatures.ItemGetMenuMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 8
    )

    public val SetEventFlag: GamePointer = GamePointer(
        name = "SetEventFlag",
        base = PTDESignatures.SetEventFlag,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val GetEventFlag: GamePointer = GamePointer(
        name = "GetEventFlag",
        base = PTDESignatures.GetEventFlag,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val EventFlagMan: GamePointer = GamePointer(
        name = "EventFlagMan",
        base = PTDESignatures.EventFlags,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 8
    )
}
