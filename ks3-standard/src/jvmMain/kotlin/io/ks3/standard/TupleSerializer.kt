package io.ks3.standard

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.serializer
import kotlin.reflect.KProperty1

@ExperimentalSerializationApi
inline fun <reified T> tupleSerializer(vararg properties: KProperty1<T, *>): KSerializer<T> = object : KSerializer<T> {
   override val descriptor = TupleDescriptor(
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
