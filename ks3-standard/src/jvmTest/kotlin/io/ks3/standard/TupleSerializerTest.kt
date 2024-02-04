package io.ks3.standard

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable(with = PointAsTupleSerializer::class)
data class Point(val x: Int, val y: Int)

@OptIn(ExperimentalSerializationApi::class)
object PointAsTupleSerializer : KSerializer<Point> by tupleSerializer(Point::x, Point::y)

@Serializable(with = PersonAsTupleSerializer::class)
data class Person(val name: String, val age: Int, val origin: Point)

@OptIn(ExperimentalSerializationApi::class)
object PersonAsTupleSerializer : KSerializer<Person> by tupleSerializer(Person::name, Person::age, Person::origin)

@Serializable(with = CoordinatesDetailsSerializer::class)
data class CoordinatesDetails(
   val x: Int,
   val y: Int,
   private val colour: String,
   private val active: Boolean,
) {
   // Required for decoding partially encoded tuple
   constructor(x: Int, y: Int) : this(x, y, "red", true)
}

object CoordinatesDetailsSerializer : KSerializer<CoordinatesDetails> by tupleSerializer(CoordinatesDetails::x, CoordinatesDetails::y)

@Serializable(with = CoordinatesDetailsWithDefaultsSerializer::class)
data class CoordinatesDetailsWithDefaults(
   val x: Int,
   val y: Int,
   val colour: String = "red",
   val active: Boolean = true,
)

object CoordinatesDetailsWithDefaultsSerializer :
   KSerializer<CoordinatesDetailsWithDefaults> by tupleSerializer(CoordinatesDetailsWithDefaults::x, CoordinatesDetailsWithDefaults::y)

class TupleSerializerTest : FreeSpec(
   {
      val format = Json

      "should serialize" {
         format.encodeToString(Point(1, 2)) shouldEqualJson "[1,2]"
      }

      "should deserialize" {
         format.decodeFromString<Point>("[1,2]") shouldBe Point(1, 2)
      }

      "Tuple in tuple" - {
         val person = Person("Emil", 35, Point(1, 2))
         val serialForm = """["Emil", 35, [1,2]]"""

         "should serialize" {
            format.encodeToString(person) shouldEqualJson serialForm
         }

         "should deserialize" {
            format.decodeFromString<Person>(serialForm) shouldBe person
         }
      }

      "Partially encoded tuple can be decoded using matching constructor" {
         format.encodeToString(CoordinatesDetails(1, 2, "red", true)) shouldEqualJson "[1,2]"
         format.decodeFromString<CoordinatesDetails>("[1,2]") shouldBe CoordinatesDetails(1, 2, "red", true)
      }

      "Partially encoded tuple can be decoded using default values" {
         format.encodeToString(CoordinatesDetailsWithDefaults(1, 2, "red", true)) shouldEqualJson "[1,2]"
         format.decodeFromString<CoordinatesDetailsWithDefaults>("[1,2]") shouldBe CoordinatesDetailsWithDefaults(1, 2, "red", true)
      }
   },
)
