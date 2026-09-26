package com.sappyoak.dsanalyzer.formats.emevd.emedf

public data class ArgDefinition(
    public val name: String,
    public val type: ArgType,
    /** Names a set of known values for this argument */
    public val enumName: String?,
    /** what the game uses when the instruction leaves this argument off the end */
    public val default: Double
) {
    public val reference: ArgReference? = ArgReference.of(name)
}

/** THe primitive types an argument can hold */
public enum class ArgType(public val size: Int, public val signed: Boolean) {
    UByte(1, false),
    UShort(2, false),
    UInt(4, false),
    Byte(1, true),
    Short(2, true),
    Int(4, true),
    Float(4, true);

    /** Arguments sit at a multiple of their own widths, so this is where one starting at [at] lands */
    internal fun align(at: Int): Int = if (at % size == 0) at else at + size - at % size
}
