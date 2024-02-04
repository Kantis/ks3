package io.ks3.standard

import kotlinx.serialization.KSerializer
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
         if (decoder !is JsonDecoder) error("This deserializer can only be used with Json")
         val json = decoder.json
         val list = decoder.decodeJsonElement()
         return list
            .jsonArray
            .mapNotNull {
               runCatching {
                  json.decodeFromJsonElement(elementSerializer, it)
               }.getOrNull()
            }
      }

      override fun serialize(
         encoder: Encoder,
         value: List<T>,
      ) = serializer.serialize(encoder, value)
   }
