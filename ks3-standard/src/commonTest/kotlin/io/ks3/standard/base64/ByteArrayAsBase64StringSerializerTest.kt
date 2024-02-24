package io.ks3.standard.base64

import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json

class ByteArrayAsBase64StringSerializerTest : FreeSpec(
   {
      val format = Json

      fun ByteArray.serializeBase64() = format.encodeToString(ByteArrayAsBase64StringSerializer, this)

      fun String.deserializeBase64() = format.decodeFromString(ByteArrayAsBase64StringSerializer, this)

      "Encode to base64 string" {
         "test".encodeToByteArray().serializeBase64() shouldBe "\"dGVzdA==\""
      }

      "Decode from base64 string" {
         "\"dGVzdA==\"".deserializeBase64() shouldBe "test".encodeToByteArray()
      }

      "Non-base64 content" {
         shouldThrowWithMessage<IllegalArgumentException>("Invalid symbol '['(133) at index 0") {
            "\"[][]\"".deserializeBase64()
         }
      }

      "Invalid base64" {
         shouldThrowWithMessage<IllegalArgumentException>("The last unit of input does not have enough bits") {
            "\"A===\"".deserializeBase64()
         }
      }
   },
)
