package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object DSRFunctions {
    public val LevelUp: GamePointer = GamePointer(
        name = "LevelUp",
        base = DSRSignatures.LevelUp,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val Warp: GamePointer = GamePointer(
        name = "Warp",
        base = DSRSignatures.Warp,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val EventMan: GamePointer = GamePointer(
        name = "EventMan",
        base = DSRSignatures.EventMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0x10
    )

    public val TalkDispatch: GamePointer = GamePointer(
        name = "TalkDispatch",
        base = DSRSignatures.TalkDispatch,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val TalkOwnerLookup: GamePointer = GamePointer(
        name = "TalkOwnerLookup",
        base = DSRSignatures.TalkOwnerLookup,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val TalkEventVTable: GamePointer = GamePointer(
        name = "TalkEventVTable",
        base = DSRSignatures.TalkEventVTable,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val ItemAdd: GamePointer = GamePointer(
        name = "ItemAdd",
        base = DSRSignatures.ItemAdd,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val ItemPopup: GamePointer = GamePointer(
        name = "ItemPopup",
        base = DSRSignatures.ItemPopup,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val ItemGetMenuMan: GamePointer = GamePointer(
        name = "ItemGetMenuMan",
        base = DSRSignatures.ItemGetMenuMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 8
    )

    public val SetEvent: GamePointer = GamePointer(
        name = "SetEvent",
        base = DSRSignatures.SetEvent,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val GetEvent: GamePointer = GamePointer(
        name = "GetEvent",
        base = DSRSignatures.GetEvent,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val EventFlagMan: GamePointer = GamePointer(
        name = "EventFlagMan",
        base = DSRSignatures.EventFlagMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 8
    )
}
