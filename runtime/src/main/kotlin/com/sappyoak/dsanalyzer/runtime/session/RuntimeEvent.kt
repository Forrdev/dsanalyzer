package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.runtime.GameProcess
import com.sappyoak.dsanalyzer.runtime.pointers.ResolvedPointers
import com.sappyoak.dsanalyzer.runtime.ptde.PTDEBuild

public sealed interface RuntimeEvent {
    public data object Searching : RuntimeEvent

    public data class Attached(
        public val game: GameProcess,
        public val build: PTDEBuild?,
        public val pointers: ResolvedPointers
    ) : RuntimeEvent

    public data class Sampled(public val snapshot: RuntimeSnapshot) : RuntimeEvent


    public data class Refused(
        public val game: GameProcess,
        public val reason: String
    ) : RuntimeEvent

    public data class Unsupported(public val game: GameProcess) : RuntimeEvent

    public data class Failed(public val reason: String) : RuntimeEvent
}
