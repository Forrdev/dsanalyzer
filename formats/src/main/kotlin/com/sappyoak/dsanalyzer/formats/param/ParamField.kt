package com.sappyoak.dsanalyzer.formats.param

import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

public class ParamField<in R : ParamRow>(
    /** The property's 'behaviorVariationId' */
    public val name: String,
    public val type: ParamFieldType,
    public val offset: Int,
    /** Set fora  bitfield, where in its storage unit it starts */
    public val bit: Int?,
    /** Set for a bitfield, how many bits it takes */
    public val bitCount: Int?,
    /** The enum the paramdef types it as */
    public val enumName: String?,
    /** The paramdef's display name, Japanese as shipped */
    public val label: String,
    private val read: (R) -> Any
) {
    public fun valueOf(row: R): Any = read(row)
}

/** A field together with what one row holds in it */
public class FieldValue(
    public val field: ParamField<*>,
    public val value: Any
)

/** A field of the row at [index], in file order whose value a search accepted */
public class FieldMatch(
    public val index: Int,
    public val field: ParamField<*>,
    public val value: Any
)
