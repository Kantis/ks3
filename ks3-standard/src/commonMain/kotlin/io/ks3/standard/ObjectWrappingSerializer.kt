package io.ks3.standard

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer

/**
 * Wraps the inner serializer by putting the entire structure into a JSON object, keyed by [fieldName].
 *
 * Example:
 * ```kotlin
 * val serializer = objectWrappingSerializer<Person>("someFieldName")
 * data class Person(val name: String, val age: Int)
 *
 * println(Json.encodeToString(serializer, Person("John", 42)))
 * // > { "someFieldName": { "name" : "John" , "age" : 42 } }
 * ```
 *
 * Can be used to wrap a value class in a JSON object, for instance if an API requires you to send ` { "id": 42 } ` instead of just `42`.
 */
inline fun <reified T : Any> objectWrappingSerializer(fieldName: String) =
   object : JsonTransformingSerializer<T>(serializer()) {
      override fun transformSerialize(element: JsonElement): JsonElement {
         return buildJsonObject {
            put(fieldName, element)
         }
      }

      override fun transformDeserialize(element: JsonElement): JsonElement {
         return element.jsonObject[fieldName]!!
      }
   }
