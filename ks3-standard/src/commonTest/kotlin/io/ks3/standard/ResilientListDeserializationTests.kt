package io.ks3.standard

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class ResilientListDeserializationTests : StringSpec(
   {
      val json = Json

      "Single bad element is discarded" {
         json.decodeFromString(resilientListSerializer<Int>(), "[1,2,3, \"foo\",5]") shouldBe listOf(1, 2, 3, 5)
      }

      "Entire list can be discarded" {
         json.decodeFromString(resilientListSerializer<Int>(), "[\"foo\",\"bar\"]") shouldBe listOf()
      }

      "Should work with nested lists" {
         json.decodeFromString(
            // We need to pass the inner serializer so resilience will be applied within the nested list deserialization
            resilientListSerializer<List<Int>>(
               resilientListSerializer<Int>(),
            ),
            "[[1,2,3], [4,\"foo\",6], [7,8,9]]",
         ) shouldBe
            listOf(
               listOf(1, 2, 3),
               listOf(4, 6),
               listOf(7, 8, 9),
            )
      }

      "Should omit data classes when they fail to deserialize" {
         @Serializable
         data class Foo(
            val bar: String,
            val baz: Int,
         )

         json.decodeFromString(
            resilientListSerializer<Foo>(),
            """
            [
               {"bar": "bar", "baz":1},
               {"bar": "bar"},
               {"bar": "bar", "baz": "x"}
            ]
            """.trimIndent(),
         ) shouldBe listOf(Foo("bar", 1))
      }
   },
)
