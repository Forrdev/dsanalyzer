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
}
