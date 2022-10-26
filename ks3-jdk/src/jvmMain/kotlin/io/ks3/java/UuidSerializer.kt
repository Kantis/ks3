package io.ks3.java

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.UUID

public object UuidSerializer : KSerializer<UUID> {
   override fun serialize(encoder: Encoder, value: UUID) {
      encoder.encodeString(value.toString())
   }

   override fun deserialize(decoder: Decoder): UUID = UUID.fromString(decoder.decodeString())

   override val descriptor = PrimitiveSerialDescriptor(UuidSerializer::class.qualifiedName!!, PrimitiveKind.STRING)
}
