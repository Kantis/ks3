package io.ks3.java.time

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import java.time.LocalTime

typealias LocalTimeAsString = @Serializable(with = LocalTimeAsStringSerializer::class) LocalTime

object LocalTimeAsStringSerializer : KSerializer<LocalTime> by stringSerializer(
   LocalTimeAsStringSerializer::class.qualifiedName!!,
   LocalTime::parse,
   LocalTime::toString,
)
