package org.jetbrains.greeting.responses

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class ModeSerializer : KSerializer<MusicalMode> {

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("MusicalKey", PrimitiveKind.INT)
    override fun deserialize(decoder: Decoder): MusicalMode {
        val value = decoder.decodeInt()
        return MusicalMode.fromInt(value)
    }

    override fun serialize(encoder: Encoder, value: MusicalMode) {
        encoder.encodeInt(value.ordinal)
    }
}

