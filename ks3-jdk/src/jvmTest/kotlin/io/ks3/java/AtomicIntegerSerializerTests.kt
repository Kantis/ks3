package io.ks3.java

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.property.Arb
import io.kotest.property.Exhaustive
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.map
import io.kotest.property.checkAll
import io.kotest.property.exhaustive.cartesianPairs
import io.kotest.property.exhaustive.exhaustive
import io.ks3.test.generateEncoders
import io.ks3.test.generateSerializerTests
import kotlinx.serialization.json.Json
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class AtomicIntegerSerializerTests : FunSpec(
   {
      include(
         generateSerializerTests(AtomicIntegerSerializer, Arb.int().map(::AtomicInteger)) { originalValue ->
            this
               .shouldBeInstanceOf<AtomicInteger>()
               .get() shouldBe originalValue.get()
         }
      )
   },
)
