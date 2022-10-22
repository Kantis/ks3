package io.ks3.java.time

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal inline fun <reified T> stringSerializer(
   name: String,
   crossinline decode: (String) -> T,
   crossinline encode: (T) -> String = { it.toString() },
): KSerializer<T> = object : KSerializer<T> {
   override val descriptor = PrimitiveSerialDescriptor(name, PrimitiveKind.STRING)

   override fun deserialize(decoder: Decoder) =
      decode(decoder.decodeString())

   override fun serialize(encoder: Encoder, value: T) =
      encoder.encodeString(encode(value))
}
