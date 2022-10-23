package io.ks3.java.time

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json
import java.time.LocalDateTime

class LocalDateTimeAsStringSerializerTests : FunSpec(
   {
      val format = Json
      test("Serializes to string") {
         format.encodeToString(
            LocalDateTimeAsStringSerializer,
            LocalDateTime.of(2022, 10, 23, 1, 23, 15),
         ) shouldBe "\"2022-10-23T01:23:15\""
      }

      test("Deserializes") {
         format.decodeFromString(
            LocalDateTimeAsStringSerializer,
            "\"2022-10-23T01:23:15\"",
         ) shouldBe LocalDateTime.of(2022, 10, 23, 1, 23, 15)
      }
   },
)
