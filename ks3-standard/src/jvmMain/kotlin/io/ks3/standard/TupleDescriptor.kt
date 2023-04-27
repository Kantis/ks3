package io.ks3.standard

import io.ks3.core.Ks3Internal
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.StructureKind

@Ks3Internal
@ExperimentalSerializationApi
class TupleDescriptor(override val serialName: String, vararg val elementDescriptors: SerialDescriptor) :
    SerialDescriptor {
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
      if (other !is TupleDescriptor) return false
      return elementDescriptors.contentEquals(other.elementDescriptors) && serialName == other.serialName
   }

   override fun hashCode(): Int {
      return elementDescriptors.hashCode() * 31 + serialName.hashCode()
   }

   override fun toString(): String = "$serialName(${elementDescriptors.joinToString()})"
}
