package com.sappyoak.dsanalyzer.game.world.flags

import com.sappyoak.dsanalyzer.formats.emevd.EventNames
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Opcode
import com.sappyoak.dsanalyzer.formats.param.refs.items
import com.sappyoak.dsanalyzer.game.ref.WorldRef
import com.sappyoak.dsanalyzer.game.world.params.GameParam
import com.sappyoak.dsanalyzer.game.world.text.TextCategory

private val SET_FLAG = Opcode(2003, 2)
private val REGISTER_BONFIRE = Opcode(2009, 3)
private val REGISTER_LADDER = Opcode(2009, 0)
private val SNUGGLY_ITEM = Opcode(2003, 34)
private val BOSS_BAR = Opcode(2003, 11)
private val BOSS_DEFEAT = Opcode(2003, 12)
private val CHARACTER_DEAD = Opcode(4, 0)
private val OBJECT_DESTROYED = Opcode(5, 0)
private val ACTION_BUTTONS = setOf(Opcode(3, 5), Opcode(3, 13), Opcode(3, 18), Opcode(3, 19))
private val ITEM_AWARDS = setOf(Opcode(2003, 4), Opcode(2003, 36))
private val MESSAGES = setOf(Opcode(2007, 1), Opcode(2007, 3), Opcode(2007, 4))
private val IN_AREA = Opcode(3, 2)
private val ALL_IN_AREA = Opcode(3, 7)

private const val ON = 1
private const val DEAD = 1
private const val DESTROYED = 1
private const val INSIDE = 1

/**
 * Flags the event scripts give a meaning to. Registrations name their flag directly; every other flag
 * an event turns on takes the event's strongest trigger, and its name where the game's own list has one
 */
internal fun NamingContext.scriptFlags(events: List<BoundEvent>, names: (BoundEvent) -> EventNames): List<Pair<Int, FlagEvidence>> =
    events.flatMap { event ->
        val source = WorldRef.ScriptEvent(event.script, event.eventId)
        val direct = event.instructions.flatMap { registrations(it, source) }
        val trigger = event.instructions.mapNotNull { trigger(it, event.instructions) }.minByOrNull { it.kind.ordinal }
        val named = names(event)[event.eventId]?.let { Trigger(FlagKind.EventState, it) }
        val readings = listOfNotNull(trigger, named).map { FlagEvidence(it.kind, it.label, source, direct = false) }

        val written = event.instructions.filter { it.opcode == SET_FLAG && it.int(1) == ON }.mapNotNull { it.int(0) }
        // An event's own id is, by convention, the flag that records it ran
        val own = listOfNotNull(named?.let { event.eventId.toInt() namedBy FlagEvidence(it.kind, it.label, source, direct = false) })
        direct + own + written.flatMap { flag -> readings.map { flag namedBy it } }
    }

/** What an event is about, before it is tied to a flag */
private data class Trigger(val kind: FlagKind, val label: String)

private fun NamingContext.registrations(instruction: BoundInstruction, source: WorldRef): List<Pair<Int, FlagEvidence>> {
    fun direct(flag: Int?, kind: FlagKind, label: String) =
        flag?.takeIf { it > 0 }?.let { it namedBy FlagEvidence(kind, label, source, direct = true) }

    return when (instruction.opcode) {
        REGISTER_BONFIRE -> listOfNotNull(direct(instruction.int(0), FlagKind.Bonfire, "Bonfire lit: ${entity(instruction.int(1))}"))
        REGISTER_LADDER -> {
            val ladder = entity(instruction.int(2))
            listOfNotNull(
                direct(instruction.int(0), FlagKind.Ladder, "Ladder top blocked: $ladder"),
                direct(instruction.int(1), FlagKind.Ladder, "Ladder bottom blocked: $ladder")
            )
        }
        SNUGGLY_ITEM -> listOfNotNull(direct(instruction.int(2), FlagKind.ItemPickup, "Traded for ${lot(instruction.int(0))}"))
        else -> emptyList()
    }
}

/** What [instruction] says the event is about */
private fun NamingContext.trigger(instruction: BoundInstruction, all: List<BoundInstruction>): Trigger? {
    return when (instruction.opcode) {
        BOSS_DEFEAT -> Trigger(FlagKind.BossDefeated, "Defeated ${bossName(instruction.int(0), all)}")
        CHARACTER_DEAD -> if (instruction.int(2) == DEAD) Trigger(FlagKind.CharacterDead, "Killed ${entity(instruction.int(1))}") else null
        OBJECT_DESTROYED -> if (instruction.int(1) == DESTROYED) Trigger(FlagKind.ObjectState, "Destroyed ${entity(instruction.int(2))}") else null
        in ACTION_BUTTONS -> {
            val prompt = instruction.int(6)?.let { text(TextCategory.EventText, it) } ?: "Used"
            Trigger(FlagKind.ObjectState, "$prompt: ${entity(instruction.int(2))}")
        }
        in ITEM_AWARDS -> Trigger(FlagKind.ItemPickup, "Given ${lot(instruction.int(0))}")
        in MESSAGES -> instruction.int(0)?.let { text(TextCategory.EventText, it) }?.let { Trigger(FlagKind.Message, "Shown \"$it\"") }
        IN_AREA -> if (instruction.int(1) == INSIDE) Trigger(FlagKind.Region, "Entered ${entity(instruction.int(3))}") else null
        ALL_IN_AREA -> if (instruction.int(1) == INSIDE) Trigger(FlagKind.Region, "Entered ${entity(instruction.int(2))}") else null
        else -> null
    }
}

/** The boss bar's name for the same character, else the character's own */
private fun NamingContext.bossName(entityId: Int?, all: List<BoundInstruction>): String {
    val bar = all.firstOrNull { it.opcode == BOSS_BAR && it.int(1) == entityId }
    return bar?.int(3)?.let { text(TextCategory.NpcNames, it) } ?: entity(entityId)
}

private fun NamingContext.entity(entityId: Int?): String = entityId?.let(entities::label) ?: "an entity from the caller"

private fun NamingContext.lot(lotId: Int?): String {
    val row = lotId?.let { params[GameParam.ItemLotParam][it] } ?: return "lot ${lotId ?: "from the caller"}"
    return row.items.filterNotNull().joinToString { item(it) }.ifEmpty { "lot $lotId" }
}
