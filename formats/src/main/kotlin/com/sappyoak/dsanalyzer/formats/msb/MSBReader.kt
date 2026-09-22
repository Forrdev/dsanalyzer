package com.sappyoak.dsanalyzer.formats.msb

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue


/** An offset the format requires to point somewhere, so a zero means the data is malformed */
internal fun BinaryReader.readRequiredOffset(): Long {
    val at = position
    return readInt().toLong().takeUnless { it == 0L }
        ?: throw BinaryFormatException("required offset is zero", at)
}

internal fun BinaryReader.assertZeros(count: Int) = repeat(count) { assertValue(0) { readInt() } }
internal fun BinaryReader.readStringAt(start: Long, offset: Long): String = at(start + offset) { readShiftJIS() }