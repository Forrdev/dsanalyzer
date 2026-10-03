package com.sappyoak.dsanalyzer.formats.emevd.emedf

/** The first action bank. Everything below tests a flag, everything above acts on one */
private const val FIRST_ACTION_BANK = 2000

private const val ADDRESSED_BY_EVENT = 1
private const val ADDRESSED_BY_SLOT = 2

public data class FlagUse(
    public val target: FlagTarget,
    public val access: FlagAccess,
    public val label: String
)

/** Whether a script reads a flag or writes one */
public enum class FlagAccess { Reads, Writes; }

public sealed interface FlagTarget {
    public data class One(public val flagId: Int) : FlagTarget

    /** Every flag from [first] to [last] which one instruction can set in a single go */
    public data class Span(public val first: Int, public val last: Int) : FlagTarget {
        public operator fun contains(flagId: Int): Boolean = flagId in first..last
    }

    /**
     * A flag addressed as an offset from the event's initialization slot
     *
     * Which flag that is depends on the slot the event was initialized in, so it cannot be pinned
     * down from the event alone and is recorded rather than resolved
     */
    public data class SlotRelative(public val offset: Int) : FlagTarget

    /**
     * A flag the event's caller passes in, so the instruction itself does not name one
     *
     * The value is in whichever 'InitializeEvent' started this event, and following that is currently
     * not implemented yet. Recorded so a search can say it is incomplete rather than imply the flag is set
     * by nothing
     */
    public data object FromCaller : FlagTarget
}

/**
 * Every flag this instruction names, with [eventId] for the ones addressed relative to their event
 */
public fun DecodedInstruction.flagUses(eventId: Long): List<FlagUse> {
    val covering = definition ?: return emptyList()
    val flags = args.filter { it.definition.reference?.namesFlag == true }
    if (flags.isEmpty()) return emptyList()

    val access = if (instruction.bank >= FIRST_ACTION_BANK) FlagAccess.Writes else FlagAccess.Reads
    val use = { target: FlagTarget -> FlagUse(target, access, covering.label) }

    val span = flags.span()
    if (span != null) return listOf(use(span))

    val addressing = args
        .firstOrNull { it.definition.reference == ArgReference.EventFlagType }
        ?.literal()
        ?.toInt()

    return flags.map { arg ->
        val raw = arg.literal() ?: return@map use(FlagTarget.FromCaller)
        use(
            when (addressing) {
                ADDRESSED_BY_EVENT -> FlagTarget.One((eventId + raw).toInt())
                ADDRESSED_BY_SLOT -> FlagTarget.SlotRelative(raw.toInt())
                else -> FlagTarget.One(raw.toInt())
            }
        )
    }
}

private fun List<DecodedArg>.span(): FlagTarget.Span? {
    val first = firstOrNull { it.definition.reference == ArgReference.EventFlagRangeStart }?.literal()
    val last = firstOrNull { it.definition.reference == ArgReference.EventFlagRangeEnd }?.literal()

    return if (first == null || last == null) null else FlagTarget.Span(first.toInt(), last.toInt())
}

private fun DecodedArg.literal(): Long? = (value as? ArgValue.Literal)?.raw