package io.ks3.java.time

import io.kotest.core.spec.style.FreeSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.localTime
import io.ks3.test.generateSerializerTests

class LocalTimeAsStringSerializerTests: FreeSpec(
   {
      include(generateSerializerTests(LocalTimeAsStringSerializer, Arb.localTime()))
   }
)
