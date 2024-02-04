package io.ks3.standard

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

inline fun <reified T> intSerializer(
   crossinline decode: (Int) -> T,
   crossinline encode: (T) -> Int,
   nameOverride: String? = null,
): KSerializer<T> =
   object : KSerializer<T> {
      override val descriptor = PrimitiveSerialDescriptor(nameOverride ?: T::class.simpleName!!, PrimitiveKind.LONG)

      override fun deserialize(decoder: Decoder) = decode(decoder.decodeInt())

      override fun serialize(
         encoder: Encoder,
         value: T,
      ) = encoder.encodeInt(encode(value))
   }
