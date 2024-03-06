package io.ks3.java.math

import io.ks3.standard.doubleSerializer
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

/**
 * Encodes a [BigDecimal] as a string, preserving the full precision of the number. Wraps the number in quotes.
 */
typealias BigDecimalAsString =
   @Serializable(with = BigDecimalAsStringSerializer::class)
   BigDecimal

/**
 * Encodes a [BigDecimal] to it's double representation. [Double] have limited precision, so the number might not be
 * exactly the same as the original [BigDecimal].
 */
typealias BigDecimalAsDouble =
   @Serializable(with = BigDecimalAsDoubleSerializer::class)
   BigDecimal

/**
 * Encodes a [BigDecimal] as an unquoted JSON literal, preserving the full precision of the number while being encoded as a number.
 *
 * Note that this typealias is primarily meant for JSON, other formats will have the [BigDecimal] encoded as a string.
 */
@ExperimentalSerializationApi
typealias BigDecimalAsJsonNumber =
   @Serializable(with = BigDecimalAsJsonNumberSerializer::class)
   BigDecimal

object BigDecimalAsStringSerializer : KSerializer<BigDecimal> by stringSerializer(::BigDecimal, BigDecimal::toPlainString)

object BigDecimalAsDoubleSerializer : KSerializer<BigDecimal> by doubleSerializer({ it.toString().toBigDecimal() }, BigDecimal::toDouble)

/**
 * Encodes a [BigDecimal] as an exact numeric value, preserving the full precision of the number.
 *
 * Note that this serializer is primarily meant for JSON, other formats will have the [BigDecimal] encoded as a string.
 */
@ExperimentalSerializationApi
object BigDecimalAsJsonNumberSerializer : KSerializer<BigDecimal> {
   override val descriptor = PrimitiveSerialDescriptor(BigDecimal::class.qualifiedName!!, PrimitiveKind.STRING)

   override fun deserialize(decoder: Decoder): BigDecimal {
      return if (decoder is JsonDecoder) {
         BigDecimal(decoder.decodeJsonElement().jsonPrimitive.content)
      } else {
         BigDecimal(decoder.decodeString())
      }
   }

   override fun serialize(
      encoder: Encoder,
      value: BigDecimal,
   ) {
      val bdString = value.toPlainString()

      if (encoder is JsonEncoder) {
         encoder.encodeJsonElement(JsonUnquotedLiteral(bdString))
      } else {
         encoder.encodeString(bdString)
      }
   }
}
