package com.sappyoak.dsanalyzer.formats.emevd

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.binary.readUInt

private const val INITIALIZE_BANK = 2000
private const val INITIALIZE_ID = 0
private const val HEADER_BYTES = 8

/**
 * One event starting another, with the arguments it hands over
 */
public data class EventCall(
    public val slot: Int,
    public val eventId: Long,
    public val args: ArgData
)

public fun Instruction.asEventCall(): EventCall? {
    if (bank != INITIALIZE_BANK || id != INITIALIZE_ID) return null
    if (args.size < HEADER_BYTES) return null

    val reader = args.reader().also { it.order = ByteOrder.LITTLE_ENDIAN }
    return EventCall(
        slot = reader.readInt(),
        eventId = reader.readUInt().toLong(),
        args = args
    )
}
