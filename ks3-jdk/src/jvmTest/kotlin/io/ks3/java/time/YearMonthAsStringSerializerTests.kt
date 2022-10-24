package io.ks3.java.time

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.yearMonth
import io.kotest.property.checkAll
import io.kotest.property.exhaustive.exhaustive
import io.ks3.test.generateEncoders
import kotlinx.serialization.json.Json

class YearMonthAsStringSerializerTests : FreeSpec(
   {
      val (encoders, decoders) = Json.generateEncoders(YearMonthAsStringSerializer)

      "Encodes and decodes back to original value" {
         checkAll(Arb.yearMonth(), encoders.exhaustive(), decoders.exhaustive()) { yearMonth, encode, decode ->
            yearMonth.encode().decode() shouldBe yearMonth
         }
      }
   },
)
