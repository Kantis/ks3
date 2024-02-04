package io.ks3.java.math

import io.kotest.core.spec.style.FunSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.bigInt
import io.ks3.test.generateSerializerTests
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
class BigIntegerSerializerTests : FunSpec(
   {

      include(
         generateSerializerTests(
            BigIntegerAsStringSerializer,
            Arb.bigInt(maxNumBits = 4096),
            { "BigIntegerAsStringSerializer performs round-trip serialization" },
         ),
      )

      include(
         generateSerializerTests(
            BigIntegerAsJsonLiteralSerializer,
            Arb.bigInt(maxNumBits = 4096),
            { "BigIntegerAsJsonLiteralSerializer performs round-trip serialization" },
         ),
      )
   },
)
