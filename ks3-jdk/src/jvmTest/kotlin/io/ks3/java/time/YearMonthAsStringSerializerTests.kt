package io.ks3.java.time

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.yearMonth
import io.kotest.property.checkAll
import io.kotest.property.exhaustive.exhaustive
import io.ks3.test.generateEncoders
import io.ks3.test.generateSerializerTests
import kotlinx.serialization.json.Json

class YearMonthAsStringSerializerTests : FreeSpec(
   {
      include(generateSerializerTests(YearMonthAsStringSerializer, Arb.yearMonth()))
   },
)
