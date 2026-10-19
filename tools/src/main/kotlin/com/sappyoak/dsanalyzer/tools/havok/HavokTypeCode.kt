package com.sappyoak.dsanalyzer.tools.havok

/** Names for 'hkClassMember.type'/'.subtype' used only to annotate generated offsets */
internal object HavokTypeCode {

    /** The composed type, e.g. `hkArray<hkpShape*>` or `enum:uint8`. */
    fun describe(type: Int, subtype: Int, cArraySize: Int): String {
        val base = when (type) {
            POINTER -> "${nameOf(subtype)}*"
            ARRAY -> "hkArray<${nameOf(subtype)}>"
            SIMPLE_ARRAY -> "hkSimpleArray<${nameOf(subtype)}>"
            ENUM -> "enum:${nameOf(subtype)}"
            FLAGS -> "flags:${nameOf(subtype)}"
            else -> nameOf(type)
        }
        return if (cArraySize > 0) "$base[$cArraySize]" else base
    }

    private const val POINTER = 20
    private const val ARRAY = 22
    private const val ENUM = 24
    private const val SIMPLE_ARRAY = 26
    private const val FLAGS = 31

    private fun nameOf(code: Int): String = when (code) {
        0 -> "void"
        1 -> "bool"
        2 -> "char"
        3 -> "int8"
        4 -> "uint8"
        5 -> "int16"
        6 -> "uint16"
        7 -> "int32"
        8 -> "uint32"
        10 -> "int64"
        11 -> "float"
        12 -> "hkVector4"
        13 -> "hkQuaternion"
        14 -> "hkMatrix3"
        15 -> "hkRotation"
        16 -> "hkQsTransform"
        17 -> "hkMatrix4"
        18 -> "hkTransform"
        POINTER -> "ptr"
        ARRAY -> "array"
        ENUM -> "enum"
        25 -> "struct"
        SIMPLE_ARRAY -> "simpleArray"
        28 -> "homogeneousArray"
        29 -> "char*"
        30 -> "hkUlong"
        FLAGS -> "flags"
        32 -> "hkHalf"
        33 -> "hkStringPtr"
        else -> "type$code"
    }
}