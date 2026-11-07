package com.sappyoak.dsanalyzer.runtime.read.character

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.PlayerGameDataLayout
import com.sappyoak.dsanalyzer.runtime.layout.Stat
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.ptde.AnimData
import com.sappyoak.dsanalyzer.runtime.ptde.ChrCtrl
import com.sappyoak.dsanalyzer.runtime.ptde.ChrIns
import com.sappyoak.dsanalyzer.runtime.ptde.ChrPosData
import com.sappyoak.dsanalyzer.runtime.ptde.GameMan
import com.sappyoak.dsanalyzer.runtime.read.cheatsIn
import com.sappyoak.dsanalyzer.runtime.session.AttributeSnapshot
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.PlayerSnapshot
import com.sappyoak.dsanalyzer.runtime.session.ResistSnapshot
import com.sappyoak.dsanalyzer.runtime.session.WorldSnapshot

internal class PTDEPlayerDetailsReader(private val layout: PlayerGameDataLayout) {
    private val attributes = StructView(layout.pointer)
    private val worldState = StructView(GameMan.Pointer)
    private val control = StructView(ChrCtrl.Pointer.size)
    private val position = StructView(ChrPosData.Pointer.size)
    private val animation = StructView(AnimData.PlaySpeed + Float.SIZE_BYTES)

    val structures: Int = 5
    var present: Int = 0
        private set

    fun read(game: GameMemory, character: StructView, base: CharacterSnapshot): PlayerSnapshot {
        val sheetRead = attributes.refresh(game)
        val worldRead = worldState.refresh(game)
        val controller = character.pointer(ChrIns.ChrCtrlPointer)
        val controlRead = refreshAt(game, control, controller)
        val positionRead = controlRead && refreshAt(game, position, control.pointer(ChrCtrl.PositionPointer))
        val animationRead = controlRead && refreshAt(game, animation, control.pointer(ChrCtrl.AnimDataPointer))
        present = listOf(sheetRead, worldRead, controlRead, positionRead, animationRead).count { it }

        return PlayerSnapshot(
            character = base,
            angle = if (positionRead) position.float(ChrPosData.Angle) else 0f,
            playRegion = character.int(ChrIns.PlayRegion),
            animationSpeed = if (animationRead) animation.float(AnimData.PlaySpeed) else null,
            cheats = cheatsIn(
                flags1 = character.int(ChrIns.Flags1),
                flags2 = character.int(ChrIns.Flags2),
                mapFlags = if (controlRead) control.int(ChrCtrl.Flags) else 0
            ),
            attributes = if (sheetRead) attributesOf(attributes) else null
        )
    }

    fun world(deathCam: Boolean): WorldSnapshot? {
        if (!worldState.isPresent) return null
        return WorldSnapshot(
            stablePosition = worldState.vec3(GameMan.StablePosition),
            stableAngle = worldState.float(GameMan.StableAngle),
            lastBonfire = worldState.int(GameMan.LastBonfire),
            deathCam = deathCam
        )
    }

    private fun attributesOf(view: StructView): AttributeSnapshot {
        val vitals = layout.vitals
        val progress = layout.progress
        val identity = layout.identity
        val resists = layout.resists
        return AttributeSnapshot(
            healthMax = view.int(vitals.maxHp),
            healthBaseMax = view.int(vitals.baseMaxHp),
            mp = view.int(vitals.mp),
            mpMax = view.int(vitals.maxMp),
            staminaMax = view.int(vitals.maxSp),
            staminaBaseMax = view.int(vitals.baseMaxSp),
            stats = Stat.entries.associateWith { view.int(layout.stats.offsetOf(it)) },
            soulLevel = view.int(progress.soulLevel),
            souls = view.int(progress.souls),
            soulMemory = view.int(progress.soulMemory),
            humanity = view.int(progress.humanity),
            durability = view.int(progress.durability),
            covenant = progress.covenant?.let(view::unsigned),
            stance = layout.stance?.let(view::int),
            gender = view.unsigned(identity.gender),
            startingClass = view.unsigned(identity.archeType),
            physique = view.unsigned(identity.appearance),
            gift = view.unsigned(identity.gift),
            resists = ResistSnapshot(
                poison = view.int(resists.poison),
                bleed = view.int(resists.blood),
                toxic = view.int(resists.disease),
                curse = view.int(resists.curse)
            )
        )
    }

    private fun refreshAt(game: GameMemory, view: StructView, at: Address): Boolean =
        !at.isNull && view.refresh(game.memory, at)
}

