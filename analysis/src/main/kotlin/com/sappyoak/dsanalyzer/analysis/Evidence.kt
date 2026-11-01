package com.sappyoak.dsanalyzer.analysis

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.game.GameBuild

/**
 * How a claim was established, paired with the thing that establishes it
 */
@Serializable
public sealed interface Evidence {
    /** Why this supports the claim, when the citation alone doesn't make that obvious */
    public val note: String?

    /** Read in decompiled or disassembled code or in a game file */
    @Serializable
    @SerialName("read")
    public data class ReadInCode(
        public val citation: Citation,
        override val note: String? = null
    ) : Evidence

    /**
     * Observed in a running game
     */
    @Serializable
    @SerialName("measured")
    public data class MeasuredLive(
        public val citation: Citation,
        public val observedOn: GameBuild,
        override val note: String? = null
    ) : Evidence

    /** Concluded from other findings */
    @Serializable
    @SerialName("inferred")
    public data class Inferred(
        public val from: List<FindingId>,
        override val note: String? = null
    ) : Evidence {
        init {
            require(from.isNotEmpty()) {
                "Inferred evidence must name what it was inferred from. A finding with no evidence " +
                        "is how an unsupported claim is recorded"
            }
        }
    }
}
