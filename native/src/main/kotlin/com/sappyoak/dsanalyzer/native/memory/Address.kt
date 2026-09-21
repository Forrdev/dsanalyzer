package com.sappyoak.dsanalyzer.native.memory

/** An address in a process's memory */
@JvmInline
public value class Address(public val value: Long) {
    public val isNull: Boolean get() = value == 0L

    public operator fun plus(offset: Long): Address = Address(value + offset)
    public operator fun plus(other: Address): Address = Address(value + other.value)

    public operator fun minus(offset: Long): Address = Address(value - offset)
    public operator fun minus(other: Address): Address = Address(value - other.value)

    override fun toString(): String = "0x${value.toULong().toString(16).uppercase()}"

    public companion object {
        public val Null: Address = Address(0)
    }
}

/** A span of memory */
public data class AddressRange(public val start: Address, public val size: Long) {
    public val end: Address = start + size

    public operator fun contains(address: Address): Boolean =
        address.value >= start.value && address.value < end.value
}