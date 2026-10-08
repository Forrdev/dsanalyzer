package com.sappyoak.dsanalyzer.formats.tae.template

/**
 * The types a TAE event parameter can hold.
 *
 * Parameters are packed one after another with **no alignment**, so [size] is the whole
 * story about where the next one begins
 */
public enum class ParamType(public val size: Int) {
    B(1),
    U8(1),
    S8(1),
    U16(2),
    S16(2),
    S32(4),
    F32(4),
    F32Grad(8);

    public companion object {
        private val BY_NAME: Map<String, ParamType> = entries.associateBy { it.name.lowercase() }

        public fun of(name: String): ParamType? = BY_NAME[name.lowercase()]
    }
}