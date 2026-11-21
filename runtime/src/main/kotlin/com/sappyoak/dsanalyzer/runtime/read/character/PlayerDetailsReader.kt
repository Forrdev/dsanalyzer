package com.sappyoak.dsanalyzer.runtime.read.character

import com.sappyoak.dsanalyzer.runtime.layout.CharacterLayout
import com.sappyoak.dsanalyzer.runtime.layout.PlayerGameDataLayout
import com.sappyoak.dsanalyzer.runtime.layout.Stat
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.snapshot.AttributeSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.CheatBit
import com.sappyoak.dsanalyzer.runtime.snapshot.CheatFlag
import com.sappyoak.dsanalyzer.runtime.snapshot.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.PlayerSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.ResistSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.WorldSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.cheatsIn

/** The player's own fields on top of its [CharacterSnapshot], and the world state around it */
internal class PlayerDetailsReader(
    private val chars: CharacterLayout,
    private val sheet: PlayerGameDataLayout,
    private val cheatBits: Map<CheatFlag, CheatBit>
) {
    private val attributes = StructView(sheet.pointer)
    private val worldState = StructView(chars.world.pointer)

    val structures: Int = 5
    var present: Int = 0
        private set

    /** [player] already refreshed, so its ChrCtrl and pose are shared with [base]'s reading */
    fun read(game: GameMemory, player: CharacterView, base: CharacterSnapshot): PlayerSnapshot {
        val sheetRead = attributes.refresh(game)
        val control = player.control()
        val pose = player.pose()
        val animation = player.animData()
        present = listOf(sheetRead, worldState.isPresent, control != null, pose != null, animation != null).count { it }

        return PlayerSnapshot(
            character = base,
            angle = pose?.float(chars.pose.angle) ?: 0f,
            playRegion = player.fields.int(chars.chr.playRegion),
            animationSpeed = animation?.float(chars.control.playSpeed),
            cheats = cheatsIn(
                bits = cheatBits,
                flags1 = player.fields.int(chars.chr.flags1),
                flags2 = player.fields.int(chars.chr.flags2),
                mapFlags = control?.int(chars.control.flags) ?: 0
            ),
            attributes = if (sheetRead) attributesOf(attributes) else null
        )
    }

    fun world(game: GameMemory, deathCam: Boolean): WorldSnapshot? {
        if (!worldState.refresh(game)) return null
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
}
