package com.sappyoak.dsanalyzer.formats.tae.template

public enum class EventBank(internal val key: String) {
    Character("chr"),
    Object("obj");
}

public data class KnownValue(
    public val value: Long,
    public val name: String,
    public val note: String? = null
)

/**
 * One parameter inside an event's packed bytes
 *
 * A parameter with no [name] is one the templates only assert a value for.
 */
public data class ParamDefinition(
    public val type: ParamType,
    public val name: String? = null,
    /** The value the templates say this should be */
    public val asserted: String? = null,
    public val values: List<KnownValue> = emptyList()
) {
    private val byValue: Map<Long, KnownValue> = values.associateBy { it.value }

    /** What [value] is called when it is one of the values this parameter is known to take */
    public fun nameOf(value: Long): KnownValue? = byValue[value]
}

public data class EventDefinition(
    public val type: Int,
    public val name: String,
    public val params: List<ParamDefinition>
) {
    /** How many bytes the parameters take */
    public val packedSize: Int = params.sumOf { it.type.size }
}