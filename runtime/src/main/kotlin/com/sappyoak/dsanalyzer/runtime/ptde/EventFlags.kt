package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/** Where a flag's bit sits in the block */
public data class FlagLocation(public val byteOffset: Int, public val mask: UInt)

/**
 * Finding an event flag's bit.
 *
 * Flags are not a flat array. An eight digit id decomposes positionally into a group, an area, a
 * section and a number, and group and area are **table lookups rather than arithmetic**: the
 * group bases are irregular and the area codes are the game's own sparse map identifiers. Working
 * either out by division, which the digits invite, lands inside the block and reads a real bit
 * belonging to a different flag — not a crash, a wrong answer that looks like a working reader.
 *
 * The bit is addressed from the **high end** of its word, so a conventional low-end shift
 * reverses the order of every group of thirty-two flags.
 *
 */
public object EventFlags {
    /** Irregular by design; these are not a multiple of anything */
    private val GROUP_BASES = mapOf(
        0 to 0x00000,
        1 to 0x00500,
        5 to 0x05F00,
        6 to 0x0B900,
        7 to 0x11300
    )

    /** The game's own map identifiers, in the order the block lays them out */
    private val AREA_CODES = intArrayOf(
        0, 100, 101, 102, 110, 120, 121, 130, 131, 132, 140, 141, 150, 151, 160, 170, 180, 181
    )

    private val AREA_INDICES: Map<Int, Int> =
        AREA_CODES.withIndex().associate { (index, code) -> code to index }

    private const val AREA_STRIDE = 0x500
    private const val SECTION_STRIDE = 128
    private const val PER_WORD = 32
    private const val MAX_NUMBER = 999
    private const val HIGH_BIT = 0x80000000u

    /** The block runs to the end of the last group, which holds every area */
    public const val BlockSize: Int = 0x11300 + 18 * AREA_STRIDE

    /**
     * How many areas each group has room for, which is not the same for all of them.
     *
     * Group 0 is one area wide, so a flag that pairs it with any other area would otherwise be
     * given an offset inside group 1 and read a bit that belongs to something else
     */
    private val GROUP_AREAS: Map<Int, Int> = run {
        val ordered = GROUP_BASES.entries.sortedBy { it.value }
        ordered.mapIndexed { index, entry ->
            val end = ordered.getOrNull(index + 1)?.value ?: BlockSize
            entry.key to (end - entry.value) / AREA_STRIDE
        }.toMap()
    }

    public val Pointer: GamePointer = GamePointer(
        name = "EventFlags",
        base = Signature.parse(
            name = "EventFlags",
            pattern = "56 8B F1 8B 46 1C 50 A1 ?? ?? ?? ?? 32 C9",
            target = SignatureTarget.Embedded(8)
        ),
        offsets = listOf(0L, 0L),
        lifetime = Lifetime.Session,
        size = BlockSize
    )

    /** Where [flagId] lives, or null when those digits are not a flag the block has room for */
    public fun locate(flagId: Int): FlagLocation? {
        if (flagId < 0 || flagId > 99_999_999) return null

        val group = flagId / 10_000_000
        val areaCode = (flagId / 10_000) % 1_000
        val section = (flagId / 1_000) % 10
        val number = flagId % 1_000

        val base = GROUP_BASES[group] ?: return null
        val areaIndex = AREA_INDICES[areaCode] ?: return null
        if (areaIndex >= GROUP_AREAS.getValue(group)) return null

        val offset = base +
                areaIndex * AREA_STRIDE +
                section * SECTION_STRIDE +
                (number / PER_WORD) * Int.SIZE_BYTES

        return FlagLocation(offset, HIGH_BIT shr (number % PER_WORD))
    }

    /**
     * Which flag the bit at [byteOffset] and [bit] belongs to, or null where nothing does.
     */
    public fun flagAt(byteOffset: Int, bit: Int): Int? {
        if (bit !in 0 until PER_WORD) return null
        if (byteOffset < 0 || byteOffset >= BlockSize || byteOffset % Int.SIZE_BYTES != 0) return null

        val group = GROUP_BASES.entries.filter { it.value <= byteOffset }.maxByOrNull { it.value } ?: return null
        val withinGroup = byteOffset - group.value
        val areaIndex = withinGroup / AREA_STRIDE
        if (areaIndex >= GROUP_AREAS.getValue(group.key)) return null

        val withinArea = withinGroup % AREA_STRIDE
        val section = withinArea / SECTION_STRIDE
        val number = (withinArea % SECTION_STRIDE) / Int.SIZE_BYTES * PER_WORD + bit
        if (number > MAX_NUMBER) return null

        return group.key * 10_000_000 + AREA_CODES[areaIndex] * 10_000 + section * 1_000 + number
    }
}