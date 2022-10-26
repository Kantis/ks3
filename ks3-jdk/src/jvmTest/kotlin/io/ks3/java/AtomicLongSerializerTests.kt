package io.ks3.java

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.property.Arb
import io.kotest.property.arbitrary.long
import io.kotest.property.arbitrary.map
import io.ks3.test.generateSerializerTests
import java.util.concurrent.atomic.AtomicLong

class AtomicLongSerializerTests : FunSpec(
   {
      include(
         generateSerializerTests(AtomicLongSerializer, Arb.long().map(::AtomicLong)) { originalValue ->
            this
               .shouldBeInstanceOf<AtomicLong>()
               .get() shouldBe originalValue.get()
         },
      )
   },
)
