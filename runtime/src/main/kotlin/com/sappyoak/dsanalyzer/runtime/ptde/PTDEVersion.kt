package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.native.memory.Address
import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.process.ProcessMemory

/** Where the DOS stub records the PE header's offset */
private const val E_LFANEW_AT = 0x3CL

/** `TimeDateStamp`, measured from the start of the PE signature */
private const val TIME_DATE_STAMP_AT = 8L

/** The MSVC Rich header's encrypted `DanS` marker */
private const val RICH_MARKER_AT = 0x80L

public enum class PTDEVersion(
    private val timestamp: UInt?,
    private val richMarker: UInt
) {
    Steam(null, 0xFC293654u),
    Debug(null, 0xCE9634B4u),
    SteamworksBeta(0x54B6EFC0u, 0xE91B11E2u);

    public companion object {
        public fun of(timestamp: UInt, richMarker: UInt): PTDEVersion? =
            entries.firstOrNull { it.timestamp == timestamp }
                ?: entries.firstOrNull { it.richMarker == richMarker }
    }
}

public fun ProcessMemory.ptdeVersion(module: AddressRange): PTDEVersion? {
    val lfanew = dwordAt(module.start + E_LFANEW_AT) ?: return null
    val timestamp = dwordAt(module.start + lfanew.toLong() + TIME_DATE_STAMP_AT) ?: return null
    val richMarker = dwordAt(module.start + RICH_MARKER_AT) ?: return null
    return PTDEVersion.of(timestamp, richMarker)
}

private fun ProcessMemory.dwordAt(at: Address): UInt? = read(at, Int.SIZE_BYTES)
    ?.let { ByteBuffer.wrap(it).order(ByteOrder.LITTLE_ENDIAN).int.toUInt() }