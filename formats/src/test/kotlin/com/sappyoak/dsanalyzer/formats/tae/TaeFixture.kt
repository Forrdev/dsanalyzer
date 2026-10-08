package com.sappyoak.dsanalyzer.formats.tae

import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

/**
 * A Dark Souls TAE built by hand.
 *
 * Every block is placed at a constant rather than appended, so the layout is something this file
 * states and the reader has to agree with, instead of something both sides discover together.
 *
 * Two animations: the first owns two events, and the second imports everything from the first,
 * which is how the game reuses timings and the reason an empty event list is not the whole story
 */
internal const val TAE_ID = 100

/** The animation table opens straight after the header, which runs to 0xA4 */
private const val ANIMATION_TABLE = 0xB0
private const val FIRST_ANIMATION = 0xC0
private const val SECOND_ANIMATION = 0xDC
private const val TIMES = 0xF8
private const val EVENT_HEADERS = 0x104
private const val FIRST_EVENT = 0x11C
private const val SECOND_EVENT = 0x134
private const val FIRST_SOURCE = 0x140
private const val SECOND_SOURCE = 0x154
private const val ANIMATION_FILE_NAME = 0x168
private const val SKELETON_NAME = 0x182
private const val SIB_NAME = 0x196
private const val SIZE = 0x1AA

internal const val FLAG_EVENT = 0
internal const val WEAPON_STYLE_EVENT = 32

internal const val FLAG_PARAM_BYTES = 16
internal const val WEAPON_STYLE_PARAM_BYTES = 4

internal const val OWN_ANIMATION = 0L
internal const val IMPORTING_ANIMATION = 1L

internal const val SKELETON = "c0000.hkt"
internal const val SIB = "c0000.sib"
internal const val MOTION_FILE = "a00_0000.hkx"

/** Values packed into the first event, which the character templates call `ChrActionFlag` */
internal const val INVINCIBLE = 8
internal const val FLAG_ARG_A = 1.5f
internal const val FLAG_ARG_B = -1
internal const val FLAG_ARG_C = 2
internal const val FLAG_ARG_D = 3
internal const val FLAG_ARG_E: Short = -5

internal const val WEAPON_STYLE = 7

internal fun ptdeTae(
    version: Int = 0x1000B,
    magic: String = "TAE ",
    wide: Boolean = false
): BinaryReader {
    val bytes = ByteBuffer.allocate(SIZE).order(ByteOrder.LITTLE_ENDIAN)

    magic.forEachIndexed { index, char -> bytes.put(index, char.code.toByte()) }
    bytes.put(0x04, 0)                              // little endian
    bytes.put(0x07, if (wide) 0xFF.toByte() else 0) // 32-bit offsets
    bytes.putInt(0x08, version)
    bytes.putInt(0x0C, SIZE)

    // The sub-block positions the reader fingerprints the layout by
    bytes.putInt(0x10, 0x40)
    bytes.putInt(0x60, 0x90)
    bytes.putInt(0x94, 0x98)

    bytes.putInt(0x50, TAE_ID)
    bytes.putInt(0x54, 2)
    bytes.putInt(0x58, ANIMATION_TABLE)
    bytes.putInt(0x98, SKELETON_NAME)
    bytes.putInt(0x9C, SIB_NAME)

    bytes.putInt(ANIMATION_TABLE, OWN_ANIMATION.toInt())
    bytes.putInt(ANIMATION_TABLE + 4, FIRST_ANIMATION)
    bytes.putInt(ANIMATION_TABLE + 8, IMPORTING_ANIMATION.toInt())
    bytes.putInt(ANIMATION_TABLE + 12, SECOND_ANIMATION)

    // eventCount, eventHeaders, groupCount, groups, timesCount, times, source. No event groups,
    // so the last event's parameters have to be bounded by something else
    bytes.animation(FIRST_ANIMATION, events = 2, eventHeaders = EVENT_HEADERS, times = 3, timesAt = TIMES, source = FIRST_SOURCE)
    bytes.animation(SECOND_ANIMATION, events = 0, eventHeaders = 0, times = 0, timesAt = 0, source = SECOND_SOURCE)

    bytes.putFloat(TIMES, 0.0f)
    bytes.putFloat(TIMES + 4, 0.5f)
    bytes.putFloat(TIMES + 8, 1.0f)

    bytes.eventHeader(EVENT_HEADERS, startTime = TIMES, endTime = TIMES + 4, data = FIRST_EVENT)
    bytes.eventHeader(EVENT_HEADERS + 12, startTime = TIMES + 4, endTime = TIMES + 8, data = SECOND_EVENT)

    bytes.putInt(FIRST_EVENT, FLAG_EVENT)
    bytes.putInt(FIRST_EVENT + 4, FIRST_EVENT + 8)
    bytes.putInt(FIRST_EVENT + 8, INVINCIBLE)
    bytes.putFloat(FIRST_EVENT + 12, FLAG_ARG_A)
    bytes.putInt(FIRST_EVENT + 16, FLAG_ARG_B)
    bytes.put(FIRST_EVENT + 20, FLAG_ARG_C.toByte())
    bytes.put(FIRST_EVENT + 21, FLAG_ARG_D.toByte())
    bytes.putShort(FIRST_EVENT + 22, FLAG_ARG_E)

    bytes.putInt(SECOND_EVENT, WEAPON_STYLE_EVENT)
    bytes.putInt(SECOND_EVENT + 4, SECOND_EVENT + 8)
    bytes.putInt(SECOND_EVENT + 8, WEAPON_STYLE)

    // Its own motion, looping, naming the '.hkx' it plays
    bytes.putInt(FIRST_SOURCE, 0)
    bytes.putInt(FIRST_SOURCE + 4, FIRST_SOURCE + 8)
    bytes.putInt(FIRST_SOURCE + 8, ANIMATION_FILE_NAME)
    bytes.put(FIRST_SOURCE + 12, 1)
    bytes.put(FIRST_SOURCE + 13, 0)
    bytes.put(FIRST_SOURCE + 14, 0)
    bytes.putInt(FIRST_SOURCE + 16, 0)

    // Everything from the first animation, so it names no file of its own
    bytes.putInt(SECOND_SOURCE, 1)
    bytes.putInt(SECOND_SOURCE + 4, SECOND_SOURCE + 8)
    bytes.putInt(SECOND_SOURCE + 8, 0)
    bytes.putInt(SECOND_SOURCE + 12, OWN_ANIMATION.toInt())
    bytes.putInt(SECOND_SOURCE + 16, -1)

    bytes.utf16(ANIMATION_FILE_NAME, MOTION_FILE)
    bytes.utf16(SKELETON_NAME, SKELETON)
    bytes.utf16(SIB_NAME, SIB)

    return BinaryReader.of(bytes.array())
}

private fun ByteBuffer.animation(
    at: Int,
    events: Int,
    eventHeaders: Int,
    times: Int,
    timesAt: Int,
    source: Int
) {
    putInt(at, events)
    putInt(at + 4, eventHeaders)
    putInt(at + 8, 0) // event group count
    putInt(at + 12, 0) // event groups offset
    putInt(at + 16, times)
    putInt(at + 20, timesAt)
    putInt(at + 24, source)
}

private fun ByteBuffer.eventHeader(at: Int, startTime: Int, endTime: Int, data: Int) {
    putInt(at, startTime)
    putInt(at + 4, endTime)
    putInt(at + 8, data)
}

private fun ByteBuffer.utf16(at: Int, text: String) {
    text.forEachIndexed { index, char -> putShort(at + index * 2, char.code.toShort()) }
    putShort(at + text.length * 2, 0)
}