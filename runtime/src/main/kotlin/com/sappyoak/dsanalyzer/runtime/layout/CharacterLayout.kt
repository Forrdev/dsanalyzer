package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable

public data class CharacterLayout(
    public val manager: GamePointer,
    public val player: Int,
    public val deathCam: Int,
    public val playerSlots: GameTable,
    public val blocks: GameTable,
    public val blockCharacters: GameTable,
    public val slot: CharacterSlotLayout,
    public val chr: ChrInsLayout,
    public val control: ChrCtrlLayout,
    public val pose: PoseLayout,
    public val world: GameManLayout
)

public data class CharacterSlotLayout(
    public val size: Int,
    public val character: Int
)

public data class ChrInsLayout(
    public val handle: Int,
    public val modelId: Int,
    public val npcParamId: Int,
    public val chrType: Int,
    public val teamType: Int,
    public val health: Int,
    public val healthMax: Int,
    public val stamina: Int,
    public val target: TargetLayout,
    public val flags1: Int,
    public val flags2: Int,
    public val playRegion: Int,
    public val specialEffect: Int,
    public val control: Int,
    public val comManipulator: Int,
    public val position: PositionLayout,
    public val size: Int
)

public data class PositionLayout(public val x: Int, public val stride: Int)

/** Where a character's current target handle is */
public sealed interface TargetLayout {
    public data class InCharacter(public val offset: Int) : TargetLayout
    public data class InManipulator(public val offset: Int) : TargetLayout
}

public data class ChrCtrlLayout(
    public val position: Int,
    public val animData: Int,
    public val flags: Int,
    /** In the structure [animData] points at */
    public val playSpeed: Int,
    public val size: Int
)

/** The structure that holds a character's live position */
public data class PoseLayout(
    public val position: Int,
    public val angle: Int,
    public val size: Int
)

public data class GameManLayout(
    public val pointer: GamePointer,
    public val stablePosition: Int,
    public val stableAngle: Int,
    public val lastBonfire: Int
)
