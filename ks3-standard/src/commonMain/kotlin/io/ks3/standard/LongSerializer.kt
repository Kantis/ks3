package io.ks3.standard

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

inline fun <reified T> longSerializer(
   crossinline decode: (Long) -> T,
   crossinline encode: (T) -> Long,
   nameOverride: String? = null,
): KSerializer<T> =
   object : KSerializer<T> {
      override val descriptor = PrimitiveSerialDescriptor(nameOverride ?: T::class.simpleName!!, PrimitiveKind.LONG)

      override fun deserialize(decoder: Decoder) = decode(decoder.decodeLong())

      override fun serialize(
         encoder: Encoder,
         value: T,
      ) = encoder.encodeLong(encode(value))
   }
