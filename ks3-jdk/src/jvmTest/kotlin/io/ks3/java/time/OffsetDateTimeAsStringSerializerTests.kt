package io.ks3.java.time

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.localTime
import io.ks3.test.generateSerializerTests
import kotlinx.serialization.json.Json
import java.time.OffsetDateTime
import java.time.ZoneOffset

class OffsetDateTimeAsStringSerializerTests : FreeSpec(
   {
      include(generateSerializerTests(LocalTimeAsStringSerializer, Arb.localTime()))

      "Serialized form is expected" {
         Json.encodeToString(
            OffsetDateTimeAsStringSerializer,
            OffsetDateTime.of(
               2022, 10, 25, 18, 59, 13, 0, ZoneOffset.ofHours(1),
            ),
         ) shouldBe "\"2022-10-25T18:59:13+01:00\""
      }
   },
)
