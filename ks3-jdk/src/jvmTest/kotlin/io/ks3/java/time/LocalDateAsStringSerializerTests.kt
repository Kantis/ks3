package io.ks3.java.time

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.format.DateTimeParseException

class LocalDateAsStringSerializerTests : FunSpec(
   {
      val json = Json {
         ignoreUnknownKeys = true
         coerceInputValues = true
      }

      test("handles timestamps with time included if it contains no information") {
         json.decodeFromString<Sample>(
            """
               {
                 "date": "2021-01-01T00:00:00"
               }
            """.trimIndent(),
         ) shouldBe Sample(LocalDate.of(2021, 1, 1))
      }

      test("timestamps cause error") {
         shouldThrow<DateTimeParseException> {
            json.decodeFromString(LocalDateAsStringSerializer(), "\"2021-01-01T12:30:45.000Z\"")
         }
      }

      test("Just date is fine as well") {
         json.decodeFromString(LocalDateAsStringSerializer(), "\"2021-01-01\"") shouldBe LocalDate.of(2021, 1, 1)
      }
   },
)

@Serializable
private data class Sample(
   @Serializable(with = LocalDateAsStringSerializer::class)
   val date: LocalDate? = null,
)
