package io.ks3.java.time

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll
import io.kotest.property.exhaustive.exhaustive
import io.ks3.test.generateEncoders
import kotlinx.serialization.json.Json
import java.time.LocalDateTime

class LocalDateTimeAsStringSerializerTests : FunSpec(
   {
      val (encoders, decoders) = Json.generateEncoders(LocalDateTimeAsStringSerializer)

      context("Serializes to string") {
         checkAll(encoders.exhaustive()) { encode ->
            LocalDateTime.of(2022, 10, 23, 1, 23, 15).encode() shouldBe "\"2022-10-23T01:23:15\""
         }
      }

      test("Deserializes") {
         checkAll(decoders.exhaustive()) { decode ->
            "\"2022-10-23T01:23:15\"".decode() shouldBe LocalDateTime.of(2022, 10, 23, 1, 23, 15)
         }
      }
   },
)
