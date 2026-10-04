package com.sappyoak.dsanalyzer.formats.fmg

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.maps.shouldContainExactly
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.FixtureWriter
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

private const val VERSION = 1
private const val UNICODE = 1

/** Two runs with a gap between them, which is what every real file is made of */
private const val FIRST_RUN_START = 10
private const val SECOND_RUN_START = 100

/** Three ids in the first run and two in the second, so the offsets table holds five */
private const val RUN_ENTRIES = 5

/** Larger than the fixture, for the file that claims a size it does not have */
private const val OVERSTATED_SIZE = 0x1000

/** Japanese, to prove the text is decoded as UTF-16 rather than bytes that happen to look right */
private const val ASH_LAKE = "灰の湖"

class FmgReaderTest : FunSpec({
    test("a run of ids reads back as the strings it names") {
        read().strings shouldContainExactly mapOf(
            10 to "Firelink Shrine",
            12 to ASH_LAKE,
            100 to "Gravelord Nito",
            101 to ""
        )
    }

    /**
     * A zero offset inside a run is how the format marks an id nothing was written for, so the run
     * covering it does not mean the id exists. Read as absent, not as an empty string, which is a
     * separate thing the file says differently
     */
    test("an id with no string is absent rather than empty") {
        val fmg = read()

        assertSoftly {
            fmg[11] shouldBe null
            fmg[101] shouldBe ""
            fmg[13] shouldBe null
        }
    }

    test("a file that opens with a hash is refused rather than read past") {
        val hashed = fmg().toByteArray().also { it[0] = 1 }

        shouldThrow<BinaryFormatException> { readFmg(BinaryReader.of(hashed)) }
    }

    test("a version this cannot read is refused by name") {
        shouldThrow<BinaryFormatException> { read(fmg(version = 2)) }
    }

    test("text encoded as anything but UTF-16 is refused") {
        shouldThrow<BinaryFormatException> { read(fmg(unicode = 0)) }
    }

    /**
     * The runs and the declared count are two statements of the same thing, and the runs are what
     * is actually indexed — so a disagreement means the header belongs to a different file
     */
    test("runs that do not cover the declared number of strings are refused") {
        shouldThrow<BinaryFormatException> { read(fmg(declaredStrings = 4)) }
    }

    test("a file claiming to be longer than it is is refused") {
        shouldThrow<BinaryFormatException> { read(fmg(overstateSize = true)) }
    }
})

private fun read(writer: FixtureWriter = fmg()): Fmg = readFmg(BinaryReader.of(writer.toByteArray()))

/**
 * A Dark Souls FMG built by hand.
 *
 * Laid out the way a writer lays one out: the header, then one record per run of consecutive ids,
 * then a packed table of string offsets in id order, then the strings. The offsets are filled in
 * as each string is written, so the fixture cannot disagree with itself about where anything is
 */
private fun fmg(
    version: Int = VERSION,
    unicode: Int = UNICODE,
    declaredStrings: Int = 5,
    overstateSize: Boolean = false
): FixtureWriter = FixtureWriter().apply {
    byte(0)
    byte(0)
    byte(version)
    byte(0)
    val sizeSlot = slot()
    byte(unicode)
    byte(0)
    byte(0)
    byte(0)
    int(2)
    int(declaredStrings)
    val offsetsSlot = slot()
    int(0)

    // Ids 10 to 12, then 100 to 101. The first run's offsets are entries 0 to 2 of the table and
    // the second run's are 3 and 4, which is what makes the table id-ordered
    int(0)
    int(FIRST_RUN_START)
    int(FIRST_RUN_START + 2)
    int(3)
    int(SECOND_RUN_START)
    int(SECOND_RUN_START + 1)

    fill(offsetsSlot)
    val strings = List(RUN_ENTRIES) { slot() }

    fill(strings[0])
    utf16("Firelink Shrine")
    // strings[1] is left at zero: id 11 is inside the run and has nothing written for it
    fill(strings[2])
    utf16(ASH_LAKE)
    fill(strings[3])
    utf16("Gravelord Nito")
    fill(strings[4])
    utf16("")

    if (overstateSize) fill(sizeSlot, OVERSTATED_SIZE) else fill(sizeSlot)
}