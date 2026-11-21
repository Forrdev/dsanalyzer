package com.sappyoak.dsanalyzer.runtime.snapshot

import com.sappyoak.dsanalyzer.runtime.GameProcess
import com.sappyoak.dsanalyzer.runtime.pointers.ResolvedPointers

public sealed interface RuntimeEvent {
    public data object Searching : RuntimeEvent

    public data class Attached(
        public val game: GameProcess,
        public val build: String?,
        public val pointers: ResolvedPointers,
        public val supports: Set<RuntimeFeature>
    ) : RuntimeEvent

    public data class Sampled(public val snapshot: RuntimeSnapshot) : RuntimeEvent

    /** Signatures the attach scan missed and a later one found */
    public data class Resolved(public val pointers: ResolvedPointers) : RuntimeEvent

    /** The answer to a [com.sappyoak.dsanalyzer.runtime.sampling.SampleRequests.requestPlacedEnemies] */
    public data class PlacedEnemies(public val rosters: List<BlockRoster>) : RuntimeEvent

    public data class CharacterEffects(public val characters: List<CharacterSnapshot>) : RuntimeEvent

    public data class Refused(
        public val game: GameProcess,
        public val reason: String
    ) : RuntimeEvent

    public data class Unsupported(public val game: GameProcess) : RuntimeEvent

    public data class Failed(public val reason: String) : RuntimeEvent
}
