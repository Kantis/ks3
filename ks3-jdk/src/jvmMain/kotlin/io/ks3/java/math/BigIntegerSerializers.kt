package io.ks3.java.math

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonUnquotedLiteral
import kotlinx.serialization.json.jsonPrimitive
import java.math.BigInteger

object BigIntegerSerializer : KSerializer<BigInteger> {

   override val descriptor = PrimitiveSerialDescriptor("java.math.BigDecimal", PrimitiveKind.DOUBLE)

   override fun deserialize(decoder: Decoder): BigInteger {
      return if (decoder is JsonDecoder) {
         BigInteger(decoder.decodeJsonElement().jsonPrimitive.content)
      } else {
         BigInteger(decoder.decodeString())
      }
   }

   @OptIn(ExperimentalSerializationApi::class)
   override fun serialize(encoder: Encoder, value: BigInteger) {
      val bdString = value.toString()

      if (encoder is JsonEncoder) {
         encoder.encodeJsonElement(JsonUnquotedLiteral(bdString))
      } else {
         encoder.encodeString(bdString)
      }
   }
}
