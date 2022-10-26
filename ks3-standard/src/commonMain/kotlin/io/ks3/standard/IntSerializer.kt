package io.ks3.standard

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

inline fun <reified T> longSerializer(
   name: String,
   crossinline decode: (Long) -> T,
   crossinline encode: (T) -> Long,
): KSerializer<T> = object : KSerializer<T> {
   override val descriptor = PrimitiveSerialDescriptor(name, PrimitiveKind.LONG)

   override fun deserialize(decoder: Decoder) =
      decode(decoder.decodeLong())

   override fun serialize(encoder: Encoder, value: T) =
      encoder.encodeLong(encode(value))
}
