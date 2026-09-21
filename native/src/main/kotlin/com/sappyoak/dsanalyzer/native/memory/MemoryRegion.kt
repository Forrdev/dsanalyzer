package com.sappyoak.dsanalyzer.native.memory

/** A mapped span of memory and whether its contents can be read */
public data class MemoryRegion(
    public val range: AddressRange,
    public val readable: Boolean
)