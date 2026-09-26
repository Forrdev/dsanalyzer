package com.sappyoak.dsanalyzer.shared.binary

/** An enum whose entries a file stores as integer codes */
public interface Coded {
    public val code: Int
}

public inline fun <reified E> BinaryReader.readCoded(): E where E : Enum<E>, E : Coded {
    val at = position
    val code = readInt()
    return enumValues<E>().firstOrNull { it.code == code }
        ?: throw BinaryFormatException("Unknown ${E::class.simpleName} $code", at)
}