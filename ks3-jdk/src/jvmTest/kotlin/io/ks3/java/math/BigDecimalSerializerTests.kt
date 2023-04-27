package io.ks3.java.math

import io.kotest.core.spec.style.FunSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.bigDecimal
import io.kotest.property.arbitrary.double
import io.kotest.property.arbitrary.map
import io.kotest.property.arbitrary.withEdgecases
import io.ks3.test.generateSerializerTests
import kotlinx.serialization.ExperimentalSerializationApi
import java.math.BigDecimal

@OptIn(ExperimentalSerializationApi::class)
class BigDecimalSerializerTests : FunSpec(
   {
      val someNumbers = listOf(
         "0",
         "-1E307",
         "-1E-307",
         "1.234349584529824359834295834958345892374892173498721349872398457234985798234758927349872341908273978174239823475982743982734988",
         "982137498243758927439872398472983479827345982459824375982173948721398479817223987129483745782347823461982379812739817239873450.1",
      ).map(::BigDecimal)
         .map(BigDecimal::toPlainString) // Get rid of scientific notation
         .map(::BigDecimal)

      include(
         generateSerializerTests(
            BigDecimalAsStringSerializer,
            Arb.bigDecimal().withEdgecases(someNumbers),
            { "BigDecimalAsStringSerializer performs round-trip serialization" },
         ),
      )

      include(
         generateSerializerTests(
            BigDecimalAsJsonLiteralSerializer,
            Arb.bigDecimal().withEdgecases(someNumbers),
            { "BigDecimalAsHighPrecisionNumberSerializer performs round-trip serialization" },
         ),
      )

      include(
         generateSerializerTests(
            BigDecimalAsDoubleSerializer,
            Arb.double()
               .withEdgecases(emptyList()) // get rid of NaN and Infinity
               .map { BigDecimal(it.toString()) },
            { "BigDecimalAsDoubleSerializer performs round-trip serialization" },
         ),
      )
   },
)
