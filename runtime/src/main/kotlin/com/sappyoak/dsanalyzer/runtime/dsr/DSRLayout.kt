package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.layout.AILayout
import com.sappyoak.dsanalyzer.runtime.layout.ActionLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimChannelLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimControlLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimRequestBand
import com.sappyoak.dsanalyzer.runtime.layout.AnimRequestEntryLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimRequestLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimSlotLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimationLayout
import com.sappyoak.dsanalyzer.runtime.layout.CallArch
import com.sappyoak.dsanalyzer.runtime.layout.CameraLayout
import com.sappyoak.dsanalyzer.runtime.layout.CharacterLayout
import com.sappyoak.dsanalyzer.runtime.layout.CharacterSlotLayout
import com.sappyoak.dsanalyzer.runtime.layout.ChrCtrlLayout
import com.sappyoak.dsanalyzer.runtime.layout.ChrInsLayout
import com.sappyoak.dsanalyzer.runtime.layout.DebugDrawLayout
import com.sappyoak.dsanalyzer.runtime.layout.Displaced
import com.sappyoak.dsanalyzer.runtime.layout.EsdStateLayout
import com.sappyoak.dsanalyzer.runtime.layout.EventFlagLayout
import com.sappyoak.dsanalyzer.runtime.layout.FieldLayout
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.layout.GameManLayout
import com.sappyoak.dsanalyzer.runtime.layout.GoalLayout
import com.sappyoak.dsanalyzer.runtime.layout.GoalTypeLayout
import com.sappyoak.dsanalyzer.runtime.layout.HitInsLayout
import com.sappyoak.dsanalyzer.runtime.layout.IdentityLayout
import com.sappyoak.dsanalyzer.runtime.layout.LoadQueueLayout
import com.sappyoak.dsanalyzer.runtime.layout.MailboxLayout
import com.sappyoak.dsanalyzer.runtime.layout.MenuLayout
import com.sappyoak.dsanalyzer.runtime.layout.PartFilterLayout
import com.sappyoak.dsanalyzer.runtime.layout.PlacedPartsLayout
import com.sappyoak.dsanalyzer.runtime.layout.PlayerGameDataLayout
import com.sappyoak.dsanalyzer.runtime.layout.PoseLayout
import com.sappyoak.dsanalyzer.runtime.layout.PositionLayout
import com.sappyoak.dsanalyzer.runtime.layout.ProgressLayout
import com.sappyoak.dsanalyzer.runtime.layout.ResistBlockLayout
import com.sappyoak.dsanalyzer.runtime.layout.SpecialEffectLayout
import com.sappyoak.dsanalyzer.runtime.layout.StatBlockLayout
import com.sappyoak.dsanalyzer.runtime.layout.TargetLayout
import com.sappyoak.dsanalyzer.runtime.layout.TargetingLayout
import com.sappyoak.dsanalyzer.runtime.layout.ThinkLayout
import com.sappyoak.dsanalyzer.runtime.layout.VitalsLayout
import com.sappyoak.dsanalyzer.runtime.layout.WriteLayout

internal fun dsrLayout(version: DSRVersion): GameLayout = GameLayout(
    camera = CameraLayout(
        pointer = ChrCam.Pointer,
        transform = ChrCam.Transform,
        fieldOfView = ChrCam.FieldOfView,
        aspect = ChrCam.Aspect,
        near = ChrCam.Near,
        far = ChrCam.Far,
        smoothedPosition = null,
        orientation = null,
        targetPitch = null
    ),
    characters = CharacterLayout(
        manager = WorldChrMan.Pointer,
        player = WorldChrMan.PlayerPointer,
        deathCam = WorldChrMan.DeathCamActive,
        playerSlots = WorldChrMan.PlayerSlots,
        blocks = WorldChrMan.Blocks,
        blockCharacters = WorldBlockChr.Characters,
        slot = CharacterSlotLayout(size = CHARACTER_SLOT_SIZE, character = CharacterSlot.Character),
        chr = ChrInsLayout(
            handle = version.chr(ChrIns.Handle),
            modelId = version.chr(ChrIns.ModelId),
            npcParamId = version.chr(ChrIns.NpcParamId),
            chrType = version.chr(ChrIns.ChrType),
            teamType = version.chr(ChrIns.TeamType),
            health = version.chr(ChrIns.Health),
            healthMax = version.chr(ChrIns.HealthMax),
            stamina = version.chr(ChrIns.Stamina),
            target = TargetLayout.InManipulator(ComManipulator.TargetHandle),
            flags1 = version.chr(ChrIns.Flags1),
            flags2 = version.chr(ChrIns.Flags2),
            playRegion = version.chr(ChrIns.PlayRegion),
            specialEffect = version.chr(ChrIns.SpecialEffectPointer),
            control = version.chr(ChrIns.ChrCtrlPointer),
            comManipulator = version.chr(ChrIns.ComManipulatorPointer),
            position = PositionLayout(x = version.chr(ChrIns.Position), stride = Float.SIZE_BYTES),
            size = CHR_INS_SIZE
        ),
        control = ChrCtrlLayout(
            position = ChrCtrl.PositionPointer,
            animData = ChrCtrl.AnimDataPointer,
            flags = ChrCtrl.Flags,
            playSpeed = AnimData.PlaySpeed,
            size = CHR_CTRL_SIZE
        ),
        pose = PoseLayout(position = ChrPosData.Position, angle = ChrPosData.Angle, size = POSE_SIZE),
        world = GameManLayout(
            pointer = GameMan.Pointer,
            stablePosition = GameMan.StablePosition - version.gameManShift,
            stableAngle = GameMan.StableAngle - version.gameManShift,
            lastBonfire = GameMan.LastBonfire - version.gameManShift
        )
    ),
    ai = AILayout(
        think = ThinkLayout(
            fromManipulator = ComManipulator.AIThinkPointer,
            manipulatorOwner = ComManipulator.ThinkOwner,
            owner = AIThink.OwnerPointer,
            targeting = AIThink.TargetingSystemPointer,
            topGoal = AIThink.TopGoalPointer,
            timers = AIThink.Timers,
            timerCount = AIThink.TIMER_COUNT,
            numbers = AIThink.Numbers,
            numberCount = AIThink.NUMBER_COUNT,
            thinkId = AIThink.ThinkId,
            size = AIThink.SIZE
        ),
        targeting = TargetingLayout(
            known = TargetingSystem.Known,
            nodeKind = TargetNode.Kind,
            nodeNext = TargetNode.Next,
            nodeShape = TargetNode.Shape,
            nodeWeight = TargetNode.Weight,
            nodeHandle = TargetNode.Handle,
            nodeSize = TargetNode.SIZE
        ),
        goal = GoalLayout(
            think = Goal.ThinkPointer,
            self = Goal.SelfPointer,
            life = Goal.Life,
            subgoalMap = Goal.SubgoalMap,
            subgoalMapSize = Goal.SubgoalMapSize,
            subgoalOffset = Goal.SubgoalOffset,
            subgoalCount = Goal.SubgoalCount,
            dequeBlock = Goal.DEQUE_BLOCK,
            params = Goal.Params,
            paramCount = Goal.PARAM_COUNT,
            extraParamsBegin = Goal.ExtraParamsBegin,
            extraParamsEnd = Goal.ExtraParamsEnd,
            type = Goal.TypePointer,
            size = Goal.SIZE
        ),
        goalType = GoalTypeLayout(
            name = GoalType.Name,
            nameLength = GoalType.NameLength,
            nameCapacity = GoalType.NameCapacity,
            inlineCapacity = GoalType.INLINE_CAPACITY,
            id = GoalType.Id,
            size = GoalType.SIZE
        ),
        pointerWidth = Long.SIZE_BYTES
    ),
    action = ActionLayout(
        fromControl = ChrCtrl.ActionCtrlPointer,
        slots = ActionCtrl.MachineSlots,
        slotStride = ActionCtrl.MachineSlotStride,
        slotCount = ActionCtrl.MACHINE_SLOT_COUNT,
        slotEnabled = ActionCtrl.MachineSlotEnabled,
        state = EsdStateLayout.Inline(ActionCtrl.MachineSlotState),
        playingAction = ActionCtrl.PlayingAction,
        playingTime = ActionCtrl.PlayingTime,
        requestedAction = ActionCtrl.RequestedAction,
        esdFlags = ActionCtrl.EsdFlags,
        ladderState = ActionCtrl.LadderState,
        damageAnimation = ActionCtrl.DamageAnimation,
        damageLevel = ActionCtrl.DamageLevel,
        size = ActionCtrl.RequestedAction + Int.SIZE_BYTES,
        events = null
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
            pendingWeight = AnimSlot.PreviousLocalTime,
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
    eventFlags = EventFlagLayout(pointer = EventFlags.Pointer),
    menu = MenuLayout(pointer = MenuMan.Pointer, arrayBase = MenuMan.ArrayBase),
    playerGameData = PlayerGameDataLayout(
        pointer = PlayerGameData.Pointer,
        vitals = VitalsLayout(
            hp = PlayerGameData.Hp,
            maxHp = PlayerGameData.MaxHp,
            baseMaxHp = PlayerGameData.BaseMaxMp,
            mp = PlayerGameData.Mp,
            maxMp = PlayerGameData.MaxMp,
            baseMaxMp = PlayerGameData.BaseMaxMp,
            sp = PlayerGameData.Sp,
            maxSp = PlayerGameData.MaxSp,
            baseMaxSp = PlayerGameData.BaseMaxSp
        ),
        stats = StatBlockLayout(
            first = PlayerGameData.Stats,
            stride = PlayerGameData.StatStride,
            count = 8
        ),
        progress = ProgressLayout(
            humanity = PlayerGameData.Humanity,
            durability = PlayerGameData.Durability,
            soulLevel = PlayerGameData.SoulLevel,
            souls = PlayerGameData.Souls,
            soulMemory = PlayerGameData.SoulMemory,
            covenant = null
        ),
        identity = IdentityLayout(
            gender = PlayerGameData.Gender,
            archeType = PlayerGameData.ArcheType,
            appearance = PlayerGameData.Appearance,
            gift = PlayerGameData.Gift
        ),
        resists = ResistBlockLayout(
            poison = PlayerGameData.PoisonResist,
            blood = PlayerGameData.BloodResist,
            disease = PlayerGameData.DiseaseResist,
            curse = PlayerGameData.CurseResist
        ),
        stance = null
    ),
    placedParts = PlacedPartsLayout(
        manager = WorldChrMan.Pointer,
        blocks = WorldChrMan.Blocks,
        blockSize = WORLD_BLOCK_CHR_SIZE,
        mapData = WorldBlockChr.MapDataPointer,
        partsList = MsbResCap.PartsList,
        parts = MapParts.All,
        partSize = MAP_PART_SIZE,
        type = MapPart.Type,
        enemyType = MapPartType.Enemy,
        name = MapPart.Name,
        entityId = MapPart.EntityId,
        position = MapPart.Position,
        npcParamId = MapPart.NpcParamId,
        thinkParamId = MapPart.ThinkParamId
    ),
    loadQueue = LoadQueueLayout(
        pointer = WorldInfoOwner.Pointer,
        currentMap = WorldInfoOwner.CurrentMap,
        previousMap = WorldInfoOwner.PreviousMap,
        warpTarget = WorldInfoOwner.WarpTarget,
        blocks = WorldRes.Blocks,
        blockSize = WORLD_BLOCK_RES_SIZE,
        blockInfos = WorldInfoOwner.Blocks,
        blockInfoSize = WORLD_BLOCK_INFO_SIZE,
        packedMapId = WorldBlockInfo.MapId,
        suppress = WorldBlockRes.Suppress,
        forceLoad = WorldBlockRes.ForceLoad,
        requested = WorldBlockRes.Requested,
        latched = WorldBlockRes.Latched,
        wantLoaded = WorldBlockRes.WantLoaded,
        stage = WorldBlockRes.Stage,
        // Remastered's WorldBlockRes has not been walked as far as its pins
        pinLoaded = null,
        pinOther = null,
        groupMasks = null
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
            collisionType = MapPartType.Collision,
            relative = true
        )
    ),
    writes = WriteLayout(
        chrFlags1 = FieldLayout(Player.Pointer, ChrIns.Flags1),
        chrFlags2 = FieldLayout(Player.Pointer, ChrIns.Flags2),
        mapFlags = FieldLayout(ChrCtrl.Pointer, ChrCtrl.Flags),
        hitboxDraw = FieldLayout(AttackMan.Pointer, AttackMan.HitboxDraw),
        cheatBits = DSRCheatBits,
        mailbox = MailboxLayout(DSRHookSites.Mailbox, Displaced.RipLoadRax, CallArch.X64)
    )
)

