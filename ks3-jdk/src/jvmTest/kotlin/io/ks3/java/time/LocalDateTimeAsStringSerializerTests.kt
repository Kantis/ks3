package io.ks3.java.time

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.localDateTime
import io.ks3.test.generateSerializerTests
import kotlinx.serialization.json.Json
import java.time.LocalDateTime

class LocalDateTimeAsStringSerializerTests : FunSpec(
   {
      include(generateSerializerTests(LocalDateTimeAsStringSerializer, Arb.localDateTime()))

      test("sample") {
         Json.encodeToString(LocalDateTimeAsStringSerializer, LocalDateTime.of(2022, 10, 25, 18, 59, 13)) shouldBe
            "\"2022-10-25T18:59:13\""
      }
   },
)
