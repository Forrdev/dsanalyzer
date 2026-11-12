package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.layout.AILayout
import com.sappyoak.dsanalyzer.runtime.layout.ActionLayout
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
import com.sappyoak.dsanalyzer.runtime.layout.EntityTableLayout
import com.sappyoak.dsanalyzer.runtime.layout.EsdStateLayout
import com.sappyoak.dsanalyzer.runtime.layout.EventFieldsLayout
import com.sappyoak.dsanalyzer.runtime.layout.EventFlagLayout
import com.sappyoak.dsanalyzer.runtime.layout.FieldLayout
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.layout.GameManLayout
import com.sappyoak.dsanalyzer.runtime.layout.GoalLayout
import com.sappyoak.dsanalyzer.runtime.layout.GoalTypeLayout
import com.sappyoak.dsanalyzer.runtime.layout.HitInsLayout
import com.sappyoak.dsanalyzer.runtime.layout.IdentityLayout
import com.sappyoak.dsanalyzer.runtime.layout.LevelUpLayout
import com.sappyoak.dsanalyzer.runtime.layout.LivePositionLayout
import com.sappyoak.dsanalyzer.runtime.layout.LoadQueueLayout
import com.sappyoak.dsanalyzer.runtime.layout.MailboxLayout
import com.sappyoak.dsanalyzer.runtime.layout.MenuLayout
import com.sappyoak.dsanalyzer.runtime.layout.PartFilterLayout
import com.sappyoak.dsanalyzer.runtime.layout.PlacedPartsLayout
import com.sappyoak.dsanalyzer.runtime.layout.PlayerGameDataLayout
import com.sappyoak.dsanalyzer.runtime.layout.PointWarpLayout
import com.sappyoak.dsanalyzer.runtime.layout.PoseLayout
import com.sappyoak.dsanalyzer.runtime.layout.PositionLayout
import com.sappyoak.dsanalyzer.runtime.layout.ProgressLayout
import com.sappyoak.dsanalyzer.runtime.layout.ResistBlockLayout
import com.sappyoak.dsanalyzer.runtime.layout.SpecialEffectLayout
import com.sappyoak.dsanalyzer.runtime.layout.StatBlockLayout
import com.sappyoak.dsanalyzer.runtime.layout.StatWriteLayout
import com.sappyoak.dsanalyzer.runtime.layout.TalkEventLayout
import com.sappyoak.dsanalyzer.runtime.layout.TalkLayout
import com.sappyoak.dsanalyzer.runtime.layout.TargetLayout
import com.sappyoak.dsanalyzer.runtime.layout.TargetingLayout
import com.sappyoak.dsanalyzer.runtime.layout.ThinkLayout
import com.sappyoak.dsanalyzer.runtime.layout.VitalsLayout
import com.sappyoak.dsanalyzer.runtime.layout.WarpLayout
import com.sappyoak.dsanalyzer.runtime.layout.WriteLayout
import com.sappyoak.dsanalyzer.runtime.session.CHEAT_BITS
import com.sappyoak.dsanalyzer.runtime.write.mailbox.CallRegister

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
    ai = AILayout(
        think = ThinkLayout(
            fromManipulator = ComManipulator.AIThinkPointer,
            manipulatorOwner = ComManipulator.ThinkOwner,
            owner = AIThink.OwnerPointer,
            targeting = AIThink.TargetingSystemPointer,
            topGoal = AIThink.TopGoalPointer,
            timers = AIThink.Timers,
            timerCount = AI_THINK_TIMER_COUNT,
            numbers = AIThink.Numbers,
            numberCount = AI_THINK_NUMBER_COUNT,
            thinkId = AIThink.ThinkId,
            size = AI_THINK_SIZE
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
        pointerWidth = Int.SIZE_BYTES
    ),
    action = ActionLayout(
        fromControl = ChrCtrl.ActionCtrlPointer,
        slots = ActionCtrl.MachineSlots,
        slotStride = ActionCtrl.MachineSlotStride,
        slotCount = ActionCtrl.MACHINE_SLOT_COUNT,
        slotEnabled = ActionCtrl.MachineSlotEnabled,
        state = EsdStateLayout.ThroughMachine(ActionCtrl.MachineState),
        playingAction = ActionCtrl.PlayingAction,
        playingTime = ActionCtrl.PlayingTime,
        requestedAction = ActionCtrl.RequestedAction,
        esdFlags = ActionCtrl.EsdFlags,
        ladderState = ActionCtrl.LadderState,
        damageAnimation = ActionCtrl.DamageAnimation,
        damageLevel = ActionCtrl.DamageLevel,
        size = ActionCtrl.RequestedAction + Int.SIZE_BYTES,
        events = EventFieldsLayout(
            commands = ChrIns.EventCommands,
            commandCount = ChrIns.EventCommandCount,
            message = ChrIns.EventMessage,
            actionPointer = ChrIns.EventActionPointer,
            actionType = ChrEventAction.Type,
            actionFirst = ChrEventAction.First,
            actionSecond = ChrEventAction.Second,
            actionSize = ChrEventAction.SIZE
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
    eventFlags = EventFlagLayout(pointer = EventFlags.Pointer),
    menu = MenuLayout(pointer = MenuMan.Pointer, arrayBase = MenuMan.ARRAY_BASE),
    placedParts = PlacedPartsLayout(
        manager = WorldChrMan.Pointer,
        blocks = WorldChrMan.Blocks,
        blockSize = WORLD_BLOCK_SIZE,
        mapData = WorldBlock.MapDataPointer,
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
            count = 8,
            resistance = PlayerGameData.Resistance
        ),
        progress = ProgressLayout(
            humanity = PlayerGameData.Humanity,
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
        cheatBits = CHEAT_BITS,
        mailbox = MailboxLayout.Code(PTDEHookSites.Mailbox),
        position = LivePositionLayout(
            chrCtrl = ChrCtrl.Pointer,
            warp = ChrCtrl.Warp,
            warpPosition = ChrCtrl.WarpPosition,
            warpAngle = ChrCtrl.WarpAngle,
            posData = ChrCtrl.PositionPointer,
            position = ChrPosData.Position,
            angle = ChrPosData.Angle,
            proxy = ChrPosData.ProxyPointer,
            proxyVelocity = CharacterProxy.Velocity,
            velocity = ChrPosData.Velocity,
            airTimer = ChrPosData.AirTimer
        ),
        stats = StatWriteLayout(
            newGame = FieldLayout(GameDataMan.Pointer, GameDataMan.NewGameCount),
            covenant = PlayerGameData.Covenant,
            covenantPoints = PlayerGameData.WarriorsOfSunlightPoints,
            levelUp = LevelUpLayout.OneBlock(PTDEFunctions.LevelUp)
        ),
        warp = WarpLayout(
            lastBonfire = FieldLayout(GameMan.Pointer, GameMan.LastBonfire),
            point = PointWarpLayout(
                map = GameMan.PointWarpMap,
                position = GameMan.PointWarpPosition,
                angle = GameMan.PointWarpAngle,
                flag = GameMan.PointWarpFlag
            ),
            function = PTDEFunctions.WarpDispatch,
            owner = PTDEFunctions.WarpOwner,
            ownerRegister = CallRegister.Esi,
            kindRegister = null
        ),
        talk = TalkLayout(
            dispatcher = PTDEFunctions.TalkDispatch,
            ownerLookup = PTDEFunctions.TalkOwnerLookup,
            eventVTable = PTDEFunctions.TalkEventVTable,
            handlerVTable = PTDEFunctions.TalkHandlerVTable,
            event = TalkEventLayout(
                size = TalkEvent.Size,
                command = TalkEvent.Command,
                firstParam = TalkEvent.FirstParam,
                paramStride = TalkEvent.ParamStride,
                paramType = TalkEvent.ParamType,
                highestIndex = TalkEvent.HighestIndex,
                maxParams = TalkEvent.MaxParams
            ),
            handlerOwner = TalkHandler.Owner,
            ownerEntity = TalkOwner.EntityId,
            ownerLink = null,
            entities = EntityTableLayout(
                manager = WorldChrMan.Pointer,
                blocks = WorldChrMan.Blocks,
                count = WorldBlock.EntityCount,
                entries = WorldBlock.EntitiesPointer
            )
        )
    )
)
