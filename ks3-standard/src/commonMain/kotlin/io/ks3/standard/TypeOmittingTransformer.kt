package io.ks3.standard

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer

/**
 * A serializer that omits the type discriminator when serializing with polymorphic serialization.
 *
 * This is useful when you want to serialize a sealed class hierarchy to JSON without including the type discriminator.
 *
 * ⚠️Warning: Using a custom class discriminator will make this transformer silently fail‼️
 *
 * ⚠️Warning: JSON produced by this serializer cannot be deserialized back using the default serializer.
 */
inline fun <reified T : Any> typeOmittingTransformer() =
   object : JsonTransformingSerializer<T>(serializer()) {
      override fun transformSerialize(element: JsonElement): JsonElement =
         when (element) {
            is JsonObject ->
               buildJsonObject {
                  element.jsonObject.forEach { (key, value) ->
                     if (key != "type") put(key, value)
                  }
               }

            else -> element
         }
   }
