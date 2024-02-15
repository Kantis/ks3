package io.ks3.standard

import io.ks3.core.ExperimentalKs3
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.serializer

/**
 * Creates a serializer that will parse a JSON array into a list of [T] elements, ignoring any elements that fail to parse.
 *
 * Example:
 * ```
 * val lenientIntListSerializer = lenientJsonArraySerializer<Int>()
 * val numbers = Json.decodeFromString(lenientIntListSerializer, "[1,2,3, \"foo\",5]")
 *
 * println(numbers) // > [1, 2, 3, 5]
 * ```
 */
@ExperimentalKs3
inline fun <reified T> lenientJsonArraySerializer(explicitElementSerializer: KSerializer<T>? = null) =
   object : KSerializer<List<T>> {
      private val elementSerializer = explicitElementSerializer ?: serializer<T>()
      private val serializer: SerializationStrategy<List<T>> = ListSerializer(elementSerializer)
      override val descriptor: SerialDescriptor = serializer.descriptor

      override fun deserialize(decoder: Decoder): List<T> {
         require(decoder is JsonDecoder) { "This deserializer can only be used with Json" }
         val json = decoder.json
         val list = decoder.decodeJsonElement()
         return list
            .jsonArray
            .mapNotNull {
               try {
                  json.decodeFromJsonElement(elementSerializer, it)
               } catch (e: SerializationException) {
                  null
               } catch (e: IllegalArgumentException) {
                  null
               }
            }
      }

      override fun serialize(
         encoder: Encoder,
         value: List<T>,
      ): Unit = serializer.serialize(encoder, value)
   }
