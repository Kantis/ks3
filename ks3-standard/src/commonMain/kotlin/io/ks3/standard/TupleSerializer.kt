package io.ks3.standard

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

data class Dummy(val x: Int, val s: String)

class DummySerializer(vararg val elementDescriptors: SerialDescriptor) : KSerializer<Dummy> {
   override val descriptor = TupleDescriptor(*elementDescriptors)

   override fun deserialize(decoder: Decoder): Dummy =
      decoder
         .beginStructure(descriptor)
         .run {
            Dummy(
               decodeIntElement(elementDescriptors[0], 0),
               decodeStringElement(elementDescriptors[1], 1),
            ).also {
               endStructure(descriptor)
            }
         }

   override fun serialize(encoder: Encoder, value: Dummy) {
      encoder.beginCollection(descriptor, elementDescriptors.size).apply {
         encodeIntElement(elementDescriptors.first(), 0, value.x)
         encodeStringElement(elementDescriptors[1], 1, value.s)
         endStructure(descriptor)
      }

   }
}

@ExperimentalSerializationApi
class TupleDescriptor(vararg val elemDescriptors: SerialDescriptor) : SerialDescriptor {
   override val kind: SerialKind get() = StructureKind.LIST
   override val elementsCount: Int = elemDescriptors.size
   override val serialName = "Tuple"

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
      return elemDescriptors[index]
   }

   override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other !is TupleDescriptor) return false
      if (elemDescriptors.contentEquals(other.elemDescriptors) && serialName == other.serialName) return true
      return false
   }
}
