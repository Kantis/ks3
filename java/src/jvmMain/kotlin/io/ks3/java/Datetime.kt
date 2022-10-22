package io.ks3.java

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.LocalDateTime

abstract class SimpleStringSerializer<T>(
   private val parser: (String) -> T,
   private val stringify: (T) -> String = { it.toString() },
) : KSerializer<T> {

   override fun deserialize(decoder: Decoder): T = parser(decoder.decodeString())

   override val descriptor = PrimitiveSerialDescriptor("SimpleStringSerializer", PrimitiveKind.STRING)

   override fun serialize(encoder: Encoder, value: T) = encoder.encodeString(stringify(value))
}

class LocalDateAsStringSerializer : SimpleStringSerializer<LocalDate>(
   { if (it.endsWith("T00:00:00")) LocalDate.parse(it.split("T")[0]) else LocalDate.parse(it) },
)

class LocalDateTimeAsStringSerializer : SimpleStringSerializer<LocalDateTime>(
   { LocalDateTime.parse(it) },
)
