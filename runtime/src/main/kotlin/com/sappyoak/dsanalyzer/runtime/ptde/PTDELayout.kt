package com.sappyoak.dsanalyzer.runtime.ptde


import com.sappyoak.dsanalyzer.runtime.layout.AnimChannelLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimControlLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimRequestBand
import com.sappyoak.dsanalyzer.runtime.layout.AnimRequestEntryLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimRequestLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimSlotLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimationLayout
import com.sappyoak.dsanalyzer.runtime.layout.CameraLayout
import com.sappyoak.dsanalyzer.runtime.layout.CharacterLayout
import com.sappyoak.dsanalyzer.runtime.layout.CharacterSlotLayout
import com.sappyoak.dsanalyzer.runtime.layout.ChrCtrlLayout
import com.sappyoak.dsanalyzer.runtime.layout.ChrInsLayout
import com.sappyoak.dsanalyzer.runtime.layout.DebugDrawLayout
import com.sappyoak.dsanalyzer.runtime.layout.FieldLayout
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.layout.GameManLayout
import com.sappyoak.dsanalyzer.runtime.layout.HitInsLayout
import com.sappyoak.dsanalyzer.runtime.layout.IdentityLayout
import com.sappyoak.dsanalyzer.runtime.layout.LoadQueueLayout
import com.sappyoak.dsanalyzer.runtime.layout.PartFilterLayout
import com.sappyoak.dsanalyzer.runtime.layout.PlayerGameDataLayout
import com.sappyoak.dsanalyzer.runtime.layout.PoseLayout
import com.sappyoak.dsanalyzer.runtime.layout.PositionLayout
import com.sappyoak.dsanalyzer.runtime.layout.ProgressLayout
import com.sappyoak.dsanalyzer.runtime.layout.ResistBlockLayout
import com.sappyoak.dsanalyzer.runtime.layout.SpecialEffectLayout
import com.sappyoak.dsanalyzer.runtime.layout.StatBlockLayout
import com.sappyoak.dsanalyzer.runtime.layout.TargetLayout
import com.sappyoak.dsanalyzer.runtime.layout.VitalsLayout
import com.sappyoak.dsanalyzer.runtime.layout.WriteLayout
import com.sappyoak.dsanalyzer.runtime.session.CHEAT_BITS

private const val CHARACTER_POSITION_STRIDE: Int = 0x10

internal fun ptdeLayout(): GameLayout = GameLayout(
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
    characters = CharacterLayout(
        manager = WorldChrMan.Pointer,
        player = WorldChrMan.PlayerPointer,
        deathCam = WorldChrMan.DeathCamActive,
        playerSlots = WorldChrMan.PlayerSlots,
        blocks = WorldChrMan.Blocks,
        blockCharacters = WorldBlock.Characters,
        slot = CharacterSlotLayout(size = CHARACTER_SLOT_SIZE, character = CharacterSlot.Character),
        chr = ChrInsLayout(
            handle = ChrIns.Handle,
            modelId = ChrIns.ModelId,
            npcParamId = ChrIns.NpcParamId,
            chrType = ChrIns.ChrType,
            teamType = ChrIns.TeamType,
            health = ChrIns.Health,
            healthMax = ChrIns.HealthMax,
            stamina = ChrIns.Stamina,
            target = TargetLayout.InCharacter(ChrIns.TargetHandle),
            flags1 = ChrIns.Flags1,
            flags2 = ChrIns.Flags2,
            playRegion = ChrIns.PlayRegion,
            specialEffect = ChrIns.SpecialEffectPointer,
            control = ChrIns.ChrCtrlPointer,
            comManipulator = ChrIns.ComManipulatorPointer,
            position = PositionLayout(x = ChrIns.PositionX, stride = CHARACTER_POSITION_STRIDE),
            size = ChrIns.Pointer.size
        ),
        control = ChrCtrlLayout(
            position = ChrCtrl.PositionPointer,
            animData = ChrCtrl.AnimDataPointer,
            flags = ChrCtrl.Flags,
            playSpeed = AnimData.PlaySpeed,
            size = ChrCtrl.Pointer.size
        ),
        pose = PoseLayout(position = ChrPosData.Position, angle = ChrPosData.Angle, size = ChrPosData.Pointer.size),
        world = GameManLayout(
            pointer = GameMan.Pointer,
            stablePosition = GameMan.StablePosition,
            stableAngle = GameMan.StableAngle,
            lastBonfire = GameMan.LastBonfire
        )
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
    ),
    animation = AnimationLayout(
        pointer = AnimData.Pointer,
        fromController = ChrCtrl.AnimDataPointer,
        slotCapacity = ANIM_SLOT_COUNT,
        slotStride = ANIM_SLOT_SIZE,
        slotCountField = AnimData.SlotCount,
        slotsPointer = AnimData.SlotsPointer,
        skeletonPointer = AnimData.SkeletonPointer,
        skeletonControls = AnimData.SkeletonControls,
        playSpeed = AnimData.PlaySpeed,
        slot = AnimSlotLayout(
            handle = AnimSlot.Handle,
            serial = AnimSlot.Serial,
            control = AnimSlot.ControlPointer,
            manager = AnimSlot.ManagerPointer,
            elapsed = AnimSlot.Elapsed,
            priority = AnimSlot.Priority,
            pendingWeight = AnimSlot.PendingWeight,
            state = AnimSlot.State,
            previousLocalTime = AnimSlot.PreviousLocalTime,
            fadeFrom = AnimSlot.FadeFrom,
            fadeDuration = AnimSlot.FadeDuration,
            fadeRemaining = AnimSlot.FadeRemaining
        ),
        control = AnimControlLayout(
            localTime = AnimControl.LocalTime,
            masterWeight = AnimControl.MasterWeight,
            binding = AnimControl.Binding,
            weightScale = AnimControl.WeightScale,
            playbackSpeed = AnimControl.PlaybackSpeed,
            cycles = AnimControl.Cycles,
            maxCycles = AnimControl.MaxCycles,
            easeRate = AnimControl.EaseRate,
            easeT = AnimControl.EaseT,
            easeState = AnimControl.EaseState
        )
    ),
    animRequest = AnimRequestLayout(
        pointer = AnimRequestMgr.Pointer,
        fromController = ChrCtrl.AnimRequestMgrPointer,
        entryCount = ANIM_REQUEST_COUNT,
        entryStride = ANIM_REQUEST_STRIDE,
        entry = AnimRequestEntryLayout(
            animationId = AnimRequestEntry.AnimationId,
            channelHandle = AnimRequestEntry.ChannelHandle,
            channelSerial = AnimRequestEntry.ChannelSerial,
            enableFlag = AnimRequestEntry.EnableFlag,
            inlineEnableFlag = AnimRequestEntry.InlineEnableFlag
        ),
        animData = AnimRequestMgr.AnimDataPointer,
        channelTable = AnimRequestMgr.ChannelTablePointer,
        channelTableFirst = AnimRequestMgr.ChannelTableFirst,
        groupHandles = AnimRequestMgr.GroupHandles,
        auxHandles = AnimRequestMgr.AuxHandles,
        bands = listOf(
            AnimRequestBand(AnimRequestMgr.Band1First, AnimRequestMgr.Band1Last),
            AnimRequestBand(AnimRequestMgr.Band2First, AnimRequestMgr.Band2Last)
        ),
        latchedEntry = AnimRequestMgr.LatchedEntry,
        specialEntries = AnimRequestMgr.SpecialEntries,
        channel = AnimChannelLayout(
            count = AnimChannel.Count,
            ownHandle = AnimChannel.OwnHandle,
            animData = AnimChannel.AnimDataPointer,
            currentSlotHandle = AnimChannel.CurrentSlotHandle,
            currentSlotSerial = AnimChannel.CurrentSlotSerial,
            fadeWeight = AnimChannel.FadeWeight
        )
    ),
    playerGameData = PlayerGameDataLayout(
        pointer = PlayerGameData.Pointer,
        vitals = VitalsLayout(
            hp = PlayerGameData.Health,
            maxHp = PlayerGameData.MaxHp,
            baseMaxHp = PlayerGameData.BaseMaxHp,
            mp = PlayerGameData.Mp,
            baseMaxMp = PlayerGameData.BaseMaxMp,
            maxMp = PlayerGameData.MpMax,
            sp = PlayerGameData.Stamina,
            maxSp = PlayerGameData.MaxStamina,
            baseMaxSp = PlayerGameData.BaseMaxStamina
        ),
        stats = StatBlockLayout(
            first = PlayerGameData.Stats,
            stride = 0x08,
            count = 8
        ),
        progress = ProgressLayout(
            humanity = PlayerGameData.Humanity,
            durability = PlayerGameData.Durability,
            soulLevel = PlayerGameData.SoulLevel,
            souls = PlayerGameData.Souls,
            soulMemory = PlayerGameData.SoulMemory,
            covenant = PlayerGameData.Covenant
        ),
        identity = IdentityLayout(
            gender = PlayerGameData.Sex,
            archeType = PlayerGameData.CharacterClass,
            appearance = PlayerGameData.Physique,
            gift = PlayerGameData.Gift
        ),
        resists = ResistBlockLayout(
            poison = PlayerGameData.PoisonResist,
            blood = PlayerGameData.BleedResist,
            disease = PlayerGameData.ToxicResist,
            curse = PlayerGameData.CurseResist
        ),
        stance = PlayerGameData.Stance
    ),
    loadQueue = LoadQueueLayout(
        pointer = WorldRes.Pointer,
        currentMap = WorldRes.CurrentMap,
        previousMap = WorldRes.PreviousMap,
        warpTarget = WorldRes.WarpTarget,
        blocks = WorldRes.Blocks,
        blockSize = WORLD_BLOCK_RES_SIZE,
        blockInfos = WorldRes.BlockInfos,
        blockInfoSize = WORLD_BLOCK_INFO_SIZE,
        packedMapId = WorldBlockInfo.PackedMapId,
        suppress = WorldBlockRes.Suppress,
        forceLoad = WorldBlockRes.ForceLoad,
        requested = WorldBlockRes.Requested,
        latched = WorldBlockRes.Latched,
        wantLoaded = WorldBlockRes.WantLoaded,
        stage = WorldBlockRes.Stage,
        pinLoaded = WorldBlockRes.PinLoaded,
        pinOther = WorldBlockRes.PinOther,
        groupMasks = WorldBlockRes.GroupMasks
    ),
    debugDraw = DebugDrawLayout(
        hitIns = HitInsLayout(
            manager = WorldHitMan.Pointer,
            areas = WorldHitMan.Areas,
            groups = WorldHitMan.Groups,
            instances = WorldHitMan.Instances,
            partIndex = HitIns.PartIndex,
            msb = HitIns.Msb,
            tag = HitIns.Tag
        ),
        partFilters = PartFilterLayout(
            partsList = MsbResCap.PartsList,
            parts = MapParts.All,
            type = MapPart.Type,
            indexInType = MapPart.IndexInType,
            typeParams = MapPart.TypeParams,
            collisionType = MapPartType.Collision
        )
    ),
    writes = WriteLayout(
        chrFlags1 = FieldLayout(ChrIns.Pointer, ChrIns.Flags1),
        chrFlags2 = FieldLayout(ChrIns.Pointer, ChrIns.Flags2),
        mapFlags = FieldLayout(ChrCtrl.Pointer, ChrCtrl.Flags),
        hitboxDraw = FieldLayout(AttackMan.Pointer, AttackMan.HitboxDraw),
        cheatBits = CHEAT_BITS
    )
)
