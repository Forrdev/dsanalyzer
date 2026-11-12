package com.sappyoak.dsanalyzer.runtime.read.character

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.layout.Stat
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.session.AttributeSnapshot
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.PlayerSnapshot
import com.sappyoak.dsanalyzer.runtime.session.ResistSnapshot
import com.sappyoak.dsanalyzer.runtime.session.WorldSnapshot
import com.sappyoak.dsanalyzer.runtime.session.cheatsIn


internal class PlayerDetailsReader(layout: GameLayout) {
    private val sheet = layout.playerGameData
    private val chars = layout.characters
    private val cheatBits = layout.writes.cheatBits

    private val attributes = StructView(sheet.pointer)
    private val worldState = StructView(chars.world.pointer)
    private val control = StructView(chars.control.size)
    private val position = StructView(chars.pose.size)
    private val animation = StructView(chars.control.playSpeed + Float.SIZE_BYTES)

    val structures: Int = 5
    var present: Int = 0
        private set

    fun read(game: GameMemory, character: StructView, base: CharacterSnapshot): PlayerSnapshot {
        val sheetRead = attributes.refresh(game)
        val worldRead = worldState.refresh(game)
        val controlRead = refreshAt(game, control, character.pointer(chars.chr.control))
        val positionRead = controlRead && refreshAt(game, position, control.pointer(chars.control.position))
        val animationRead = controlRead && refreshAt(game, animation, control.pointer(chars.control.animData))
        present = listOf(sheetRead, worldRead, controlRead, positionRead, animationRead).count { it }

        return PlayerSnapshot(
            character = base,
            angle = if (positionRead) position.float(chars.pose.angle) else 0f,
            playRegion = character.int(chars.chr.playRegion),
            animationSpeed = if (animationRead) animation.float(chars.control.playSpeed) else null,
            cheats = cheatsIn(
                bits = cheatBits,
                flags1 = character.int(chars.chr.flags1),
                flags2 = character.int(chars.chr.flags2),
                mapFlags = if (controlRead) control.int(chars.control.flags) else 0
            ),
            attributes = if (sheetRead) attributesOf(attributes) else null
        )
    }

    fun world(deathCam: Boolean): WorldSnapshot? {
        if (!worldState.isPresent) return null
        val world = chars.world
        return WorldSnapshot(
            stablePosition = worldState.vec3(world.stablePosition),
            stableAngle = worldState.float(world.stableAngle),
            lastBonfire = worldState.int(world.lastBonfire),
            deathCam = deathCam
        )
    }

    private fun attributesOf(view: StructView): AttributeSnapshot {
        val vitals = sheet.vitals
        val progress = sheet.progress
        val identity = sheet.identity
        val resists = sheet.resists
        return AttributeSnapshot(
            healthMax = view.int(vitals.maxHp),
            healthBaseMax = view.int(vitals.baseMaxHp),
            mp = view.int(vitals.mp),
            mpMax = view.int(vitals.maxMp),
            staminaMax = view.int(vitals.maxSp),
            staminaBaseMax = view.int(vitals.baseMaxSp),
            stats = Stat.entries.associateWith { view.int(sheet.stats.offsetOf(it)) },
            soulLevel = view.int(progress.soulLevel),
            souls = view.int(progress.souls),
            soulMemory = view.int(progress.soulMemory),
            humanity = view.int(progress.humanity),
            covenant = progress.covenant?.let(view::unsigned),
            stance = sheet.stance?.let(view::int),
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
