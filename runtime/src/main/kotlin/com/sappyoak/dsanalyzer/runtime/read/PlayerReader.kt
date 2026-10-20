package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.ptde.AnimData
import com.sappyoak.dsanalyzer.runtime.ptde.ChrPosData
import com.sappyoak.dsanalyzer.runtime.ptde.ChrCtrl
import com.sappyoak.dsanalyzer.runtime.ptde.ChrIns
import com.sappyoak.dsanalyzer.runtime.ptde.GameDataMan
import com.sappyoak.dsanalyzer.runtime.ptde.GameMan
import com.sappyoak.dsanalyzer.runtime.ptde.PlayerGameData
import com.sappyoak.dsanalyzer.runtime.ptde.WorldChrMan
import com.sappyoak.dsanalyzer.runtime.session.AttributeSnapshot
import com.sappyoak.dsanalyzer.runtime.session.PlayerSnapshot
import com.sappyoak.dsanalyzer.runtime.session.WorldSnapshot

internal class PlayerReader {
    private val manager = StructView(WorldChrMan.Pointer)
    private val character = StructView(ChrIns.Pointer)
    private val position = StructView(ChrPosData.Pointer)
    private val placement = StructView(ChrCtrl.Pointer)
    private val animation = StructView(AnimData.Pointer)
    private val attributes = StructView(PlayerGameData.Pointer)
    private val worldState = StructView(GameMan.Pointer)
    private val gameData = StructView(GameDataMan.Pointer)

    private val all = listOf(
        character, position, placement, animation, attributes, worldState, gameData, manager
    )

    val structures: Int get() = all.size

    fun refresh(memory: GameMemory): Int = all.count { it.refresh(memory) }

    fun timeMillis(): Int = if (gameData.isPresent) gameData.int(GameDataMan.InGameTimeMillis) else 0

    fun frame(): Long = GameDataMan.frameOf(timeMillis())

    fun player(): PlayerSnapshot? {
        if (!character.isPresent || !position.isPresent) return null

        return PlayerSnapshot(
            position = position.vec3(ChrPosData.Position),
            angle = position.float(ChrPosData.Angle),
            health = character.int(ChrIns.Health),
            stamina = character.int(ChrIns.Stamina),
            characterType = character.int(ChrIns.ChrType),
            teamType = character.int(ChrIns.TeamType),
            playRegion = character.int(ChrIns.PlayRegion),
            animationSpeed = if (animation.isPresent) animation.float(AnimData.PlaySpeed) else null,
            cheats = cheatsIn(
                flags1 = character.int(ChrIns.Flags1),
                flags2 = character.int(ChrIns.Flags2),
                mapFlags = if (placement.isPresent) placement.int(ChrCtrl.Flags) else 0
            ),
            attributes = attributes()
        )
    }

    fun world(): WorldSnapshot? {
        if (!worldState.isPresent) return null

        return WorldSnapshot(
            stablePosition = worldState.vec3(GameMan.StablePosition),
            stableAngle = worldState.float(GameMan.StableAngle),
            lastBonfire = worldState.int(GameMan.LastBonfire),
            deathCam = manager.isPresent && manager.boolean(WorldChrMan.DeathCamActive)
        )
    }

    private fun attributes(): AttributeSnapshot? {
        if (!attributes.isPresent) return null

        return AttributeSnapshot(
            healthMax = attributes.int(PlayerGameData.MaxHp),
            staminaMax = attributes.int(PlayerGameData.MaxStamina),
            soulLevel = attributes.int(PlayerGameData.SoulLevel),
            souls = attributes.int(PlayerGameData.Souls),
            humanity = attributes.int(PlayerGameData.Humanity),
            covenant = attributes.unsigned(PlayerGameData.Covenant),
            stance = attributes.int(PlayerGameData.Stance)
        )
    }
}