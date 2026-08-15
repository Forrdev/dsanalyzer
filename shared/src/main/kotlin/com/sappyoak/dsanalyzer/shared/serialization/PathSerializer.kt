package com.sappyoak.dsanalyzer.shared.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.nio.file.Path

object PathSerializer : KSerializer<Path> {
    override val descriptor = PrimitiveSerialDescriptor("java.nio.file.Path", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Path) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Path = Path.of(decoder.decodeString())
}

object NullablePathSerializer : KSerializer<Path?> {
    override val descriptor = PrimitiveSerialDescriptor("java.nio.file.Path?", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Path?) {
        encoder.encodeString(value?.toString() ?: "")
    }

    override fun deserialize(decoder: Decoder): Path? {
        val raw = decoder.decodeString()
        if (raw.isEmpty()) return null

        return try {
            Path.of(raw)
        } catch (err: Throwable) { null }
    }
}