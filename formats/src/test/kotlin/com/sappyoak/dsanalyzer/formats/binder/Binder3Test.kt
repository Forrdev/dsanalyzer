package com.sappyoak.dsanalyzer.formats.binder

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.FixtureWriter
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

private const val VERSION = "07D7R6"

private const val FORMAT = 0x74

private const val ENTRY_FLAGS = 0x40

private const val FIRST_ID = 100
private const val SECOND_ID = 200
private const val FIRST_NAME = "N:\\FRPG\\data\\first.hkx"
private const val SECOND_NAME = "N:\\FRPG\\data\\second.hkx"
private val FIRST_DATA = byteArrayOf(1, 2, 3, 4)
private val SECOND_DATA = byteArrayOf(5, 6, 7, 8, 9)

class Binder3Test : FunSpec({
    test("a single-file binder's entries carry their ids, names and sizes") {
        val binder = readBinder(BinaryReader.of(bnd3()))

        assertSoftly {
            binder.version shouldBe VERSION
            binder.entries.size shouldBe 2
            binder.flags.hasIds shouldBe true
            binder.flags.hasNames shouldBe true
            binder.flags.hasLongOffsets shouldBe false

            binder[FIRST_ID]?.name shouldBe FIRST_NAME
            binder[SECOND_ID]?.name shouldBe SECOND_NAME
            binder[FIRST_NAME]?.id shouldBe FIRST_ID
            binder[FIRST_ID]?.compressedSize shouldBe FIRST_DATA.size.toLong()
            binder[SECOND_ID]?.compressedSize shouldBe SECOND_DATA.size.toLong()
        }
    }

    test("a single-file binder's entries point at their own data") {
        val bytes = bnd3()
        val opened = openBinder(BinaryReader.of(bytes))

        assertSoftly {
            opened.read(FIRST_ID) shouldBe FIRST_DATA.toList()
            opened.read(SECOND_ID) shouldBe SECOND_DATA.toList()
        }
    }

    test("a split binder reads its entries from where the header actually ends") {
        val binder = readBinder(BinaryReader.of(bhf3()), BinaryReader.of(bdf3()))

        assertSoftly {
            binder.entries.size shouldBe 2
            binder[FIRST_ID]?.name shouldBe FIRST_NAME
            binder[SECOND_ID]?.name shouldBe SECOND_NAME
        }
    }

    test("a split binder's entries point into the data file") {
        val opened = openBinder(BinaryReader.of(bhf3()), BinaryReader.of(bdf3()))

        assertSoftly {
            opened.read(FIRST_ID) shouldBe FIRST_DATA.toList()
            opened.read(SECOND_ID) shouldBe SECOND_DATA.toList()
        }
    }
})

private fun OpenBinder.read(id: Int): List<Byte> {
    val entry = checkNotNull(binder[id]) { "no entry $id" }
    return open(entry).readBytes(entry.compressedSize.toInt()).toList()
}

private fun FixtureWriter.version3Header(magic: String, entries: Int) = apply {
    bytes(magic.toByteArray())
    bytes(VERSION.toByteArray())
    repeat(8 - VERSION.length) { byte(0) }
    byte(FORMAT)
    byte(0)
    byte(0)
    byte(0)
    int(entries)
    zeros(3)
}

private fun FixtureWriter.entry(id: Int, data: ByteArray): Pair<Int, Int> {
    byte(ENTRY_FLAGS)
    repeat(3) { byte(0) }
    int(data.size)
    val dataSlot = slot()
    int(id)
    val nameSlot = slot()
    int(data.size)
    return dataSlot to nameSlot
}

private fun bnd3(): ByteArray = FixtureWriter().run {
    version3Header("BND3", entries = 2)
    val first = entry(FIRST_ID, FIRST_DATA)
    val second = entry(SECOND_ID, SECOND_DATA)

    fill(first.second)
    string(FIRST_NAME)
    fill(second.second)
    string(SECOND_NAME)

    fill(first.first)
    bytes(FIRST_DATA)
    fill(second.first)
    bytes(SECOND_DATA)
    toByteArray()
}

private const val DATA_HEADER = 16

private fun bhf3(): ByteArray = FixtureWriter().run {
    version3Header("BHF3", entries = 2)
    val first = entry(FIRST_ID, FIRST_DATA)
    val second = entry(SECOND_ID, SECOND_DATA)

    fill(first.second)
    string(FIRST_NAME)
    fill(second.second)
    string(SECOND_NAME)

    // The offsets point into the data file, which starts with its own sixteen byte header
    fill(first.first, DATA_HEADER)
    fill(second.first, DATA_HEADER + FIRST_DATA.size)
    toByteArray()
}

private fun bdf3(): ByteArray = FixtureWriter().run {
    bytes("BDF3".toByteArray())
    bytes(VERSION.toByteArray())
    repeat(8 - VERSION.length) { byte(0) }
    int(0)
    bytes(FIRST_DATA)
    bytes(SECOND_DATA)
    toByteArray()
}