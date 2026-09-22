package com.sappyoak.dsanalyzer.formats.msb

import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

/**
 * Entries refer to each other by index, and not every index counts from the same list. Most
 * count across a whole param, but some count within one subtype. Each list gets its own type
 * so the two can never be mixed up. An index of -1 means no reference and is read as null
 */

private const val NO_REFERENCE = -1

@JvmInline
public value class ModelIndex(public val value: Int) {
    override fun toString(): String = value.toString()
}

@JvmInline
public value class RegionIndex(public val value: Int) {
    override fun toString(): String = value.toString()
}

@JvmInline
public value class PartIndex(public val value: Int) {
    override fun toString(): String = value.toString()
}


@JvmInline
public value class CollisionIndex(public val value: Int) {
    override fun toString(): String = value.toString()
}

@JvmInline
public value class EnvironmentIndex(public val value: Int) {
    override fun toString(): String = value.toString()
}

internal fun BinaryReader.readEntityId(): Int? = readInt().takeUnless { it == NO_REFERENCE }

internal fun BinaryReader.readPartIndex(): PartIndex? = readInt().asReference(::PartIndex)
internal fun BinaryReader.readRegionIndex(): RegionIndex? = readInt().asReference(::RegionIndex)

internal inline fun <T> Int.asReference(wrap: (Int) -> T): T? = if (this == NO_REFERENCE) null else wrap(this)