package io.ks3.java.time

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.yearMonth
import io.ks3.java.typealiases.YearMonthAsString
import io.ks3.test.generateSerializerTests
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.time.YearMonth

class YearMonthAsStringSerializerTests : FreeSpec(
   {
      include(generateSerializerTests(YearMonthAsStringSerializer, Arb.yearMonth()))

      "sample" {
         Json.encodeToString(YearMonthAsStringSerializer, YearMonth.of(2022, 10)) shouldBe "\"2022-10\""
      }

      "nullability with typealias" {
         @Serializable
         data class Appointment(
            val yearMonth: YearMonthAsString?,
         )

         Json.decodeFromString<Appointment>("""{ "yearMonth": null }""") shouldBe Appointment(null)
         Json.decodeFromString<Appointment>("""{ "yearMonth": "2022-10" }""") shouldBe Appointment(YearMonth.of(2022, 10))
      }
   },
)
