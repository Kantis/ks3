package io.ks3.standard

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ks3.core.ExperimentalKs3
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@OptIn(ExperimentalKs3::class)
class SortedCollectionSerializerTest : StringSpec(
   {
      val json = Json

      "A list of strings can be serialized by natural ordering" {
         json.encodeToString(
            sortedListSerializer<String>(
               compareBy { it },
            ),
            listOf("c", "b", "a"),
         ) shouldBe
            """
            ["a","b","c"]
            """.trimIndent()
      }

      "A list of non-comparable objects can be serialized by custom ordering" {
         @Serializable
         data class Friend(val name: String, val age: Int)

         val friends =
            listOf(
               Friend("Alice", 20),
               Friend("Bob", 30),
               Friend("Charles", 40),
            )

         json.encodeToString(sortedListSerializer<Friend>(compareByDescending { it.age }), friends) shouldBe
            """
            [{"name":"Charles","age":40},{"name":"Bob","age":30},{"name":"Alice","age":20}]
            """.trimIndent()
      }
   },
)
