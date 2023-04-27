package io.ks3.standard

import io.ks3.core.Ks3Internal
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.serializer
import kotlin.reflect.KProperty1

@ExperimentalSerializationApi
inline fun <reified T> tupleSerializer(vararg properties: KProperty1<T, *>): KSerializer<T> = object : KSerializer<T> {
   override val descriptor = ListLikeDescriptor(
      "io.ks3.Tuple(${T::class.qualifiedName})",
      *properties.map { serializer(it.returnType).descriptor }.toTypedArray(),
   )

   override fun deserialize(decoder: Decoder): T {
      val matchingConstructor = T::class.constructors.find {
         it.parameters.size == properties.size && it.parameters.zip(properties).all { (param, prop) -> param.type == prop.returnType }
      }

      if (matchingConstructor != null) {
         return decoder.decodeStructure(descriptor) {
            val args = mutableListOf<Any?>()
            while (true) {
               val index = decodeElementIndex(descriptor)
               if (index == -1) break
               decodeSerializableElement(descriptor, index, serializer(properties[index].returnType)).let { args.add(it) }
            }
            matchingConstructor.call(*args.toTypedArray())
         }
      } else {
         error("No matching constructor for the parameter list!")
      }
   }

   override fun serialize(encoder: Encoder, value: T) {
      encoder.encodeStructure(descriptor) {
         for (i in properties.indices) {
            encodeSerializableElement(descriptor, i, serializer(properties[i].returnType), properties[i].get(value))
         }
      }
   }
}


@Ks3Internal
@ExperimentalSerializationApi
class ListLikeDescriptor(override val serialName: String, vararg val elementDescriptors: SerialDescriptor) : SerialDescriptor {
   override val kind: SerialKind get() = StructureKind.LIST
   override val elementsCount: Int = elementDescriptors.size

   override fun getElementName(index: Int): String = index.toString()
   override fun getElementIndex(name: String): Int =
      name.toIntOrNull() ?: throw IllegalArgumentException("$name is not a valid list index")

   override fun isElementOptional(index: Int): Boolean {
      require(index >= 0) { "Illegal index $index, $serialName expects only non-negative indices" }
      return false
   }

   override fun getElementAnnotations(index: Int): List<Annotation> {
      require(index >= 0) { "Illegal index $index, $serialName expects only non-negative indices" }
      return emptyList()
   }

   override fun getElementDescriptor(index: Int): SerialDescriptor {
      require(index >= 0) { "Illegal index $index, $serialName expects only non-negative indices" }
      return elementDescriptors[index]
   }

   override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other !is ListLikeDescriptor) return false
      return elementDescriptors.contentEquals(other.elementDescriptors) && serialName == other.serialName
   }

   override fun hashCode(): Int {
      return elementDescriptors.hashCode() * 31 + serialName.hashCode()
   }

   override fun toString(): String = "$serialName(${elementDescriptors.joinToString()})"
}
