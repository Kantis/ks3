package io.ks3.java.time

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.localDate
import io.kotest.property.checkAll
import io.kotest.property.exhaustive.exhaustive
import io.ks3.test.generateEncoders
import io.ks3.test.generateSerializerTests
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.format.DateTimeParseException

class LocalDateAsStringSerializerTests : FunSpec(
   {
      val format = Json
      val (_, decoders) = format.generateEncoders(LocalDateAsStringSerializer)

      include(generateSerializerTests(LocalDateAsStringSerializer, Arb.localDate()))

      test("handles timestamps with time included if, and only if, it contains no information") {
         format.decodeFromString<Sample>(
            """
               {
                 "date": "2021-01-01T00:00:00"
               }
            """.trimIndent(),
         ) shouldBe Sample(LocalDate.of(2021, 1, 1))
      }

      test("timestamps cause error") {
         checkAll(decoders.exhaustive()) { decode ->
            shouldThrow<DateTimeParseException> {
               "\"2021-01-01T12:30:45.000Z\"".decode()
            }
         }
      }

      test("Just date is fine as well") {
         checkAll(decoders.exhaustive()) { decode ->
            "\"2021-01-01\"".decode() shouldBe LocalDate.of(2021, 1, 1)
         }
      }
   },
)

@Serializable
private data class Sample(
   @Serializable(with = LocalDateAsStringSerializer::class)
   val date: LocalDate? = null,
)
