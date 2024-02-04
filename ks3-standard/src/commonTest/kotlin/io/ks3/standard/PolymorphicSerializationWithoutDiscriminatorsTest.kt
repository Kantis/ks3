package io.ks3.standard

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class PolymorphicSerializationWithoutDiscriminatorsTest : DescribeSpec(
   {
      val fooWithoutTypeSerializer = typeOmittingTransformer<Foo>()
      val json = Json

      describe("PolymorphicSerializationWithoutDiscriminatorsTest") {
         it("should do something") {
            json.encodeToString(
               ListSerializer(fooWithoutTypeSerializer),
               listOf(Foo.Bar("bar"), Foo.Baz(1)),
            ) shouldBe """[{"bar":"bar"},{"baz":1}]"""
         }
      }
   },
) {
   @Serializable
   sealed class Foo {
      @Serializable
      data class Bar(val bar: String) : Foo()

      @Serializable
      data class Baz(val baz: Int) : Foo()
   }
}
