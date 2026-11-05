package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.layout.AnimChannelLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimControlLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimRequestBand
import com.sappyoak.dsanalyzer.runtime.layout.AnimRequestEntryLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimRequestLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimSlotLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimationLayout
import com.sappyoak.dsanalyzer.runtime.layout.CameraLayout
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.layout.IdentityLayout
import com.sappyoak.dsanalyzer.runtime.layout.LoadQueueLayout
import com.sappyoak.dsanalyzer.runtime.layout.PlayerGameDataLayout
import com.sappyoak.dsanalyzer.runtime.layout.ProgressLayout
import com.sappyoak.dsanalyzer.runtime.layout.ResistBlockLayout
import com.sappyoak.dsanalyzer.runtime.layout.SpecialEffectLayout
import com.sappyoak.dsanalyzer.runtime.layout.StatBlockLayout
import com.sappyoak.dsanalyzer.runtime.layout.VitalsLayout
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

internal val DSRLayout: GameLayout = GameLayout(
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
            count = 0
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
        pinOther = null
    )
)

public val DSREdition: EditionTables = EditionTables(
    pointers = DSRPointers,
    layout = DSRLayout,
    buildOf = { memory, module -> DSRVersion.from(module.size.toInt()).name }
)
