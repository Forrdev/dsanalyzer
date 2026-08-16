package com.sappyoak.dsanalyzer.shared.io

import kotlin.math.pow
import kotlin.math.roundToInt

@JvmInline
value class ByteUnits (val bytes: Long) {
    operator fun plus(other: ByteUnits) = ByteUnits(bytes + other.bytes)
    operator fun minus(other: ByteUnits) = ByteUnits(bytes - other.bytes)
    operator fun times(other: ByteUnits) = ByteUnits(bytes * other.bytes)
    operator fun div(other: ByteUnits) = ByteUnits(bytes / other.bytes)
    operator fun rem(other: ByteUnits) = ByteUnits(bytes % other.bytes)

    operator fun plus(other: Number) = ByteUnits(bytes + other.toLong())
    operator fun minus(other: Number) = ByteUnits(bytes - other.toLong())
    operator fun times(other: Number) = ByteUnits(bytes * other.toLong())
    operator fun div(other: Number) = ByteUnits(bytes / other.toLong())
    operator fun rem(other: Number) = ByteUnits(bytes % other.toLong())

    override fun toString(): String = when {
        bytes >= 1_000_000_000 -> "${(bytes / 1_000_000_000.0).roundToDigits(1)} GB"
        bytes >= 1_000_000 -> "${(bytes / 1_000_000.0).roundToDigits(1)} MB"
        bytes >= 1_000 -> "${(bytes / 1_000.0).roundToDigits(1)} KB"
        else -> "$bytes B"
    }

    private fun Double.roundToDigits(digits: Int): Double {
        val num = 10.0.pow(digits)
        return (bytes.toDouble() * num).roundToInt().toDouble() / num
    }
}