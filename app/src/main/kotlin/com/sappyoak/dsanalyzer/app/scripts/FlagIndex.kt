package com.sappyoak.dsanalyzer.app.scripts

import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagTarget
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagUse
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

/** A flag use and the event it was found in */
public data class FoundFlagUse(
    public val script: ScriptId,
    public val eventId: Long,
    public val use: FlagUse
)

/** One place a script has something to do with a flag */
public data class FlagReference(
    public val script: ScriptId,
    public val eventId: Long,
    /** The instruction which is what says whether the flag is set here or handed to the game */
    public val label: String,
    public val access: FlagAccess,
    /** Named as part of a span of flags rather than on its own */
    public val viaSpan: Boolean
)

/**
 * Where ever script mentions every flag
 *
 * Spans are kept as spans rather than expanded. One 'SetFlagRangeState' can cover thousands of ids,
 * so writing each into a map would turn a few hundred references into hundreds of thousands of
 * entries
 */
public class FlagIndex private constructor(
    private val byFlag: Map<Int, List<FlagReference>>,
    private val spans: List<SpannedReference>,
    public val scripts: Int,
    /**
     * References whose flag the event's caller passes in
     *
     * They cannot be looked up by flag id, so they are counted rather than indexed
     */
    public val parameterized: Int
) {
    public val built: Boolean get() = scripts > 0

    public operator fun get(flagId: Int): List<FlagReference> =
        byFlag[flagId].orEmpty() + spans.filter { flagId in it.span }.map { it.reference }

    /**
     * Everywhere this flag is mentioned, likeliest explanation first
     *
     * What writes a flag outranks what reads it. A flag changing while the character stands in
     * one map is routinely the work of another map's script, which is the whole reason for searching
     * all of them
     */
    public fun explain(flagId: Int, seenIn: MapId? = null): List<FlagReference> =
        get(flagId).sortedWith(ranking(seenIn))

    private fun ranking(seenIn: MapId?): Comparator<FlagReference> = compareBy(
        { if (it.access == FlagAccess.Writes) 0 else 1 },
        { if (it.viaSpan) 1 else 0 },
        { it.script.closeness(seenIn) },
        { it.script.label },
        { it.eventId }
    )

    private data class SpannedReference(val span: FlagTarget.Span, val reference: FlagReference)

    public companion object {
        public val Empty: FlagIndex = FlagIndex(emptyMap(), emptyList(), scripts = 0, parameterized = 0)

        public fun of(found: List<FoundFlagUse>, scripts: Int): FlagIndex {
            val byFlag = mutableMapOf<Int, MutableList<FlagReference>>()
            val spans = mutableListOf<SpannedReference>()
            var parameterized = 0

            found.forEach { entry ->
                when (val target = entry.use.target) {
                    is FlagTarget.One -> byFlag.getOrPut(target.flagId) { mutableListOf() }.add(entry.reference())
                    is FlagTarget.Span -> spans.add(SpannedReference(target, entry.reference(viaSpan = true)))
                    FlagTarget.FromCaller -> parameterized++
                    is FlagTarget.SlotRelative -> parameterized++
                }
            }

            return FlagIndex(byFlag, spans, scripts, parameterized)
        }
    }
}

private fun FoundFlagUse.reference(viaSpan: Boolean = false) = FlagReference(
    script = script,
    eventId = eventId,
    label = use.label,
    access = use.access,
    viaSpan = viaSpan
)
/** How near a script is to where the flag was seen */
private fun ScriptId.closeness(seenIn: MapId?): Int = when {
    seenIn != null && this == ScriptId.Of(seenIn) -> 0
    this == ScriptId.Common -> 1
    else -> 2
}