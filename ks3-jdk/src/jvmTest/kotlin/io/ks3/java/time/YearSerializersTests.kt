package io.ks3.java.time

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.map
import io.ks3.test.generateSerializerTests
import kotlinx.serialization.json.Json
import java.time.Year

class YearSerializersTests : FunSpec(
   {
      val generator = Arb.int(min = -999999999, max = 999999999).map(Year::of)

      include(generateSerializerTests(YearAsIntSerializer, generator, { "Encodes Year as Int and back again" }))
      include(generateSerializerTests(YearAsStringSerializer, generator, { "Encodes Year as String and back again" }))

      test("Sample") {
         Json.encodeToString(YearAsStringSerializer, Year.of(2022)) shouldBe "\"2022\""
         Json.encodeToString(YearAsIntSerializer, Year.of(2022)) shouldBe "2022"
      }
   },
)
