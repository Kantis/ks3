package io.ks3.test

import io.kotest.core.spec.style.funSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Exhaustive
import io.kotest.property.Gen
import io.kotest.property.checkAll
import io.kotest.property.exhaustive.cartesianPairs
import io.kotest.property.exhaustive.exhaustive
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

inline fun <reified T> generateSerializerTests(
   serializer: KSerializer<T>,
   generator: Gen<T>,
   crossinline nameFn: () -> String = { "Encodes and decodes values back to original form" },
   crossinline assertion: T.(T) -> Unit = { original -> this shouldBe original },
) = funSpec {

   val (encoders, decoders) = Json.generateEncoders(serializer)

   test(nameFn()) {
      checkAll(generator, Exhaustive.cartesianPairs(encoders.exhaustive(), decoders.exhaustive())) { value, (encode, decode) ->
         value.encode().decode().assertion(value)
      }
   }
}
