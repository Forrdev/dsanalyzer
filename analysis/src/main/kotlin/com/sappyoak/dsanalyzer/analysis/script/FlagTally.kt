package com.sappyoak.dsanalyzer.analysis.script

import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgValue
import com.sappyoak.dsanalyzer.formats.emevd.emedf.DecodedInstruction
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagTarget
import com.sappyoak.dsanalyzer.formats.emevd.emedf.flagUses

private const val FLAG_STATE = "ON/OFF/CHANGE"
private const val CHANGE = 2L

internal class FlagTally private constructor(
    val writers: Map<Int, Map<Long, Boolean>>,
    private val reads: Set<Int>,
    private val readSpans: List<FlagTarget.Span>,
    val unpinned: Int,
    val spanWrites: Int
) {
    fun isRead(flag: Int): Boolean = flag in reads || readSpans.any { flag in it }

    public companion object {
        public fun of(script: DecodedScript): FlagTally {
            val writers = HashMap<Int, MutableMap<Long, Boolean>>()
            val reads = HashSet<Int>()
            val readSpans = ArrayList<FlagTarget.Span>()
            var unpinned = 0
            var spanWrites = 0

            for ((eventId, instructions) in script.events) {
                for (instruction in instructions) {
                    for (use in instruction.flagUses(eventId)) {
                        when (val target = use.target) {
                            is FlagTarget.One -> if (use.access == FlagAccess.Writes) {
                                val event = writers.getOrPut(target.flagId) { HashMap() }
                                event[eventId] = event[eventId] == true || instruction.inverts()
                            } else {
                                reads += target.flagId
                            }
                            is FlagTarget.Span ->
                                if (use.access == FlagAccess.Reads) readSpans += target else spanWrites++
                            is FlagTarget.SlotRelative, FlagTarget.FromCaller -> unpinned++
                        }
                    }
                }
            }

            return FlagTally(writers, reads, readSpans, unpinned, spanWrites)
        }
    }
}


private fun DecodedInstruction.inverts(): Boolean = args.any { arg ->
    arg.definition.enumName == FLAG_STATE && (arg.value as? ArgValue.Literal)?.raw == CHANGE
}
