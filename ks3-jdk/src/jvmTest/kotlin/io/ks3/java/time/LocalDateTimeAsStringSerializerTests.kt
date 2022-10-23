package io.ks3.java.time

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ks3.test.encoders
import kotlinx.serialization.json.Json
import java.time.LocalDateTime

class LocalDateTimeAsStringSerializerTests : FunSpec(
   {
      val (encode, decode) = Json.encoders(LocalDateTimeAsStringSerializer)

      test("Serializes to string") {
         LocalDateTime.of(2022, 10, 23, 1, 23, 15).encode() shouldBe "\"2022-10-23T01:23:15\""
      }

      test("Deserializes") {
         "\"2022-10-23T01:23:15\"".decode() shouldBe LocalDateTime.of(2022, 10, 23, 1, 23, 15)
      }
   },
)
