package io.ks3.java.util

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.map
import io.kotest.property.arbitrary.of
import io.ks3.test.generateSerializerTests
import kotlinx.serialization.json.Json
import java.util.Currency

class CurrencySerializerTest :
   FreeSpec(
      {
         include(
            generateSerializerTests(
               CurrencySerializer,
               Arb.of(Currency.getAvailableCurrencies()).map { it },
            ),
         )
         "sample" {
            Json.encodeToString(CurrencySerializer, Currency.getInstance("AUD")) shouldBe "\"AUD\""
         }
      },
   )
