package io.ks3.standard

import io.ks3.core.ExperimentalKs3
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer

/**
 * Serializers that sort collections / lists / sets with the given [comparator] upon serialization.
 *
 * Example:
 * ```kotlin
 * class SortedStringListSerializer :
 *    KSerializer<List<String>> by sortedListSerializer(compareBy { it })
 *
 * @Serializable
 * data class Friends(
 *    @Serializable(SortedStringListSerializer::class)
 *    val names: List<String>
 * )
 * ```
 */
@ExperimentalKs3
inline fun <reified T> sortedCollectionSerializer(comparator: Comparator<in T>): KSerializer<Collection<T>> {
   val delegate = serializer<Collection<T>>()
   return object : KSerializer<Collection<T>> by delegate {
      override fun serialize(
         encoder: Encoder,
         value: Collection<T>,
      ) = delegate.serialize(encoder, value.sortedWith(comparator))
   }
}

@ExperimentalKs3
inline fun <reified T> sortedListSerializer(comparator: Comparator<in T>): KSerializer<List<T>> {
   val delegate = serializer<List<T>>()
   return object : KSerializer<List<T>> by delegate {
      override fun serialize(
         encoder: Encoder,
         value: List<T>,
      ) = delegate.serialize(encoder, value.sortedWith(comparator))
   }
}

@ExperimentalKs3
inline fun <reified T> sortedSetSerializer(comparator: Comparator<in T>): KSerializer<Set<T>> {
   val delegate = serializer<Set<T>>()
   return object : KSerializer<Set<T>> by delegate {
      override fun serialize(
         encoder: Encoder,
         value: Set<T>,
      ) = delegate.serialize(encoder, value.sortedWith(comparator).toSet())
   }
}
