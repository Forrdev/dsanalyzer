package com.sappyoak.dsanalyzer.formats.tae

/** How far an import chain is followed before the file is treated as pointing in a circle */
private const val MAX_IMPORT_HOPS = 8

/**
 * Animation TimeAct data
 *
 * One file covers every animation of one character or object. What it holds are events: a window on an
 * animation's timeline, with a type and a packed blob of parameters.
 */
public class Tae(
    public val id: Int,
    public val bigEndian: Boolean,
    /** The '.hkt' skeleton this was authored against */
    public val skeletonName: String?,
    /** The '.sib' authoring file */
    public val sibName: String?,
    public val animations: List<TaeAnimation>
) {
    private val byId: Map<Long, TaeAnimation> = animations.associateBy { it.id }

    public operator fun get(animationId: Long): TaeAnimation? = byId[animationId]

    /**
     * The events [animationId] actually plays, or null when it borrows them from an animation
     * this file does not have.
     *
     * An animation can import everything from another one, in which case its own event list
     * is empty and reading it on its own saying nothing about what it does
     */
    public fun eventsFor(animationId: Long): List<TaeEvent>? {
        var animation = byId[animationId] ?: return null

        repeat(MAX_IMPORT_HOPS) {
            val source = animation.source
            if (source !is AnimationSource.Imported) return animation.events
            animation = byId[source.fromAnimationId.toLong()] ?: return null
        }

        return null
    }

    override fun toString(): String = "Tae($id, ${animations.size} animations)"
}