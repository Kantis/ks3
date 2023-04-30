package io.ks3.standard

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.serializer
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.isSubtypeOf

/**
 * Creates a serializer that encodes select properties of [T] as a tuple.
 *
 * For deserialization to work, the following conditions must be fulfilled:
 * * There must be a constructor where only the serialized properties are required.
 *    * The constructor _can_ have other parameters, but they must have a default value.
 * * The properties must match parameters of the constructor by name and type.
 *
 *
 * Example:
 * ```kotlin
 *
 * @Serializable(with = PersonSerializer::class)
 * data class Person(val name: String, val age: Int, val profession: String)
 *
 * object PersonSerializer : KSerializer<Person> by tupleSerializer(
 *    Person::name,
 *    Person::age,
 *    Person::profession
 * )
 *
 * println(Json.encodeToString(PersonSerializer, Person("Kaylee", 21, "Mechanic")))
 * // > ["Kaylee", 21, "Mechanic"]
 * ```
 */
@ExperimentalSerializationApi
inline fun <reified T> tupleSerializer(vararg properties: KProperty1<T, *>): KSerializer<T> = object : KSerializer<T> {
   override val descriptor = TupleDescriptor(
      "io.ks3.Tuple(${T::class.qualifiedName})",
      *properties.map { serializer(it.returnType).descriptor }.toTypedArray(),
   )

   private val constructorPropertyMap = T::class.constructors
      .mapNotNull { constructor ->
         val mapping = constructor.parameters
            .filterNot(KParameter::isOptional)
            .associateWith { param ->
               properties.singleOrNull { prop ->
                  prop.returnType.isSubtypeOf(param.type) && prop.name == param.name
               }
            }

         if (mapping.any { (_, key) -> key == null }) null
         else constructor to mapping
      }
      .single()

   override fun deserialize(decoder: Decoder): T {
      return decoder.decodeStructure(descriptor) {
         val args = mutableListOf<Any?>()
         while (true) {
            val index = decodeElementIndex(descriptor)
            if (index == -1) break
            decodeSerializableElement(descriptor, index, serializer(properties[index].returnType)).let { args.add(it) }
         }

         val mappedArgs = constructorPropertyMap.second.mapValues { (_, prop) -> args[properties.indexOf(prop)] }

         constructorPropertyMap.first.callBy(mappedArgs)
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
