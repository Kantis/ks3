package io.ks3.java

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.property.Exhaustive
import io.kotest.property.exhaustive.boolean
import io.kotest.property.exhaustive.map
import io.ks3.test.generateSerializerTests
import java.util.concurrent.atomic.AtomicBoolean

class AtomicBooleanSerializerTests : FunSpec(
   {
      include(
         generateSerializerTests(AtomicBooleanSerializer, Exhaustive.boolean().map(::AtomicBoolean)) { originalValue ->
            this
               .shouldBeInstanceOf<AtomicBoolean>()
               .get() shouldBe originalValue.get()
         },
      )
   },
)
