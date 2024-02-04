package io.ks3.standard

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

inline fun <reified T : Enum<T>> enumAsOrdinalSerializer() =
   object : KSerializer<T> {
      val values = enumValues<T>()
      val name = T::class.simpleName

      override val descriptor = PrimitiveSerialDescriptor("EnumAsOrdinal", PrimitiveKind.INT)

      override fun deserialize(decoder: Decoder): T {
         val i = decoder.decodeInt()
         if (i !in values.indices) {
            throw EnumDecodingException("Invalid ordinal value $i for $name, must be in ${values.indices}")
         }
         return values[i]
      }

      override fun serialize(
         encoder: Encoder,
         value: T,
      ) {
         return encoder.encodeInt(values.indexOf(value))
      }
   }
