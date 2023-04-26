package io.ks3.standard

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable(with = PointAsTupleSerializer::class)
data class Point(val x: Int, val y: Int)
object PointAsTupleSerializer : KSerializer<Point> by tupleSerializer(Point::x, Point::y)

@Serializable(with = PersonAsTupleSerializer::class)
data class Person(val name: String, val age: Int, val origin: Point)
object PersonAsTupleSerializer : KSerializer<Person> by tupleSerializer(Person::name, Person::age, Person::origin)


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
   },
)

