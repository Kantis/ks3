package io.ks3.java.util

import io.kotest.core.spec.style.FunSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.locale
import io.kotest.property.arbitrary.map
import io.ks3.test.generateSerializerTests
import java.util.Locale

class LocaleSerializerTests : FunSpec(
   {
      include(
         generateSerializerTests(
            LocaleSerializer,
            Arb.locale().map { Locale(it) },
         ),
      )
   },
)
