package com.sappyoak.dsanalyzer.shared.layout

import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Kept on screen with enough showing to be grabbed again.
 *
 * A panel that ended up entirely off the viewport could only be recovered by editing settings by
 * hand, which is a poor outcome for a resize nobody asked the tool about.
 */
private const val MARGIN = 0.01f

public data class PanelAnchor(public val x: Float, public val y: Float) {
    /**
     * Where this panel goes after the window changes size.
     *
     * Per axis, because a window can change aspect ratio
     */
    public fun reanchored(oldWidth: Int, oldHeight: Int, newWidth: Int, newHeight: Int): PanelAnchor =
        PanelAnchor(reanchor(x, oldWidth, newWidth), reanchor(y, oldHeight, newHeight))
}

/**
 * The new fractional position of one axis after a resize
 *
 * Neither purely fractional or purely absolute would be correct here, because
 * which one should be preserved depends on where the panel is. A panel near an edge is
 * tucked into a corner, and two units from the edge means two units whatever the screen size.
 * A panel near the center sits in the middle and the middle of a bigger window is further from
 * the edge, so it blends by centrality
 *
 * 'centrality = 1 - 2 * |fraction - 0.5|' is one at the center and zero at either edge,
 * and weights between the two relationships continuously
 *
 * The edge-anchored branch scales the gap by '(sqrt(oldSize/newSize))' rather than holding it
 * fixed or scaling it fully. That makes the resulting pixel offset the geometric mean of the
 * two candidate answers
 */
public fun reanchor(fraction: Float, oldSize: Int, newSize: Int): Float {
    if (oldSize <= 0 || newSize <= 0 || oldSize == newSize) return fraction

    val damping = sqrt(oldSize.toFloat() / newSize.toFloat())
    val centrality = 1f - 2f * abs(fraction - 0.5f)

    val edgeAnchored = if (fraction < 0.5f) {
        fraction * damping
    } else {
        // Measured from the far edge, so a right-hand panel keeps its right-hand gap rather than
        // its left-hand one. Without this a bottom-right readout crawls toward the middle every
        // time the window grows.
        1f - (1f - fraction) * damping
    }

    // At the centre this is entirely proportional, which is what makes the two branches agree
    // there: the discontinuity a hard left/right split would introduce is weighted to nothing
    // exactly where the branches disagree most.
    val blended = centrality * fraction + (1f - centrality) * edgeAnchored

    return blended.coerceIn(MARGIN, 1f - MARGIN)
}