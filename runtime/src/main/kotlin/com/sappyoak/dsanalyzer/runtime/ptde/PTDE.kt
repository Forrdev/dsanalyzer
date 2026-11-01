package com.sappyoak.dsanalyzer.runtime.ptde

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
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.session.EditionTables

public val PTDEPointers: List<GamePointer> = listOf(
    FollowCam.Pointer,
    ChrIns.Pointer,
    ChrCtrl.Pointer,
    ChrPosData.Pointer,
    AnimData.Pointer,
    AnimRequestMgr.Pointer,
    SpecialEffects.Pointer,
    HitIns.Pointer,
    WorldChrMan.Pointer,
    WorldRes.Pointer,
    EmevdMan.Pointer,
    PlayerGameData.Pointer,
    GameMan.Pointer,
    WorldArea.Pointer,
    GameDataMan.Pointer,
    EventFlags.Pointer,
    EventFlags.Manager,
    MenuMan.Pointer,
    Havok.Manager,
    AttackMan.Pointer,
    BulletMan.Pointer
)

/**
 * Every collection the tables name, each paired with the structure it is read out of
 */
public val PTDETables: List<Pair<GamePointer, GameTable>> = listOf(
    SpecialEffects.Pointer to SpecialEffects.Active,
    WorldChrMan.Pointer to WorldChrMan.Blocks,
    WorldRes.Pointer to WorldRes.Blocks,
    WorldRes.Pointer to WorldRes.BlockInfos,
    EmevdMan.Pointer to EmevdMan.Scripts
)

internal val PTDELayout: GameLayout = GameLayout(
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
            manager = null,
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
        pinOther = WorldBlockRes.PinOther
    )
)

public val PTDEEdition: EditionTables = EditionTables(
    pointers = PTDEPointers,
    layout = PTDELayout,
    buildOf = { memory, module -> memory.ptdeVersion(module)?.name }
)
