package io.ks3.standard

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

inline fun <reified T> resilientListSerializer(explicitElementSerializer: KSerializer<T>? = null) =
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
