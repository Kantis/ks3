package io.ks3.standard.base64

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

typealias ByteArrayAsBase64String =
   @Serializable(with = ByteArrayAsBase64StringSerializer::class)
   ByteArray

@OptIn(ExperimentalEncodingApi::class)
object ByteArrayAsBase64StringSerializer : KSerializer<ByteArray> {
   override val descriptor: SerialDescriptor =
      PrimitiveSerialDescriptor(
         "io.ks3.standard.base64.ByteArrayAsBase64StringSerializer",
         PrimitiveKind.STRING,
      )

   override fun deserialize(decoder: Decoder): ByteArray {
      return Base64.decode(decoder.decodeString())
   }

   override fun serialize(
      encoder: Encoder,
      value: ByteArray,
   ) {
      encoder.encodeString(Base64.encode(value))
   }
}
