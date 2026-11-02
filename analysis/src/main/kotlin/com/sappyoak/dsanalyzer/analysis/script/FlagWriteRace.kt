package com.sappyoak.dsanalyzer.analysis.script

import com.sappyoak.dsanalyzer.analysis.Citation
import com.sappyoak.dsanalyzer.analysis.engine.AnalysisContext
import com.sappyoak.dsanalyzer.analysis.engine.AnalysisResult
import com.sappyoak.dsanalyzer.analysis.engine.Analyzer
import com.sappyoak.dsanalyzer.analysis.engine.AnalyzerCost
import com.sappyoak.dsanalyzer.analysis.engine.AnalyzerDescriptor
import com.sappyoak.dsanalyzer.analysis.engine.AnalyzerId
import com.sappyoak.dsanalyzer.analysis.engine.AnalyzerParameter
import com.sappyoak.dsanalyzer.analysis.engine.BugPattern
import com.sappyoak.dsanalyzer.analysis.engine.Confidence
import com.sappyoak.dsanalyzer.analysis.engine.ContextKey
import com.sappyoak.dsanalyzer.analysis.engine.Coverage
import com.sappyoak.dsanalyzer.analysis.engine.Gap
import com.sappyoak.dsanalyzer.analysis.engine.Lead
import com.sappyoak.dsanalyzer.analysis.engine.LeadKey
import com.sappyoak.dsanalyzer.analysis.engine.LeadKind
import com.sappyoak.dsanalyzer.analysis.engine.ParameterKind
import com.sappyoak.dsanalyzer.analysis.engine.resultOf
import com.sappyoak.dsanalyzer.game.ref.WorldRef
import com.sappyoak.dsanalyzer.game.ref.toDataAddress
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.title

private val ID = AnalyzerId("script.flag-write-race")
private val RACE = LeadKind("flag-write-race", "Flag written by several events", weight = 8)

/** Writers listed by name in a lead, the rest are counted */
private const val WRITERS_SHOWN = 6

private val MIN_WRITERS = AnalyzerParameter(
    id = "minWriters",
    displayName = "Minimum writers",
    kind = ParameterKind.Threshold,
    description = "How many events must write a flag before it is reported. Two is common and usually " +
            "deliberate; three or more is where unintended interleaving tends to live.",
    default = 3.0,
    range = 2.0..10.0
)

public object FlagWriteRace : Analyzer<DecodedScript> {
    override val input: ContextKey<DecodedScript> get() = ScriptInput

    override val descriptor: AnalyzerDescriptor = AnalyzerDescriptor(
        id = ID,
        version = 1,
        displayName = "Flag write races",
        summary = "Flags written by several events with nothing ordering them",
        mechanism = "Several events write one flag and nothing sequences them, so which wins depends on " +
                "the order they run in, which the player can sometimes force into a tie.",
        kinds = listOf(RACE),
        patterns = setOf(BugPattern.SameFrameRace),
        cost = AnalyzerCost.Cheap,
        parameters = listOf(MIN_WRITERS)
    )

    override suspend fun analyze(context: AnalysisContext<DecodedScript>): AnalysisResult {
        val script = context.data
        val flags = FlagTally.of(script)
        val minimum = context.run.valueOf(MIN_WRITERS).toInt()

        val leads = flags.writers
            .filter { (flag, writers) -> writers.size >= minimum && flags.isRead(flag) }
            .map { (flag, writers) -> leadFor(script.script, flag, writers) }

        val gaps = listOfNotNull(
            Gap(flags.unpinned, "name a flag the caller passes in or the slot decides")
                .takeIf { flags.unpinned > 0 },
            Gap(flags.spanWrites, "write a span of flags, which is not attributed to the flags in it")
                .takeIf { flags.spanWrites > 0 }
        )
        return resultOf(leads, Coverage(script.events.size, "events", gaps))
    }

    private fun leadFor(script: ScriptId, flag: Int, writers: Map<Long, Boolean>): Lead {
        val inverts = writers.values.any { it }
        val events = writers.keys.sorted()

        return Lead(
            key = LeadKey.of(ID, "${script.title}/$flag"),
            kind = RACE,
            title = "Flag $flag is written by ${events.size} events in ${script.title}",
            detail = buildString {
                append("Events ${events.take(WRITERS_SHOWN).joinToString()}")
                if (events.size > WRITERS_SHOWN) append(" and ${events.size - WRITERS_SHOWN} more")
                append(" all write flag $flag, and the script reads it. ")
                if (inverts) {
                    append("At least one inverts it rather than setting it, so the result depends on how often ")
                    append("each ran. ")
                }
                append("Nothing here checks whether they can run on the same frame.")
            },
            confidence = if (inverts) Confidence.Medium else Confidence.Low,
            citations = listOf(cite(WorldRef.EventFlag(flag, (script as? ScriptId.Of)?.map), "flag $flag")) +
                    events.take(WRITERS_SHOWN).map { cite(WorldRef.ScriptEvent(script, it), "event $it") },
            reproduction = listOf(
                "Find whether the writing events can be triggered together.",
                "If they can, force the tie and see which branch the readers take.",
                "Check whether the losing branch leaves state that is never cleaned up."
            ),
            patterns = setOf(BugPattern.SameFrameRace),
            precedent = "Ornstein & Smough same-frame deaths"
        )
    }

    private fun cite(ref: WorldRef, label: String): Citation = Citation.of(ref.toDataAddress(), label)
}
