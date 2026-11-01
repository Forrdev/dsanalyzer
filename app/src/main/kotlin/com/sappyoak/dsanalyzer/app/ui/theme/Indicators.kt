package com.sappyoak.dsanalyzer.app.ui.theme

import androidx.compose.ui.graphics.Color

// should probably actually just use icons but this will do for now

public enum class Indicator(public val glyph: String) {
    /** Loaded, attached, present */
    Resident("●"),

    /** On its way -- queued, loading, in flight */
    Pending("◐"),

    /** Not there, and that is expected */
    Absent("○"),

    /** Not there, and something went wrong */
    Failed("×"),

    /** Changed by us, and still changed. The one state the tool itself causes */
    Written("◆")
}

public fun AppColors.of(indicator: Indicator): Color = when (indicator) {
    Indicator.Resident -> resident
    Indicator.Pending -> pending
    Indicator.Absent -> absent
    Indicator.Failed -> failed
    Indicator.Written -> written
}
