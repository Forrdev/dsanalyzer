package com.sappyoak.dsanalyzer.formats.binder

import com.sappyoak.dsanalyzer.formats.compression.decompress
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

/**
 * A binder's table of contents paired with the bytes its entries point into
 */
public class OpenBinder(public val binder: Binder, private val data: BinaryReader) {
    /** Reads an entry, decompressing it when stored compressed and viewing it in place when not */
    public fun open(entry: BinderEntry): BinaryReader =
        data.slice(entry.dataOffset, entry.compressedSize).decompress()
}

public fun openBinder(reader: BinaryReader, data: BinaryReader = reader): OpenBinder =
    OpenBinder(readBinder(reader, data), data)