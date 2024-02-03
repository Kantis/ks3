package io.ks3.standard

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object ShapeSerializer : KSerializer<Shape> by enumAsOrdinalSerializer()

class EnumAsOrdinalSerializerTest : FreeSpec(
   {

      val serializer = ShapeSerializer
      val format = Json

      @Serializable
      data class EnumWrapper(
         @Serializable(with = ShapeSerializer::class)
         val shape: Shape?,
      )

      "Given a nullable enum property, Then null should be encoded" {
         format.encodeToString(EnumWrapper(null)) shouldEqualJson """
               {
                  "shape": null
               }
            """.trimIndent()
      }

      "Given a nullable enum property, Then non-null should be encoded" {
         format.encodeToString(EnumWrapper(Shape.SQUARE)) shouldEqualJson """
               {
                  "shape": 1
               }
            """.trimIndent()
      }

      "Given a nullable enum property, Then null should be decoded" {
         format.decodeFromString<EnumWrapper>("{ \"shape\": null }") shouldBe EnumWrapper(null)
      }

      "Given a nullable enum property, Then non-null value should be decoded" {
         format.decodeFromString<EnumWrapper>("{ \"shape\": 1 }") shouldBe EnumWrapper(Shape.SQUARE)
      }

      "Encodes to ordinal value" {
         format.encodeToString(serializer, Shape.SQUARE) shouldBe "1"
         format.encodeToString(serializer, Shape.CIRCLE) shouldBe "0"
      }

      "Decodes from ordinal value" {
         format.decodeFromString(serializer, "1") shouldBe Shape.SQUARE
         format.decodeFromString(serializer, "0") shouldBe Shape.CIRCLE
      }

      "Decodes quoted value" {
         format.decodeFromString(serializer, "\"1\"") shouldBe Shape.SQUARE
      }

      "Throws exception on invalid ordinal" {
         shouldThrow<EnumDecodingException> {
            format.decodeFromString(serializer, "-1")
         }.message shouldBe "Invalid ordinal value -1 for Shape, must be in 0..1"

         shouldThrow<EnumDecodingException> {
            format.decodeFromString(serializer, "2")
         }.message shouldBe "Invalid ordinal value 2 for Shape, must be in 0..1"
      }
   },
)

private enum class Shape {
   CIRCLE, SQUARE
}
