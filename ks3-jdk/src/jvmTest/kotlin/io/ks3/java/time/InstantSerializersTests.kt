package io.ks3.java.time

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.instant
import io.ks3.test.generateSerializerTests
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.Instant.ofEpochMilli
import java.time.temporal.ChronoUnit.MILLIS

class InstantSerializersTests : FreeSpec(
   {
      include(
         generateSerializerTests(
            InstantAsLongSerializer,
            Arb.instant(ofEpochMilli(Long.MIN_VALUE)..ofEpochMilli(Long.MAX_VALUE)), // Only values that can be represented by a Long
            { "Encodes Instant as epoch millis and back again" },
         ) { originalValue ->
            truncatedTo(MILLIS) shouldBe originalValue.truncatedTo(MILLIS) // We lose nanosecond parts when encoding to epoch millis
         },
      )

      include(generateSerializerTests(InstantAsStringSerializer, Arb.instant(), { "Encodes Instant as String and back again" }))

      "samples" {
         Json.encodeToString(InstantAsLongSerializer, Instant.ofEpochMilli(1666528056)) shouldBe "1666528056"
         Json.encodeToString(InstantAsStringSerializer, Instant.ofEpochMilli(1666528056)) shouldBe "\"1970-01-20T06:55:28.056Z\""
      }
   },
)
