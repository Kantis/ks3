package io.ks3.standard

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

inline fun <reified T> doubleSerializer(
   crossinline decode: (Double) -> T,
   crossinline encode: (T) -> Double,
   nameOverride: String? = null,
): KSerializer<T> = object : KSerializer<T> {
   override val descriptor = PrimitiveSerialDescriptor(nameOverride ?: T::class.simpleName!!, PrimitiveKind.DOUBLE)

   override fun deserialize(decoder: Decoder) =
      decode(decoder.decodeDouble())

   override fun serialize(encoder: Encoder, value: T) =
      encoder.encodeDouble(encode(value))
}
