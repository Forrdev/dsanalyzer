package com.sappyoak.dsanalyzer.formats

import java.nio.ByteBuffer
import java.nio.ByteOrder

internal class FixtureWriter {
    private val buffer = ByteBuffer.allocate(8192).order(ByteOrder.LITTLE_ENDIAN)

    val position: Int get() = buffer.position()

    fun int(value: Int) = apply { buffer.putInt(value) }
    fun short(value: Int) = apply { buffer.putShort(value.toShort()) }
    fun byte(value: Int) = apply { buffer.put(value.toByte()) }
    fun float(vararg values: Float) = apply { values.forEach(buffer::putFloat) }
    fun bytes(values: ByteArray) = apply { buffer.put(values) }
    fun zeros(ints: Int) = apply { repeat(ints) { int(0) } }
    fun string(value: String) = apply { buffer.put(value.toByteArray()).put(0) }
    fun pad() = apply { while (position % 4 != 0) byte(0) }

    /** Reserves an int slot and returns where it is */
    fun slot(): Int = position.also { int(0) }
    /** Fills [slot] with the current position */
    fun fill(slot: Int) = apply { buffer.putInt(slot, position) }

    fun toByteArray(): ByteArray = buffer.array().copyOf(position)
}

internal fun entry(build: FixtureWriter.() -> Unit): ByteArray = FixtureWriter().apply(build).toByteArray()
