package io.ks3.standard

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.serializer

/**
 * Creates a serializer that will parse a JSON property into a [T] element, or `null` if the element fails to parse.
 */
inline fun <reified T> lenientJsonPropertyDeserializer(explicitElementSerializer: KSerializer<T>? = null) =
   object : KSerializer<T?> {
      private val elementSerializer = explicitElementSerializer ?: serializer<T>()
      override val descriptor: SerialDescriptor = elementSerializer.descriptor

      override fun deserialize(decoder: Decoder): T? {
         require(decoder is JsonDecoder) { "This deserializer can only be used with Json" }
         val json = decoder.json
         val item = decoder.decodeJsonElement()
         return try {
            json.decodeFromJsonElement(elementSerializer, item)
         } catch (e: SerializationException) {
            null
         } catch (e: IllegalArgumentException) {
            null
         }
      }

      override fun serialize(
         encoder: Encoder,
         value: T?,
      ): Unit = error("Can only be used for deserialization")
   }
