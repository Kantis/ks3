package io.ks3.java.math

import io.ks3.standard.stringSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonUnquotedLiteral
import kotlinx.serialization.json.jsonPrimitive
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Encodes a [BigDecimal] as a string, preserving the full precision of the number. Wraps the number in quotes.
 */
typealias BigIntegerAsString =
   @Serializable(with = BigIntegerAsStringSerializer::class)
   BigInteger

/**
 * Encodes a [BigInteger] as an unquoted JSON literal, preserving the full precision of the number while being encoded as a number.
 *
 * Note that this typealias is primarily meant for JSON, other formats will have the [BigInteger] encoded as a string.
 */
@ExperimentalSerializationApi
typealias BigIntegerAsJsonLiteral =
   @Serializable(with = BigIntegerAsJsonLiteralSerializer::class)
   BigInteger

object BigIntegerAsStringSerializer : KSerializer<BigInteger> by stringSerializer(::BigInteger, BigInteger::toString)

/**
 * Encodes a [BigInteger] as an exact numeric value, preserving the full precision of the number.
 *
 * Note that this serializer is primarily meant for JSON, other formats will have the [BigInteger] encoded as a string.
 */
@ExperimentalSerializationApi
object BigIntegerAsJsonLiteralSerializer : KSerializer<BigInteger> {
   override val descriptor = PrimitiveSerialDescriptor("java.math.BigInteger", PrimitiveKind.DOUBLE)

   override fun deserialize(decoder: Decoder): BigInteger {
      return if (decoder is JsonDecoder) {
         BigInteger(decoder.decodeJsonElement().jsonPrimitive.content)
      } else {
         BigInteger(decoder.decodeString())
      }
   }

   override fun serialize(
      encoder: Encoder,
      value: BigInteger,
   ) {
      val bdString = value.toString()

      if (encoder is JsonEncoder) {
         encoder.encodeJsonElement(JsonUnquotedLiteral(bdString))
      } else {
         encoder.encodeString(bdString)
      }
   }
}
