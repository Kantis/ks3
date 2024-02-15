package io.ks3.standard

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class TypeOmittingTransformerTests : StringSpec(
   {
      val fooWithoutTypeSerializer = typeOmittingTransformer<Foo>()

      "should omit type when serializing" {
         Json.encodeToString(
            ListSerializer(fooWithoutTypeSerializer),
            listOf(Foo.Bar("bar"), Foo.Baz(1)),
         ) shouldBe """[{"bar":"bar"},{"baz":1}]"""
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
