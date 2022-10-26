package io.ks3.standard

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

inline fun <reified T> intSerializer(
   name: String,
   crossinline decode: (Int) -> T,
   crossinline encode: (T) -> Int,
): KSerializer<T> = object : KSerializer<T> {
   override val descriptor = PrimitiveSerialDescriptor(name, PrimitiveKind.INT)

   override fun deserialize(decoder: Decoder) =
      decode(decoder.decodeInt())

   override fun serialize(encoder: Encoder, value: T) =
      encoder.encodeInt(encode(value))
}
