package io.ks3.java.util

import io.kotest.core.spec.style.FunSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.UUIDVersion
import io.kotest.property.arbitrary.uuid
import io.ks3.test.generateSerializerTests

class UuidSerializerTests : FunSpec(
   {
      UUIDVersion.values().forEach { uuidVersion ->
         include(
             generateSerializerTests(
                 UuidSerializer,
                 Arb.uuid(uuidVersion),
                 nameFn = { "Encode and decode UUID${uuidVersion.name}" })
         )
      }
   },
)
