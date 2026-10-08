package com.sappyoak.dsanalyzer.formats.tae

public class TaeAnimation(
    public val id: Long,
    public val source: AnimationSource,
    /** The '.hkx' holding this animation's motion where the file names one */
    public val fileName: String?,
    public val events: List<TaeEvent>
) {
    override fun toString(): String = "TaeAnimation($id, ${events.size} events)"
}

/** Where an animation's motion and events come from */
public sealed interface AnimationSource {
    public data class Own(
        public val loopsByDefault: Boolean,
        public val importsHkx: Boolean,
        public val allowsDelayLoad: Boolean,
        /** The animation whose '.hkx' motion this one plays when [importsHkx] is set */
        public val hkxSourceAnimationId: Int
    ) : AnimationSource

    /** Another animation's events included so this one lists none of its own */
    public data class Imported(
        public val fromAnimationId: Int,
        public val unknown: Int
    ) : AnimationSource
}