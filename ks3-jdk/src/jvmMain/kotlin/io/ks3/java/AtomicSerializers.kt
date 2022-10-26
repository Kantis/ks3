package io.ks3.java

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

// From https://github.com/Kotlin/kotlinx.serialization/blob/15c6b59d2175485f8f5f4563ff17610003459113/runtime/jvm/src/main/kotlin/kotlinx/serialization/java/AtomicSerializers.kt

public object AtomicIntegerSerializer : KSerializer<AtomicInteger> {
   override fun serialize(encoder: Encoder, value: AtomicInteger) {
      encoder.encodeInt(value.get())
   }

   override fun deserialize(decoder: Decoder): AtomicInteger = AtomicInteger(decoder.decodeInt())

   override val descriptor = PrimitiveSerialDescriptor(AtomicIntegerSerializer::class.qualifiedName!!, PrimitiveKind.INT)
}

public object AtomicLongSerializer : KSerializer<AtomicLong> {
   override fun serialize(encoder: Encoder, value: AtomicLong) {
      encoder.encodeLong(value.get())
   }

   override fun deserialize(decoder: Decoder): AtomicLong = AtomicLong(decoder.decodeLong())

   override val descriptor = PrimitiveSerialDescriptor(AtomicLongSerializer::class.qualifiedName!!, PrimitiveKind.LONG)
}

public object AtomicBooleanSerializer : KSerializer<AtomicBoolean> {
   override fun serialize(encoder: Encoder, value: AtomicBoolean) {
      encoder.encodeBoolean(value.get())
   }

   override fun deserialize(decoder: Decoder): AtomicBoolean = AtomicBoolean(decoder.decodeBoolean())

   override val descriptor = PrimitiveSerialDescriptor(AtomicBooleanSerializer::class.qualifiedName!!, PrimitiveKind.BOOLEAN)
}
