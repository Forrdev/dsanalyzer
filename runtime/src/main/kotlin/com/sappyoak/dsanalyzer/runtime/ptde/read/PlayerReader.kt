package com.sappyoak.dsanalyzer.runtime.ptde.read

import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.ptde.AnimData
import com.sappyoak.dsanalyzer.runtime.ptde.ChrPosData
import com.sappyoak.dsanalyzer.runtime.ptde.ChrCtrl
import com.sappyoak.dsanalyzer.runtime.ptde.ChrIns
import com.sappyoak.dsanalyzer.runtime.ptde.DeathCam
import com.sappyoak.dsanalyzer.runtime.ptde.GameDataMan
import com.sappyoak.dsanalyzer.runtime.ptde.PlayerStats
import com.sappyoak.dsanalyzer.runtime.ptde.WorldState
import com.sappyoak.dsanalyzer.runtime.session.AttributeSnapshot
import com.sappyoak.dsanalyzer.runtime.session.PlayerSnapshot
import com.sappyoak.dsanalyzer.runtime.session.WorldSnapshot

internal class PlayerReader {
    private val character = StructView(ChrIns.Pointer)
    private val position = StructView(ChrPosData.Pointer)
    private val placement = StructView(ChrCtrl.Pointer)
    private val animation = StructView(AnimData.Pointer)
    private val attributes = StructView(PlayerStats.Pointer)
    private val worldState = StructView(WorldState.Pointer)
    private val deathCam = StructView(DeathCam.Pointer)
    private val gameData = StructView(GameDataMan.Pointer)

    private val all = listOf(
        character, position, placement, animation, attributes, worldState, deathCam, gameData
    )

    val structures: Int get() = all.size

    fun refresh(memory: GameMemory): Int = all.count { it.refresh(memory) }

    fun timeMillis(): Int = if (gameData.isPresent) gameData.int(GameDataMan.InGameTimeMillis) else 0

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
            stablePosition = worldState.vec3(WorldState.StablePosition),
            stableAngle = worldState.float(WorldState.StableAngle),
            lastBonfire = worldState.int(WorldState.LastBonfire),
            deathCam = deathCam.isPresent && deathCam.unsigned(DeathCam.Active) != 0
        )
    }

    private fun attributes(): AttributeSnapshot? {
        if (!attributes.isPresent) return null

        return AttributeSnapshot(
            healthMax = attributes.int(PlayerStats.HealthMax),
            staminaMax = attributes.int(PlayerStats.StaminaMax),
            soulLevel = attributes.int(PlayerStats.SoulLevel),
            souls = attributes.int(PlayerStats.Souls),
            humanity = attributes.int(PlayerStats.Humanity),
            covenant = attributes.unsigned(PlayerStats.Covenant),
            stance = attributes.int(PlayerStats.Stance)
        )
    }
}