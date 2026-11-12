package com.sappyoak.dsanalyzer.runtime.read

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

/** Group 1, area 101, section 0, number 902 */
private const val SAMPLE = 11_010_902

/** The very last flag the block has room for: group 7, area 181, section 9, number 999 */
private const val LAST = 71_819_999

class EventFlagIdsTest : FunSpec({
    test("a flag lands where its digits say, with the bit counted from the high end") {
        val at = checkNotNull(EventFlagIds.locate(SAMPLE))
        assertSoftly {
            at.byteOffset shouldBe 0xF70
            at.mask shouldBe (0x80000000u shr 6)
        }
    }

    test("the first flag is the first bit of the block") {
        EventFlagIds.locate(0) shouldBe FlagLocation(0, 0x80000000u)
    }

    test("the last flag ends exactly at the end of the block") {
        val at = checkNotNull(EventFlagIds.locate(LAST))
        assertSoftly {
            at.mask shouldBe (0x80000000u shr 7)
            at.byteOffset + Int.SIZE_BYTES shouldBe EventFlagIds.BLOCK_SIZE
        }
    }

    test("every flag that locates finds its way back to the same id") {
        val ids = listOf(0, SAMPLE, LAST, 11_020_000, 51_100_309, 60_000_031, 71_310_512)
        assertSoftly {
            ids.forEach { id ->
                val at = checkNotNull(EventFlagIds.locate(id)) { "$id did not locate" }
                val bit = Integer.numberOfLeadingZeros(at.mask.toInt())
                EventFlagIds.flagAt(at.byteOffset, bit) shouldBe id
            }
        }
    }

    test("group zero holds one area, so pairing it with another is not a flag") {
        assertSoftly {
            EventFlagIds.locate(0) shouldBe FlagLocation(0, 0x80000000u)
            EventFlagIds.locate(1_000_000) shouldBe null
        }
    }

    test("digits that name no group or no area are not flags") {
        assertSoftly {
            EventFlagIds.locate(21_000_000) shouldBe null
            EventFlagIds.locate(10_990_000) shouldBe null
            EventFlagIds.locate(-1) shouldBe null
        }
    }

    test("the tail of a section addresses numbers the digits cannot hold") {
        assertSoftly {
            EventFlagIds.flagAt(0x57C, 7) shouldBe 10_000_999
            EventFlagIds.flagAt(0x57C, 8) shouldBe null
        }
    }

    test("a bit outside the block, or midway through a word, belongs to nothing") {
        assertSoftly {
            EventFlagIds.flagAt(EventFlagIds.BLOCK_SIZE, 0) shouldBe null
            EventFlagIds.flagAt(-4, 0) shouldBe null
            EventFlagIds.flagAt(0x502, 0) shouldBe null
            EventFlagIds.flagAt(0, 32) shouldBe null
        }
    }
})
