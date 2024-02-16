package io.ks3.standard

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private object LenientIntParser : KSerializer<Int?> by lenientJsonPropertyDeserializer()

private typealias LenientInt =
   @Serializable(with = LenientIntParser::class)
   Int?

class LenientJsonPropertySerializerTest : StringSpec(
   {
      @Serializable
      data class Foo(
         val bar: String,
         val baz: LenientInt,
      )

      "Invalid value is discarded" {
         Json.decodeFromString<Foo>("""{"bar": "bar", "baz": "x"}""") shouldBe Foo("bar", null)
      }
   },
)
