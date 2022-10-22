package io.ks3.standard.base64

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json
import kotlin.text.Charsets.UTF_8

class ByteArrayAsBase64StringSerializerTest : FreeSpec(
   {
      val format = Json

      fun ByteArray.serializeBase64() = format.encodeToString(ByteArrayAsBase64StringSerializer, this)
      fun String.deserializeBase64() = format.decodeFromString(ByteArrayAsBase64StringSerializer, this)

      "Encode to base64 string" {
         "test".toByteArray(UTF_8).serializeBase64() shouldBe "\"dGVzdA==\""
      }

      "Decode from base64 string" {
         "\"dGVzdA==\"".deserializeBase64() shouldBe "test".toByteArray(UTF_8)
      }

      "Non-base64 content" {
         shouldThrow<Base64DecodingException> {
            "\"[][]\"".deserializeBase64()
         }.message shouldBe "Invalid base64 character: '[', must be one of ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
      }

      "Invalid base64" {
         shouldThrow<Base64DecodingException> {
            "\"A===\"".deserializeBase64()
         }.message shouldBe "Invalid Base64 input: A==="
      }
   },
)
