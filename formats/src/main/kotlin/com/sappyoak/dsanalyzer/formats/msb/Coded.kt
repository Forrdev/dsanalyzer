package com.sappyoak.dsanalyzer.formats.msb

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

/** An enum whose entries the file stores as integer codes */
public interface Coded {
    public val code: Int
}


internal inline fun <reified E> BinaryReader.readCoded(): E where E : Enum<E>, E : Coded {
    val at = position
    val code = readInt()
    return enumValues<E>().firstOrNull { it.code == code }
        ?: throw BinaryFormatException("Unknown ${E::class.simpleName} $code", at)
}