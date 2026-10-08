package com.sappyoak.dsanalyzer.formats.tae.template

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.formats.tae.Tae
import com.sappyoak.dsanalyzer.formats.tae.TaeEvent
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.readUByte
import com.sappyoak.dsanalyzer.shared.binary.readUShort

public sealed interface ParamValue {
    public data class Flag(public val set: Boolean) : ParamValue {
        override fun toString(): String = "$set"
    }

    public data class Number(public val raw: Long) : ParamValue {
        override fun toString(): String = raw.toString()
    }

    public data class Decimal(public val value: Float) : ParamValue {
        override fun toString(): String = value.toString()
    }

    /** A value blended from the start of the event to its end */
    public data class Gradient(public val from: Float, public val to: Float) : ParamValue {
        override fun toString(): String = "$from..$to"
    }

    public data object Missing : ParamValue {
        override fun toString(): String = "absent"
    }
}

public data class DecodedParam(
    public val definition: ParamDefinition,
    public val value: ParamValue
) {
    public val known: KnownValue?
        get() = when (value) {
            is ParamValue.Number -> definition.nameOf(value.raw)
            is ParamValue.Flag -> definition.nameOf(if (value.set) 1L else 0L)
            else -> null
        }
}

public data class DecodedEvent(
    public val event: TaeEvent,
    public val definition: EventDefinition?,
    public val params: List<DecodedParam>
) {
    public val label: String get() = definition?.name ?: "Event ${event.type}"

    public val sizeMismatch: Boolean
        get() = definition != null && event.params.size != definition.packedSize
}

public fun Tae.decode(
    animationId: Long,
    template: TaeTemplate
): List<DecodedEvent>? {
    val order = if (bigEndian) ByteOrder.BIG_ENDIAN else ByteOrder.LITTLE_ENDIAN
    return eventsFor(animationId)?.map { it.decode(template[it.type], order) }
}

public fun TaeEvent.decode(
    definition: EventDefinition?,
    order: ByteOrder = ByteOrder.LITTLE_ENDIAN
): DecodedEvent {
    if (definition == null) return DecodedEvent(this, null, emptyList())

    val reader = params.reader().also { it.order = order }
    var at = 0

    val decoded = definition.params.map { parameter ->
        val start = at
        at += parameter.type.size

        val value =
            if (at > params.size) ParamValue.Missing
            else reader.seek(start.toLong()).readParam(parameter.type)

        DecodedParam(parameter, value)
    }

    return DecodedEvent(this, definition, decoded)
}

private fun BinaryReader.readParam(type: ParamType): ParamValue = when (type) {
    ParamType.B -> ParamValue.Flag(readBoolean())
    ParamType.U8 -> ParamValue.Number(readUByte().toLong())
    ParamType.S8 -> ParamValue.Number(readByte().toLong())
    ParamType.U16 -> ParamValue.Number(readUShort().toLong())
    ParamType.S16 -> ParamValue.Number(readShort().toLong())
    ParamType.S32 -> ParamValue.Number(readInt().toLong())
    ParamType.F32 -> ParamValue.Decimal(readFloat())
    ParamType.F32Grad -> ParamValue.Gradient(readFloat(), readFloat())
}