package com.sappyoak.dsanalyzer.runtime.dsr

/** 'ChrIns' fields from here up move by [DSRVersion.chrHighShift], the rest by [DSRVersion.chrLowShift] */
internal const val CHR_HIGH_PART: Int = 0x380

/** The handle and the header before it do not move */
internal const val CHR_FIXED_PART: Int = 0x10

/**
 * The build, by main module size. The shifts are for offsets. Before 1.0.3.
 * 'ChrIns' fields below [CHR_HIGH_PART] sit [chrLowShift] lower and fields from there
 * up [chrHighShift] lower. 'GameMan' sits [gameManShift] lower on the first two builds,
 * [Unknown] reads as 1.0.3.x
 */
public enum class DSRVersion(
    public val modSize: Int,
    public val chrLowShift: Int = 0,
    public val chrHighShift: Int = 0,
    public val gameManShift: Int = 0
) {
    V1_01(0x4869400, chrLowShift = 0x20, chrHighShift = 0x10, gameManShift = 0x10),
    V1_01_1(0x496BE00, chrLowShift = 0x20, chrHighShift = 0x10, gameManShift = 0x10),
    V1_01_2(0x37CB400, chrLowShift = 0x20, chrHighShift = 0x10),
    V1_03_1(0x3817800),
    V1_03_2(0x319B000),
    Unknown(-1);

    public companion object {
        public fun from(size: Int): DSRVersion = entries.firstOrNull { it.modSize == size } ?: Unknown
    }
}

/** A 1.0.3.x 'ChrIns' offset where it sits on [this] build */
internal fun DSRVersion.chr(offset: Int): Int = when {
    offset < CHR_FIXED_PART -> offset
    offset < CHR_HIGH_PART -> offset - chrLowShift
    else -> offset - chrHighShift
}
