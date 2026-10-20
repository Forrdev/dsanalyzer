package com.sappyoak.dsanalyzer.runtime.ptde

import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.process.ProcessMemory

private const val PREFERRED_BASE = 0x400000L

public enum class PTDEVersion(private val version: UInt) {
    Steam(0xFC293654u),
    Debug(0xCE9634B4u),
    SteamworksBeta(0xE91B11E2u);

    public companion object {
        internal const val VersionAddress: Long = 0x400080

        public fun of(version: UInt): PTDEVersion? = PTDEVersion.entries.firstOrNull { it.version == version }
    }
}

public fun ProcessMemory.ptdeVersion(module: AddressRange): PTDEVersion? {
    val at = module.start + (PTDEVersion.VersionAddress - PREFERRED_BASE)
    val word = read(at, Int.SIZE_BYTES) ?: return null
    return PTDEVersion.of(ByteBuffer.wrap(word).order(ByteOrder.LITTLE_ENDIAN).int.toUInt())
}