package io.ks3.java

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.property.Arb
import io.kotest.property.Exhaustive
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.long
import io.kotest.property.arbitrary.map
import io.kotest.property.exhaustive.boolean
import io.kotest.property.exhaustive.map
import io.ks3.test.generateSerializerTests
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

class AtomicSerializersTests : FunSpec(
   {
      include(
         generateSerializerTests(AtomicBooleanSerializer, Exhaustive.boolean().map(::AtomicBoolean)) { originalValue ->
            this
               .shouldBeInstanceOf<AtomicBoolean>()
               .get() shouldBe originalValue.get()
         },
      )

      include(
         generateSerializerTests(AtomicIntegerSerializer, Arb.int().map(::AtomicInteger)) { originalValue ->
            this
               .shouldBeInstanceOf<AtomicInteger>()
               .get() shouldBe originalValue.get()
         },
      )

      include(
         generateSerializerTests(AtomicLongSerializer, Arb.long().map(::AtomicLong)) { originalValue ->
            this
               .shouldBeInstanceOf<AtomicLong>()
               .get() shouldBe originalValue.get()
         },
      )
   },
)
