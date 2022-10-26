package io.ks3.java.time

import io.ks3.java.stringSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

typealias LocalDateTimeAsString = @Serializable(with = LocalDateTimeAsStringSerializer::class) LocalDateTime

object LocalDateTimeAsStringSerializer : KSerializer<LocalDateTime> by stringSerializer(
   LocalDateTimeAsStringSerializer::class.qualifiedName!!,
   LocalDateTime::parse,
)
