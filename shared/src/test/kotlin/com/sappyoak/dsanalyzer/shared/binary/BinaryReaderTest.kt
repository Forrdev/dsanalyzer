package com.sappyoak.dsanalyzer.shared.binary

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.lang.foreign.MemorySegment
import java.nio.ByteOrder

fun createReaders(
    bytes: ByteArray,
    position: Int = 0,
    order: ByteOrder = ByteOrder.LITTLE_ENDIAN,
    pointerSize: PointerSize = PointerSize.IntPointer
): List<Pair<String, BinaryReader>> {
    return listOf(
        "ByteBufferReader" to BinaryReader.of(bytes, position, order, pointerSize),
        "MemorySegmentReader" to BinaryReader.of(MemorySegment.ofArray(bytes), position, order, pointerSize)
    )
}

class BinaryReaderTest : FunSpec({
    createReaders(byteArrayOf(0x01, 0x02, 0x03, 0x04)).forEach {
        test("${it.first} should read integers in the configured order") {
            val reader = it.second

            assertSoftly {
                reader.readInt().shouldBe(0x04030201)

                reader.position = 0
                reader.order = ByteOrder.BIG_ENDIAN
                reader.readInt().shouldBe(0x01020304)
            }
        }
    }

    createReaders(byteArrayOf(0x00, 0x01, 0x02, 0x03, 0x04)).forEach {
        test("${it.first} reads unaligned integers") {
            it.second.position = 1
            it.second.readInt().shouldBe(0x04030201)
        }
    }

    createReaders(byteArrayOf(-1, -1, -1, -1, 0, 0, 0, 0)).forEach {
        test("${it.first} pointer width follows the configured size") {
            val reader = it.second
            assertSoftly {
                reader.readPointer().shouldBe(0xFFFFFFFFL)
                reader.pointerSize = PointerSize.LongPointer
                reader.position = 0
                reader.readPointer().shouldBe(0xFFFFFFFFL)
            }
        }
    }

    createReaders("ab\u0000cd\u0000".toByteArray(Charsets.US_ASCII)).forEach {
        test("${it.first} reading null terminated string should consume the terminator") {
            it.second.readString().shouldBe("ab")
            it.second.readString().shouldBe("cd")
        }
    }

    createReaders(byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8)).forEach {
        test("${it.first} should restore the cursor on at calls") {
            val reader = it.second
            reader.position = 4

            val value = reader.at(0) { readInt() }

            assertSoftly {
                value.shouldBe(0x04030201)
                reader.position.shouldBe(4L)
            }
        }
    }

    createReaders(byteArrayOf(1, 2)).forEach {
        test("${it.first} should restore cursor when at block throws") {
            val reader = it.second
            reader.position = 2

            assertSoftly {
                shouldThrow<IllegalStateException> {
                    reader.at(0) { error("error") }
                }
                reader.position.shouldBe(2)
            }
        }
    }

    createReaders("XXXX".toByteArray(Charsets.US_ASCII)).forEach {
        test("${it.first} should report the offset when assertValue fails") {
            val reader = it.second

            assertSoftly {
                val failure = shouldThrow<BinaryFormatException> { reader.assertValue("TEST") { readString(4) } }
                failure.position.shouldBe(0)
                failure.message.orEmpty().shouldContain("TEST")
            }
        }
    }

    createReaders(byteArrayOf(1, 2)).forEach {
        test("${it.first} should fail with the offset when reading past its end") {
            val reader = it.second

            assertSoftly {
                val failure = shouldThrow<BinaryFormatException> { reader.readInt() }
                failure.position.shouldBe(0)
            }
        }
    }

    createReaders(byteArrayOf(9, 9, 1, 2, 3, 4)).forEach {
        test("${it.first} slices should carry their own cursor") {
            val reader = it.second
            val slice = reader.slice(2, 4)

            assertSoftly {
                slice.readInt().shouldBe(0x04030201)
                reader.position.shouldBe(0)
            }
        }
    }
})