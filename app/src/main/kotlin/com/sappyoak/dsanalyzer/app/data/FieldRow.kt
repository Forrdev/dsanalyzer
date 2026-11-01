package com.sappyoak.dsanalyzer.app.data

import com.sappyoak.dsanalyzer.game.ref.Address

/**
 * A single row entry for a data pane
 */
public data class FieldRow(
    public val name: String,
    public val value: String,
    /** An enum name, a default, where a value comes from etc. Null when the value speaks for itself */
    public val note: String? = null,
    public val link: Address? = null,
    /**
     * False where the value is not what the script says. A definition default, or a parameter the
     * caller fills in
     */
    public val stated: Boolean = true
)
