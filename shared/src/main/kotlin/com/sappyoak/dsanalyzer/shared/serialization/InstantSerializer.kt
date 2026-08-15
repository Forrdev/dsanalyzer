package com.sappyoak.dsanalyzer.shared.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import kotlin.time.Instant

object InstantComponentSerializer : KSerializer<Instant> {
    override val descriptor = buildClassSerialDescriptor("InstantComponent") {
        element<Long>("seconds")
        element<Int>("nanos")
    }

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeStructure(descriptor) {
            encodeLongElement(descriptor, 0, value.epochSeconds)
            encodeIntElement(descriptor, 1, value.nanosecondsOfSecond)
        }
    }

    override fun deserialize(decoder: Decoder): Instant {
        return decoder.decodeStructure(descriptor) {
            var seconds = 0L
            var nanos = 0
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> seconds = decodeLongElement(descriptor, 0)
                    1 -> nanos = decodeIntElement(descriptor, 1)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            Instant.fromEpochSeconds(seconds, nanos)
        }
    }
}