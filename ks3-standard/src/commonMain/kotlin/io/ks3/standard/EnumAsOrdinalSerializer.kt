package io.ks3

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

inline fun <reified T : Enum<T>> enumAsOrdinalSerializer() = object : KSerializer<T> {
   val values = enumValues<T>()

   override val descriptor = PrimitiveSerialDescriptor("EnumAsOrdinal", PrimitiveKind.INT)

   override fun deserialize(decoder: Decoder): T {
      val s = decoder.decodeString()
      return values.single { it.name == s }
   }

   override fun serialize(encoder: Encoder, value: T) {
      return encoder.encodeInt(values.indexOf(value))
   }
}
