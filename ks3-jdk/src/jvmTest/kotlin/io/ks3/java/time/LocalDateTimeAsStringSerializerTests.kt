package io.ks3.java.time

import io.kotest.core.spec.style.FunSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.localDateTime
import io.ks3.test.generateSerializerTests

class LocalDateTimeAsStringSerializerTests : FunSpec(
   {
      include(generateSerializerTests(LocalDateTimeAsStringSerializer, Arb.localDateTime()))
   },
)
