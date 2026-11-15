package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget


object PTDESignatures {
    public val ChrIns: Signature = Signature.parse(
        name = "ChrIns",
        pattern = "8B 15 ?? ?? ?? ?? F3 0F 10 44 24 30 52",
        target = SignatureTarget.Embedded(2)
    )

    public val EmevdMan: Signature = Signature.parse(
        name = "EmevdMan",
        pattern = "83 FF FF 73 06 03 F8 03 39 EB 02 33 FF A1 ?? ?? ?? ?? 83 C0 04",
        target = SignatureTarget.Embedded(14)
    )

    public val ChrFollowCam: Signature = Signature.parse(
        name = "ChrFollowCam",
        pattern = "D9 45 08 A1 ?? ?? ?? ?? 51 D9 1C 24 50",
        target = SignatureTarget.Embedded(4)
    )

    public val GameDataMan: Signature = Signature.parse(
        name = "GameDataMan",
        pattern = "8B 0D ?? ?? ?? ?? 8B 41 30 8B 4D 64",
        target = SignatureTarget.Embedded(2)
    )

    public val FrpgHavokMan: Signature = Signature.parse(
        name = "FrpgHavokMan",
        pattern = "51 A1 ?? ?? ?? ?? 8B 48 14 53 55 8B 6C 24 10 8B 5D 3C 56 57",
        target = SignatureTarget.Embedded(2)
    )

    public val MenuMan: Signature = Signature.parse(
        name = "MenuMan",
        pattern = "E8 ?? ?? ?? ?? 8B 35 ?? ?? ?? ?? A1 ?? ?? ?? ?? 3B C3 74 0D",
        target = SignatureTarget.Embedded(7)
    )

    public val GameMan: Signature = Signature.parse(
        name = "GameMan",
        pattern = "8B 54 24 10 8B C8 F7 D9 39 8A B8 0E 00 00 B3 01 0F 95 C2 8B 0D ?? ?? ?? ?? 80 B9 A5 0B 00 00 00",
        target = SignatureTarget.Embedded(0x15)
    )

    public val WorldArea: Signature = Signature.parse(
        name = "WorldArea",
        pattern = "8B 48 04 8B 11 50 8B 42 34 FF D0 C7 46 2C 00 00 00 00 C3 8B 0D ?? ?? ?? ?? " +
                "8B 89 50 0B 00 00 32 C0 83 79 28 00",
        target = SignatureTarget.Embedded(0x15)
    )

    public val WorldChrMan: Signature = Signature.parse(
        name = "WorldChrMan",
        pattern = "A1 ?? ?? ?? ?? 8B 40 3C 3B C8 75 02 33 C9 56",
        target = SignatureTarget.Embedded(1)
    )

    public val EventFlags: Signature = Signature.parse(
        name = "EventFlags",
        pattern = "56 8B F1 8B 46 1C 50 A1 ?? ?? ?? ?? 32 C9",
        target = SignatureTarget.Embedded(8)
    )

    public val AttackMan: Signature = Signature.parse(
        name = "AttackMan",
        pattern = "A1 ?? ?? ?? ?? 51 8B 4D 0C D9 1C 24 51 52 6A 00 56 50",
        target = SignatureTarget.Embedded(1)
    )

    public val BulletMan: Signature = Signature.parse(
        name = "BulletMan",
        pattern = "8B 57 04 83 FA FF 74 ?? A1 ?? ?? ?? ?? 56 E8",
        target = SignatureTarget.Embedded(9)
    )

    public val LockOnMan: Signature = Signature.parse(
        name = "LockOnMan",
        pattern = "8B 54 24 3C A1 ?? ?? ?? ?? 52 51 D9 1C 24 50 E8",
        target = SignatureTarget.Embedded(5)
    )

    /** Entry of GameLoop_StepFrame */
    public val GameLoopStep: Signature = Signature.parse(
        name = "GameLoopStep",
        pattern = "51 A1 ?? ?? ?? ?? 56 57 85 C0 74 17 80 78 18 00 74 05 8B 40 14 EB 03 8B 40 10"
    )

    /** `PlayerGameData_ApplyLevelUp` */
    public val LevelUp: Signature = Signature.parse(
        name = "LevelUp",
        pattern = "83 EC 08 8B 15 ?? ?? ?? ?? 56 57 8B 7A 08 8B 10 89 57 38"
    )

    /** `NS_FRPG_WarpDispatch_ByKind` */
    public val WarpDispatch: Signature = Signature.parse(
        name = "WarpDispatch",
        pattern = "33 C0 39 46 04 74 ?? 8B 0D ?? ?? ?? ?? 8B 15 ?? ?? ?? ?? C6 41 44 01"
    )

    public val WarpOwner: Signature = Signature.parse(
        name = "WarpOwner",
        pattern = "33 C0 39 46 04 74 ?? 8B 0D ?? ?? ?? ?? 8B 15 ?? ?? ?? ?? C6 41 44 01",
        target = SignatureTarget.Embedded(15)
    )

    /** In the per-frame step, the call to the IGT update just ahead of the mailbox site */
    public val MailboxStep: Signature = Signature.parse(
        name = "MailboxStep",
        pattern = "51 8B FB E8 ?? ?? ?? ?? 51 D9 45 08 D9 1C 24 E8"
    )

    public val PadPolled: Signature = Signature.parse(
        name = "PadPolled",
        pattern = "8B 07 8B 50 54 8B CF FF D2 84 C0 75 0B 8B 07 8B 50 2C 8B CF FF D2 EB 05 C6 44 24 13 01 83 C3 04"
    )

    public val InputMan: Signature = Signature.parse(
        name = "InputMan",
        pattern = "8B 4E 20 88 46 14 A1 ?? ?? ?? ?? F3 0F 10 40 34 57 8B 7E 04",
        target = SignatureTarget.Embedded(7)
    )

    private const val FILE_MAN_UPDATE = "8B 3D ?? ?? ?? ?? 6A 00 57 89 7C 24 28 E8 ?? ?? ?? ?? FF 47 58"
    public val FileMan: Signature = Signature.parse(
        name = "FileMan",
        pattern = FILE_MAN_UPDATE,
        target = SignatureTarget.Embedded(2)
    )

    public val FileManPoll: Signature = Signature.parse(
        name = "FileManPoll",
        pattern = FILE_MAN_UPDATE,
        target = SignatureTarget.Relative(
            at = 0x0E,
            instructionEnd = 0x12
        )
    )

    public val FileRings: Signature = Signature.parse(
        name = "FileRings",
        pattern = "A1 ?? ?? ?? ?? 6A 01 50 C6 44 24 1B 01 E8 ?? ?? ?? ?? BD ?? ?? ?? ??",
        target = SignatureTarget.Embedded(0x13)
    )
    /**
     * 'ProcessMoveManStepFrame's. It turns on FileMan_UpdateFrame's wall-time
     * budget every in-game frame
     */
    public val LoadBudgetWrite: Signature = Signature.parse(
        name = "LoadBudgetWrite",
        pattern = "8B 15 ?? ?? ?? ?? 8B 0D ?? ?? ?? ?? F3 0F 10 15 ?? ?? ?? ?? 89 42 24 A1 ?? ?? ?? ?? C6 40 14 01 C6 41 68 01"
    )

    public val SleepImport: Signature = Signature.parse(
        name = "SleepImport",
        pattern = "6A 00 FF 15 ?? ?? ?? ?? C3 CC CC",
        target = SignatureTarget.Embedded(4)
    )

    private const val HIT_INS_DEBUG_DRAW =
        "55 8B EC 83 E4 F0 0F 57 C0 F3 0F 10 0D ?? ?? ?? ?? 83 EC 28 80 7E 50 06 53 8B 1D"

    /** Entry of 'HitIns_DebugDraw' */
    public val HitInsDebugDraw: Signature = Signature.parse(name = "HitInsDebugDraw", pattern = HIT_INS_DEBUG_DRAW)

    /** The renderer global: the EzDraw layers and the draw registry hang off it */
    public val GraphicsMan: Signature = Signature.parse(
        name = "GraphicsMan",
        pattern = HIT_INS_DEBUG_DRAW,
        target = SignatureTarget.Embedded(0x1B)
    )

    public val WorldHitMan: Signature = Signature.parse(
        name = "WorldHitMan",
        pattern = "8B 15 ?? ?? ?? ?? E8 ?? ?? ?? ?? 84 C0 0F 84 ?? ?? ?? ?? 8A 86 FD 01 00 00",
        target = SignatureTarget.Embedded(2)
    )

    /** In 'HGDrawPlanWalker_Next', the registry lookup ahead of its push */
    public val PlanWalkerPush: Signature = Signature.parse(
        name = "PlanWalkerPush",
        pattern = "8B 43 04 8B 50 18 8B 42 04 8B 0C 88 8D 54 24 0C 52 8D 43 0C 89 4C 24 14 C7 44 24 18 00 00 00 00 E8"
    )

    /** In 'EzDraw_FlushTrianglesUP', the layer test before the draw, and the state reset after it */
    public val FlushBegin: Signature = Signature.parse(
        name = "FlushBegin",
        pattern = "85 82 84 00 00 00 0F 84 ?? ?? ?? ?? 8B 75 18 8B 4E 18 85 C9 74 09"
    )
    public val FlushEnd: Signature = Signature.parse(
        name = "FlushEnd",
        pattern = "8D 50 30 83 C0 20 52 50 8B 45 20 8B 40 20 E8 ?? ?? ?? ?? 5F 5E 5D 5B C2 10 00"
    )

    /** 'HitRes_DrawLoRigid' and 'HitRes_DrawHiRigid' building their constant colours */
    public val LoColor: Signature = Signature.parse(name = "LoColor", pattern = "F3 0F 10 0D 2C 50 1E 01 F3 0F 10 05 A8 4D 1E 01")
    public val HiColor: Signature = Signature.parse(name = "HiColor", pattern = "F3 0F 10 0D 2C 50 1E 01 F3 0F 10 05 34 53 1E 01")

    /** Entry of 'EzDrawShapeVisitor_AddTriangle' */
    public val AddTriangle: Signature = Signature.parse(
        name = "AddTriangle",
        pattern = "53 8B 59 0C 85 DB 0F 84 ?? ?? ?? ?? 8B 54 24 08 81 E2 FF 03 00 80"
    )

    public val TalkDispatch: Signature = Signature.parse(
        name = "TalkDispatch",
        pattern = "55 8B EC 83 E4 F0 6A FF 68 ?? ?? ?? ?? 64 A1 00 00 00 00 50 81 EC A8 02 00 00 53 56 57 " +
                "A1 ?? ?? ?? ?? 33 C4 50 8D 84 24 B8 02 00 00 64 A3 00 00 00 00 8B F1"
    )

    public val TalkOwnerLookup: Signature = Signature.parse(
        name = "TalkOwnerLookup",
        pattern = "8B 41 08 E8 ?? ?? ?? ?? 8B 10 8B C8 8B 82 54 02 00 00",
        target = SignatureTarget.Relative(at = 4, instructionEnd = 8)
    )

    /** The `EzStateExternalEventTemp` constructor */
    public val TalkEventVTable: Signature = Signature.parse(
        name = "TalkEventVTable",
        pattern = "8B C1 8B 4C 24 04 C7 00 ?? ?? ?? ?? C7 80 FC 00 00 00 00 00 00 00 89 48 04 C2 04 00",
        target = SignatureTarget.Embedded(8)
    )

    /** The `EzStateTalkEvent`*/
    public val TalkHandlerVTable: Signature = Signature.parse(
        name = "TalkHandlerVTable",
        pattern = "8B 4E 14 89 48 08 8B 4C 24 1C C7 00 ?? ?? ?? ?? 89 70 04 89 78 0C",
        target = SignatureTarget.Embedded(12)
    )

    /** Entry of 'ChrAsm_AddItem' */
    public val ItemAdd: Signature = Signature.parse(
        name = "ItemAdd",
        pattern = "55 8B EC 83 E4 F8 83 EC 34 8B 4D 0C 53 8B 5D 08 56 83 C8 FF 33 F6"
    )

    private const val ITEM_POPUP = "C1 E0 1C 55 50 A1 ?? ?? ?? ?? E8 ?? ?? ?? ?? 85 F6"

    /** The item-get menu's global, loaded into EAX ahead of the popup call in the award path */
    public val ItemGetMenuMan: Signature = Signature.parse(
        name = "ItemGetMenuMan",
        pattern = ITEM_POPUP,
        target = SignatureTarget.Embedded(6)
    )

    /** 'ItemGetMenuMan_PushItem', called from the award path */
    public val ItemPopup: Signature = Signature.parse(
        name = "ItemPopup",
        pattern = ITEM_POPUP,
        target = SignatureTarget.Relative(at = 11, instructionEnd = 15)
    )

    /** Entry of 'SetEventFlag' */
    public val SetEventFlag: Signature = Signature.parse(
        name = "SetEventFlag",
        pattern = "80 B8 14 01 00 00 00 56 8B 74 24 08 74 ?? 57 51 50 8B CE E8"
    )

    /** 'GetEventFlag', called right after the [EventFlags] match */
    public val GetEventFlag: Signature = Signature.parse(
        name = "GetEventFlag",
        pattern = "56 8B F1 8B 46 1C 50 A1 ?? ?? ?? ?? 32 C9 E8 ?? ?? ?? ??",
        target = SignatureTarget.Relative(at = 15, instructionEnd = 19)
    )
}
