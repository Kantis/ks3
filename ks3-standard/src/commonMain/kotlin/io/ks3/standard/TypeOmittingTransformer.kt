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
 * @param classDiscriminatorProperty The name of the property that is used as the type discriminator. Defaults to "type".
 */
inline fun <reified T : Any> typeOmittingTransformer(classDiscriminatorProperty: String = "type") =
   object : JsonTransformingSerializer<T>(serializer()) {
      override fun transformSerialize(element: JsonElement): JsonElement =
         when (element) {
            is JsonObject ->
               buildJsonObject {
                  element.jsonObject.forEach { (key, value) ->
                     if (key != classDiscriminatorProperty) put(key, value)
                  }
               }

            else -> element
         }
   }
