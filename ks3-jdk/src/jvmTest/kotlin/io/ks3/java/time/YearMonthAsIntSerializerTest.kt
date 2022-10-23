package io.ks3.java.time

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json
import java.time.YearMonth

class YearMonthAsIntSerializerTest : FunSpec(
   {
      val format = Json

      fun YearMonth.encode() = format.encodeToString(YearMonthAsIntSerializer, this)
      fun String.decode() = format.decodeFromString(YearMonthAsIntSerializer, this)

      context("normal year") {
         test("Encode") {
            YearMonth.of(2022, 10).encode() shouldBe "202210"
         }

         test("Decode") {
            "202210".decode() shouldBe YearMonth.of(2022, 10)
         }
      }

      context("Negative year") {
         val yearMonth = YearMonth.of(-8, 12)

         test("Encode") {
            yearMonth.encode() shouldBe "-812"
         }

         test("Decode") {
            "-812".decode() shouldBe yearMonth
         }
      }

      context("Year zero") {
         val yearMonth = YearMonth.of(0, 12)

         test("Encode") {
            yearMonth.encode() shouldBe "12"
         }

         test("Decode") {
            "12".decode() shouldBe yearMonth
         }
      }
   },
)
