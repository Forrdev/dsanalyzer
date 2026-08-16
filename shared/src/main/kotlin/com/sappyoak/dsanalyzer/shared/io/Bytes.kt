package com.sappyoak.dsanalyzer.shared.io

val Byte.unsigned: Int get() = this and 0xFF
val Short.unsigned: Int get() = toInt() and 0xFFFF
val Int.unsigned: Long get() = toLong() and 0xFFFFFFFFL

@Suppress("NOTHING_TO_INLINE")
inline infix fun Byte.shr(other: Int): Int = toInt() shr other

@Suppress("NOTHING_TO_INLINE")
inline infix fun Byte.shl(other: Int): Int = toInt() shl other

@Suppress("NOTHING_TO_INLINE")
inline infix fun Byte.and(other: Int): Int = toInt() and other

@Suppress("NOTHING_TO_INLINE")
inline infix fun Byte.and(other: Long): Long = toLong() and other

@Suppress("NOTHING_TO_INLINE")
inline infix fun Int.and(other: Long): Long = toLong() and other

@Suppress("NOTHING_TO_INLINE")
inline fun Int.interpretAsFloat(): Float = Float.fromBits(this)

@Suppress("NOTHING_TO_INLINE")
inline fun Long.interpretAsDouble(): Double = Double.fromBits(this)

@Suppress("NOTHING_TO_INLINE")
inline fun Float.interpretAsInt(): Int = this.toRawBits()

@Suppress("NOTHING_TO_INLINE")
inline fun Double.interpretAsLong(): Long = this.toRawBits()