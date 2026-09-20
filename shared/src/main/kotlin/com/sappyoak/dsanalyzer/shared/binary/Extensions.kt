package com.sappyoak.dsanalyzer.shared.binary

@Suppress("NOTHING_TO_INLINE")
inline fun Int.reverseBits(): Int = Integer.reverse(this) ushr 24

@Suppress("NOTHING_TO_INLINE")
inline fun Int.hasFlag(flag: Int): Boolean = this and flag != 0