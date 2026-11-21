package com.sappyoak.dsanalyzer.game.world.flags

import com.sappyoak.dsanalyzer.game.ref.DataAddress

/** What a flag records, ordered from the most specific meaning to the least */
public enum class FlagKind(public val label: String) {
    ItemPickup("Item pickup"),
    ShopStock("Shop stock"),
    Bonfire("Bonfire"),
    Ladder("Ladder"),
    ObjectState("Object"),
    BossDefeated("Boss defeated"),
    CharacterDead("Character dead"),
    NpcState("NPC"),
    Message("Message"),
    Region("Region"),
    EventState("Event state");
}

/**
 * One reason to read a flag a certain way. [direct] evidence names this flag in the same record as the
 * thing it describes; indirect evidence only shares an event with it, so it describes what the event
 * waits for rather than this flag alone
 */
public data class FlagEvidence(
    public val kind: FlagKind,
    public val label: String,
    /** Where it was found */
    public val source: DataAddress,
    public val direct: Boolean
)

/** A flag's evidence, likeliest reading first */
public data class FlagName(public val flagId: Int, public val evidence: List<FlagEvidence>) {
    public val best: FlagEvidence get() = evidence.first()
    public val kind: FlagKind get() = best.kind
    public val label: String get() = best.label

    /** Readings that disagree with [best] in kind, which are what a person should look at */
    public val conflicts: List<FlagEvidence> get() = evidence.filter { it.direct && it.kind != best.kind }
}

/** Every flag the game data gives a meaning to */
public class FlagNames private constructor(private val byFlag: Map<Int, FlagName>) {
    public val size: Int get() = byFlag.size

    public operator fun get(flagId: Int): FlagName? = byFlag[flagId]

    public fun all(): Collection<FlagName> = byFlag.values

    /** How many flags each kind names best */
    public fun coverage(): Map<FlagKind, Int> =
        byFlag.values.groupingBy { it.kind }.eachCount().toSortedMap(compareBy { it.ordinal })

    public companion object {
        public val Empty: FlagNames = FlagNames(emptyMap())

        public fun of(found: List<Pair<Int, FlagEvidence>>): FlagNames = FlagNames(
            found.groupBy({ it.first }, { it.second }).mapValues { (flagId, evidence) ->
                FlagName(flagId, evidence.distinct().sortedWith(RANKING))
            }
        )

        private val RANKING = compareBy<FlagEvidence>({ it.weight }, { it.kind.ordinal }, { it.source.toAddress() })
    }
}

/** Kinds that read well even from a shared event: what is killed or handed over is what the event is for */
private val TELLING = setOf(FlagKind.ItemPickup, FlagKind.BossDefeated, FlagKind.CharacterDead)

/**
 * Direct evidence first. Among the rest a telling trigger beats the event's own name, and the name beats
 * a generic trigger such as a prompt or a region, which says where an event starts rather than what it does
 */
private val FlagEvidence.weight: Int
    get() = when {
        direct -> 0
        kind in TELLING -> 1
        kind == FlagKind.EventState -> 2
        else -> 3
    }

/** A flag id and why it reads as it does */
internal infix fun Int.namedBy(evidence: FlagEvidence): Pair<Int, FlagEvidence> = this to evidence
