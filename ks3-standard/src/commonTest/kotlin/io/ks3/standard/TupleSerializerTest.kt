package io.ks3.standard

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.json.Json

class TupleSerializerTest : FunSpec(
   {
      val firstElement = PrimitiveSerialDescriptor("tuple1", PrimitiveKind.INT)
      val secondElement = PrimitiveSerialDescriptor("tuple2", PrimitiveKind.STRING)
      test("tuple serialization") {
         Json.encodeToString(DummySerializer(firstElement, secondElement), Dummy(5, "hello")) shouldBe "[5,\"hello\"]"
      }

      test("Tuple deserialization") {
         Json.decodeFromString(DummySerializer(firstElement, secondElement), "[5, \"hello\"]") shouldBe Dummy(5, "hello")
      }
   }
)
