package io.ks3.java.time

import io.ks3.java.stringSerializer
import kotlinx.serialization.KSerializer
import java.time.LocalDateTime

object LocalDateTimeAsStringSerializer : KSerializer<LocalDateTime> by stringSerializer(
   LocalDateTimeAsStringSerializer::class.qualifiedName!!,
   LocalDateTime::parse,
)
